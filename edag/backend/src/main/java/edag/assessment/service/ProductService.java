package edag.assessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edag.assessment.model.Product;
import edag.assessment.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(String code) {
        return productRepository.findByCode(code).get(0);
    }

    public List<Product> getProducts(int page, int size) {
        Pageable paging = PageRequest.of(page-1, size);
        Page<Product> pageResult = productRepository.findAll(paging);
        return pageResult.toList();
    }

    public void deleteProduct(String code) {
        Product product = productRepository.findByCode(code).get(0);
        productRepository.deleteById(product.getId());
    }


    public Product updateProduct(String code, Product productDetails) {
        Product product = productRepository.findByCode(code).get(0);
        product.setBrand(productDetails.getBrand());
        product.setCategory(productDetails.getCategory());
        product.setCode(productDetails.getCode());
        product.setDescription(productDetails.getDescription());
        product.setName(productDetails.getName());
        product.setType(productDetails.getType());

        return productRepository.save(product);
    }
}
