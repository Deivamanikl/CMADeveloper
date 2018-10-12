package com.hackerrank.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Item;
import com.hackerrank.sample.repository.ItemRepository;

/**
 * .
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ItemRepository itemRepository;
    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        Item item =itemRepository.findOne(id);
        if(item==null)
        {
            throw new NoSuchResourceFoundException("No Item with given id found");
        }
        return item;
    }

    @Override
    public Item createItem(Item item) {
        Item item1=itemRepository.findOne(item.getSkuId());
        if(item1!=null)
        {
            throw new BadResourceRequestException("Item with same id exists");
        }
        itemRepository.save(item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        Item item1=itemRepository.findOne(item.getSkuId());
        if(item1==null)
        {
            throw new NoSuchResourceFoundException("No Item with given id found");
        }
        item1.setSkuId(item.getSkuId());
        item1.setProductName(item.getProductName());
        item1.setProductLabel(item.getProductLabel());
        item1.setInventoryOnHand(item.getInventoryOnHand());
        item1.setMinQtyReq(item.getMinQtyReq());
        item1.setPrice(item.getPrice());
        itemRepository.save(item1);
        return item1;
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.delete(id);
    }

    @Override
    public void deleteAllItems() {
        itemRepository.deleteAllInBatch();
    }
}
