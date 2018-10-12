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
import com.hackerrank.sample.model.Vendor;

/**
 * Created by root on 10/8/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VendorTest {
    @Autowired
    private WebApplicationContext context;
    private Vendor vendor;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllVendorsTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/vendor").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].vendorId").isNumber())
                .andExpect(jsonPath("$[0].vendorName").isString());
    }
    @Test
    public void getVendorByIdTest() throws Exception
    {
        vendor = new Vendor((long)2,"kaushikh",(long)123456,"kaushikh@1234.com","kaushikh","hyd");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendor")
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendorId").isNumber()).andReturn();
        JSONObject json=new JSONObject(result.getResponse().getContentAsString());
        mockMvc.perform(MockMvcRequestBuilders.get("/vendor/"+json.get("vendorId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.vendorId").isNumber())
                .andExpect(jsonPath("$.vendorId").value(2))
                .andExpect(jsonPath("$.vendorName").isString())
                .andExpect(jsonPath("$.vendorName").value("kaushikh"));
    }

    @Test
    public void getVendorInvalidTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/vendor/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void addVendorTest() throws Exception {

        vendor = new Vendor((long)2,"kaushikh",(long)123456,"kaushikh@1234.com","kaushikh","hyd");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendor")
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendorId").isNumber()).andReturn();

        JSONObject json = new JSONObject(result.getResponse().getContentAsString());

        mockMvc.perform(MockMvcRequestBuilders.get("/vendor/"+json.get("vendorId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.vendorId").value(json.get("vendorId")))
                .andExpect(jsonPath("$.vendorName").value("kaushikh"));

    }
    @Test
    public void addItemDuplicateIdTest() throws Exception
    {
        vendor =  new Vendor((long)8,"savitha",(long)123456,"test@sgfjksd.com","test","hyd");
        vendor.toString();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendor")
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendorId").isNumber()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/vendor").content(toJson(vendor)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
    @Test
    public void updateVendorTest() throws Exception
    {
        vendor =  new Vendor((long)3,"test",(long)123456,"test@sgfjksd.com","test","hyd");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendor")
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendorId").isNumber()).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        long vendorId = (Integer)json.get("vendorId");
        vendor = new Vendor((long)2,"test",(long)123456,"test@sgfjksd.com","test","chennai");
        mockMvc.perform(MockMvcRequestBuilders.put("/vendor/"+vendorId)
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    public void updateVendorByInvalidIdTest() throws Exception
    {
        vendor = new Vendor((long)10,"test",(long)123456,"test@sgfjksd.com","kaushikh","hyd");
        mockMvc.perform(MockMvcRequestBuilders.put("/vendor/"+10)
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    @Test
    public void deleteVendorByIdTest() throws Exception {

        //add a resource
        vendor = new Vendor((long)2,"test",(long)123456,"test@sgfjksd.com","test","hyd");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendor")
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendorId").isNumber()).andReturn();

        //delete the resource
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        long vendorId = (Integer)json.get("vendorId");
        vendor.setVendorId(vendorId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/vendor/"+vendorId)
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        //confirm deletion
        mockMvc.perform(MockMvcRequestBuilders.get("/vendor/"+vendorId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void deleteVendorByInvalidIdTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/vendor/"+0)
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    @Test
    public void deleteAllVendors() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/vendor")
                .content(toJson(vendor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
