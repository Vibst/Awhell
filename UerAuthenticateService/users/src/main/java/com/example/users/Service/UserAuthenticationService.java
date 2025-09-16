package com.example.users.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.users.entity.Users;
import com.example.users.models.usersModel;
import com.example.users.repository.UsersAuthenticationRepository;

@Service
public class UserAuthenticationService {

    @Autowired
    private UsersAuthenticationRepository userAuthRepository;

    public Users create(Users model) {

        Users saveUsers = new Users();

        try {
            Users data = new Users();
            data.setConfirmPassword(model.getConfirmPassword());
            data.setEmail(model.getEmail());
            data.setUserName(model.getUserName());
            data.setUserPassword(model.getUserPassword());
            saveUsers = userAuthRepository.save(data);

        } catch (Exception e) {
            System.out.println("Error Are Found" + e.getMessage());

            return null;
        }
        return saveUsers;

    }

    public List<Users> getAllUsers() {
        try {
            List<Users> users = (List<Users>) userAuthRepository.findAll();
            Users uu = new Users();
            for (Users value : users) {
                uu.setConfirmPassword(value.getConfirmPassword());
                uu.setEmail(value.getEmail());
                uu.setUserId(value.getUserId());
                uu.setUserName(value.getUserName());
                uu.setUserPassword(value.getUserPassword());

            }

            return users;
        } catch (Exception e) {
            System.out.println("Error are found :" + e.getMessage());
            return null;
        }
    }

    public Users getUsersById(Integer usrId) {
        try {
            Optional<Users> user = this.userAuthRepository.findById(usrId);
            System.out.println("Starting to sleep");
            Thread.sleep(15000);
            System.out.println("Finished sleeping");

            if (user.isPresent()) {
                return user.get();
            }
            return (Users) Collections.EMPTY_LIST;

        } catch (Exception e) {
            System.out.println("Error are Found:" + e.getMessage());
            return null;
        }

    }

}
