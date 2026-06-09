package App.Service;

import App.Model.Review;
import App.Model.ReviewDTO;
import App.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    ReviewRepository reviewRepository;
    int review_counter = 0;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public Review addReview(ReviewDTO reviewDTO){
        return addReview(new Review(review_counter, reviewDTO.getUser_id(), reviewDTO.getShop_id(), reviewDTO.getReview(), reviewDTO.getRating()));
    }

    public Review addReview(Review review){
        return reviewRepository.save(review);
    }
    
    public Optional<Review> getReviewById(Long id){
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByShopId(Long shop_id){
        return reviewRepository.getReviewsByShopId(shop_id);
    }

    public List<Review> getReviewsByUserId(Long user_id){
        return reviewRepository.getReviewsByUserId(user_id);
    }

    public double getCoffeeShopRating(Long shop_id){
        return reviewRepository.getRatingByShopId(shop_id);
    }


    public Review getTopReviewsByShopId(long id) {
        return reviewRepository.getTopReviewByShopId(id).get(0);
    }

    public Long getTopShopByRating() {
        return reviewRepository.getTopRatedShop().get(0);
    }
}
