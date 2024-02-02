package com.bitscoder.swoppiapp.repository;

import com.bitscoder.swoppiapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {


}
