package com.wishstore.infrastructure.adapters.output.persistence.repository;

import com.wishstore.infrastructure.adapters.output.persistence.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaWishlistRepository extends JpaRepository<WishlistEntity, Long> {

    Optional<WishlistEntity> findByProductId(Long productId);

    boolean existsByProductId(Long productId);
}