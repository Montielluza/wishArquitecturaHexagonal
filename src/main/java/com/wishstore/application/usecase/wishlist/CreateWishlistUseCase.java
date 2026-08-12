package com.wishstore.application.usecase.wishlist;

import com.wishstore.application.dto.ProductResponse;
import com.wishstore.application.dto.WishlistRequest;
import com.wishstore.application.dto.WishlistResponse;
import com.wishstore.application.exception.OutOfStockException;
import com.wishstore.application.exception.ProductNotFoundException;
import com.wishstore.application.mapper.ProductMapper;
import com.wishstore.application.mapper.WishlistMapper;
import com.wishstore.domain.model.ActionType;
import com.wishstore.domain.model.Product;
import com.wishstore.domain.model.Wishlist;
import com.wishstore.domain.model.WishlistHistory;
import com.wishstore.domain.ports.HistoryRepositoryPort;
import com.wishstore.domain.ports.ProductRepositoryPort;
import com.wishstore.domain.ports.WishlistRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/* Caso de uso: agregar un producto a la wishlist.
 * <p>Descripcion: Implementa la regla de negocio completa de creacion de un
 * registro de wishlist, incluyendo validacion de producto, validacion de
 * stock, actualizacion de cantidad si ya existe, y registro de historial.</p>
 * <p>Dependencias: {@link ProductRepositoryPort}, {@link WishlistRepositoryPort},
 * {@link HistoryRepositoryPort}, {@link ProductMapper} y {@link WishlistMapper}.</p>*/
@Service
public class CreateWishlistUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final WishlistRepositoryPort wishlistRepositoryPort;
    private final HistoryRepositoryPort historyRepositoryPort;
    private final ProductMapper productMapper;
    private final WishlistMapper wishlistMapper;

    /* Construye el caso de uso mediante inyeccion por constructor.
     * @param productRepositoryPort  puerto de persistencia de productos
     * @param wishlistRepositoryPort puerto de persistencia de wishlist
     * @param historyRepositoryPort  puerto de persistencia de historial
     * @param productMapper          mapper entre dominio y DTO de producto
     * @param wishlistMapper         mapper entre dominio y DTO de wishlist*/
    public CreateWishlistUseCase(ProductRepositoryPort productRepositoryPort,
                                 WishlistRepositoryPort wishlistRepositoryPort,
                                 HistoryRepositoryPort historyRepositoryPort,
                                 ProductMapper productMapper,
                                 WishlistMapper wishlistMapper) {
        this.productRepositoryPort = productRepositoryPort;
        this.wishlistRepositoryPort = wishlistRepositoryPort;
        this.historyRepositoryPort = historyRepositoryPort;
        this.productMapper = productMapper;
        this.wishlistMapper = wishlistMapper;
    }

    /* Ejecuta el caso de uso de creacion/actualizacion de un registro de wishlist.
     * @param request datos de entrada con el producto y la cantidad deseada
     * @return DTO de salida {@link WishlistResponse}
     * @throws ProductNotFoundException si el producto no existe
     * @throws OutOfStockException      si no hay stock suficiente*/
    public WishlistResponse execute(WishlistRequest request) {
        Product product = productRepositoryPort.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        if (!product.hasStockFor(request.getQuantity())) {
            throw new OutOfStockException(product.getId(), request.getQuantity());
        }

        Wishlist wishlist = wishlistRepositoryPort.findByProduct(request.getProductId())
                .orElse(null);

        ActionType action;
        if (wishlist != null) {
            wishlist.increaseQuantity(request.getQuantity());
            action = ActionType.UPDATED;
        } else {
            wishlist = wishlistMapper.toDomain(request);
            wishlist.setCreatedAt(LocalDateTime.now());
            action = ActionType.ADDED;
        }

        Wishlist savedWishlist = wishlistRepositoryPort.save(wishlist);

        registerHistory(savedWishlist, product, action,
                action == ActionType.ADDED
                        ? "Producto agregado a la wishlist"
                        : "Cantidad actualizada en la wishlist");

        ProductResponse productResponse = productMapper.toResponse(product);
        productResponse.setOutOfStock(!product.hasStockFor(1));

        return wishlistMapper.toResponse(savedWishlist, productResponse);
    }

    /* Registra un evento de historial asociado a una operacion de wishlist.
     * @param wishlist    registro de wishlist afectado
     * @param product     producto asociado
     * @param action      tipo de accion realizada
     * @param description descripcion legible del evento*/
    private void registerHistory(Wishlist wishlist, Product product, ActionType action, String description) {
        WishlistHistory history = WishlistHistory.builder()
                .wishlistId(wishlist.getId())
                .productId(product.getId())
                .action(action)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();

        historyRepositoryPort.save(history);
    }
}