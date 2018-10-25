package main.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import main.mybatis.dao.PlaceMapper;
import main.mybatis.dto.Place;
import main.mybatis.util.MybatisSqlSessionFactory;

public class PlaceService {
	
	public void insertPlace(Place Place) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			PlaceMapper mapper = sqlSession.getMapper(PlaceMapper.class);
			if (mapper.findPlaceByPlaceId(Place.getPlaceId()) != null) { // if place already exists 
				addKeyword(Place.getPlaceId(), Place.getKeywords());
			}
			else
				mapper.insertPlace(Place);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public Place findPlaceByPlaceId(String placeId) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			PlaceMapper mapper = sqlSession.getMapper(PlaceMapper.class);
			return mapper.findPlaceByPlaceId(placeId);
		} finally {
			sqlSession.close();
		}
	}
	
	public boolean addKeyword(String placeId, String tagCode) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			PlaceMapper mapper = sqlSession.getMapper(PlaceMapper.class);
			Place p = mapper.findPlaceByPlaceId(placeId);
			String keyword = p.getKeywords();
			if (keyword.contains(tagCode)) return false;
			else {
				keyword += "," + tagCode;
				Map<String, String> map = new HashMap<String, String>();
				map.put("placeId", placeId);
				map.put("keyword", keyword);
				mapper.updateKeyword(map);
				sqlSession.commit();
				return true;
			}
		} finally {
			sqlSession.close();
		}
	}
	
	public boolean removeKeyword(String placeId, String tagCode) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			PlaceMapper mapper = sqlSession.getMapper(PlaceMapper.class);
			Place p = mapper.findPlaceByPlaceId(placeId);
			String keyword = p.getKeywords();
			if (!keyword.contains(tagCode)) return false;
			else {
				keyword = keyword.replace(","+tagCode, "");
				keyword = keyword.replace(tagCode+",", "");
				Map<String, String> map = new HashMap<String, String>();
				map.put(placeId, placeId);
				map.put(keyword, keyword);
				mapper.updateKeyword(map);
				sqlSession.commit();
				return true;
			}
		} finally {
			sqlSession.close();
		}
	}
	public List<HashMap<String, Object>> findAllAttrCode(){
    	SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			PlaceMapper mapper = sqlSession.getMapper(PlaceMapper.class);
			List<HashMap<String, Object>> map = mapper.findAllAttrCode();
			return map;
		} finally {
			sqlSession.close();
		}
    }
}
