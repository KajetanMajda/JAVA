package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.UsersRole;
import com.example.trackAdmin.Respositories.UsersRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/userRole")
public class UsersRoleController {

    @Autowired
    private UsersRoleRepository usersRoleRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUsersRole(@RequestParam String name){
        UsersRole usersRole = new UsersRole();

        usersRole.setName(name);
        usersRoleRepository.save(usersRole);
        return "Saved";

    }

    @GetMapping(path = "/all")
    public  @ResponseBody Iterable<UsersRole> getAllUsersRole(){
        return usersRoleRepository.findAll();
    }


}
