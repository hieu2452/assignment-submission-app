package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.dto.BillRequestDto;
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
    public ResponseEntity<?> addNewBill(BillRequestDto billRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = (UserEntity) authentication.getPrincipal();

        Bill bill = new Bill();

        bill.setName(billRequestDto.getName());
        bill.setEmail(billRequestDto.getEmail());
        bill.setContactNumber(billRequestDto.getContactNumber());
        bill.setPaymentMethod(billRequestDto.getPaymentMethod());
        bill.setUser(user);

        Bill savedBill = billRepository.saveAndFlush(bill);

        for(ProductIdDto productIdDto : billRequestDto.getProducts()){
            BillDetailKey billDetailKey = new BillDetailKey(savedBill.getId(),productIdDto.getProductId());
            Product product = productRepository.findById(productIdDto.getProductId())
                    .orElseThrow();
            BillDetail billDetail = new BillDetail(billDetailKey,savedBill,product,productIdDto.getQuantity());
            billDetailRepository.save(billDetail);
        }

        return new ResponseEntity<>("Create bill successfully", HttpStatus.OK);
    }
}
