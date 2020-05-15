package cn.sdut.dao;

import cn.sdut.domain.User;

import java.util.List;

public interface UserDao {
    /**
     * 登录时使用手机号查找用户
     * @param phone 手机号
     * @return 用户信息
     */
    User findUserByPhone(String phone);
    User findUserById(Integer id);
    List<User> findAllUser();
    int addUser(User user);
    int deleteUser(Integer id);
    int updateUser(User user);
}
