package com.wowItemsAPI.service;

import com.wowItemsAPI.entity.Item;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();

    public Item findById(int id);

    public Item findByName(String name);

    public void save(String name);

    public void update(String name);

    public void deleteById(int id);

    public void deleteByName(String name);
}
