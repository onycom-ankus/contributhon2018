package main.mybatis.dao;


import java.util.HashMap;
import java.util.List;

import main.mybatis.dto.FilteredPlace;

public interface FilteredPlaceMapper {

	void insertFP(FilteredPlace fp);
	
	public List<HashMap<String, Object>> findAllPlace();
	
}