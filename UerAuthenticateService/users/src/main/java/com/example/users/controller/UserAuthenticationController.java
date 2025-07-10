package com.example.users.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.Service.UserAuthenticationService;
import com.example.users.entity.Users;

@RestController
@RequestMapping("/api/v2/users")
public class UserAuthenticationController {

  @Autowired
  private UserAuthenticationService userAuthenticationService;

  @PostMapping("/create")
  public ResponseEntity<Users> create(@RequestBody Users model) {
    Users data = new Users();
    try {
      data = userAuthenticationService.create(model);
      if (data != null) {
        System.out.println("Data is Create in User Id ;" + data.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
      }
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(data);

    } catch (Exception e) {
      System.out.println("Error are found in" + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

  }

  @PostMapping("/listAll")
  public ResponseEntity<List<Users>> allListUser() {
    List<Users> data = new ArrayList<>();
    try {
      data = userAuthenticationService.getAllUsers();
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(data);
    } catch (Exception e) {
      System.out.println("Error are In :" + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }
  }

  @PostMapping("/getUsersById/{userId}")
  public ResponseEntity<Users> getUsersById(@PathVariable("userId") Integer usrId) {
    Users data = new Users();
    try {
      data = userAuthenticationService.getUsersById(usrId);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(data);
    } catch (Exception e) {
      System.out.println("Error are In :" + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }
  }

}
