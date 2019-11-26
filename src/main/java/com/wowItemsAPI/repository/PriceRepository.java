package com.wowItemsAPI.repository;

import com.wowItemsAPI.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Integer> {}
