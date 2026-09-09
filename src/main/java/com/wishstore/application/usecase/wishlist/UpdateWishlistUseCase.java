package com.wishstore.application.usecase.wishlist;

import com.wishstore.application.dto.ProductResponse;
import com.wishstore.application.dto.WishlistRequest;
import com.wishstore.application.dto.WishlistResponse;
import com.wishstore.application.exception.OutOfStockException;
import com.wishstore.application.exception.ProductNotFoundException;
import com.wishstore.application.exception.WishlistException;
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

/**
 * Caso de uso: actualizar un registro existente de la wishlist.
 *
 * <p>Descripcion: Implementa la regla de negocio de actualizacion: buscar,
 * validar, actualizar, guardar y registrar historial.</p>
 *
 * <p>Responsabilidad: Orquestar el flujo completo de actualizacion de un
 * registro de wishlist ya existente, validando producto y stock antes de
 * aplicar los cambios.</p>
 *
 * <p>Dependencias: {@link ProductRepositoryPort}, {@link WishlistRepositoryPort},
 * {@link HistoryRepositoryPort}, {@link ProductMapper} y {@link WishlistMapper}.</p>
 */
@Service
public class UpdateWishlistUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final WishlistRepositoryPort wishlistRepositoryPort;
    private final HistoryRepositoryPort historyRepositoryPort;
    private final ProductMapper productMapper;
    private final WishlistMapper wishlistMapper;

    /**
     * Construye el caso de uso mediante inyeccion por constructor.
     *
     * @param productRepositoryPort  puerto de persistencia de productos
     * @param wishlistRepositoryPort puerto de persistencia de wishlist
     * @param historyRepositoryPort  puerto de persistencia de historial
     * @param productMapper          mapper entre dominio y DTO de producto
     * @param wishlistMapper         mapper entre dominio y DTO de wishlist
     */
    public UpdateWishlistUseCase(ProductRepositoryPort productRepositoryPort,
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

    /**
     * Ejecuta el caso de uso de actualizacion de un registro de wishlist.
     *
     * <p>Reglas aplicadas, en orden:</p>
     * <ol>
     *     <li>Buscar el registro de wishlist (si no existe, {@link WishlistException}).</li>
     *     <li>Validar que el producto referenciado exista.</li>
     *     <li>Validar que haya stock suficiente para la nueva cantidad.</li>
     *     <li>Actualizar el registro.</li>
     *     <li>Guardar.</li>
     *     <li>Registrar el evento en el historial.</li>
     * </ol>
     *
     * @param wishlistId identificador del registro de wishlist a actualizar
     * @param request    datos de entrada con la nueva informacion
     * @return DTO de salida {@link WishlistResponse}
     * @throws WishlistException        si el registro de wishlist no existe
     * @throws ProductNotFoundException si el producto referenciado no existe
     * @throws OutOfStockException      si no hay stock suficiente
     */
    public WishlistResponse execute(Long wishlistId, WishlistRequest request) {
        Wishlist wishlist = wishlistRepositoryPort.findById(wishlistId)
                .orElseThrow(() -> new WishlistException(wishlistId, "el registro no existe"));

        Product product = productRepositoryPort.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        if (!product.hasStockFor(request.getQuantity())) {
            throw new OutOfStockException(product.getId(), request.getQuantity());
        }

        wishlistMapper.update(request, wishlist);

        Wishlist updatedWishlist = wishlistRepositoryPort.save(wishlist);

        registerHistory(updatedWishlist, product, ActionType.UPDATED,
                "Wishlist actualizada");

        ProductResponse productResponse = productMapper.toResponse(product);
        productResponse.setOutOfStock(!product.hasStockFor(1));

        return wishlistMapper.toResponse(updatedWishlist, productResponse);
    }

    /**
     * Registra un evento de historial asociado a la actualizacion de wishlist.
     *
     * @param wishlist    registro de wishlist afectado
     * @param product     producto asociado
     * @param action      tipo de accion realizada
     * @param description descripcion legible del evento
     */
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