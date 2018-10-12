package com.hackerrank.sample.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.model.Item;

/**
 * Created by root on 10/8/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryTest {
    @Autowired
    private WebApplicationContext context;
    private Item item;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getInventoryTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/item").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].skuId").isNumber())
                .andExpect(jsonPath("$[0].productName").isString());
    }
    @Test
    public void getInventoryByIdTest() throws Exception
    {
        item = new Item((long)2, "pencils", "pencil",50,20,5);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/item")
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.skuId").isNumber()).andReturn();
        JSONObject json=new JSONObject(result.getResponse().getContentAsString());
        mockMvc.perform(MockMvcRequestBuilders.get("/item/"+json.get("skuId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.skuId").isNumber())
                .andExpect(jsonPath("$.skuId").value(2))
                .andExpect(jsonPath("$.productName").isString())
                .andExpect(jsonPath("$.productName").value("pencils"));
    }

    @Test
    public void getInventoryInvalidTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/item/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void addItemTest() throws Exception {

        item = new Item((long)3, "pencils", "pencil",50,20,5);
        item.toString();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/item")
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.skuId").isNumber()).andReturn();

        JSONObject json = new JSONObject(result.getResponse().getContentAsString());

        mockMvc.perform(MockMvcRequestBuilders.get("/item/"+json.get("skuId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.skuId").value(json.get("skuId")))
                .andExpect(jsonPath("$.productName").value("pencils"));

    }
    @Test
    public void addItemDuplicateIdTest() throws Exception
    {
        item = new Item((long)7, "pencils", "pencil",50,20,5);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/item")
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.skuId").isNumber()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/item").content(toJson(item)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
    @Test
    public void updateItemTest() throws Exception
    {
        item = new Item((long)2, "pencils", "pencil",50,20,5);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/item")
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.skuId").isNumber()).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        long skuId = (Integer)json.get("skuId");
        item = new Item((long)2,"pencils","pencil",50,20,10);
        mockMvc.perform(MockMvcRequestBuilders.put("/item/"+skuId)
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    public void updateItemByInvalidIdTest() throws Exception
    {
        item = new Item((long)6, "pens", "pen",50,20,10);
        mockMvc.perform(MockMvcRequestBuilders.put("/item/"+6)
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    @Test
    public void deleteItemByIdTest() throws Exception {

        //add a resource
        item = new Item((long)4, "pencils", "pencil",50,20,5);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/item")
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.skuId").isNumber()).andReturn();

        //delete the resource
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        long skuId = (Integer)json.get("skuId");
        item.setSkuId(skuId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/item/"+skuId)
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        //confirm deletion
        mockMvc.perform(MockMvcRequestBuilders.get("/item/"+skuId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
    @Test
    public void deleteItemByInvalidIdTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/item/"+0)
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    @Test
    public void deleteAllItems() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/item")
                .content(toJson(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
