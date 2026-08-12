# WishStore Backend — Núcleo Hexagonal

## Responsable

**Adry**
Módulo entregado: Dominio, Casos de Uso, Repository Ports, DTO, Mappers, Excepciones, Configuración de Beans.

---

## 1. Propósito de este módulo

Este módulo contiene el **núcleo de la aplicación** bajo Arquitectura Hexagonal (Ports & Adapters). Define **qué hace** el sistema (reglas de negocio) sin saber **cómo** se expone (HTTP) ni **cómo** se persiste (JPA/MySQL).

```
Usuario → Angular → Controller → UseCase → Domain → Repository Port → Persistence Adapter → JpaRepository → MySQL
```

Este módulo cubre desde `Controller` (exclusive) hasta `Repository Port` (inclusive). Todo lo que está a la derecha de `Repository Port` (Persistence Adapter, JpaRepository, MySQL) y a la izquierda de `UseCase` (Controller) es responsabilidad de otros integrantes del equipo.

---

## 2. Estructura del proyecto

```
com.wishstore
├── domain
│   ├── model      → Product, Wishlist, WishlistHistory, ActionType (POJOs puros, sin Spring/JPA)
│   └── ports      → ProductRepositoryPort, WishlistRepositoryPort, HistoryRepositoryPort (interfaces)
│
└── application
    ├── usecase
    │   ├── product    → GetProductsUseCase, GetProductByIdUseCase
    │   ├── wishlist    → CreateWishlistUseCase, UpdateWishlistUseCase, DeleteWishlistUseCase, GetWishlistUseCase
    │   └── history     → GetHistoryUseCase
    ├── dto        → ProductRequest/Response, WishlistRequest/Response, HistoryResponse
    ├── mapper     → ProductMapper, WishlistMapper, HistoryMapper (MapStruct)
    ├── exception  → BusinessException, ProductNotFoundException, WishlistException, OutOfStockException
    └── config     → BeanConfiguration
```

**Regla de oro:** `domain.model` no depende de ningún otro paquete de este proyecto. Es el centro absoluto de la arquitectura.

---

## 3. Tecnologías usadas en este módulo

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje base |
| Spring Boot 3.5.x (`spring-boot-starter`) | Detección de beans (`@Service`, `@Configuration`) |
| `spring-boot-starter-validation` | Bean Validation en los DTO Request |
| Lombok | Reducción de boilerplate en modelos y DTO |
| MapStruct | Mapeo dominio ↔ DTO en tiempo de compilación |
| JUnit 5 + Mockito (`spring-boot-starter-test`) | Pruebas unitarias de los Use Cases |

**No incluidas deliberadamente** (pertenecen a otras capas del equipo): Spring Data JPA, MySQL Driver, Spring Web, Swagger/OpenAPI, Spring Security.

---

## 4. Cómo compilar y probar este módulo de forma aislada

```powershell
.\mvnw.cmd clean install
```

Debe dar `BUILD SUCCESS`. Este módulo se prueba de forma aislada mediante un test de humo simple (`WishstoreBackendApplicationTests`), **no** mediante `@SpringBootTest` con carga completa de contexto, ya que los Use Cases dependen de los Repository Ports (interfaces) cuyas implementaciones aún no existen en este repositorio — eso es intencional y confirma que la Inversión de Dependencias está bien aplicada.

---

## 5. Guía de integración para el resto del equipo

### 5.1 Para quien implemente la Persistencia (JPA/MySQL)

Debes crear, en tu propio paquete (por ejemplo `com.wishstore.infrastructure.persistence`), clases anotadas con `@Repository` que **implementen** las tres interfaces de `domain.ports`:

```java
@Repository
public class ProductJpaAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository jpaRepository; // tu JpaRepository con @Entity

    public ProductJpaAdapter(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain) // tu propio mapeo Entity -> domain.model.Product
                .toList();
    }

    // ... resto de métodos del Port
}
```

