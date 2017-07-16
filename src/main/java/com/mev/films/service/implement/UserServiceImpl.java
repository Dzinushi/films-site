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

    public UserServiceImpl(){
    }

    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper){
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<UserDTO> getUsers() {
        LOG.debug("getUsers");

        return userMapper.selectUsers();
    }

    @Override
    public List<UserDTO> getUsersSortByLogin() {
        LOG.debug("getUsersSortByLogin");

        return userMapper.selectUsersSortByLogin();
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
    public UserRoleDTO getUserRole(Long id){
        LOG.debug("getUserRole: id = {}",
                id);

        return userRoleMapper.selectUserRole(id);
    }

    @Override
    public UserRoleDTO getUserRoleByLogin(String login) {
        LOG.debug("getUserRole: login = {}",
                login);

        return userRoleMapper.selectUserRoleByLogin(login);
    }

    @Override
    public void addUser(UserDTO userDTO, UserRoleDTO userRoleDTO) {
        LOG.debug("addUser: userDTO = {}, userRoleDTO = {}",
                userDTO, userRoleDTO);

        userMapper.insertUser(userDTO, userRoleDTO);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        LOG.debug("updateUser: userDTO = {}",
                userDTO);

        userMapper.updateUser(userDTO);
    }

    @Override
    public void updateUserRole(UserRoleDTO userRoleDTO) {
        LOG.debug("updateRole: userRoleDTO = {}",
                userRoleDTO);

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