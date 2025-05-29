package estudos.spring.security.jwt.dtos;

import java.util.ArrayList;
import java.util.List;

import estudos.spring.security.jwt.model.User;

public class UserDto {
    private String username;
    private String name;
    private List<String> roles = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(String username, String name, List<String> roles) {
        this.username = username;
        this.name = name;
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    public UserDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        roles = user.getRoles() != null ? user.getRoles() : new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}