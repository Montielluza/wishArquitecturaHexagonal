# WishStore - Frontend (wishstore-web)

Frontend del proyecto WishStore, desarrollado en Angular 20. Consume la API REST del backend con arquitectura hexagonal desarrollado por el equipo.

## Tecnologías

- Angular 20
- TypeScript
- Angular Material
- Bootstrap 5
- RxJS
- Reactive Forms
- Docker

## Requisitos previos

- Node.js 20+
- Angular CLI 20 (`npm install -g @angular/cli@20`)
- Docker (opcional, para despliegue en contenedor)

## Instalación

```bash
npm install
```

## Ejecución en desarrollo

```bash
ng serve
```

La aplicación queda disponible en `http://localhost:4200`.

## Build de producción

```bash
ng build
```

## Ejecución con Docker

```bash
docker build -t wishstore-web .
docker run -p 4200:80 wishstore-web
```

La aplicación queda disponible en `http://localhost:8081`.

## Estructura del proyecto

```
src/app
├── core/            # Servicios, interceptores, guards
├── shared/          # Componentes y modelos compartidos
└── features/        # Módulos por funcionalidad (products, wishlist, history)
```

## Equipo

- Arquitectura, dominio y repositorio: Luz Adriana
- Persistencia: Alejo
- API REST: Jostin Acevedo
- Frontend: Juan Navarro 