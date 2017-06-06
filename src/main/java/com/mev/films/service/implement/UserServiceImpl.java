package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LogManager.getLogger();

    @Autowired private UserMapper userMapper;


    @Override
    public List<UserDTO> getAllUsers() {
        LOG.debug("getAllUsers");
        return userMapper.selectUsers();
    }

    @Override
    public Long addUser(UserDTO userDTO) {
        LOG.debug("addUser: id = {}, login = {}, password = {}",
                userDTO.getId(), userDTO.getLogin(), userDTO.getPassword());
        return userMapper.insertUser(userDTO);
    }

    @Override
    public Long updateUser(UserDTO userDTO) {
        LOG.debug("updateUser: id = {}, login = {}, password = {}",
                userDTO.getId(), userDTO.getLogin(), userDTO.getPassword());
        return userMapper.updateUser(userDTO);
    }

    @Override
    public Long deleteUser(String login) {
        UserDTO userDTO = userMapper.selectUserByName(login);
        LOG.debug("deleteUser: id = {}, login = {}, password = {}",
                userDTO.getId(), userDTO.getLogin(), userDTO.getPassword());
        return userMapper.removeUserByLogin(login);
    }
}