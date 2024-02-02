package com.bitscoder.swoppiapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class Option {

    @Id
    private String optionId;
    private String optionName;
    private boolean isSingleChoice;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Option() {
        this.optionId = generateCustomUUID();
    }

    private String generateCustomUUID() {
        return "swoppiOpt"+ UUID.randomUUID().toString().substring(0, 5);
    }
}
