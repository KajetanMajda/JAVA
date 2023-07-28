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


        Users userAdd = new Users();
        userAdd.setName(name);
        userAdd.setSurname(surname);
        userAdd.setEmail(email);
        userAdd.setPassword(passwordEncoder.encode(password));
        userAdd.setRole(usersRole);
        usersRole.getUsers().add(userAdd);

        usersRepository.save(userAdd);
        return "Saved";
    }

    @PostMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String email,
                                      @RequestParam String password) {
        Users userLogin = usersRepository.findByEmail(email);
        if (userLogin != null && passwordEncoder.matches(password, userLogin.getPassword())) {
            return "Zalogowano";
        } else {
            return "Błędny email lub hasło";
        }
    }

    @PostMapping(path="/checkRole")
    public @ResponseBody Boolean checkUserRole(@RequestParam String email,
                                               @RequestParam String role){
        Users userCheckRole = usersRepository.findByEmail(email);
        if(userCheckRole != null && userCheckRole.getRole() != null){
            return userCheckRole.getRole().getName().equals(role);
        }else{
            return false;
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUser(@RequestParam String email){
        Users userDelete = usersRepository.findByEmail(email);
        if(userDelete != null){
            usersRepository.delete(userDelete);
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
        Users userUpdate = usersRepository.findById(id).orElse(null);
        if (userUpdate != null) {
            if (name != null) {
                userUpdate.setName(name);
            }
            if (surname != null) {
                userUpdate.setSurname(surname);
            }
            if (email != null) {
                userUpdate.setEmail(email);
            }
            if (password != null) {
                userUpdate.setPassword(passwordEncoder.encode(password));
            }
            if (role != null) {
                userUpdate.setRole(role);
            }

            usersRepository.save(userUpdate);

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