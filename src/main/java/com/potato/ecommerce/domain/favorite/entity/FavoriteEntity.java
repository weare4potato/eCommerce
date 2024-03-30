package com.potato.ecommerce.domain.favorite.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite")
public class FavoriteEntity {

    @EmbeddedId
    private FavoriteEntityId id;
}
