package com.wowItemsAPI.service;

import com.wowItemsAPI.entity.Price;

import java.util.List;

public interface PriceService {
    public List<Price> findAll();

    public Price findById(Long id);

    public boolean save(Price price);

    public void deleteById(Long id);
}
