package com.example.UrbanAura.controllers;

import com.example.UrbanAura.exceptions.AlreadyExistsException;
import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.requests.AddItemRequest;
import com.example.UrbanAura.requests.ItemUpdateRequest;
import com.example.UrbanAura.response.ApiResponse;
import com.example.UrbanAura.services.item.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllItems() {
        List<Item> items = itemService.getAllItems();
        List<ItemDTO> convertedItem = itemService.getConvertedItems(items);
        return ResponseEntity.ok(new ApiResponse("success", convertedItem));
    }


    @GetMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse> getItemById(@PathVariable Long itemId) {
        try {
            Item item = itemService.getItemById(itemId);
            ItemDTO itemDTO = itemService.convertToDto(item);
            return ResponseEntity.ok(new ApiResponse("success", itemDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItem(@RequestBody AddItemRequest item) {
        try {
            Item theItem = itemService.addItem(item);
            ItemDTO itemDTO = itemService.convertToDto(theItem);
            return ResponseEntity.ok(new ApiResponse("Successfully added item!", itemDTO));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItem(@RequestBody ItemUpdateRequest request,
                                                  @PathVariable Long itemId) {
        try {
            Item theItem = itemService.updateItem(request, itemId);
            ItemDTO itemDTO = itemService.convertToDto(theItem);
            return ResponseEntity.ok(new ApiResponse("Successfully updated item!", itemDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/item/{itemId}/delete")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId) {
        try {
            itemService.deleteItemById(itemId);
            return ResponseEntity.ok(new ApiResponse(" Deleted item successfully! ", itemId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/items/{name}/name")
    public ResponseEntity<ApiResponse> getItemsByName(@PathVariable String name) {
        try {
            List<Item> items = itemService.getItemsByName(name);
            if (items.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            List<ItemDTO> convertedItem = itemService.getConvertedItems(items);
            return ResponseEntity.ok(new ApiResponse("success", convertedItem));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/item/{category}/all")
    public ResponseEntity<ApiResponse> findItemsByCategory(@PathVariable String category) {
        try {
            List<Item> items = itemService.findItemsByCategory(category);
            if (items.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No category found", null));
            }
            List<ItemDTO> convertedItem = itemService.getConvertedItems(items);
            return ResponseEntity.ok(new ApiResponse("success", convertedItem));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }

    }

}
