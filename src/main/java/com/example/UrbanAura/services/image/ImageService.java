package com.example.UrbanAura.services.image;

import com.example.UrbanAura.models.dtos.ImageDTO;
import com.example.UrbanAura.models.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImages(List<MultipartFile> files, Long itemId);
    void updateImage(MultipartFile file, Long imageId);
}
