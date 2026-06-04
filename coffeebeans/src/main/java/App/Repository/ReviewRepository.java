package App.Repository;

import App.Model.CoffeeShop;
import App.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("FROM Review WHERE shop_id = :shop_idVar")
    public List<Review> getReviewsByShopid(@Param("shop_idVar") Long shop_id);

    @Query("FROM Review WHERE user_id = :user_idVar")
    public List<Review> getReviewsByUserId(@Param("user_idVar") Long user_id);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.shop_id = :shop_idVar")
    public double getRatingByShopId(@Param("shop_idVar") Long shop_id);



}
