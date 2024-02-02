package com.bitscoder.swoppiapp.service;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.ProductDto;
import com.bitscoder.swoppiapp.entities.*;
import com.bitscoder.swoppiapp.repository.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

    private ModelMapper modelMapper;
    private ProductRepository productRepository;
    private VendorRepository vendorRepository;
    private Cloudinary cloudinary;
    private OptionRepository optionRepository;
    private CategoryRepository categoryRepository;

    public ResponseEntity<ApiResponse<ProductDto.Response>> createProduct(String productDtoString, Principal principal) {
        try {

            String email = principal.getName();
            Vendor vendor = vendorRepository.findVendorByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));

            ObjectMapper objectMapper = new ObjectMapper();
            ProductDto productDto = objectMapper.readValue(productDtoString, ProductDto.class);

            Product product = convertDtoToEntity(productDto);

            product.setVendor(vendor);

            productRepository.save(product);

            ProductDto.Response savedProductDto = convertEntityToDto(product);

            ApiResponse<ProductDto.Response> apiResponse = new ApiResponse<>(
                    LocalDateTime.now(),
                    UUID.randomUUID().toString(),
                    true,
                    "Product created successfully",
                    savedProductDto
            );

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new RuntimeException("Error creating product", e);
        }
    }

    public String uploadImage(MultipartFile file, Principal principal) {
        try {
            log.info("Upload Image method called with file: {}", file.getOriginalFilename());

            String email = principal.getName();
            log.info("Authenticated vendor email: {}", email);

            Vendor vendor = vendorRepository.findVendorByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));

            log.info("Compressing image...");
            InputStream compressedImage = compressImage(file);

            log.info("Uploading image...");
            Map uploadResult = cloudinary.uploader().upload(compressedImage.readAllBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            log.info("Image uploaded successfully. Image URL: {}", imageUrl);

            log.info("Creating new product with uploaded image...");
            Product product = new Product();
            product.setProductImageUrl(imageUrl);
            product.setVendor(vendor);

            log.info("Saving product...");
            productRepository.save(product);
            log.info("Product saved successfully.");

            return imageUrl;
        } catch (IOException e) {
            log.error("Error uploading image", e);
            throw new RuntimeException("Error uploading image", e);
        }
    }


    private InputStream compressImage(MultipartFile file) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream())
                .size(200, 200)
                .outputQuality(0.25)
                .toOutputStream(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private Product convertDtoToEntity(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setDefaultPrice(productDto.getDefaultPrice());
        product.setSalesPrice(productDto.getSalesPrice());
        product.setProductImageUrl(productDto.getProductImageUrl());

        Vendor vendor = vendorRepository.findById(productDto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        product.setVendor(vendor);

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        List<Option> options = optionRepository.findAllById(productDto.getOptionIds());
        product.setOptions(options);

        product.setIngredients(productDto.getIngredients());

        return product;
    }

    private ProductDto.Response convertEntityToDto(Product product) {
        ProductDto.Response productDto = modelMapper.map(product, ProductDto.Response.class);

        productDto.setVendorId(product.getVendor().getUserId());

        productDto.setCategoryId(product.getCategory().getCategoryId());

        List<String> optionIds = product.getOptions().stream()
                .map(Option::getOptionId)
                .collect(Collectors.toList());
        productDto.setOptionIds(optionIds);

        productDto.setIngredients(product.getIngredients());

        return productDto;
    }
}
