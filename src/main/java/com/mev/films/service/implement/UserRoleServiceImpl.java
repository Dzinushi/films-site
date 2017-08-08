package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.interfaces.ExceptionService;
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
    @Autowired private ExceptionService exceptionService;

    public UserRoleServiceImpl(){
        this.exceptionService = new ExceptionServiceImpl();
    }

    public UserRoleServiceImpl(UserRoleMapper userRoleMapper, UserService userService, ExceptionService exceptionService){
        this.userRoleMapper = userRoleMapper;
        this.userService = userService;
        this.exceptionService = exceptionService;
    }

    @Override
    public List<UserRoleDTO> getUserRoles(Long number, Long from) {
        LOG.debug("getUserRoles: number = {}, from = {}",
                number, from);

        exceptionService.checkUserRolesNumberFrom(number, from);

        return userRoleMapper.selects(number, from);
    }

    @Override
    public Long getUserRolesCount() {
        LOG.debug("getUserRolesCount");

        return userRoleMapper.selectsCount();
    }

    @Override
    public UserRoleDTO getUserRole(Long id) {

        LOG.debug("getUserRole: id = {}",
                id);

        exceptionService.checkUserRoleId(id);

        return userRoleMapper.select(id);
    }

    @Override
    public UserRoleDTO getUserRoleByLogin(String login) {

        LOG.debug("getUserRoleByLogin: login = {}",
                login);

        exceptionService.checkUserRoleLogin(login);

        return userRoleMapper.selectByLogin(login);
    }

//    @Override
//    public void addUserRole(UserRoleDTO userRoleDTO) {
//
//        LOG.debug("addUserRole: {}",
//                userRoleDTO);
//
//        exceptionService.checkUserRoleWithoutId(userRoleDTO);
//
//        userRoleMapper.insert(userRoleDTO);
//    }

    @Override
    public void updateUserRole(UserRoleDTO userRoleDTO) {

        LOG.debug("update: {}",
                userRoleDTO);

        exceptionService.checkUserRole(userRoleDTO);

        userRoleMapper.update(userRoleDTO);
    }
}
