package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Users;
import com.example.trackAdmin.Respositories.UsersRepository;
import com.example.trackAdmin.Classes.UsersRole;
import com.example.trackAdmin.Respositories.UsersRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;


@Controller
@RequestMapping(path= "/user")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

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

        usersRepository.save(user);
        return "Saved";
    }

    @PostMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String email,
                                      @RequestParam String password) {
        Users user = usersRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "Zalogowano";
        } else {
            return "Błędny email lub hasło";
        }
    }

    @PostMapping(path="/checkRole")
    public @ResponseBody Boolean checkUserRole(@RequestParam String email,
                                               @RequestParam String role){
        Users user = usersRepository.findByEmail(email);
        if(user != null && user.getRole() != null){
            return user.getRole().getName().equals(role);
        }else{
            return false;
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUser(@RequestParam String email){
        Users user = usersRepository.findByEmail(email);
        if(user != null){
            usersRepository.delete(user);
            return "Usunięto konto -> " + email;
        }
        else{
            return "Something Went Wrong";
        }
    }

    @InitBinder //Ta cała funkcja zamienia mi text role na obiekt UsersRole
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UsersRole.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                UsersRole usersRole = usersRoleRepository.findByName(text);
                setValue(usersRole);
            }
        });
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateUser(@RequestParam Integer id,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String surname,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) UsersRole role) {
        Users user = usersRepository.findById(id).orElse(null);
        if (user != null) {
            if (name != null) {
                user.setName(name);
            }
            if (surname != null) {
                user.setSurname(surname);
            }
            if (email != null) {
                user.setEmail(email);
            }
            if (password != null) {
                user.setPassword(passwordEncoder.encode(password));
            }
            if (role != null) {
                user.setRole(role);
            }

            usersRepository.save(user);

            return "Zaktualizowano użytkownika o ID: " + id;
        }else{
            return "Uzytkownik o ID " + id + " nie istenieje";
        }
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}