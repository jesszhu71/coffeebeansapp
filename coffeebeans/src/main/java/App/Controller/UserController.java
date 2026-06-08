package App.Controller;


import App.Model.*;
import App.Service.UserService;
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
public class UserController {
    UserService userService;
    
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user/health/")
    public String getHealth(){
        return "user controller health check: ok";
    }

    @PostMapping("/user/add")
    public UserAccount addUser(@RequestBody UserAccountDTO userAccountDTO){
        return userService.addUser(userAccountDTO);
    }

    @PostMapping("/user/get/{user_id}")
    public Optional<UserAccount> getUser(@PathVariable Long user_id){
        return userService.getUserById(user_id);
    }

    @PostMapping("/user/getAll")
    public List<UserAccount> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/user/populate")
    public List<UserAccount> populateUsers(){
        List<UserAccount> userAccountList = new ArrayList<UserAccount>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream is = new ClassPathResource("UserTestData.json").getInputStream();
            List<UserAccountDTO> userAccountDTOList = mapper.readValue(is, new TypeReference<List<UserAccountDTO>>() {});
            //}

            // Verify the results
            for (UserAccountDTO userAccountDTO : userAccountDTOList){
                System.out.println("adding user: " + userAccountDTO.getName());
                userAccountList.add(addUser(userAccountDTO));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userAccountList;
    }
    
    
    
    
}
