//package com.bitscoder.swoppiapp.entities;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//
//import java.util.UUID;
//
//@Entity
//@Data
//@AllArgsConstructor
//@Builder
//public class Ingredient {
//
//    @Id
//    private String ingredientId;
//    private String ingredientName;
//
//    @ManyToOne
//    @JoinColumn(name = "vendor_id")
//    private Vendor vendor;
//
//    public Ingredient() {
//        this.ingredientId = generateCustomUUID();
//    }
//
//    private String generateCustomUUID() {
//        return "swoppiIngr"+ UUID.randomUUID().toString().substring(0, 5);
//    }
//}
