package com.wishstore.application.mapper;

import com.wishstore.application.dto.HistoryResponse;
import com.wishstore.domain.model.WishlistHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    /* Convierte un registro de historial de dominio en su DTO de salida.
     * @param history modelo de dominio a convertir
     * @return DTO de salida {@link HistoryRespons*/
    HistoryResponse toResponse(WishlistHistory history);

    /*Convierte una lista de registros de historial de dominio en una lista
     * de DTO de salida.
     * @param histories lista de modelos de dominio
     * @return lista de DTO de salida*/
    List<HistoryResponse> toResponseList(List<WishlistHistory> histories);
}