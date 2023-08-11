package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Respositories.*;
import com.example.trackAdmin.Classes.*;
import com.example.trackAdmin.Service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@RequestMapping(path = "/user")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersRoleRepository usersRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuditLogService auditLogService;


    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name,
                                           @RequestParam String surname,
                                           @RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String role,
                                           HttpSession session) {

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

        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logUserAdd(loggedInUserEmail);
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
            session.setAttribute("loggedInUser", userLogin);

            session.setAttribute("loggedInUserEmail", email);

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
    public String normalUserPage(Model model, HttpSession session) {
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        model.addAttribute("loggedInUserEmail", loggedInUserEmail);
        auditLogService.logUserLogin(loggedInUserEmail);
        return "normalUser";
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUserEmail");
        return "redirect:/user/login";
    }

    @GetMapping(path = "/adminUser")
    public String adminUserPage(Model model, HttpSession session) {
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        model.addAttribute("loggedInUserEmail", loggedInUserEmail);
        auditLogService.logUserLogin(loggedInUserEmail);
        return "adminUser";
    }

    @GetMapping(path = "/superAdminUser")
    public String superAdminUserPage(Model model, HttpSession session) {
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        model.addAttribute("loggedInUserEmail", loggedInUserEmail);
        auditLogService.logUserLogin(loggedInUserEmail);
        return "superAdminUser";
    }

    @GetMapping(path ="/adminUser/standards")
    public String adminUserStandardsPage (Model model, HttpSession session){
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        model.addAttribute("loggedInUserEmail", loggedInUserEmail);
        return "adminUserStandardsPage";
    }

    @GetMapping(path ="/normalUser/standards")
    public String normalUserStandardsPage (Model model, HttpSession session){
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        model.addAttribute("loggedInUserEmail", loggedInUserEmail);
        return "normalUserStandardsPage";
    }


    @GetMapping(path = "/error")
    public String errorPage() {
        return "error";
    }


    @PostMapping(path = "/updateRole")
    public String updateRole(@RequestParam("emailRole") String email,
                             @RequestParam("newRole") String newRole,
                             HttpSession session) {
        Users userToUpdate = usersRepository.findByEmail(email);
        if (userToUpdate != null) {
            UsersRole newUsersRole = usersRoleRepository.findByName(newRole);
            if (newUsersRole != null) {
                String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
                auditLogService.logUpdateRole(loggedInUserEmail);
                userToUpdate.setRole(newUsersRole);
                usersRepository.save(userToUpdate);
            }
        }
        return "redirect:/user/superAdminUser";
    }


    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUser(@RequestParam String email,
                                           HttpSession session) {
        Users userDelete = usersRepository.findByEmail(email);
        if (userDelete != null) {
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logDeleteUser(loggedInUserEmail);
            usersRepository.delete(userDelete);
            return "redirect:/user/superAdminUser";
        } else {
            return "redirect:/error";
        }
    }


    @PutMapping(path = "/update")
    public @ResponseBody String updateUser(@RequestParam Integer id,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String surname,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String password,
                                           HttpSession session) {
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
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logUpdateUser(loggedInUserEmail);
            usersRepository.save(userUpdate);

            return "redirect:/user/superAdminUser";

        } else {
            return "redirect:/error";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping(path = "/allEmails")
    public @ResponseBody List<String> getAllUserEmails() {
        List<Users> users = (List<Users>) usersRepository.findAll();
        return users.stream().map(Users::getEmail).collect(Collectors.toList());
    }

    @GetMapping(value = "/adminUser/standardy.xlsx", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getFile() throws IOException {
        Resource resource = new ClassPathResource("standardy.xlsx");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=standardy.xlsx")
                .body(resource);
    }

    @GetMapping(value = "/normalUser/standardy.xlsx", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getFiles() throws IOException {

        Resource resource = new ClassPathResource("standardy.xlsx");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=standardy.xlsx")
                .body(resource);
    }

}