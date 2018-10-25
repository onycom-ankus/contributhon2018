package main.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import main.mybatis.dao.FilteredRatingMapper;
import main.mybatis.dto.FilteredRating;
import main.mybatis.util.MybatisSqlSessionFactory;

public class FilteredRatingService {
	
	public void insertFR(FilteredRating fr) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			FilteredRatingMapper mapper = sqlSession.getMapper(FilteredRatingMapper.class);
			mapper.insertFR(fr);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	

	
	
}
