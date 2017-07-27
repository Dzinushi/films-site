package com.mev.films.controllers;


import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.interfaces.UserRoleService;
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
    @Autowired private UserRoleService userRoleService;

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.GET)
    public List<UserDTO> getUsers() {
        LOG.debug("getUsers");

        return userService.getUsers();
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public UserDTO getUser(Long id){
        LOG.debug("getUser: id = {}",
                id);

        return userService.getUser(id);
    }

    @RequestMapping(value = {"/api/users/login"}, method = RequestMethod.GET)
    public UserDTO getUserByLogin(@RequestParam String login) {
        LOG.debug("getUserByLogin: login = {}",
                login);

        return userService.getUserByLogin(login);
    }

    @RequestMapping(value = {"/api/users/roles"}, method = RequestMethod.GET)
    public List<UserRoleDTO> getUserRoles() {
        LOG.debug("getUserRoles");

        return userRoleService.getUserRoles();
    }

    @RequestMapping(value = {"/api/users/role"}, method = RequestMethod.GET)
    public UserRoleDTO getUserRole(Long id){
        LOG.debug("getUserRole: id = {}",
                id);

        return userRoleService.getUserRole(id);
    }

    @RequestMapping(value = {"/api/users/roles/login"}, method = RequestMethod.GET)
    public UserRoleDTO getUserRoleByLogin(@RequestParam String login) {
        LOG.debug("getUserRole: login = {}",
                login);

        return userRoleService.getUserRoleByLogin(login);
    }

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.POST)
    public void addUser(@RequestBody UserDTO userDTO,
                        @RequestBody UserRoleDTO userRoleDTO) {
        LOG.debug("addUser: userDTO = {}, userRoleDTO = {}",
                userDTO, userRoleDTO);

        userService.addUser(userDTO);
        userRoleService.addUserRole(userRoleDTO);
    }

    @RequestMapping(value = {"/api/users"}, method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserDTO userDTO) {
        LOG.debug("updateUser: userDTO = {}",
                userDTO);

        userService.updateUser(userDTO);
    }

    @RequestMapping(value = {"/api/users/role"}, method = RequestMethod.PUT)
    public void updateUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        LOG.debug("updateRole: userDTO = {}, userRoleDTO = {}",
                userRoleDTO, userRoleDTO);

        userRoleService.updateUserRole(userRoleDTO);
    }

//    @RequestMapping(value = {"/api/users"}, method = RequestMethod.DELETE)
//    public void deleteUser(@RequestParam Long id) {
//        LOG.debug("deleteUser: id = {}",
//                id);
//
//        userService.deleteUser(id);
//    }

    @RequestMapping(value = {"/api/users/login"}, method = RequestMethod.DELETE)
    public void deleteUserByLogin(@RequestParam String login) {
        LOG.debug("deleteUserByLogin: login = {}",
                login);

        userService.deleteUserByLogin(login);
    }
}