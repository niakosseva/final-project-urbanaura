package com.example.UrbanAura.controllers;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.ImageDTO;
import com.example.UrbanAura.models.entities.Image;
import com.example.UrbanAura.response.ApiResponse;
import com.example.UrbanAura.services.image.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageRestController {

    private final ImageService imageService;

    public ImageRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,
                                                  @RequestParam Long itemId) {
        try {
            List<ImageDTO> imageDTOS = imageService.saveImages(files, itemId);

            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + image.getFileName() + "\"")
                .body(resource);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,
                                                   @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);

            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);

            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));

    }
}
