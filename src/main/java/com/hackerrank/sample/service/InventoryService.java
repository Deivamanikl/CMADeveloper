package com.hackerrank.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hackerrank.sample.model.Item;

/**
 * .
 */
@Service
public interface InventoryService{

    List<Item> getAllItems();
    Item getItemById(Long id);
    Item createItem(Item item);
    Item updateItem(Item item);
    void deleteItemById(Long id);
    void deleteAllItems();
}
