package com.ssg.backendpreassignment.service;

import com.ssg.backendpreassignment.dto.ProductDto;
import com.ssg.backendpreassignment.entity.ProductEntity;
import com.ssg.backendpreassignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly=true)
    public List<ProductDto> getProductList() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (ProductEntity productEntity: productEntities) {
            productDtos.add(productEntity.toDto());
        }

        return productDtos;
    }

    @Transactional(readOnly=true)
    public ProductDto getProduct(Long id) {
        Optional<ProductEntity> productEntityWrapper = productRepository.findById(id);
        ProductEntity productEntity = productEntityWrapper.get();
        return productEntity.toDto();
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        ProductEntity resEntity = productRepository.save(productDto.toEntity());
        return resEntity.toDto();
    }
}
