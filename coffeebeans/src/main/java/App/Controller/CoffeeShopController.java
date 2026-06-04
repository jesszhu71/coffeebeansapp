package App.Controller;


import App.Model.CoffeeShop;
import App.Model.CoffeeShopDTO;
import App.Service.CoffeeShopService;
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

@RestController
public class CoffeeShopController {
    CoffeeShopService coffeeShopService;


   @Autowired
    public CoffeeShopController(CoffeeShopService coffeeShopService){
       this.coffeeShopService = coffeeShopService;
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
    @PostMapping("/coffeeshop/get/{shop_id}")
    public Optional<CoffeeShop> getCoffeeShop(@PathVariable Long shop_id){
        return coffeeShopService.getCoffeeShopById(shop_id);
    }

    // get all coffee shop
    @PostMapping("/coffeeshop/getAll")
    public List<CoffeeShop> getCoffeeShops(){
        return coffeeShopService.getCoffeeShops();
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
