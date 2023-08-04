package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Users;
import com.example.trackAdmin.Respositories.UsersRepository;
import com.example.trackAdmin.Classes.UsersRole;
import com.example.trackAdmin.Respositories.UsersRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path= "/user")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersRoleRepository usersRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


//    @PostMapping(path = "/add")
//    public @ResponseBody String addNewUser(@RequestParam String name,
//                                           @RequestParam String surname,
//                                           @RequestParam String email,
//                                           @RequestParam String password,
//                                           @RequestParam String role) {
//
//        UsersRole usersRole = usersRoleRepository.findByName(role);
//
//
//        Users userAdd = new Users();
//        userAdd.setName(name);
//        userAdd.setSurname(surname);
//        userAdd.setEmail(email);
//        userAdd.setPassword(passwordEncoder.encode(password));
//        userAdd.setRole(usersRole);
//        usersRole.getUsers().add(userAdd);
//
//        usersRepository.save(userAdd);
//        return "redirect:/user/superAdminUser";
//    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name,
                                           @RequestParam String surname,
                                           @RequestParam String email,
                                           @RequestParam(required = false) String password,
                                           @RequestParam String role) {

        UsersRole usersRole = usersRoleRepository.findByName(role);

        Users userAdd = new Users();
        userAdd.setName(name);
        userAdd.setSurname(surname);
        userAdd.setEmail(email);

        if (password != null) {
            userAdd.setPassword(passwordEncoder.encode(password));
        }

        userAdd.setRole(usersRole);
        usersRole.getUsers().add(userAdd);

        usersRepository.save(userAdd);
        return "redirect:/user/superAdminUser";
    }

    @GetMapping(path = "/add")
    public String registerPage() {
        return "superAdminUser";
    }


    @PostMapping(path = "/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {
        Users userLogin = usersRepository.findByEmail(email);
        if (userLogin != null && passwordEncoder.matches(password, userLogin.getPassword())) {
            // Ustaw informacje o zalogowanym użytkowniku w sesji
            session.setAttribute("loggedInUser", userLogin);

            // Przekieruj użytkownika na odpowiednią stronę w zależności od jego roli
            if (userLogin.getRole() != null) {
                switch (userLogin.getRole().getName()) {
                    case "Super_Admin_User":
                        return "redirect:/user/superAdminUser";
                    case "Admin_User":
                        return "redirect:/user/adminUser";
                    case "Normal_User":
                        return "redirect:/user/normalUser";
                }
            }
        }

        return "redirect:/error";
    }

    @GetMapping(path = "/normalUser")
    public String normalUserPage() {
        return "normalUser";
    }

    @GetMapping(path = "/adminUser")
    public String adminUserPage() {
        return "adminUser";
    }

    @GetMapping(path = "/superAdminUser")
    public String superAdminUserPage() {
        return "superAdminUser";
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(path = "/error")
    public String errorPage() {
        return "error";
    }


    @PostMapping(path = "/updateRole")
    public String updateRole(@RequestParam("emailRole") String email,
                             @RequestParam("newRole") String newRole,
                             RedirectAttributes redirectAttributes) {
        Users userToUpdate = usersRepository.findByEmail(email);
        if (userToUpdate != null) {
            UsersRole newUsersRole = usersRoleRepository.findByName(newRole);
            if (newUsersRole == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid role");
            } else {
                userToUpdate.setRole(newUsersRole);
                usersRepository.save(userToUpdate);
                redirectAttributes.addFlashAttribute("success", "Updated role for user with email: " + email);
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "User with email: " + email + " not found");
        }
        return "redirect:/user/superAdminUser";
    }


    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUser(@RequestParam String email){
        Users userDelete = usersRepository.findByEmail(email);
        if(userDelete != null){
            usersRepository.delete(userDelete);
            return "redirect:/user/superAdminUser";
        }
        else{
            return "redirect:/error";
        }
    }


    @PutMapping(path = "/update")
    public @ResponseBody String updateUser(@RequestParam Integer id,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String surname,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String password) {
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
            usersRepository.save(userUpdate);

            return "redirect:/user/superAdminUser";

        } else {
            return "redirect:/error";
        }
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping(path = "/allEmails")
    public @ResponseBody List<String> getAllUserEmails() {
        List<Users> users = (List<Users>) usersRepository.findAll();
        return users.stream().map(Users::getEmail).collect(Collectors.toList());
    }

}