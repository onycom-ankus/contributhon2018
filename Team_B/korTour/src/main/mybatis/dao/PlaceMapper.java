package main.mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.mybatis.dto.Place;

public interface PlaceMapper {

    void insertPlace(Place place);
    void updatePlace(Place place);
    Place findPlaceByPlaceId(String placeId);
    boolean updateKeyword(Map<String, String> map);
    List<HashMap<String, Object>> findAllAttrCode();
}
