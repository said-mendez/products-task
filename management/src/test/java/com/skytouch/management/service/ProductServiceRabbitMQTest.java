package com.skytouch.management.service;


import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.exception.MicroserviceException;
import com.skytouch.management.service.implementation.ProductServiceRabbitMQ;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.EXCHANGE;
import static com.skytouch.commonlibrary.config.RabbitMQConfig.LIST_PRODUCTS_KEY;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceRabbitMQTest {
    @Mock
    public RabbitTemplate rabbitTemplateMock;

    @InjectMocks
    @Autowired
    ProductServiceRabbitMQ productServiceRabbitMQ;

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
