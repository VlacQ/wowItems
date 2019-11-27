//package com.wowItemsAPI.repository;
//
//import com.wowItemsAPI.entity.Item;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//public class ItemRepositoryImpl implements ItemRepository {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public List<Item> findAll() {
//        return entityManager.createQuery("from Item").getResultList();
//    }
//
//    @Override
//    public Item findById(int id) {
//        return entityManager.find(Item.class, id);
//    }
//
//    @Override
//    public Item findByName(String name) {
//        return entityManager.find(Item.class, name);
//    }
//
//    @Override
//    public void save(String name) {
//
//    }
//
//    @Override
//    public void update(String name) {
//
//    }
//
//    @Override
//    public void deleteById(int id) {
//
//    }
//
//    @Override
//    public void deleteByName(String name) {
//
//    }
//}
