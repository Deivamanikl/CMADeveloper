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
import com.hackerrank.sample.Application;
import com.hackerrank.sample.model.Customer;

/**
 * Created by root on 10/8/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest {
    @Autowired
    private WebApplicationContext context;
    private Customer customer;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void main() {
        Application.main(new String[] {});
    }
    @Test
    public void getAllCustomersTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].customerId").isNumber())
                .andExpect(jsonPath("$[0].customerName").isString());

    }

    @Test
    public void getCustomerByIdTest() throws Exception
    {
        customer = new Customer((long)5,"test",(long)123456,"Hyd","F");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNumber()).andReturn();
        JSONObject json=new JSONObject(result.getResponse().getContentAsString());
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/"+json.get("customerId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.customerId").isNumber())
                .andExpect(jsonPath("$.customerId").value(5))
                .andExpect(jsonPath("$.customerName").isString())
                .andExpect(jsonPath("$.customerName").value("test"));
    }
    @Test
    public void getCustomerByIdInvalidTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void addCustomerTest() throws Exception {

        customer = new Customer((long)4,"test",(long)123456,"Hyd","F");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNumber()).andReturn();

        JSONObject json = new JSONObject(result.getResponse().getContentAsString());

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/"+json.get("customerId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.customerId").value(json.get("customerId")))
                .andExpect(jsonPath("$.customerName").value("test"));

    }

    @Test
    public void addCustomerTestDuplicateId() throws Exception
    {
        //add e;em,
        customer = new Customer((long)6,"test",(long)123456,"Hyd","F");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNumber()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/customer").content(toJson(customer)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
    @Test
    public void updateCustomerTest() throws Exception
    {
        customer = new Customer((long)8,"test",(long)123456,"Hyd","F");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNumber()).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        long customerId = (Integer)json.get("customerId");
        customer = new Customer((long)8,"test",(long)123456,"chennai","M");
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/"+customerId)
        .content(toJson(customer))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    public void updateCustomerByInvalidIdTest() throws Exception
    {
        customer = new Customer((long)7,"test",(long)123456,"Hyd","F");
        customer.toString();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNumber()).andReturn();
        Customer customer1 = new Customer((long)10,"test",(long)123456,"HYD","M");
        Long customerId=(long)10;
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/"+customerId)
                .content(toJson(customer1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    @Test
    public void deleteCustomerByIdTest() throws Exception {

        //add a resource
        customer = new Customer((long)5,"test",(long)123456,"Hyd","F");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNumber()).andReturn();

        //delete the resource
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        long customerId = (Integer)json.get("customerId");
        customer.setCustomerId(customerId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/"+customerId)
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        //confirm deletion
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/"+customerId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void deleteCustomerByInvalidIdTest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/"+0)
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void  deleteAllCustomers() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

}
