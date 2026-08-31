package com.wishstore.infrastructure.adapters.output.persistence.mapper;

import com.wishstore.domain.model.WishlistHistory;
import com.wishstore.infrastructure.adapters.output.persistence.entity.WishlistHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistHistoryPersistenceMapper {

    WishlistHistoryEntity toEntity(WishlistHistory history);

    WishlistHistory toDomain(WishlistHistoryEntity entity);
}