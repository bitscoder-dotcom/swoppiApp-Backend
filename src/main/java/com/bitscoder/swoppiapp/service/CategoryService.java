package com.bitscoder.swoppiapp.service;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.CategoryDto;
import com.bitscoder.swoppiapp.entities.Category;
import com.bitscoder.swoppiapp.entities.Vendor;
import com.bitscoder.swoppiapp.repository.CategoryRepository;
import com.bitscoder.swoppiapp.repository.VendorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    public ResponseEntity<ApiResponse<CategoryDto.Response>> createCategory(Principal principal, String categoryDtoString) {
        try {

            Vendor vendor = getAuthenticatedVendor();

            ObjectMapper objectMapper = new ObjectMapper();
            CategoryDto categoryDto = objectMapper.readValue(categoryDtoString, CategoryDto.class);

            Category category = convertDtoToEntity(categoryDto);
            category.setVendor(vendor);

            categoryRepository.save(category);

            CategoryDto.Response savedCategoryDto = convertEntityToDto(category);

            ApiResponse<CategoryDto.Response> apiResponse = new ApiResponse<>(
                    LocalDateTime.now(),
                    UUID.randomUUID().toString(),
                    true,
                    "Category created successfully",
                    savedCategoryDto
            );

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error creating category", e);
            throw new RuntimeException("Error creating category", e);
        }
    }

    public ResponseEntity<ApiResponse<List<CategoryDto.Response>>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDto.Response> categoryResponses = categories.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());

            ApiResponse<List<CategoryDto.Response>> apiResponse = new ApiResponse<>(
                    LocalDateTime.now(),
                    UUID.randomUUID().toString(),
                    true,
                    "Categories retrieved successfully",
                    categoryResponses
            );

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error retrieving categories", e);
            throw new RuntimeException("Error retrieving categories", e);
        }
    }

    private Vendor getAuthenticatedVendor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String vendorEmail = authentication.getName();
        return vendorRepository.findVendorByEmail(vendorEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Vendor not found"));
    }

    private Category convertDtoToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        return category;
    }

    private CategoryDto.Response convertEntityToDto(Category category) {
        CategoryDto.Response categoryResponse = new CategoryDto.Response();
        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setCategoryName(category.getCategoryName());
        return categoryResponse;
    }
}
