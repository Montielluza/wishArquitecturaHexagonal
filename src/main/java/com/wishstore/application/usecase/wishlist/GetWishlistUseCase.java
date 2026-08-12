package com.wishstore.application.usecase.wishlist;

import com.wishstore.application.dto.ProductResponse;
import com.wishstore.application.dto.WishlistResponse;
import com.wishstore.application.mapper.ProductMapper;
import com.wishstore.application.mapper.WishlistMapper;
import com.wishstore.domain.model.Product;
import com.wishstore.domain.model.Wishlist;
import com.wishstore.domain.ports.ProductRepositoryPort;
import com.wishstore.domain.ports.WishlistRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/* Caso de uso: consultar los registros de la wishlist, con su informacion
 * de producto y disponibilidad de stock.
 * <p>Descripcion: Implementa la regla de negocio de consulta: buscar wishlist,
 * consultar productos, verificar stock y marcar los agotados.</p>
 *
 * <p>Responsabilidad: Componer la respuesta enriquecida de wishlist, uniendo
 * cada registro con los datos actuales de su producto asociado.</p>
 * <p>Dependencias: {@link WishlistRepositoryPort}, {@link ProductRepositoryPort},
 * {@link WishlistMapper} y {@link ProductMapper}.</p>*/
@Service
public class GetWishlistUseCase {

    private final WishlistRepositoryPort wishlistRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;
    private final WishlistMapper wishlistMapper;
    private final ProductMapper productMapper;

    /* Construye el caso de uso mediante inyeccion por constructor.
     * @param wishlistRepositoryPort puerto de persistencia de wishlist
     * @param productRepositoryPort  puerto de persistencia de productos
     * @param wishlistMapper         mapper entre dominio y DTO de wishlist
     * @param productMapper          mapper entre dominio y DTO de producto*/
    public GetWishlistUseCase(WishlistRepositoryPort wishlistRepositoryPort,
                              ProductRepositoryPort productRepositoryPort,
                              WishlistMapper wishlistMapper,
                              ProductMapper productMapper) {
        this.wishlistRepositoryPort = wishlistRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
        this.wishlistMapper = wishlistMapper;
        this.productMapper = productMapper;
    }

    /* Ejecuta el caso de uso: obtiene todos los registros de wishlist,
     * resolviendo el producto asociado a cada uno y marcando los agotados.
     * <p>Si el producto referenciado ya no existe en el catalogo, el
     * registro de wishlist se omite de la respuesta (consistencia defensiva),
     * evitando exponer referencias rotas al consumidor externo.</p>*/
    public List<WishlistResponse> execute() {
        List<Wishlist> wishlists = wishlistRepositoryPort.findAll();

        return wishlists.stream()
                .map(this::toEnrichedResponse)
                .filter(java.util.Objects::nonNull)
                .toList();
    }

    /* Compone la respuesta de un registro de wishlist junto con su producto,
     * calculando el indicador de agotado.
     * @param wishlist registro de wishlist a enriquecer
     * @return DTO compuesto, o {@code null} si el producto asociado ya no existe*/
    private WishlistResponse toEnrichedResponse(Wishlist wishlist) {
        return productRepositoryPort.findById(wishlist.getProductId())
                .map(product -> buildResponse(wishlist, product))
                .orElse(null);
    }

    /* Construye el DTO de salida combinando el registro de wishlist y su producto.
     * @param wishlist registro de wishlist
     * @param product  producto asociado
     * @return DTO de salida {@link WishlistResponse}*/
    private WishlistResponse buildResponse(Wishlist wishlist, Product product) {
        ProductResponse productResponse = productMapper.toResponse(product);
        productResponse.setOutOfStock(!product.hasStockFor(1));
        return wishlistMapper.toResponse(wishlist, productResponse);
    }
}