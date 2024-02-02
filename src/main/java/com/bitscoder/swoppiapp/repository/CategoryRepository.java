package com.bitscoder.swoppiapp.repository;

import com.bitscoder.swoppiapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
