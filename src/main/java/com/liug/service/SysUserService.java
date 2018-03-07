package com.liug.service;

import com.liug.model.dto.LoginInfo;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.SysUser;

import java.io.Serializable;

/**
 * @Author: liug
 * @Date : 2016/10/7
 */
public interface SysUserService {

    long insertUser(SysUser user);

    long insertUser(SysUser user, String jobIds, String permissionIds);

    boolean isExistLoginName(String loginName);

    void deleteById(long id);

    SysUser selectById(long id);

    boolean isExistLoginNameExcludeId(long id, String loginName);

    void updateUser(SysUser user, String jobIds, String permissionIds);

    PageInfo selectPage(int page, int rows, String sort, String order, String loginName, String zhName, String email, String phone, String address);

    void updateUser(SysUser user);

    SysUser selectByLoginName(String loginName);

    LoginInfo login(SysUser user, Serializable id, int platform);

    LoginInfo login(String username, String password, boolean isMantis);

    LoginInfo login(String username, String password);

}
