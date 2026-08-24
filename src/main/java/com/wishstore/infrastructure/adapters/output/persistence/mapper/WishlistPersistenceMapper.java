package com.wishstore.infrastructure.adapters.output.persistence.mapper;

import com.wishstore.domain.model.Wishlist;
import com.wishstore.infrastructure.adapters.output.persistence.entity.WishlistEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistPersistenceMapper {

    WishlistEntity toEntity(Wishlist wishlist);

    Wishlist toDomain(WishlistEntity entity);
}