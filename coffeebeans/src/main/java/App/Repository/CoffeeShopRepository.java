package App.Repository;

import App.Model.CoffeeShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoffeeShopRepository extends JpaRepository<CoffeeShop, Long> {

    // get all coffee shops with this name
//    @Query("FROM CoffeeShop WHERE name = :nameVar")
//    List<CoffeeShop> getCoffeeShopByName(@Param("nameVar") String name);
//
//    @Query("FROM CoffeeShop WHERE name = :idVar")
//    CoffeeShop getCoffeeShopById(@Param("idVar") String id);




}



