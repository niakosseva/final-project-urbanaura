package com.example.UrbanAura.models.entities;

import com.example.UrbanAura.models.BaseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {

    @NotBlank
    private String name;

    private String slug;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Item> items;

    public Category(String name) {
        this.name = name;

    }

    @PrePersist
    public void generateSlug() {
        if (this.slug == null || this.slug.isEmpty()) {
            this.slug = this.name.toLowerCase().replace(" ", "-");
        }
    }


}
