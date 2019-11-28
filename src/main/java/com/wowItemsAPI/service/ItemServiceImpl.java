package com.wowItemsAPI.service;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.repository.ItemRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (itemList.isEmpty()){
            itemList = new ArrayList<>();
            Item item = new Item();
            item.setId(0);
            item.setName("Table item is empty");
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public Item findById(int id) {
        Optional<Item> result = itemRepository.findById(id);

        Item item = null;

        if (result.isPresent()) {
            item = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return item;
    }

    @Override
    public Item findByName(String name) {
        return new Item();
//        return itemRepository.findByName(name);
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

//    @Override
//    public void update(Item item) {
//        itemRepository.update(item);
//    }

    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

//    @Override
//    public void deleteByName(String name) {
//        itemRepository.deleteByName(name);
//    }
}
