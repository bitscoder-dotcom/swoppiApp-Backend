package com.bitscoder.swoppiapp.controller;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.ProductDto;
import com.bitscoder.swoppiapp.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("swoppi-app/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<ApiResponse<ProductDto.Response>> createProduct(@RequestBody String productDtoString, Principal principal) {
        return productService.createProduct(productDtoString, principal);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            String imageUrl = productService.uploadImage(file, principal);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading image", e);
        }
    }
}
