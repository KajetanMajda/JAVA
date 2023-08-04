package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.UsersRole;
import com.example.trackAdmin.Respositories.UsersRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path = "/userRole")
public class UsersRoleController {

    @Autowired
    private UsersRoleRepository usersRoleRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUsersRole(@RequestParam String name) {
        UsersRole usersRoleAdd = new UsersRole();

        usersRoleAdd.setName(name);
        usersRoleRepository.save(usersRoleAdd);
        return "redirect:/user/superAdminUser";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUserRole(@RequestParam String name) {
        UsersRole usersRoleDelete = usersRoleRepository.findByName(name);
        if (usersRoleDelete != null) {
            usersRoleRepository.delete(usersRoleDelete);
            return "redirect:/user/superAdminUser";
        } else {
            return "Rola o nazwie: " + name + " nie istenieje";
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateUserRole(@RequestParam Integer id,
                                               @RequestParam String name) {
        UsersRole usersRoleUpdate = usersRoleRepository.findById(id).orElse(null);
        if (usersRoleUpdate != null) {
            usersRoleUpdate.setName(name);
            usersRoleRepository.save(usersRoleUpdate);
            return "redirect:/user/superAdminUser";
        } else {
            return "Pod Id: " + id + " nie zostala utworzona wczesniej rola, najpierw utworz role userRole/add, by moc ja edytowac";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UsersRole> getAllUsersRole() {
        return usersRoleRepository.findAll();
    }


    @GetMapping(path = "/allRoles")
    public @ResponseBody List<String> getAllRoles() {
        Iterable<UsersRole> rolesIterable = usersRoleRepository.findAll();
        List<UsersRole> rolesList = StreamSupport.stream(rolesIterable.spliterator(), false)
                .collect(Collectors.toList());
        return rolesList.stream().map(UsersRole::getName).collect(Collectors.toList());
    }
}