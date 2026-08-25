package com.wishstore.infrastructure.adapters.output.persistence.mapper;

import com.wishstore.domain.model.Product;
import com.wishstore.infrastructure.adapters.output.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

    ProductEntity toEntity(Product product);

    Product toDomain(ProductEntity entity);
}