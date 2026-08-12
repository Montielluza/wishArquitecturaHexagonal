package com.wishstore.application.usecase.wishlist;

import com.wishstore.application.exception.WishlistException;
import com.wishstore.domain.model.ActionType;
import com.wishstore.domain.model.Wishlist;
import com.wishstore.domain.model.WishlistHistory;
import com.wishstore.domain.ports.HistoryRepositoryPort;
import com.wishstore.domain.ports.WishlistRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/*Caso de uso: eliminar un registro de la wishlist.
 * <p>Descripcion: Implementa la regla de negocio de eliminacion: buscar,
 * eliminar y registrar historial.</p>
 * <p>Responsabilidad: Orquestar el flujo de eliminacion de un registro de
 * wishlist, garantizando que quede trazabilidad en el historial incluso
 * despues de eliminado el registro.</p>
 * <p>Dependencias: {@link WishlistRepositoryPort} y {@link HistoryRepositoryPort}.</p>*/
@Service
public class DeleteWishlistUseCase {

    private final WishlistRepositoryPort wishlistRepositoryPort;
    private final HistoryRepositoryPort historyRepositoryPort;

    /*Construye el caso de uso mediante inyeccion por constructor.
     * @param wishlistRepositoryPort puerto de persistencia de wishlist
     * @param historyRepositoryPort  puerto de persistencia de historial*/
    public DeleteWishlistUseCase(WishlistRepositoryPort wishlistRepositoryPort,
                                 HistoryRepositoryPort historyRepositoryPort) {
        this.wishlistRepositoryPort = wishlistRepositoryPort;
        this.historyRepositoryPort = historyRepositoryPort;
    }

    /* Ejecuta el caso de uso de eliminacion de un registro de wishlist.
     * <p>Reglas aplicadas, en orden:</p>
     * <ol>
     *     <li>Buscar el registro (si no existe, {@link WishlistException}).</li>
     *     <li>Eliminarlo.</li>
     *     <li>Registrar el evento en el historial.</li>
     * </ol>
     * @param wishlistId identificador del registro de wishlist a eliminar
     * @throws WishlistException si el registro no existe*/
    public void execute(Long wishlistId) {
        Wishlist wishlist = wishlistRepositoryPort.findById(wishlistId)
                .orElseThrow(() -> new WishlistException(wishlistId, "el registro no existe"));

        wishlistRepositoryPort.delete(wishlistId);

        WishlistHistory history = WishlistHistory.builder()
                .wishlistId(wishlist.getId())
                .productId(wishlist.getProductId())
                .action(ActionType.REMOVED)
                .description("Producto eliminado de la wishlist")
                .createdAt(LocalDateTime.now())
                .build();

        historyRepositoryPort.save(history);
    }
}