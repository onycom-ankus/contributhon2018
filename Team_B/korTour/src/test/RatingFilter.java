package test;

import java.util.HashMap;
import java.util.List;

import main.mybatis.dto.FilteredRating;
import main.mybatis.dto.Review;
import main.mybatis.dto.User;
import main.mybatis.service.PlaceService;
import main.mybatis.service.FilteredRatingService;
import main.mybatis.service.ReviewService;
import main.mybatis.service.UserService;

public class RatingFilter {

	public static void main(String[] args){

		
//		PlaceService ps = new PlaceService();
//		List<HashMap<String, Object>> attrCodeList = ps.findAllAttrCode();
//		System.out.println(attrCodeList.size());
		
		
		UserService us = new UserService();
		FilteredRatingService frs = new FilteredRatingService();
		HashMap<Integer,String> FilteredUser = new HashMap<Integer,String>();
		for(User u : us.reviewNumFiltering(30)) {
			FilteredUser.put(u.getindex(), u.getUserId());
		}
		
		ReviewService rs = new ReviewService();
		for(Integer i : FilteredUser.keySet()) { // i,장소id(long으로),value(float)
			for(Review r : rs.findReviewByuserId(FilteredUser.get(i))) {
				FilteredRating fr= new FilteredRating(Long.valueOf(i),Long.parseLong(r.getPlaceId().replaceAll("d","")),r.getScore());
				frs.insertFR(fr);
			}
		}
	
		
		
		
		
		
	}
}



