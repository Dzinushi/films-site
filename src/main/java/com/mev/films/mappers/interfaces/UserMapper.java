package com.mev.films.mappers.interfaces;

import com.mev.films.model.UserDTO;

import java.math.BigInteger;
import java.util.List;

public interface UserMapper {
    List<UserDTO> selectUsers();
    UserDTO selectUserByName(String userName);
    BigInteger insertUser(UserDTO userDTO);
    BigInteger updateUser(UserDTO userDTO);
    BigInteger removeUserByName(String userName);
}