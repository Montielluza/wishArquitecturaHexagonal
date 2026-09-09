package com.wishstore.infrastructure.adapters.output.persistence.repository;

import com.wishstore.domain.model.ActionType;
import com.wishstore.infrastructure.adapters.output.persistence.entity.WishlistHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaWishlistHistoryRepository extends JpaRepository<WishlistHistoryEntity, Long> {

    List<WishlistHistoryEntity> findByWishlistId(Long wishlistId);

    List<WishlistHistoryEntity> findByProductId(Long productId);

    List<WishlistHistoryEntity> findByAction(ActionType action);
}