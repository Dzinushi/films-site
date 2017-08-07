package com.mev.films.mappers.interfaces;


import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserRoleMapper {
    List<UserRoleDTO> select();
    UserRoleDTO select(Long id);
    UserRoleDTO selectByLogin(String login);
    void insert(UserRoleDTO userRoleDTO);
    void update(UserRoleDTO userRoleDTO);
}
