package main.mybatis.dto;

public class Place {

	private String placeId;
	private String placeName;
	private String placeAddress;
	private String placePhone;
	private Float scoreAverage;
	private Integer totalReview;
	private String keywords; // category
	private String placeImageUrl;
	
	public Place(String placeId, String placeName, String placeAddress, String placePhone, Float scoreAverage,
			Integer totalReview, String keywords, String placeImageUrl) {
		this.placeId = placeId;
		this.placeName = placeName;
		this.placeAddress = placeAddress;
		this.placePhone = placePhone;
		this.scoreAverage = scoreAverage;
		this.totalReview = totalReview;
		this.keywords = keywords;
		this.placeImageUrl = placeImageUrl;
	}
	
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getPlaceAddress() {
		return placeAddress;
	}
	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}
	public String getPlacePhone() {
		return placePhone;
	}
	public void setPlacePhone(String placePhone) {
		this.placePhone = placePhone;
	}
	public Float getScoreAverage() {
		return scoreAverage;
	}
	public void setScoreAverage(Float scoreAverage) {
		this.scoreAverage = scoreAverage;
	}
	public Integer getTotalReview() {
		return totalReview;
	}
	public void setTotalReview(Integer totalReview) {
		this.totalReview = totalReview;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getPlaceImageUrl() {
		return placeImageUrl;
	}
	public void setPlaceImageUrl(String placeImageUrl) {
		this.placeImageUrl = placeImageUrl;
	}
}
