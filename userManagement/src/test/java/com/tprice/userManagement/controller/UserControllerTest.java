package com.tprice.userManagement.controller;

import com.tprice.userManagement.TestHelper;
import com.tprice.userManagement.model.User;
import com.tprice.userManagement.model.UserService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    private User user;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private TestHelper testHelper;

    @Before
    public void setUp(){
        user = mock(User.class);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void addUser() throws Exception
        {
        testHelper = new TestHelper();
        JSONObject userDetails = testHelper.AddSingleUser();

        mockMvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON).
                        content(userDetails.toString())).andExpect(status().isOk());
    }

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")).andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/api/users/{id}",1L).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void findByLastName() throws Exception {
        mockMvc.perform(get("/api/users/lastName?lastName={lastName}", "THULO").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void editUser() throws Exception {
        testHelper = new TestHelper();
        JSONObject userDetails = testHelper.AddSingleUser();

        mockMvc.perform(put("/api/users/{id}", 1L).
                contentType(MediaType.APPLICATION_JSON).content(userDetails.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1L) .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}