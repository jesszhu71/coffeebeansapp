package App.Controller;


import App.Model.CoffeeShop;
import App.Model.CoffeeShopDTO;
import App.Model.Review;
import App.Service.CoffeeShopService;
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
public class CoffeeShopController {
    CoffeeShopService coffeeShopService;
    ReviewService reviewService;


   @Autowired
    public CoffeeShopController(CoffeeShopService coffeeShopService, ReviewService reviewService){
       this.coffeeShopService = coffeeShopService;
       this.reviewService = reviewService;
       populateCoffeeShops();
   }

   //@RequestMapping(value = '', method = RequestMethod.GET)
    @GetMapping("/coffeeshop/health/")
    public String getHealth(){
       return "coffeeshop controller health check: ok";
    }


    // insert coffee shop
    @PostMapping("/coffeeshop/add")
    public CoffeeShop addCoffeeShop(@RequestBody CoffeeShopDTO coffeeShopDTO){
        return coffeeShopService.addCoffeeShop(coffeeShopDTO);
    }

    // retrieve coffee shop
    @GetMapping("/coffeeshop/get/{shop_id}")
    public Optional<CoffeeShop> getCoffeeShop(@PathVariable Long shop_id){
        return coffeeShopService.getCoffeeShopById(shop_id);
    }

    // get all coffee shop
    @GetMapping("/coffeeshop/getAll")
    public List<CoffeeShop> getCoffeeShops(){
        return coffeeShopService.getCoffeeShops();
    }

    @GetMapping("/coffeeshop/getCount")
    public int getTotalCoffeeShops(){
        return coffeeShopService.getTotalCoffeeShops();
    }
//    @GetMapping("/coffeeshop/getTopRecShops/{count}")
//    public List<CoffeeShop> getTopRecShops(@PathVariable int count){
//        return coffeeShopService.getTopRecShops(1);
//    }
    @GetMapping("/coffeeshop/getTopRecShop")
    public Optional<CoffeeShop> getTopRecShops(){ // gets the coffee shop of highest rating
        // return coffeeShopService.getTopRecShops(1).get(0);
        Long id = reviewService.getTopShopByRating();
        return coffeeShopService.getCoffeeShopById(id);

    }
    @GetMapping("/coffeeshop/getByCity/{city}")
    public List<CoffeeShop> getCoffeeShopsByCity(@PathVariable String city){
        return coffeeShopService.getCoffeeShopsByCity(city);
    }

    @GetMapping("/review/getTopRecShopReview")
    public Review getTopRecShopReview(){ // returns the top review of the top shop
       Optional<CoffeeShop> coffeeShop = getTopRecShops();
       return reviewService.getTopReviewsByShopId(coffeeShop.get().getId());
    }

    @GetMapping("/review/getTopRecShopRating")
    public double getTopRecShopRating(){
        Optional<CoffeeShop> coffeeShop = getTopRecShops();
        return reviewService.getCoffeeShopRating(coffeeShop.get().getId());
    }




    // populate default coffee shop population
    @PostMapping("/coffeeshop/populate")
    public List<CoffeeShop> populateCoffeeShops(){
        List<CoffeeShop> coffeeShopList = new ArrayList<CoffeeShop>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream is = new ClassPathResource("CoffeeShopTestData.json").getInputStream();

            //try (InputStream is = new ClassPathResource("CoffeeShopTestData.json").getInputStream()) {
            List<CoffeeShopDTO> coffeeShopDTOList = mapper.readValue(is, new TypeReference<List<CoffeeShopDTO>>() {});
        //}

            // Verify the results
            for (CoffeeShopDTO coffeeShopDTO:coffeeShopDTOList ){
                System.out.println("adding coffee shop: " + coffeeShopDTO.getName());
                coffeeShopList.add(addCoffeeShop(coffeeShopDTO));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return coffeeShopList;

    }



}
