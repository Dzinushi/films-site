package com.mev.films.service.interfaces;

import com.mev.films.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    Long addUser(UserDTO userDTO);
    Long updateUser(UserDTO userDTO);
    Long deleteUser(String login);
}