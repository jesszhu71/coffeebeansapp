package App.Service;

import App.Model.UserAccount;
import App.Model.UserAccountDTO;
import App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    UserRepository userRepository;
    int user_counter = 0;

    @Autowired
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserAccount> getUsers(){
        return userRepository.findAll();
    }

    public Optional<UserAccount> getUserById(Long id){
        return userRepository.findById(id);
    }

    public UserAccount addUser(UserAccount userAccount){
        return userRepository.save(userAccount);
    }

    public UserAccount addUser(UserAccountDTO userAccountDTO){
        user_counter = user_counter + 1;
        return addUser(new UserAccount(user_counter, userAccountDTO.getName(), userAccountDTO.getBirthday()));
    }
}
