package estudos.spring.security.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estudos.spring.security.jwt.dtos.UserDto;
import estudos.spring.security.jwt.model.User;
import estudos.spring.security.jwt.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;
    @PostMapping
    public void postUser(@RequestBody User user){
        service.createUser(user);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = service.getAllUsers().stream().map(UserDto::new).toList();
        return new ResponseEntity<>(users, users.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}