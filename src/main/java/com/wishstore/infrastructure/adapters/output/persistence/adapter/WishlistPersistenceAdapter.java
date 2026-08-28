package com.wishstore.infrastructure.adapters.output.persistence.adapter;

import com.wishstore.domain.model.Wishlist;
import com.wishstore.domain.ports.WishlistRepositoryPort;
import com.wishstore.infrastructure.adapters.output.persistence.entity.WishlistEntity;
import com.wishstore.infrastructure.adapters.output.persistence.mapper.WishlistPersistenceMapper;
import com.wishstore.infrastructure.adapters.output.persistence.repository.WishlistJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WishlistPersistenceAdapter implements WishlistRepositoryPort {

    private final WishlistJpaRepository repository;
    private final WishlistPersistenceMapper mapper;

    @Override
    public List<Wishlist> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Wishlist> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        WishlistEntity entity = mapper.toEntity(wishlist);
        WishlistEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Wishlist> findByProduct(Long productId) {
        return repository.findByProductId(productId)
                .map(mapper::toDomain);
    }
}