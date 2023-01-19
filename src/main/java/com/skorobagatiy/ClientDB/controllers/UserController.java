package com.skorobagatiy.ClientDB.controllers;

import com.skorobagatiy.ClientDB.exceptions.GenericSystemException;
import com.skorobagatiy.ClientDB.models.Car;
import com.skorobagatiy.ClientDB.models.User;
import com.skorobagatiy.ClientDB.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LogManager.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        log.debug("GET /users");
        List<User> users = userService.getUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User userRequest) {
        log.debug("POST /users");
        User user = userService.createUser(userRequest);
        log.debug("Create user id={}", user.getId());
        log.error("Create user id={}", user.getId());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) throws GenericSystemException {
        log.debug("GET /users/{}", userId);

        User user = userService.getUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) throws GenericSystemException {
        log.debug("DELETE /users/{}", userId);

        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User updateUserRequest)
            throws GenericSystemException {
        log.debug("PATCH /users/{}", userId);

        User user = userService.updateUser(userId, updateUserRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/car")
    public ResponseEntity<User> addCarForUser(@PathVariable int userId, @RequestBody Car carRequest)
            throws GenericSystemException {
        log.debug("GET /users/{}/car", userId);
        log.debug("Add car {} for userId={}", carRequest.toString(),userId);

        User user = userService.addCarForUser(userId, carRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
