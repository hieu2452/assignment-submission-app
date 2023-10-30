package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.entity.Category;
import com.example.spring.CafeManagerApplication.entity.Product;
import com.example.spring.CafeManagerApplication.exception.ProductExistException;
import com.example.spring.CafeManagerApplication.repository.CategoryRepository;
import com.example.spring.CafeManagerApplication.repository.ProductRepository;
import com.example.spring.CafeManagerApplication.service.FileUpload;
import com.example.spring.CafeManagerApplication.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileUpload fileUpload;

//    @Override
//    @Transactional
//    public ResponseEntity<?> addNewProduct(ProductDto productDto) {
//        if(productRepository.existsByName(productDto.getName()))
//            throw new ProductExistException("Product already exist");
//        Product product = productMapper(productDto);
//        productRepository.save(product);
//        return new ResponseEntity<>(product, HttpStatus.OK);
//    }

    @Override
    @Transactional
    public ResponseEntity<?> addNewProduct(MultipartFile file, String model) throws IOException {
        ProductDto productDto = new ObjectMapper().readValue(model, ProductDto.class);

        if(productRepository.existsByName(productDto.getName()))
            throw new ProductExistException("Product already exist");

        String url = fileUpload.uploadFile(file);
        Product product = productMapper(productDto,url);

        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct() {

        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    private Product productMapper(ProductDto productDto,String imageUrl){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setStatus("available");
        product.setImageUrl(imageUrl);
        Category category = categoryRepository.findByName(productDto.getCategory());

        product.setCategory(category);

        return product;
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
