package com.skytouch.management.controller;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.management.service.ProductClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/products")
public class ProductController {

    public static final String MODEL = "product";
    private static final String ADD_PRODUCT_VIEW = "addProduct";
    private static final String LIST_PRODUCTS_VIEW = "listProducts";
    private static final String ADD_PRODUCTS_URL = "/products/addProduct";
    private final ProductClientService productClientService;

    public ProductController(ProductClientService productClientService) {
        this.productClientService = productClientService;
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute(MODEL, new Product());

        return ADD_PRODUCT_VIEW;
    }

    @PostMapping("/addProduct")
    public RedirectView addBook(@ModelAttribute(MODEL) Product product, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView(ADD_PRODUCTS_URL);
        redirectAttributes.addFlashAttribute("addProductSuccess", true);
        redirectAttributes.addFlashAttribute("responseStatus", productClientService.addProduct(product));

        return redirectView;
    }

    @GetMapping("")
    public String listProducts(Model model) {
        model.addAttribute("products", productClientService.listProducts());
        return LIST_PRODUCTS_VIEW;
    }
}
