package com.hackerrank.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Item;
import com.hackerrank.sample.service.InventoryService;

/**
 * .
 */
@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ResponseEntity<?> getAllItems()
    {
        List<Item> itemList=inventoryService.getAllItems();
        if(itemList.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<List>(itemList,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getItemById(@PathVariable Long id)
    {
        Item item=null;
        try
        {
            item=inventoryService.getItemById(id);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Item>(item,HttpStatus.OK);
        }
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ResponseEntity<?> addItem(@RequestBody Item item) throws Exception
    {
        Item item1=null;
        try
        {
            item1=inventoryService.createItem(item);
        }
        catch(BadResourceRequestException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Item>(item,HttpStatus.CREATED);

    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateItem(@RequestBody Item item) throws Exception
    {
        Item item1=null;
        Item item2=null;
        try
        {
            item1=inventoryService.getItemById(item.getSkuId());
            item2=inventoryService.updateItem(item);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Item>(item2,HttpStatus.OK);
    }

    @RequestMapping(value = "/item", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllItems()
    {
        inventoryService.deleteAllItems();
        List<Item> itemList=inventoryService.getAllItems();
        if(itemList.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemById(@PathVariable Long id)
    {
        Item item=null;
        try
        {
            item=inventoryService.getItemById(id);
            inventoryService.deleteItemById(id);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
