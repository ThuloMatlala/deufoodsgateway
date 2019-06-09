package com.tprice.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tprice.userManagement.TestHelper;
import com.tprice.userManagement.model.User;
import com.tprice.userManagement.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
//THE FOLLOWING TESTS ARE FOR TESTING WHETHER THE CONTROLLER RETURNS THE REQUIRED OBJECTS IDEALLY.
// I AM TESTING OBJECT MAPPING FROM MODEL TO VIEW.
//  THESE ARE NOT FOR TESTING BUSINESS LOGIC
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private TestHelper testHelper = new TestHelper();

    @Test
    public void addUserShouldReturnCreatedUser() throws Exception {
        User user = testHelper.CreateSingleUser();

        when(userService.SaveUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/users/create")
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }



    @Test
        public void getAllUsersShouldReturnAListOfUsers() throws Exception {
        List<User> userList = testHelper.CreateMultipleUsers();

        when(userService.findAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/users")
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].firstName").value(userList.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(userList.get(0).getLastName()));
        verify(userService, times(1)).findAllUsers();
        verifyNoMoreInteractions(userService);
    }


    @Test
    public void findByIdShouldReturnASingleUser() throws Exception {
        User user = testHelper.CreateSingleUser();
        long userId = 1;
        when(userService.GetOneUserById(userId)).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}",1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).GetOneUserById(userId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void findByLastNameShouldReturnASingleUser() throws Exception {
        List<User> userList = testHelper.CreateMultipleUsers();
        String userLastName = "Test Last Name";
        when(userService.FindUsersByLastName(userLastName)).thenReturn(userList);

        mockMvc.perform(get("/api/users/lastName?lastName={lastName}", userLastName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).FindUsersByLastName(userLastName);
        verifyNoMoreInteractions(userService);
    }
}

//
//
//    @Test
//    public void findByLastName() throws Exception {
//        mockMvc.perform(get("/api/users/lastName?lastName={lastName}", "THULO").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//    }
//
//    @Test
//    public void editUser() throws Exception {
//        testHelper = new TestHelper();
//        JSONObject userDetails = testHelper.AddSingleUserJSONObject();
//
//        mockMvc.perform(put("/api/users/{id}", 1L).
//                contentType(MediaType.APPLICATION_JSON).content(userDetails.toString()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void deleteUser() throws Exception {
//        mockMvc.perform(delete("/api/users/{id}", 1L) .contentType(MediaType.APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
