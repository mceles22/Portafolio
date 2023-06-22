package com.Tienda.controllers;
import com.tienda.entities.Product;
import com.tienda.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    private final IProductService productService;
    public ProductController(IProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/product") //read
    public String index(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", this.productService.getAllProducts());
        return "product";
    }
    @PostMapping("product/save") //create y update 
    public String save(@ModelAttribute("product") Product product) {
        this.productService.save(product);
        return "redirect:/product"; //no se puede utilizar 
    }
    @PostMapping("product/delete") //delete 
    public ResponseEntity<String> delete(@RequestBody Product product) {
        this.productService.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
