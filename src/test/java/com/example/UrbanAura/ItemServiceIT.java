package com.example.UrbanAura;

import com.example.UrbanAura.exceptions.AlreadyExistsException;
import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.entities.Category;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.CategoryRepository;
import com.example.UrbanAura.repositories.ItemRepository;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.requests.AddItemRequest;
import com.example.UrbanAura.services.item.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ItemServiceIT {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Long testItemId;
    @BeforeEach
    void setUp() {
        // Create a test item to be used for the test
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(19.99));
        itemRepository.save(item);

        // Store the id of the saved item for testing
        testItemId = item.getId();
    }

    @Test
    void testAddItem() {

        User user = new User();
        user.setFirstName("tester");
        User savedUser = userRepository.save(user);

        Category category = new Category("Jackets");
        category.setSlug("jacket");
        BigDecimal price = new BigDecimal("89.90");

        Item item = new Item("Jacket", price, 10, "Great jacket", category);

        Item savedItem = itemRepository.save(item);

        assertNotNull(savedItem.getId());
        assertEquals("Jacket", savedItem.getName());
        assertEquals(price, savedItem.getPrice());
        assertEquals(10, savedItem.getInventory());
        assertEquals("Great jacket", savedItem.getDescription());
        assertEquals(category, savedItem.getCategory());

    }

    @Test
    void createProductTest() {
        Category category = new Category("Jackets");
        // Можеш да генерираш slug, ако имаш такъв метод:
        category.generateSlug();
        category = categoryRepository.save(category);

        BigDecimal price = new BigDecimal("89.90");
        AddItemRequest request = new AddItemRequest();
        request.setName("Jacket");
        request.setPrice(price);
        request.setInventory(10);
        request.setDescription("Great jacket");
        request.setCategory(category);
        Item item = itemService.createProduct(request, category);
        assertEquals("Jacket", item.getName());
        assertEquals(price, item.getPrice());
        assertEquals(10, item.getInventory());
        assertEquals("Great jacket", item.getDescription());
        assertEquals(category, item.getCategory());

    }
    @Test
    void deleteItemById_ItemExists_DeletesSuccessfully() {
        assertTrue(itemRepository.existsById(testItemId));

        itemService.deleteItemById(testItemId);
        assertFalse(itemRepository.existsById(testItemId));
    }

    @Test
    void deleteItemById_ItemDoesNotExist_ThrowsResourceNotFoundException() {
        Long nonExistentItemId = 999L;

        // Assert that the exception is thrown when the item doesn't exist
        assertThrows(ResourceNotFoundException.class, () -> {
            itemService.deleteItemById(nonExistentItemId);
        });
    }

    @Test
    void addItem_AlreadyExists_ThrowsAlreadyExistsException() {
        Item existingItem = new Item();
        existingItem.setName("Existing Product");
        existingItem.setPrice(BigDecimal.valueOf(200.0));
        itemRepository.save(existingItem);

        // Подготовка на заявка
        AddItemRequest request = new AddItemRequest();
        request.setName("Existing Product"); // Използваме същото име
        request.setCategory(new Category("Any Category"));

        // Проверка на хвърляне на изключение
        assertThrows(AlreadyExistsException.class, () -> itemService.addItem(request));
    }





}
