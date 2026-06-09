package App.Repository;

import App.Model.CoffeeShop;
import App.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.http.HttpHeaders.FROM;

@Repository
public interface CoffeeShopRepository extends JpaRepository<CoffeeShop, Long> {
    public List<CoffeeShop> findTop1ByOrderById();


    @Query("FROM CoffeeShop WHERE city = :cityVar")
    public List<CoffeeShop> getCoffeeShopByCity(@Param("cityVar") String city);

    // get all coffee shops with this name
//    @Query("FROM CoffeeShop WHERE name = :nameVar")
//    List<CoffeeShop> getCoffeeShopByName(@Param("nameVar") String name);
//
//    @Query("FROM CoffeeShop WHERE name = :idVar")
//    CoffeeShop getCoffeeShopById(@Param("idVar") String id);




}



