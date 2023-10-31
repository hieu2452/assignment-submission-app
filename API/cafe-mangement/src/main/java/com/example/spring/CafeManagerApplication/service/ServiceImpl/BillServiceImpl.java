package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.dto.BillDetailDto;
import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.dto.ProductIdDto;
import com.example.spring.CafeManagerApplication.entity.*;
import com.example.spring.CafeManagerApplication.repository.BillDetailRepository;
import com.example.spring.CafeManagerApplication.repository.BillRepository;
import com.example.spring.CafeManagerApplication.repository.ProductRepository;
import com.example.spring.CafeManagerApplication.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    @Transactional
    public ResponseEntity<?> addNewBill(BillDetailDto billDetailDto) {

        UserEntity user = getUserFromAuth();

        Bill bill = new Bill();

        bill.setName(billDetailDto.getName());
        bill.setEmail(billDetailDto.getEmail());
        bill.setContactNumber(billDetailDto.getContactNumber());
        bill.setPaymentMethod(billDetailDto.getPaymentMethod());
        bill.setUser(user);

        Bill savedBill = billRepository.saveAndFlush(bill);

        for(ProductDto productDto  : billDetailDto.getProducts()){

            BillDetailKey billDetailKey = new BillDetailKey(savedBill.getId(),productDto.getProductId());
            Product product = productRepository.findById(productDto.getProductId())
                    .orElseThrow();
            BillDetail billDetail = new BillDetail(billDetailKey,savedBill,product,productDto.getQuantity());
            billDetailRepository.save(billDetail);
        }

        return new ResponseEntity<>("Create bill successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllBill() {

        UserEntity user = getUserFromAuth();

        List<Bill> bills = billRepository.findAll();


        List<BillDetailDto> detailDtos = bills.stream().map(this::mapBillDetailDto).toList();

        return new ResponseEntity<>(detailDtos,HttpStatus.OK);
    }

    private BillDetailDto mapBillDetailDto(Bill bill){

        BillDetailDto billDetailDto = new BillDetailDto();

        billDetailDto.setId(bill.getId());
        billDetailDto.setName(bill.getName());
        billDetailDto.setEmail(bill.getEmail());
        billDetailDto.setContactNumber(bill.getContactNumber());
        billDetailDto.setPaymentMethod(bill.getPaymentMethod());

        List<ProductDto> products = bill.getBillDetails().stream().map( x -> mapProductDto(x.getProduct(),x.getQuantity())).collect(Collectors.toList());



        billDetailDto.setProducts(products);

        return billDetailDto;
    }

    private ProductDto mapProductDto(Product product, Integer quantity){

        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(quantity);
        productDto.setCategory(productDto.getCategory());

        return productDto;
    }

    private UserEntity getUserFromAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
}
