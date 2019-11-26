package com.wowItemsAPI.repository;

import com.wowItemsAPI.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {}
