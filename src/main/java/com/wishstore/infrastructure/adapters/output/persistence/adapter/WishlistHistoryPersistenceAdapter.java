package com.wishstore.infrastructure.adapters.output.persistence.adapter;

import com.wishstore.domain.model.WishlistHistory;
import com.wishstore.domain.ports.HistoryRepositoryPort;
import com.wishstore.infrastructure.adapters.output.persistence.entity.WishlistHistoryEntity;
import com.wishstore.infrastructure.adapters.output.persistence.mapper.WishlistHistoryPersistenceMapper;
import com.wishstore.infrastructure.adapters.output.persistence.repository.JpaWishlistHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WishlistHistoryPersistenceAdapter implements HistoryRepositoryPort {

    private final JpaWishlistHistoryRepository repository;
    private final WishlistHistoryPersistenceMapper mapper;

    @Override
    public WishlistHistory save(WishlistHistory history) {
        WishlistHistoryEntity entity = mapper.toEntity(history);
        WishlistHistoryEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<WishlistHistory> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<WishlistHistory> findByWishlist(Long wishlistId) {
        return repository.findByWishlistId(wishlistId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<WishlistHistory> findByProduct(Long productId) {
        return repository.findByProductId(productId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}