package main.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import main.mybatis.dao.ReviewMapper;
import main.mybatis.dao.UserMapper;
import main.mybatis.dto.Review;
import main.mybatis.util.MybatisSqlSessionFactory;

public class ReviewService {
	
	public void insertReview(Review review) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			ReviewMapper mapper = sqlSession.getMapper(ReviewMapper.class);
			if (mapper.findReviewByReviewId(review.getReviewId()) != null) { // if review already exists 
//				System.out.println("Already stored review: " + review.getUserId());
				return;
			}
			mapper.insertReview(review);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public Review findReviewByReviewId(String reviewId) {
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			ReviewMapper mapper = sqlSession.getMapper(ReviewMapper.class);
			return mapper.findReviewByReviewId(reviewId);
		} finally {
			sqlSession.close();
		}
	}
	
	public List<Review> findReviewByuserId(String userId){
		SqlSession sqlSession = MybatisSqlSessionFactory.openSession();
		try {
			ReviewMapper mapper = sqlSession.getMapper(ReviewMapper.class);
			return mapper.findReviewByuserId(userId);
		} finally {
			sqlSession.close();
		}
	}
	
	
}
