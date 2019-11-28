package com.wowItemsAPI.repository;

import com.wowItemsAPI.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    public List<Item> findAllByOrderByNameAsc();
//    @Query(value = "SELECT * FROM Item i ORDER BY i.name ASC",
//            nativeQuery = true)
//    public List<Item> findAll();
//
//    @Query("SELECT i FROM Item i WHERE i.id = :id")
//    public Item findById(int id);
//
//    @Query("SELECT i FROM Item i WHERE i.name = :name")
//    public Item findByName(@Param("name") String name);
//
//    @Modifying
//    @Query(value = "INSERT INTO Item (name) VALUES (:name)",
//            nativeQuery = true)
//    public void save(@Param("name") String name);
//
//    @Modifying
//    @Query("UPDATE Item i SET i.name = :name WHERE i.name = :name")
//    public void update(@Param("name") String name);
//
//    @Modifying
//    @Query("DELETE FROM Item i WHERE i.id = :id")
//    public void deleteById(@Param("id") int id);
//
//    @Modifying
//    @Query("DELETE FROM Item i WHERE i.name = :name")
//    public void deleteByName(@Param("name") String name);
}
