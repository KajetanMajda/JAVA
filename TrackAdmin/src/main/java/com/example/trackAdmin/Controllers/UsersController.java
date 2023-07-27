package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Users;
import com.example.trackAdmin.Respositories.UsersRepository;
import com.example.trackAdmin.Classes.UsersRole;
import com.example.trackAdmin.Respositories.UsersRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path= "/user")
public class UsersController {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UsersRoleRepository usersRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name,
                                           @RequestParam String surname,
                                           @RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String role) {

        UsersRole usersRole = usersRoleRepository.findByName(role);


        Users user = new Users();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(usersRole);
        usersRole.getUsers().add(user);

        userRepository.save(user);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return userRepository.findAll();
    }
}