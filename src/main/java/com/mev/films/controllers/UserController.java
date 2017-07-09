package com.mev.films.controllers;


import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired private UserService userService;

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() {
        LOG.debug("getAllUsers");
        return userService.getAllUsers();
    }

    @RequestMapping(value = {"/api/users/user"}, method = RequestMethod.GET)
    public UserDTO getUser(@RequestParam String login) {
        LOG.debug("getUser: login = {}",
                login);
        return userService.getUser(login);
    }

    @RequestMapping(value = {"/api/users/roles"}, method = RequestMethod.GET)
    public List<UserRoleDTO> getUserRoles() {
        LOG.debug("getUserRoles");
        return userService.getUserRoles();
    }

    @RequestMapping(value = {"/api/users/role"}, method = RequestMethod.GET)
    public UserRoleDTO getUserRole(Long id){
        LOG.debug("getUserRole: id = {}",
                id);
        return userService.getUserRole(id);
    }

    @RequestMapping(value = {"/api/users/login"}, method = RequestMethod.GET)
    public UserRoleDTO getUserRoleByLogin(@RequestParam String login) {
        LOG.debug("getUserRole: login = {}",
                login);
        return userService.getUserRoleByLogin(login);
    }

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.POST)
    public void addUser(@RequestBody UserDTO userDTO, @RequestBody UserRoleDTO userRoleDTO) {
        LOG.debug("addUser: id = {}, login = {}, password = {}, role = {}",
                userDTO.getId(), userDTO.getLogin(), userDTO.getPassword(), userRoleDTO.getRole());
        userService.addUser(userDTO, userRoleDTO);
    }

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserDTO userDTO) {
        LOG.debug("updateUser: id = {}, login = {}, password = {}",
                userDTO.getId(), userDTO.getLogin(), userDTO.getPassword());
        userService.updateUser(userDTO);
    }

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.PUT)
    public void updateUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        LOG.debug("updateRole: id = {}, login = {}, role = {}",
                userRoleDTO.getId(), userRoleDTO.getLogin(), userRoleDTO.getRole());
        userService.updateUserRole(userRoleDTO);
    }

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam Long id) {
        LOG.debug("deleteUser: id = {}",
                id);
        userService.deleteUser(id);
    }

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.DELETE)
    public void deleteUserByLogin(@RequestParam String login) {
        LOG.debug("deleteUserByLogin: login = {}",
                login);
        userService.deleteUserByLogin(login);
    }
}