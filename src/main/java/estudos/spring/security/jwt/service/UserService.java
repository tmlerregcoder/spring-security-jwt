package estudos.spring.security.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import estudos.spring.security.jwt.model.User;
import estudos.spring.security.jwt.repository.UserRepository;

@Service
public class UserService {
      @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    public void createUser(User user){
        String pass = user.getPassword();
        //criptografando antes de salvar no banco
        user.setPassword(encoder.encode(pass));
        repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

}
