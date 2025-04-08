package com.example.UrbanAura.unitTests;

import com.example.UrbanAura.requests.AddItemRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ItemServiceUnitTest {

    @Test
    public void createProduct(){
        AddItemRequest request = new AddItemRequest();
        request.setName("test");
        request.setPrice(BigDecimal.valueOf(10.00));
        request.setCategory(request.getCategory());
        request.setDescription("description");


    }
}
