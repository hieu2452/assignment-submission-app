package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.Utils.CafeUtils;
import com.example.spring.CafeManagerApplication.Utils.EmailUtils;
import com.example.spring.CafeManagerApplication.dto.BillDetailDto;
import com.example.spring.CafeManagerApplication.dto.MessageResponse;
import com.example.spring.CafeManagerApplication.dto.ProductDto;

import com.example.spring.CafeManagerApplication.entity.*;
import com.example.spring.CafeManagerApplication.repository.BillDetailRepository;
import com.example.spring.CafeManagerApplication.repository.BillRepository;
import com.example.spring.CafeManagerApplication.repository.ProductRepository;
import com.example.spring.CafeManagerApplication.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmailUtils emailUtils;
    @Override
    @Transactional
    public ResponseEntity<?> addNewBill(BillDetailDto billDetailDto) {
        billDetailDto.setCreatedDate(LocalDateTime.now());
        UserEntity user = getUserFromAuth();

        Bill bill = new Bill();

        bill.setName(billDetailDto.getName());
        bill.setEmail(billDetailDto.getEmail());
        bill.setContactNumber(billDetailDto.getContactNumber());
        bill.setPaymentMethod(billDetailDto.getPaymentMethod());
        bill.setUser(user);

        Bill savedBill = billRepository.saveAndFlush(bill);
        billDetailDto.setId(savedBill.getId());

        for(ProductDto productDto  : billDetailDto.getProducts()){

            BillDetailKey billDetailKey = new BillDetailKey(savedBill.getId(),productDto.getId());
            Product product = productRepository.findById(productDto.getId())
                    .orElseThrow();
            BillDetail billDetail = new BillDetail(billDetailKey,savedBill,product,productDto.getQuantity());
            billDetailRepository.save(billDetail);
        }

//        emailUtils.sendInvoiceEmail(savedBill.getEmail(), CafeUtils.createPdf(billDetailDto));

        return new ResponseEntity<>(billDetailDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllBill() {

        UserEntity user = getUserFromAuth();

        List<Bill> bills = billRepository.findAll(Sort.by(Sort.Direction.DESC,"createdDate"));


        List<BillDetailDto> detailDos = bills.stream().map(this::mapBillDetailDto).toList();

        if(detailDos.isEmpty()) return new ResponseEntity<>(new MessageResponse("No data"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(detailDos,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getBillById(Integer id) {
        Optional<Bill> optional = billRepository.findById(id);
        if(optional.isEmpty()) return new ResponseEntity<>(new MessageResponse("Bill not found"), HttpStatus.NOT_FOUND);

        Bill bill = optional.get();

        BillDetailDto billDetailDto = mapBillDetailDto(bill);

        byte[] byteArray =CafeUtils.createPdf(billDetailDto);

        return new ResponseEntity<>(byteArray,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBill(Integer id) {

        Optional<Bill> optional = billRepository.findById(id);

        if(optional.isEmpty()) return new ResponseEntity<>(new MessageResponse("Bill not found"),HttpStatus.BAD_REQUEST);

        Bill bill = optional.get();

        billRepository.delete(bill);
        return new ResponseEntity<>(new MessageResponse("delete successfully"),HttpStatus.NO_CONTENT);
    }

    private BillDetailDto mapBillDetailDto(Bill bill){

        BillDetailDto billDetailDto = new BillDetailDto();

        billDetailDto.setId(bill.getId());
        billDetailDto.setName(bill.getName());
        billDetailDto.setEmail(bill.getEmail());
        billDetailDto.setContactNumber(bill.getContactNumber());
        billDetailDto.setPaymentMethod(bill.getPaymentMethod());
        billDetailDto.setCreatedDate(bill.getCreatedDate());

        List<ProductDto> products = bill.getBillDetails().stream().map( x -> mapProductDto(x.getProduct(),x.getQuantity())).collect(Collectors.toList());



        billDetailDto.setProducts(products);

        return billDetailDto;
    }

    private ProductDto mapProductDto(Product product, Integer quantity){

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
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
