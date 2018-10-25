package main.mybatis.dao;

import java.util.List;

import main.mybatis.dto.Review;

public interface ReviewMapper {

    void insertReview(Review review);
    Review findReviewByReviewId(String reviewId);
    List<Review> findReviewByuserId(String userId);
}
