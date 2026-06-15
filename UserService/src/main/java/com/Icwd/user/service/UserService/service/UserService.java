package com.Icwd.user.service.UserService.service;

import com.Icwd.user.service.UserService.entities.User;

import java.util.List;

public interface UserService {
    // User operations

    // Create
    User saveUser(User user);

    // Get all users
    List<User> getAllUsers();

    // Get single user of given userID
    User getUser(String userId);

    // Delete single user of given userID
    User deleteUser(String userId);

    // Update single user of given userID
    User updateUser(User user, String userId);
}
