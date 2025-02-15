package com.example.UrbanAura.services.item;

import com.example.UrbanAura.exceptions.AlreadyExistsException;
import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.ImageDTO;
import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.Category;
import com.example.UrbanAura.models.entities.Image;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.repositories.CategoryRepository;
import com.example.UrbanAura.repositories.ImageRepository;
import com.example.UrbanAura.repositories.ItemRepository;
import com.example.UrbanAura.requests.AddItemRequest;
import com.example.UrbanAura.requests.ItemUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, ImageRepository imageRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
    }

    @Override
    public Item addItem(AddItemRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        if (itemExists(request.getName())) {
            throw new AlreadyExistsException(request.getName() + " already exists, you may update this instead!");
        }
        return itemRepository.save(createProduct(request, category));
    }

    private boolean itemExists(String name) {
        return itemRepository.existsByName(name);
    }

    private Item createProduct(AddItemRequest request, Category category) {
        return new Item(
                request.getName(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }


    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.findById(id)
                .ifPresentOrElse(itemRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Product not found!");
                        });
    }

    @Override
    public Item updateItem(ItemUpdateRequest request, Long productId) {
        return itemRepository.findById(productId)
                .map(existingProduct -> updateExistingItem(existingProduct, request))
                .map(itemRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found!"));
    }

    private Item updateExistingItem(Item existingProduct, ItemUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> findItemsByCategory(String category) {
        return itemRepository.findByCategoryName(category);
    }


    @Override
    public List<Item> getItemsByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    public Long countItemsByName(String name) {
        return itemRepository.countByName(name);
    }

    @Override
    public Optional<Item> findBySlug(String slug) {
        return itemRepository.findBySlug(slug);
    }

    @Override
    public List<ItemDTO> getConvertedItems(List<Item> items) {
        return items.stream().map(this::convertToDto).toList();
    }

    @Override
    public ItemDTO convertToDto(Item item) {
        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
        List<Image> images = imageRepository.findByItemId(item.getId());
        List<ImageDTO> imageDTOS = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        itemDTO.setImages(imageDTOS);
        return itemDTO;
    }

    public String generateSlug(String name) {
        return name.toLowerCase()
                .replace(" ", "-").replaceAll("[^a-z0-9-]", "");
    }

}
