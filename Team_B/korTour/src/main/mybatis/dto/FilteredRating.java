package main.mybatis.dto;

public class FilteredRating {

	private Long userindex;
	private Long placeid;
	private float score; 
	
	public FilteredRating(Long userindex, Long placeid, float score) {
		this.userindex = userindex;
		this.placeid = placeid;
		this.score = score;	
	}
	
	public Long getuserindex() {
		return userindex;
	}
	public void setuserindex(Long userindex) {
		this.userindex = userindex;
	}
	
	public Long getplaceid() {
		return placeid;
	}
	public void setplaceid(Long placeid) {
		this.placeid = placeid;
	}
	
	public float getscore() {
		return score;
	}
	public void setscore(float score) {
		this.score = score;
	}
	
	
}
