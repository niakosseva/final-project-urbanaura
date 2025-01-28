package com.example.UrbanAura.models.entities;

import com.example.UrbanAura.models.BaseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sizes")
public class Size extends BaseEntity {

    private String sizeName;

    public Size() {

    }

    public Size(String sizeName) {
        this.sizeName = sizeName;
    }
}
