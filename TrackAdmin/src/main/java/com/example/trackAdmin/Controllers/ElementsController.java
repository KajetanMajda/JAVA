package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.*;
import com.example.trackAdmin.Respositories.DivisionRepository;
import com.example.trackAdmin.Respositories.ElementsRepository;
import com.example.trackAdmin.Respositories.StatusRepository;
import com.example.trackAdmin.Service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping(path = "/elements")
public class ElementsController {
    @Autowired
    private ElementsRepository elementsRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewElement(@RequestParam (required = false) String transaction,
                                              @RequestParam (required = false) String description,
                                              @RequestParam (required = false) String comment,
                                              @RequestParam (required = false) String confirm_name,
                                              @RequestParam (required = false) Integer statusId,
                                              @RequestParam (required = false) String accomplish,
                                              @RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate accomplish_date,
                                              @RequestParam (required = false) String confirm,
                                              @RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate confirm_date,
                                              @RequestParam (required = false) Integer divisionId,
                                              HttpSession session) {
        Elements elements = new Elements();
        elements.setTransaction(transaction);
        elements.setDescription(description);
        elements.setComment(comment);
        elements.setConfirm_name(confirm_name);
        elements.setAccomplish(accomplish);
        elements.setAccomplish_date(accomplish_date);
        elements.setConfirm(confirm);
        elements.setConfirm_date(confirm_date);

        Division division = new Division();
        division.setId(divisionId);
        elements.setDivision(division);

        Status status = new Status();
        status.setId(statusId);
        elements.setStatus(status);



        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logElementsAdd(loggedInUserEmail);

        elementsRepository.save(elements);
        return "saved";
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateUser(@RequestParam Integer id,
                                           @RequestParam(required = false) String transaction,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) String comment,
                                           @RequestParam(required = false) String confirm_name,
                                           @RequestParam(required = false) Integer statusId,
                                           @RequestParam(required = false) String accomplish,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate accomplish_date,
                                           @RequestParam(required = false) String confirm,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate confirm_date,
                                           @RequestParam(required = false) Integer divisionId,
                                           HttpSession session) {
        Elements elementsUpdate = elementsRepository.findById(id).orElse(null);
        if (elementsUpdate != null) {
            if (transaction != null) {
                elementsUpdate.setTransaction(transaction);
            }
            if (description != null) {
                elementsUpdate.setDescription(description);
            }
            if (comment != null) {
                elementsUpdate.setComment(comment);
            }
            if (confirm_name != null) {
                elementsUpdate.setConfirm_name(confirm_name);
            }
            if (statusId != null) {
                Status status = statusRepository.findById(statusId).orElse(null);
                if (status != null) {
                    elementsUpdate.setStatus(status);
                }
            }
            if (accomplish != null) {
                elementsUpdate.setAccomplish(accomplish);
            }
            if (accomplish_date != null) {
                elementsUpdate.setAccomplish_date(accomplish_date);
            }
            if (confirm != null) {
                elementsUpdate.setConfirm(confirm);
            }
            if (confirm_date != null) {
                elementsUpdate.setConfirm_date(confirm_date);
            }
            if (divisionId != null) {
                Division division = divisionRepository.findById(divisionId).orElse(null);
                if (division != null) {
                    elementsUpdate.setDivision(division);
                }
            }

            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logElementUpdate(loggedInUserEmail, id);
            elementsRepository.save(elementsUpdate);
            return "Zaktualizowano element o ID: " + id;
        } else {
            return "Element o ID " + id + " nie istenieje";
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String elemnentDelete(@RequestParam Integer id, HttpSession session){
        Elements elementsDelete = elementsRepository.findById(id).orElse(null);
        if(elementsDelete != null){
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logDeleteElement(loggedInUserEmail, id);
            elementsRepository.delete(elementsDelete);
            return id + " zostalo usuiniete";
        }else {
            return id + " nie istnieje";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Elements> getAllElements() {
        return elementsRepository.findAll();
    }
}
