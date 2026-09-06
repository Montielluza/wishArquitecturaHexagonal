package com.wishstore.infrastructure.adapters.output.persistence.adapter;

import com.wishstore.domain.model.Product;
import com.wishstore.domain.ports.ProductRepositoryPort;
import com.wishstore.infrastructure.adapters.output.persistence.entity.ProductEntity;
import com.wishstore.infrastructure.adapters.output.persistence.mapper.ProductPersistenceMapper;
import com.wishstore.infrastructure.adapters.output.persistence.repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final JpaProductRepository repository;
    private final ProductPersistenceMapper mapper;

    @Override
    public List<Product> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Product update(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}