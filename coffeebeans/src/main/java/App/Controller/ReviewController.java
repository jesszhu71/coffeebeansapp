package App.Controller;

import App.Model.Review;
import App.Model.ReviewDTO;
import App.Service.ReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
public class ReviewController {
    ReviewService reviewService;

    @Autowired
    public ReviewController (ReviewService reviewService){

        this.reviewService = reviewService;
        populateReviews();
    }

    @GetMapping("/review/health/")
    public String getHealth(){
        return "review controller health check: ok";
    }

    @PostMapping("/review/add")
    public Review addReview(@RequestBody ReviewDTO reviewDTO){
        return reviewService.addReview(reviewDTO);
    }

    @GetMapping("/review/get/{review_id}")
    public Optional<Review> getReview(@PathVariable Long review_id){
        return reviewService.getReviewById(review_id);
    }

    @GetMapping("/review/getAll")
    public List<Review> getReviews(){
        return reviewService.getReviews();
    }

    // get all reviews for a shop
    @GetMapping("/review/byShop/{shop_id}")
    public List<Review> getReviewsByShopid(@PathVariable Long shop_id){
        return reviewService.getReviewsByShopId(shop_id);
    }

    // get all reviews from a user
    @GetMapping("/review/byUser/{user_id}")
    public List<Review> getReviewsByUserId(@PathVariable Long user_id){
        return reviewService.getReviewsByUserId(user_id);
    }
    // calculate a shop's rating
    @GetMapping("/review/getCoffeeShopRating/{shop_id}")
    public double getCoffeeShopRating(@PathVariable Long shop_id){
        return reviewService.getCoffeeShopRating(shop_id);
    }

    // calculate how critical a user is for their coffeeshops


    @PostMapping("/review/populate")
    public List<Review> populateReviews(){
        List<Review> reviewList = new ArrayList<Review>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream is = new ClassPathResource("ReviewTestData.json").getInputStream();

            //try (InputStream is = new ClassPathResource("ReviewTestData.json").getInputStream()) {
            List<ReviewDTO> reviewDTOList = mapper.readValue(is, new TypeReference<List<ReviewDTO>>() {});
            //}

            // Verify the results
            for (ReviewDTO reviewDTO:reviewDTOList ){
                System.out.println("adding review: " + reviewDTO.getReview());
                reviewList.add(addReview(reviewDTO));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviewList;

    }

}
