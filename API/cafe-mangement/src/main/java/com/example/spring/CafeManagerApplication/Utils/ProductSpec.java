package com.example.spring.CafeManagerApplication.Utils;

import com.example.spring.CafeManagerApplication.entity.Category;
import com.example.spring.CafeManagerApplication.entity.Product;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpec {
    public static final String CATEGORY = "category";
    public static final String ORDER_BY = "id";
    public static final String PRICE = "price";

    private ProductSpec(){

    }

    public static Specification<Product> filterBy(ProductFilters productFilters) {
        return Specification
                .where(hasCategory(productFilters.getCategory()))
                .and(orderByPrice(productFilters.getPrice()));
    }

    public static Specification<Product> filterBy(ProductFilters productFilters, boolean status) {
        return Specification
                .where(hasCategory(productFilters.getCategory()))
                .and(orderByPrice(productFilters.getPrice()))
                .and(filtersByStatus(status));
    }

    public static Specification<Product> hasCategory(String name) {
        return (root, query, cb) -> {
            if(name.isEmpty()) {
                query.orderBy(cb.desc(root.get("id")));
                return cb.conjunction();
            }
            Join<Category,Product> productCategory = root.join("category");
            return cb.equal(productCategory.get("name"),name);
        };
    }

    public static Specification<Product> orderByPrice(String orderBy) {
        return (root, query, cb) -> {
            if(orderBy.equals("cheapest")) {
                query.orderBy(cb.asc(root.get("price")));
                return cb.conjunction();
            }else if(orderBy.equals("expensive")) {
                query.orderBy(cb.desc(root.get("price")));
                return cb.conjunction();
            }
            return cb.conjunction();
        };
    }

    public static Specification<Product> filtersByStatus(boolean status) {
        return (root, query, cb) -> {
            return cb.equal(root.get("status"),status);
        };
    }

//    public static Specification<Product> orderBy(String orderBy) {
//        return (root, query, cb) -> {
//            if(orderBy.equals("oldest")) {
//                query.orderBy(cb.asc(root.get("id")));
//                return cb.conjunction();
//            }
//            query.orderBy(cb.desc(root.get("id")));
//            return cb.conjunction();
//        };
//    }

}
