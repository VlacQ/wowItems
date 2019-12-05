package com.wowItemsAPI.service;

import com.wowItemsAPI.entity.Item;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();

    public Item findById(int id);

    public Item findByName(String name);

    public Boolean save(Item item);

//    public void update(Item item);

    public void deleteById(int id);

//    public void deleteByName(String name);
}
