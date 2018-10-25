package main.mybatis.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import main.mybatis.dao.FilteredPlaceMapper;
import main.mybatis.dao.PlaceMapper;
import main.mybatis.dto.FilteredPlace;
import main.mybatis.util.MybatisSqlSessionFactory;

public class FilteredPlaceService {
	
	public void insertFP(FilteredPlace fp) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			FilteredPlaceMapper mapper = sqlSession.getMapper(FilteredPlaceMapper.class);
			mapper.insertFP(fp);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public List<HashMap<String, Object>> findAllPlace(){
    	SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			FilteredPlaceMapper mapper = sqlSession.getMapper(FilteredPlaceMapper.class);
			List<HashMap<String, Object>> map = mapper.findAllPlace();
			return map;
		} finally {
			sqlSession.close();
		}
    }
	
	
}