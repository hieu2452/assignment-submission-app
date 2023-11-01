package com.example.spring.CafeManagerApplication.service.ServiceImpl;

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
        Product product = new Product();

        productMapper(productDto,url,product);

        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct() {

        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    private void productMapper(ProductDto productDto, String imageUrl, Product product){

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
//        product.setStatus("available");
        product.setImageUrl(imageUrl);

        Category category = categoryRepository.findByName(productDto.getCategory());

        product.setCategory(category);

    }

    private Product productMapper(ProductDto productDto, Product product){

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        Category category = categoryRepository.findByName(productDto.getCategory());

        product.setCategory(category);

        return product;
    }


    @Override
    @Transactional
    public ResponseEntity<String> updateProductStatus(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isEmpty()) throw new ProductNotFoundException("Product not found");

        Product product = optional.get();
        if(product.getStatus().equals("available"))
            product.setStatus("unavailable");
        else product.setStatus("available");

        return new ResponseEntity<>("enable product successfully", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProduct(MultipartFile file,String model) throws IOException {

        ProductDto productDto = new ObjectMapper().readValue(model, ProductDto.class);

        Optional<Product> optional = productRepository.findById(productDto.getProductId());
        if(optional.isEmpty()) throw new ProductNotFoundException("Product not found");

        Product product = optional.get();

        if(file.isEmpty()) {
            productMapper(productDto,product);
        } else {
            String url = fileUpload.uploadFile(file);
            productMapper(productDto,product);
        }

//        productRepository.save(product);
        return new ResponseEntity<>("update successfully", HttpStatus.OK);
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
