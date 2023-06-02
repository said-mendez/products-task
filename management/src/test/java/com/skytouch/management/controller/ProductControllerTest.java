package com.skytouch.management.controller;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.exception.MicroserviceException;
import com.skytouch.management.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addProductView() throws Exception {
        // Given:
        // When:
        MvcResult result = mockMvc.perform(get("/products/addProduct")).andReturn();

        // Then:
        assertThat(result.getModelAndView().getModel().get("product")).isInstanceOf(Product.class);
    }

    @Test
    public void addProduct() throws Exception {
        // Given:
        Product expectedProduct = new Product();
        expectedProduct.setName("New Product I");
        expectedProduct.setDescription("New Product I");
        expectedProduct.setPrice(new BigDecimal("123.5"));

        ResponseStatus expectedResponseStatus = new ResponseStatus();
        expectedResponseStatus.setSuccess(true);
        expectedResponseStatus.setMessage("Product " + expectedProduct.getName() + " was created.");
        expectedResponseStatus.setException(null);

        // When:
        when(productService.addProduct(expectedProduct)).thenReturn(expectedResponseStatus);

        // Then:
        MvcResult result =
                mockMvc.perform(
                post("/products/addProduct")
                        .param("name", expectedProduct.getName())
                        .param("description", expectedProduct.getDescription())
                        .param("price", expectedProduct.getPrice().toString())
                )
                .andExpect(MockMvcResultMatchers.flash().attribute("responseStatus", expectedResponseStatus))
                .andReturn()
                ;
        ResponseStatus responseStatusResult = (ResponseStatus) result.getFlashMap().get("responseStatus");

        assertThat(expectedResponseStatus.getSuccess()).isEqualTo(responseStatusResult.getSuccess());
        assertThat(expectedResponseStatus.getMessage()).isEqualTo(responseStatusResult.getMessage());
        assertThat(expectedResponseStatus.getException()).isEqualTo(responseStatusResult.getException());
    }

    @Test
    public void tryToAddDuplicatedProduct() throws Exception {
        // Given:
        Product duplicatedProduct = new Product();
        duplicatedProduct.setName("New Product I");
        duplicatedProduct.setDescription("New Product I");
        duplicatedProduct.setPrice(new BigDecimal("123.5"));

        ResponseStatus expectedResponseStatus = new ResponseStatus();
        expectedResponseStatus.setSuccess(false);
        expectedResponseStatus.setMessage("Microservice is not responding!");
        expectedResponseStatus.setException("java.lang.RuntimeException");

        // When:
        when(productService.addProduct(duplicatedProduct)).thenThrow(new RuntimeException("Microservice is not responding!"));

        MvcResult result =
                mockMvc.perform(
                                post("/products/addProduct")
                                        .param("name", duplicatedProduct.getName())
                                        .param("description", duplicatedProduct.getDescription())
                                        .param("price", duplicatedProduct.getPrice().toString())
                        )
                        .andReturn()
                ;
        ResponseStatus responseStatusResult = (ResponseStatus) result.getModelAndView().getModel().get("responseStatus");

        // Then:
        assertThat(expectedResponseStatus.getSuccess()).isEqualTo(responseStatusResult.getSuccess());
        assertThat(expectedResponseStatus.getMessage()).isEqualTo(responseStatusResult.getMessage());
        assertThat(expectedResponseStatus.getException()).isEqualTo(responseStatusResult.getException());
    }

    @Test
    public void listProducts() throws Exception {
        // Given:
        ResponseStatus expectedResponseStatus = new ResponseStatus();
        expectedResponseStatus.setSuccess(true);
        expectedResponseStatus.setMessage("Listing products");
        expectedResponseStatus.setException(null);
        ListProductsRequestResponse expectedResponse = new ListProductsRequestResponse(null, expectedResponseStatus);

        // When:
        when(productService.listProducts()).thenReturn(expectedResponse);
        MvcResult result = mockMvc.perform(get("/products")).andReturn();
        ListProductsRequestResponse responseStatusResult = (ListProductsRequestResponse) result.getModelAndView().getModel().get("products");
        ResponseStatus actualResponseStatus = responseStatusResult.getResponseStatus().get("status");


        // Then:
        assertThat(expectedResponseStatus.getSuccess()).isEqualTo(actualResponseStatus.getSuccess());
        assertThat(expectedResponseStatus.getMessage()).isEqualTo(actualResponseStatus.getMessage());
        assertThat(expectedResponseStatus.getException()).isEqualTo(actualResponseStatus.getException());
    }

    @Test
    public void tryingToListProductsThrowsException() throws Exception {
        // Given:
        ResponseStatus expectedResponseStatus = new ResponseStatus();
        expectedResponseStatus.setSuccess(false);
        expectedResponseStatus.setMessage("Exception from test!");
        expectedResponseStatus.setException("com.skytouch.management.exception.MicroserviceException");

        // When:
        when(productService.listProducts()).thenThrow(new MicroserviceException("Exception from test!"));

        MvcResult result = mockMvc.perform(get("/products")).andReturn();
        ResponseStatus responseStatusResult = (ResponseStatus) result.getModelAndView().getModel().get("responseStatus");

        // Then:;
        assertThat(expectedResponseStatus.getSuccess()).isEqualTo(responseStatusResult.getSuccess());
        assertThat(expectedResponseStatus.getMessage()).isEqualTo(responseStatusResult.getMessage());
        assertThat(expectedResponseStatus.getException()).isEqualTo(responseStatusResult.getException());
    }
}