**Reglas importantes:**
- Tu `@Entity` de JPA **nunca** debe cruzar hacia el dominio. Siempre conviertes entre tu `Entity` y el `domain.model.Product` dentro del Adapter.
- Debes implementar los **3 Ports completos**: `ProductRepositoryPort`, `WishlistRepositoryPort`, `HistoryRepositoryPort`.
- En cuanto tus 3 Adapters existan como beans de Spring, todos los Use Cases de este módulo arrancarán automáticamente sin ningún cambio de código aquí.

### 5.2 Para quien implemente los Controllers

Inyecta los Use Cases directamente por constructor y expón los DTO que ya existen en `application.dto`:

```java
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final CreateWishlistUseCase createWishlistUseCase;
    private final GetWishlistUseCase getWishlistUseCase;
    private final UpdateWishlistUseCase updateWishlistUseCase;
    private final DeleteWishlistUseCase deleteWishlistUseCase;

    public WishlistController(CreateWishlistUseCase createWishlistUseCase,
                               GetWishlistUseCase getWishlistUseCase,
                               UpdateWishlistUseCase updateWishlistUseCase,
                               DeleteWishlistUseCase deleteWishlistUseCase) {
        this.createWishlistUseCase = createWishlistUseCase;
        this.getWishlistUseCase = getWishlistUseCase;
        this.updateWishlistUseCase = updateWishlistUseCase;
        this.deleteWishlistUseCase = deleteWishlistUseCase;
    }

    @PostMapping
    public ResponseEntity<WishlistResponse> create(@Valid @RequestBody WishlistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createWishlistUseCase.execute(request));
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getAll() {
        return ResponseEntity.ok(getWishlistUseCase.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistResponse> update(@PathVariable Long id, @Valid @RequestBody WishlistRequest request) {
        return ResponseEntity.ok(updateWishlistUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteWishlistUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Manejo de excepciones:** debes crear un `@RestControllerAdvice` que capture `BusinessException` (y sus subtipos `ProductNotFoundException`, `OutOfStockException`, `WishlistException`) y las traduzca a respuestas HTTP apropiadas, por ejemplo:

| Excepción | Código HTTP sugerido |
|---|---|
| `ProductNotFoundException` | 404 Not Found |
| `WishlistException` | 404 Not Found o 409 Conflict, según el caso |
| `OutOfStockException` | 409 Conflict |

Este `@RestControllerAdvice` **no pertenece a este módulo** — lo crea el integrante de Controller, ya que requiere `spring-boot-starter-web`.

---

## 6. Reglas de negocio implementadas (referencia rápida)

| Caso de Uso | Reglas aplicadas |
|---|---|
| `CreateWishlistUseCase` | Valida producto existente → valida stock → si ya existe en wishlist, suma cantidad; si no, crea nuevo → guarda → registra historial |
| `UpdateWishlistUseCase` | Busca wishlist existente → valida producto → valida stock → actualiza → guarda → registra historial |
| `DeleteWishlistUseCase` | Busca wishlist existente → elimina → registra historial |
| `GetWishlistUseCase` | Lista todos los registros → resuelve producto asociado a cada uno → marca `outOfStock` si corresponde |
| `GetProductsUseCase` / `GetProductByIdUseCase` | Lista/busca productos → calcula `outOfStock` |
| `GetHistoryUseCase` | Lista el historial completo de auditoría |

---

## 7. Historial de avances de este módulo

| Avance | Rama | Commit |
|---|---|---|
| 1 | `feature/domain-model` | `feat(domain): create domain models and project structure` |
| 2 | `feature/repository-ports-dto` | `feat(domain): create repository ports and dto layer` |
| 3 | `feature/mappers-exceptions` | `feat(application): add mappers and business exceptions` |
| 4 | `feature/usecases` | `feat(application): implement business use cases` |
| 5 | `feature/domain-finalization` | `docs(core): finalize hexagonal architecture and bean configuration` |

