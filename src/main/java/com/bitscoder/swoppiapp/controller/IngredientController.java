//package com.bitscoder.swoppiapp.controller;
//
//import com.bitscoder.swoppiapp.dto.ApiResponse;
//import com.bitscoder.swoppiapp.dto.IngredientDto;
//import com.bitscoder.swoppiapp.service.IngredientService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.security.Principal;
//
//@Controller
//@RequestMapping("/swoppiApp/v1/ingredients")
//@AllArgsConstructor
//public class IngredientController {
//
//    private IngredientService ingredientService;
//
//    @PostMapping("add-ingredient")
//    public ResponseEntity<ApiResponse<IngredientDto.Response>> createIngredient(Principal principal, @RequestBody String ingredientDtoString) {
//        return ingredientService.createIngredient(principal, ingredientDtoString);
//    }
//}
