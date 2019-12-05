package com.wowItemsAPI.service;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.repository.ItemRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository){this.itemRepository = itemRepository;}


    @Override
    public List<Item> findAll() {
        List<Item> itemList = itemRepository.findAllByOrderByNameAsc();
        return itemList;
    }

    @Override
    public Item findById(int id) {
        Optional<Item> result = itemRepository.findById(id);

        Item item = null;

        if (result.isPresent()) {
            item = result.get();
        }

        return item;
    }

    @Override
    public Item findByName(String name) {
        return itemRepository.findItemByName(name);
    }

    @Override
    public Boolean save(Item item) {
        try {
            itemRepository.save(item);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

//    @Override
//    public void deleteByName(String name) {
//        itemRepository.deleteByName(name);
//    }
}
