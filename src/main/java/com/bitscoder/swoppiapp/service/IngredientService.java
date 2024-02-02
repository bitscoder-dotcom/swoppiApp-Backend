//package com.bitscoder.swoppiapp.service;
//
//import com.bitscoder.swoppiapp.dto.ApiResponse;
//import com.bitscoder.swoppiapp.dto.IngredientDto;
//import com.bitscoder.swoppiapp.entities.Ingredient;
//import com.bitscoder.swoppiapp.entities.Vendor;
//import com.bitscoder.swoppiapp.repository.IngredientRepository;
//import com.bitscoder.swoppiapp.repository.VendorRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.security.Principal;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class IngredientService {
//
//    private IngredientRepository ingredientRepository;
//    private VendorRepository vendorRepository;
//
//    public ResponseEntity<ApiResponse<IngredientDto.Response>> createIngredient(Principal principal, String ingredientDtoString) {
//        try {
//
//            Vendor vendor = vendorRepository.findVendorByEmail(principal.getName())
//                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            IngredientDto ingredientDto = objectMapper.readValue(ingredientDtoString, IngredientDto.class);
//
//            Ingredient ingredient = convertDtoToEntity(ingredientDto);
//            ingredient.setVendor(vendor);
//
//            // save the Ingredient
//            ingredientRepository.save(ingredient);
//
//            // convert entity back to DTO.Response
//            IngredientDto.Response savedIngredientDto = convertEntityToDto(ingredient);
//
//            // create ApiResponse
//            ApiResponse<IngredientDto.Response> apiResponse = new ApiResponse<>(
//                    LocalDateTime.now(),
//                    UUID.randomUUID().toString(),
//                    true,
//                    "Ingredient created successfully",
//                    savedIngredientDto
//            );
//
//            return ResponseEntity.ok(apiResponse);
//        } catch (Exception e) {
//            log.error("Error creating Ingredient", e);
//            throw new RuntimeException("Error creating Ingredient", e);
//        }
//
//    }
//
//    private Ingredient convertDtoToEntity(IngredientDto ingredientDto) {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setIngredientName(ingredientDto.getIngredientName());
//        return ingredient;
//    }
//
//    private IngredientDto.Response convertEntityToDto(Ingredient Ingredient) {
//        IngredientDto.Response IngredientResponse = new IngredientDto.Response();
//        IngredientResponse.setIngredientId(Ingredient.getIngredientId());
//        IngredientResponse.setIngredientName(Ingredient.getIngredientName());
//        return IngredientResponse;
//    }
//}
