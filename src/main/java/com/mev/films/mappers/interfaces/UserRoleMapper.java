package com.mev.films.mappers.interfaces;


import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserRoleMapper {
    List<UserRoleDTO> selectsAll();
    List<UserRoleDTO> selects(Long limit, Long offset);
    Long selectsCount();
    UserRoleDTO select(Long id);
    UserRoleDTO selectByLogin(String login);
    void insert(UserRoleDTO userRoleDTO);
    void update(UserRoleDTO userRoleDTO);
}
