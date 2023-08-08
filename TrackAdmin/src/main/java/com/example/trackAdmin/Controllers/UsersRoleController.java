package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.UsersRole;
import com.example.trackAdmin.Respositories.UsersRoleRepository;
import com.example.trackAdmin.Service.AuditLogService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUsersRole(@RequestParam String name, HttpSession session) {
        UsersRole usersRoleAdd = new UsersRole();

        usersRoleAdd.setName(name);
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logAddRole(loggedInUserEmail);
        usersRoleRepository.save(usersRoleAdd);
        return "redirect:/user/superAdminUser";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUserRole(@RequestParam String name, HttpSession session) {
        UsersRole usersRoleDelete = usersRoleRepository.findByName(name);
        if (usersRoleDelete != null) {
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logDeleteRole(loggedInUserEmail);
            usersRoleRepository.delete(usersRoleDelete);
            return "redirect:/user/superAdminUser";
        } else {
            return "Rola o nazwie: " + name + " nie istenieje";
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateUserRole(@RequestParam Integer id,
                                               @RequestParam String name,
                                               HttpSession session) {
        UsersRole usersRoleUpdate = usersRoleRepository.findById(id).orElse(null);
        if (usersRoleUpdate != null) {
            usersRoleUpdate.setName(name);
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logUpdataRole(loggedInUserEmail);
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