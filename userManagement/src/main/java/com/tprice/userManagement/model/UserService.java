package com.tprice.userManagement.model;

import com.tprice.userManagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User AddUser(User user)
    {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        userRepo.findAll().forEach(usersList::add);
        return usersList;
    }

    public User GetUserById(long id){
        return userRepo.getOne(id);
    }

    public List<User> GetUsersBySurname(String lastName) {
        return userRepo.findByLastName(lastName);
    }

    public User EditUser(Long id, User newUserDetails) {
        User userToUpdate = userRepo.getOne(id);
        userToUpdate.setFirstName(newUserDetails.getFirstName());
        userToUpdate.setLastName(newUserDetails.getLastName());
        userToUpdate.setPhone(newUserDetails.getPhone());
        userToUpdate.setEmail(newUserDetails.getEmail());
        userToUpdate.setPosition(newUserDetails.getPosition());
        userToUpdate.setCompanyName(newUserDetails.getCompanyName());
        userToUpdate.setTradingName(newUserDetails.getTradingName());
        userToUpdate.setCdibGrade(newUserDetails.getCdibGrade());
        return userRepo.save(newUserDetails);
    }

    public void DeleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
