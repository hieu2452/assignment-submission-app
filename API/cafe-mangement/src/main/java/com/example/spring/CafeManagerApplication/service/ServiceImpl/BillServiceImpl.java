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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = (UserEntity) authentication.getPrincipal();

        Bill bill = new Bill();

        bill.setName(billDetailDto.getName());
        bill.setEmail(billDetailDto.getEmail());
        bill.setContactNumber(billDetailDto.getContactNumber());
        bill.setPaymentMethod(billDetailDto.getPaymentMethod());
        bill.setUser(user);

        Bill savedBill = billRepository.saveAndFlush(bill);

        for(ProductDto productDto  : billDetailDto.getProducts()){
            System.out.println("CHECKKKKKKK");
            BillDetailKey billDetailKey = new BillDetailKey(savedBill.getId(),productDto.getProductId());
            Product product = productRepository.findById(productDto.getProductId())
                    .orElseThrow();
            BillDetail billDetail = new BillDetail(billDetailKey,savedBill,product,productDto.getQuantity());
            billDetailRepository.save(billDetail);
        }

        return new ResponseEntity<>("Create bill successfully", HttpStatus.OK);
    }
}
