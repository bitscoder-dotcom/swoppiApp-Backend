package com.bitscoder.swoppiapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    private String categoryId;
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    public Category() {
        this.categoryId = generateCustomUUID();
    }

    private String generateCustomUUID() {
        return "swoppi-cate"+ UUID.randomUUID().toString().substring(0, 5);
    }
}
