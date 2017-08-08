package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserInfoDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.interfaces.ExceptionService;
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
    @Autowired private UserRoleMapper userRoleMapper;

    @Autowired private ExceptionService exceptionService;

    public UserServiceImpl(){
    }

    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper, ExceptionService exceptionService){
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.exceptionService = exceptionService;
    }

    @Override
    public List<UserDTO> getUsers(Long number, Long from) {
        LOG.debug("getUsers: number = {}, from = {}",
                number, from);

        exceptionService.checkUserNumberFrom(number, from);

        return userMapper.selects(number, from);
    }

    @Override
    public Long getUsersCount() {
        LOG.debug("getUsersCount");

        return userMapper.selectsCount();
    }

    @Override
    public UserDTO getUser(Long id) {
        LOG.debug("getUser: id = {}",
                id);

        exceptionService.checkUserId(id);

        return userMapper.select(id);
    }

    @Override
    public List<UserDTO> getUsersSortByLogin() {
        LOG.debug("getUsersSortByLogin");

        return userMapper.selectsSortByLogin();
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        LOG.debug("getUserByLogin: login = {}",
                login);

        exceptionService.checkUserLogin(login);

        return userMapper.selectByLogin(login);
    }

    @Override
    public List<UserDTO> getUsersIdLogin() {
        LOG.debug("getUsersIdLogin");

        return userMapper.selectsIdLogin();
    }

    @Override
    public void addUser(UserInfoDTO userInfoDTO) {
        LOG.debug("addUser: {}",
                userInfoDTO);

        exceptionService.checkUserInfoWithoutId(userInfoDTO);

        UserDTO userDTO = new UserDTO(userInfoDTO.getLogin(), userInfoDTO.getPassword(), userInfoDTO.getEnabled());
        UserRoleDTO userRoleDTO = new UserRoleDTO(userInfoDTO.getLogin(), userInfoDTO.getRole());

        userMapper.insert(userDTO);
        userRoleMapper.insert(userRoleDTO);
    }

//    @Override
//    public void addUser(UserDTO userDTO) {
//        LOG.debug("addUser: {}",
//                userDTO);
//
//        exceptionService.checkUserWithoutId(userDTO);
//
//        userMapper.insert(userDTO);
//    }

    @Override
    public void updateUser(UserDTO userDTO) {
        LOG.debug("update: userDTO = {}",
                userDTO);

        exceptionService.checkUser(userDTO);

        userMapper.update(userDTO);
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
        LOG.debug("deleteByLogin: login = {}",
                login);

        exceptionService.checkUserLogin(login);

        userMapper.deleteByLogin(login);
    }
}