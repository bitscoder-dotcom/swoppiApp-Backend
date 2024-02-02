package com.bitscoder.swoppiapp.repository;

import com.bitscoder.swoppiapp.entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, String> {
}
