package com.mev.films.mappers.interfaces;

import com.mev.films.model.UserDTO;

import java.util.List;

public interface UserMapper {
    List<UserDTO> selectsAll();
    List<UserDTO> selects(Long limit, Long offset);
    Long selectsCount();
    UserDTO select(Long id);
    UserDTO selectIdLogin(Long id);
    List<UserDTO> selectsIdLogin();
    List<UserDTO> selectsSortByLogin();
    UserDTO selectByLogin(String login);
    void insert(UserDTO userDTO);
    void update(UserDTO userDTO);
    void deleteByLogin(String login);
}