package com.skytouch.management.controller;

import com.skytouch.commonlibrary.model.Product;
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

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());

        return "addProduct";
    }

    @PostMapping("/addProduct")
    public RedirectView addBook(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/products/addProduct", true);
        redirectAttributes.addFlashAttribute("addProductSuccess", true);

        return redirectView;
    }

    @GetMapping("")
    public String listProducts() {
        return "listProducts";
    }
}
