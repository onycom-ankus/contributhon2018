package main.mybatis.dao;

import java.util.List;

import main.mybatis.dto.User;

public interface UserMapper {

    int insertUser(User user);
    User findUserByUserId(String userId);
    List<User> reviewNumFiltering(int rNum);
}
