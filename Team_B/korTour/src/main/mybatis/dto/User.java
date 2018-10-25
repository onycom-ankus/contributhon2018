package main.mybatis.dto;

public class User {

	private String userId;
	private Integer fromAge;
	private Integer toAge;
	private String gender;
	private String residence;
	private Integer reviewNum;
	private Integer reviewHelpful;
	private Integer index;
	
	
	public User(String userId, Integer fromAge, Integer toAge, String gender, String residence, Integer reviewNum,
			Integer reviewHelpful) {
		this.userId = userId;
		this.fromAge = fromAge;
		this.toAge = toAge;
		this.gender = gender;
		this.residence = residence;
		this.reviewNum = reviewNum;
		this.reviewHelpful = reviewHelpful;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getFromAge() {
		return fromAge;
	}
	public void setFromAge(Integer fromAge) {
		this.fromAge = fromAge;
	}
	public Integer getToAge() {
		return toAge;
	}
	public void setToAge(Integer toAge) {
		this.toAge = toAge;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public Integer getReviewNum() {
		return reviewNum;
	}
	public void setReviewNum(Integer reviewNum) {
		this.reviewNum = reviewNum;
	}
	public Integer getReviewHelpful() {
		return reviewHelpful;
	}
	public void setReviewHelpful(Integer reviewHelpful) {
		this.reviewHelpful = reviewHelpful;
	}
	
	public Integer getindex() {
		return index;
	}
	public void setindex(Integer index) {
		this.index = index;
	}
	
	
	
}
