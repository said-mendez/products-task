package com.skytouch.management.service;


import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.exception.MicroserviceException;
import com.skytouch.management.service.implementation.ProductServiceRabbitMQ;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceRabbitMQTest {
    @Mock
    public RabbitTemplate rabbitTemplateMock;

    @InjectMocks
    ProductServiceRabbitMQ productServiceRabbitMQ;

    @Test
    public void addProduct() {
        // Given:
        String randomName = UUID.randomUUID().toString();
        Product expectedProduct = new Product();
        expectedProduct.setName(randomName);
        expectedProduct.setDescription("Product Description");
        expectedProduct.setPrice(new BigDecimal("182.54"));

        // When:
        productServiceRabbitMQ.addProduct(expectedProduct);

        // Then:
        verify(rabbitTemplateMock, times(1)).convertAndSend(EXCHANGE, ADD_PRODUCTS_KEY, expectedProduct);
    }

    @Test(expected = MicroserviceException.class)
    public void listingProductsWithMicroserviceDownThrowsMicroserviceException() {
        // Given:
        // When:
        // Then:
        productServiceRabbitMQ.listProducts();
    }

    @Test
    public void listProducts() {
        // Given:
        String responseMessage = "Listing products";
        ListProductsRequestResponse expectedResponse = new ListProductsRequestResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccess(true);
        responseStatus.setMessage(responseMessage);
        responseStatus.setException(null);
        Map<String, ResponseStatus> statusMap = new HashMap<>();
        statusMap.put("status", responseStatus);
        expectedResponse.setResponseStatus(statusMap);

        // When:
        when(rabbitTemplateMock.convertSendAndReceive(EXCHANGE, LIST_PRODUCTS_KEY, "")).thenReturn(expectedResponse);
        ListProductsRequestResponse actualResponse = productServiceRabbitMQ.listProducts();


        // Then:
        assertThat(actualResponse.getResponseStatus().get("status").getSuccess()).isTrue();
        assertThat(actualResponse.getResponseStatus().get("status").getMessage()).isEqualTo(responseMessage);
    }
}
