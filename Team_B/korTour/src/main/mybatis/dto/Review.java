package main.mybatis.dto;

import java.sql.Date;

public class Review {

	private String reviewId;
	private String placeId;
	private String userId;
	private String review;
	private Float score;
	private Date reviewDate; // date type?
	
	public Review(String reviewId, String placeId, String userId, String review, Float score, Date reviewDate) {
		this.reviewId = reviewId;
		this.placeId = placeId;
		this.userId = userId;
		this.review = review;
		this.score = score;
		this.reviewDate = reviewDate;
	}
	
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
}
