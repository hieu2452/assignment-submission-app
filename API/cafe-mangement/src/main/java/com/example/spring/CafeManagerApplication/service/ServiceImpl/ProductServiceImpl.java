package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.Utils.ProductFilters;
import com.example.spring.CafeManagerApplication.Utils.ProductSpec;
import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.entity.Category;
import com.example.spring.CafeManagerApplication.entity.Product;
import com.example.spring.CafeManagerApplication.exception.ProductExistException;
import com.example.spring.CafeManagerApplication.exception.ProductNotFoundException;
import com.example.spring.CafeManagerApplication.repository.CategoryRepository;
import com.example.spring.CafeManagerApplication.repository.ProductRepository;
import com.example.spring.CafeManagerApplication.service.FileUpload;
import com.example.spring.CafeManagerApplication.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileUpload fileUpload;

    @Override
    @Transactional
    public ResponseEntity<?> addNewProduct(MultipartFile file, String model) throws IOException {
        ProductDto productDto = new ObjectMapper().readValue(model, ProductDto.class);

        if(productRepository.existsByName(productDto.getName()))
            throw new ProductExistException("Product already exist");

        String url = fileUpload.uploadFile(file);
        Product product = new Product();

        productMapper(productDto,url,product);

        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct(ProductFilters productFilters) {

        Specification<Product> spec = ProductSpec.filterBy(productFilters,true);

        List<Product> products = productRepository.findAll(spec);

        if(products.isEmpty()) throw new ProductNotFoundException("Products not found");

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProductAdmin(ProductFilters productFilters) {
        Specification<Product> spec = ProductSpec.filterBy(productFilters);

        List<Product> products = productRepository.findAll(spec);

        if(products.isEmpty()) throw new ProductNotFoundException("Products not found");

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    private void productMapper(ProductDto productDto, String imageUrl, Product product){

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
//        product.setStatus("available");
        product.setImageUrl(imageUrl);

        Category category = categoryRepository.findByName(productDto.getCategory().getName());

        product.setCategory(category);

    }

    private void productMapper(ProductDto productDto, Product product){

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        Category category = categoryRepository.findByName(productDto.getCategory().getName());

        product.setCategory(category);

    }


    @Override
    @Transactional
    public ResponseEntity<?> updateProductStatus(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isEmpty()) throw new ProductNotFoundException("Product not found");

        Product product = optional.get();
        product.setStatus(!product.getStatus());

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProduct(MultipartFile file,String model) throws IOException {

        ProductDto productDto = new ObjectMapper().readValue(model, ProductDto.class);

        Optional<Product> optional = productRepository.findById(productDto.getId());
        if(optional.isEmpty()) throw new ProductNotFoundException("Product not found");

        Product product = optional.get();

        if(file==null) {
            productMapper(productDto,product);
        } else {
            String url = fileUpload.uploadFile(file);
            productMapper(productDto,product);
        }

//        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getProductById(Integer id) {
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isEmpty()) throw new ProductNotFoundException("Product not found");

        return new ResponseEntity<>(optional.get(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getByCategory(Integer id) {

        List<Product> products = productRepository.getProductsByCategory(id);

        if(products.isEmpty()) throw new ProductNotFoundException("Products not found");

        return new ResponseEntity<>(products,HttpStatus.OK);
    }


//    private Product productMapper(ProductDto productDto){
//        Product product = new Product();
//        product.setName(productDto.getName());
//        product.setPrice(productDto.getPrice());
//        product.setDescription(productDto.getDescription());
//        product.setStatus("available");
//        Category category = categoryRepository.findByName(productDto.getCategory());
//
//        product.setCategory(category);
//
//        return product;
//    }
}
