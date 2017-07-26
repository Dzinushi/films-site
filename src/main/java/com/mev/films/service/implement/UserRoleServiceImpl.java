package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.interfaces.UserRoleService;
import com.mev.films.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

    private Logger LOG = LogManager.getLogger();

    @Autowired private UserRoleMapper userRoleMapper;

    @Autowired private UserService userService;

    public UserRoleServiceImpl(){
    }

    public UserRoleServiceImpl(UserRoleMapper userRoleMapper, UserService userService){
        this.userRoleMapper = userRoleMapper;
        this.userService = userService;
    }

    @Override
    public List<UserRoleDTO> getUserRoles() {

        LOG.debug("getUserRoles");

        return userRoleMapper.selectUserRoles();
    }

    @Override
    public UserRoleDTO getUserRole(Long id) {

        LOG.debug("getUserRole: id = {}",
                id);

        UserRoleDTO userRoleDTO = null;
        if (id != null && id >= 0){
            userRoleDTO = userRoleMapper.selectUserRole(id);
        }
        else {
            LOG.debug("Error in 'getUserRole'! 'id' is not validate: id = {}",
                    id);
        }

        return userRoleDTO;
    }

    @Override
    public UserRoleDTO getUserRoleByLogin(String login) {

        LOG.debug("getUserRoleByLogin: login = {}",
                login);

        UserRoleDTO userRoleDTO = null;
        if (login != null){
            userRoleDTO = userRoleMapper.selectUserRoleByLogin(login);
        }
        else {
            LOG.debug("Error in 'getUserRoleByLogin'! 'login' is not validate: login = {}",
                    login);
        }

        return userRoleDTO;
    }

    @Override
    public void addUserRole(UserRoleDTO userRoleDTO) {

        LOG.debug("addUserRole: {}",
                userRoleDTO);

        if (userRoleDTO != null){
            if (userRoleDTO.getLogin() != null) {

                UserDTO userDTO = userService.getUserByLogin(userRoleDTO.getLogin());
                if (userDTO != null) {
                    if (userRoleDTO.getRole() != null) {

                        userRoleMapper.insertUserRole(userRoleDTO);

                    } else {
                        LOG.debug("Error in 'addUserRole'! 'role' is null");
                    }
                }
                else {
                    LOG.debug("Error in 'addUserRole'! User is not exist");
                }
            }
            else {
                LOG.debug("Error in 'addUserRole'! 'login' is null");
            }
        }
        else {
            LOG.debug("Error in 'addUserRole'! 'userRoleDTO' is null");
        }
    }

    @Override
    public void updateUserRole(UserRoleDTO userRoleDTO) {

        LOG.debug("updateUserRole: {}",
                userRoleDTO);

        if (userRoleDTO != null){
            if (userRoleDTO.getId() != null && userRoleDTO.getId() >= 0){
                if (userRoleDTO.getLogin() != null) {

                    UserDTO userDTO = userService.getUserByLogin(userRoleDTO.getLogin());
                    if (userDTO != null) {
                        if (userRoleDTO.getRole() != null) {

                            userRoleMapper.updateUserRole(userRoleDTO);

                        } else {
                            LOG.debug("Error in 'updateUserRole'! 'role' is null");
                        }
                    }
                    else {
                        LOG.debug("Error in updateUserRole: that user is not exist");
                    }
                }
                else {
                    LOG.debug("Error in 'updateUserRole'! 'login' is null");
                }
            }
            else {
                LOG.debug("Error in 'updateUserRole'! 'id' is not validate: id = {}",
                        userRoleDTO.getId());
            }
        }
        else {
            LOG.debug("Error in 'updateUserRole'! 'userRoleDTO' is null");
        }
    }
}
