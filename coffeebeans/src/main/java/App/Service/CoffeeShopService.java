package App.Service;

import App.Model.CoffeeShop;
import App.Model.CoffeeShopDTO;
import App.Repository.CoffeeShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CoffeeShopService {

    CoffeeShopRepository coffeeShopRepository;
    int coffee_shop_counter = 0;

    @Autowired
    public CoffeeShopService (CoffeeShopRepository coffeeShopRespository){
        this.coffeeShopRepository = coffeeShopRespository;
    }


    public List<CoffeeShop> getCoffeeShops(){
        return coffeeShopRepository.findAll();
    }

    public Optional<CoffeeShop> getCoffeeShopById(Long id){
        return coffeeShopRepository.findById(id);
    }

    public CoffeeShop addCoffeeShop(CoffeeShop coffeeShop){
        return coffeeShopRepository.save(coffeeShop);
    }
    public CoffeeShop addCoffeeShop(CoffeeShopDTO coffeeShopDTO){
        coffee_shop_counter = coffee_shop_counter + 1;
        return addCoffeeShop(new CoffeeShop(coffee_shop_counter, coffeeShopDTO.getName(), coffeeShopDTO.getCity(), coffeeShopDTO.getState(), coffeeShopDTO.getFullAddress(), coffeeShopDTO.getOutlets(), coffeeShopDTO.getBathrooms(), coffeeShopDTO.getOutdoorSeating(), coffeeShopDTO.getCashOk()));
    }

    public int getTotalCoffeeShops(){
        return getCoffeeShops().size();
    }

    public List<CoffeeShop> getTopRecShops(int count) {

        return coffeeShopRepository.findTop1ByOrderById();
    }

    public List<CoffeeShop> getCoffeeShopsByCity(String city) {
        return coffeeShopRepository.getCoffeeShopByCity(city);
    }
}
