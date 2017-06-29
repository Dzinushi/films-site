package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LogManager.getLogger();

    @Autowired private UserMapper userMapper;
    @Autowired private UserRoleMapper userRoleMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        LOG.debug("getAllUsers");
        return userMapper.selectUsers();
    }

    @Override
    public UserDTO getUser(String login) {
        LOG.debug("getUser: login = {}",
                login);
        return userMapper.selectUserByLogin(login);
    }

    @Override
    public List<UserRoleDTO> getUserRoles() {
        LOG.debug("getUserRoles");
        return userRoleMapper.selectUserRoles();
    }

    @Override
    public List<UserRoleDTO> getUserRole(String login) {
        LOG.debug("getUserRole: login = {}",
                login);
        return userRoleMapper.selectUserRolesByLogin(login);
    }

    @Override
    public void addUser(UserDTO userDTO, UserRoleDTO userRoleDTO) {
        LOG.debug("addUser: id = {}, login = {}, password = {}, role = {}",
                userDTO.getId(), userDTO.getLogin(), userDTO.getPassword(), userRoleDTO.getRole());
        userMapper.insertUser(userDTO);
    }

    @Override
    public void addUserRole(UserRoleDTO userRoleDTO) {
        LOG.debug("addRole: id = {}, login = {}, role = {}",
                userRoleDTO.getId(), userRoleDTO.getLogin(), userRoleDTO.getRole());
        userRoleMapper.insertUserRole(userRoleDTO);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        LOG.debug("updateUser: id = {}, login = {}, password = {}",
                userDTO.getId(), userDTO.getLogin(), userDTO.getPassword());
        userMapper.updateUser(userDTO);
    }

    @Override
    public void updateUserRole(UserRoleDTO userRoleDTO) {
        LOG.debug("updateRole: id = {}, login = {}, role = {}",
                userRoleDTO.getId(), userRoleDTO.getLogin(), userRoleDTO.getRole());
        userRoleMapper.updateUserRole(userRoleDTO);
    }

    @Override
    public void deleteUser(Long id) {
        LOG.debug("deleteUser: id = {}",
                id);
        userMapper.deleteUser(id);
    }

    @Override
    public void deleteUserByLogin(String login) {
        LOG.debug("deleteUserByLogin: login = {}",
                login);
        userMapper.deleteUserByLogin(login);
    }
}