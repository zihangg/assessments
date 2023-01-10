package edag.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edag.assessment.model.Product;
import edag.assessment.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    ProductService productService;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @GetMapping()
    public List<Product> getProducts(@RequestParam int page, @RequestParam int size) {
        return productService.getProducts(page, size);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @GetMapping("{code}")
    public Product getProductByCode(@PathVariable(value = "code") String code) {
        return productService.getProduct(code);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @PutMapping("{code}")
    public Product updateProduct(@PathVariable(value = "code") String code, @RequestBody Product product) {
        return productService.updateProduct(code, product);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @DeleteMapping("{code}")
    public void deleteProduct(@PathVariable(value = "code") String code) {
        productService.deleteProduct(code);
    }

 }
