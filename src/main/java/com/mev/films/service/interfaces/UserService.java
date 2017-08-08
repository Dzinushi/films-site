package com.mev.films.service.interfaces;

import com.mev.films.model.UserDTO;
import com.mev.films.model.UserInfoDTO;
import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers(Long number, Long from);
    Long getUsersCount();
    UserDTO getUser(Long id);
    List<UserDTO> getUsersSortByLogin();
    UserDTO getUserByLogin(String login);
    List<UserDTO> getUsersIdLogin();
    void addUser(UserInfoDTO userInfoDTO);
    //void addUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
//    void deleteUser(Long id);
    void deleteUserByLogin(String login);
}