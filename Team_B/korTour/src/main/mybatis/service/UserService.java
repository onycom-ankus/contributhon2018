package main.mybatis.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import main.mybatis.dao.UserMapper;
import main.mybatis.dto.User;
import main.mybatis.util.MybatisSqlSessionFactory;

public class UserService {
	
	public void insertUser(User user) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			if (mapper.findUserByUserId(user.getUserId()) != null) { // if user already exists 
				System.out.println("Already stored user id: " + user.getUserId());
				return;
			}
			mapper.insertUser(user);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public User findUserByUserId(String userId) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			return mapper.findUserByUserId(userId);
		} finally {
			sqlSession.close();
		}
	}
	
	public List<User> reviewNumFiltering(int rNum){
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			return mapper.reviewNumFiltering(rNum);
		} finally {
			sqlSession.close();
		}
	}
	
	
	
}
