package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySizeName(String sizeName);


}
