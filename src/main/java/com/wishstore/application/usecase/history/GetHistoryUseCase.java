package com.wishstore.application.usecase.history;

import com.wishstore.application.dto.HistoryResponse;
import com.wishstore.application.mapper.HistoryMapper;
import com.wishstore.domain.model.WishlistHistory;
import com.wishstore.domain.ports.HistoryRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/*Caso de uso: consultar el historial completo de acciones sobre la wishlist.
 * <p>Descripcion: Recupera todos los registros de auditoria almacenados y
 * los traduce a su representacion externa.</p>
 * <p>Responsabilidad: Orquestar la consulta de historial sin aplicar ninguna
 * transformacion de negocio adicional, ya que el historial es un registro
 * inmutable de hechos ya ocurridos.</p>
 * <p>Dependencias: {@link HistoryRepositoryPort} para la consulta y
 * {@link HistoryMapper} para la traduccion a DTO.</p>*/
@Service
public class GetHistoryUseCase {

    private final HistoryRepositoryPort historyRepositoryPort;
    private final HistoryMapper historyMapper;

    /* Construye el caso de uso mediante inyeccion por constructor.
     * @param historyRepositoryPort puerto de persistencia de historial
     * @param historyMapper         mapper entre dominio y DTO de historial*/
    public GetHistoryUseCase(HistoryRepositoryPort historyRepositoryPort, HistoryMapper historyMapper) {
        this.historyRepositoryPort = historyRepositoryPort;
        this.historyMapper = historyMapper;
    }

    /* Ejecuta el caso de uso: obtiene todos los registros de historial y
     * los convierte a DTO.
     * @return lista de {@link HistoryResponse}*/
    public List<HistoryResponse> execute() {
        List<WishlistHistory> histories = historyRepositoryPort.findAll();
        return historyMapper.toResponseList(histories);
    }
}