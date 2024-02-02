package com.bitscoder.swoppiapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product {

    @Id
    private String productId;
    private String productName;
    private String description;

    @ElementCollection
    private List<String> ingredients;
    private double defaultPrice;
    private double salesPrice;
    private String productImageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
        this.productId = generateCustomUUID();
    }

    private String generateCustomUUID() {
        return "swoppiProd"+ UUID.randomUUID().toString().substring(0, 5);
    }
}
