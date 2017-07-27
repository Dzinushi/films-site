package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserMapper;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LogManager.getLogger();

    @Autowired private UserMapper userMapper;

    public UserServiceImpl(){
    }

    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getUsers() {
        LOG.debug("getUsers");

        return userMapper.selectUsers();
    }

    @Override
    public UserDTO getUser(Long id) {
        LOG.debug("getUser: id = {}",
                id);

        UserDTO userDTO = null;
        if (id != null && id >=0){
             userDTO = userMapper.selectUser(id);
        }
        else {
            LOG.debug("Error in 'getUser'! 'id' is not validate: id = {}",
                    id);
        }

        return userDTO;
    }

    @Override
    public List<UserDTO> getUsersSortByLogin() {
        LOG.debug("getUsersSortByLogin");

        return userMapper.selectUsersSortByLogin();
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        LOG.debug("getUserByLogin: login = {}",
                login);

        UserDTO userDTO = null;
        if (login != null){
            userDTO = userMapper.selectUserByLogin(login);
        }
        else {
            LOG.debug("Error in 'getUserByLogin'! 'login' is null");
        }

        return userDTO;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        LOG.debug("addUser: {}",
                userDTO);

        if (userDTO != null){
            if (userDTO.getLogin() != null){
                if (userDTO.getPassword() != null){
                    if (userDTO.getEnabled() >= 0){

                        userMapper.insertUser(userDTO);
                    }
                    else {
                        LOG.debug("Error in 'addUser'! 'enabled' < 0 ({})");
                    }
                }
                else {
                    LOG.debug("Error in 'addUser'! 'password is null");
                }
            }
            else {
                LOG.debug("Error in 'addUser'! 'login' is null");
            }
        }
        else {
            LOG.debug("Error in 'addUser'! 'userDTO' is null");
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        LOG.debug("updateUser: userDTO = {}",
                userDTO);

        if (userDTO != null){
            if (userDTO.getId() != null && userDTO.getId() >= 0) {
                if (userDTO.getLogin() != null) {
                    if (userDTO.getPassword() != null) {
                        if (userDTO.getEnabled() >= 0) {

                            userMapper.updateUser(userDTO);

                        } else {
                            LOG.debug("Error in 'updateUser'! 'enabled' < 0 ({})");
                        }
                    } else {
                        LOG.debug("Error in 'updateUser'! 'password is null");
                    }
                } else {
                    LOG.debug("Error in 'updateUser'! 'login' is null");
                }
            }
            else {
                LOG.debug("Error in 'updateUser'! 'id' is not validate: id = {}",
                        userDTO.getId());
            }
        }
        else {
            LOG.debug("Error in 'updateUser'! 'userDTO' is null");
        }
    }

//    @Override
//    public void deleteUser(Long id) {
//        LOG.debug("deleteUser: id = {}",
//                id);
//
//        if (id != null && id >= 0){
//            userMapper.deleteUser(id);
//        }
//        else {
//            LOG.debug("Error in 'deleteUser'! 'id' is not validate: id = {}",
//                    id);
//        }
//    }

    @Override
    public void deleteUserByLogin(String login) {
        LOG.debug("deleteUserByLogin: login = {}",
                login);

        if (login != null){
            userMapper.deleteUserByLogin(login);
        }
        else {
            LOG.debug("Error in 'deleteUserByLogin'! 'login' is null");
        }
    }
}