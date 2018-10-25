package main.mybatis.dto;

public class FilteredPlace {

	private int index;
	private Long placeid;
	private String placename; 
	
	public FilteredPlace(int index, Long placeid, String placename) {
		this.index = index;
		this.placeid = placeid;
		this.placename = placename;	
	}
	
	public FilteredPlace(Long placeid, String placename) {
		this.placeid = placeid;
		this.placename = placename;	
	}
	
	public int getindex() {
		return index;
	}
	public void setindex(int index) {
		this.index = index;
	}
	
	public Long getplaceid() {
		return placeid;
	}
	public void setplaceid(Long placeid) {
		this.placeid = placeid;
	}
	
	public String placename() {
		return placename;
	}
	public void setplacename(String placename) {
		this.placename = placename;
	}
	
	
}
