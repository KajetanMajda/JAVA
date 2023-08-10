package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.*;
import com.example.trackAdmin.Respositories.*;
import com.example.trackAdmin.Service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public @ResponseBody String addNewElement(@RequestParam(required = false) String transaction,
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
    public @ResponseBody String elemnentDelete(@RequestParam Integer id, HttpSession session) {
        Elements elementsDelete = elementsRepository.findById(id).orElse(null);
        if (elementsDelete != null) {
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logDeleteElement(loggedInUserEmail, id);
            elementsRepository.delete(elementsDelete);
            return id + " zostalo usuiniete";
        } else {
            return id + " nie istnieje";
        }
    }

    @DeleteMapping("/adminDelete/{id}")
    public ResponseEntity<String> deleteElement(@PathVariable Integer id) {
        try {
            elementsRepository.deleteById(id);
            return ResponseEntity.ok("Element został usunięty.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd usuwania elementu.");
        }
    }

    @GetMapping(path = "/getToCopy/{divisionId}")
    public @ResponseBody ResponseEntity<List<String>> copyDivision(@PathVariable Integer divisionId) {
        List<Elements> elementsList = elementsRepository.findByDivisionId(divisionId);

        if (elementsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<String> transactions = elementsList.stream()
                .map(Elements::getTransaction)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactions);
    }

    @PostMapping(path = "/copy")
    public ResponseEntity<String> copyElementsToDivision(@RequestBody List<String> transactions,
                                                         @RequestParam Integer divisionIdTo,
                                                         HttpSession session) {
        try {
            Division divisionTo = divisionRepository.findById(divisionIdTo)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono dywizji o podanym id: " + divisionIdTo));

            Status statusToSet = statusRepository.findById(5)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono statusu o podanym id: 5"));

            for (String transaction : transactions) {
                Elements elementToCopy = new Elements();
                elementToCopy.setTransaction(transaction);
                elementToCopy.setDivision(divisionTo);
                elementToCopy.setStatus(statusToSet);

                String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
                auditLogService.logCopyElement(loggedInUserEmail);

                elementsRepository.save(elementToCopy);
            }

            return ResponseEntity.ok("Dane zostały skopiowane");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd kopiowania danych: " + e.getMessage());
        }
    }


    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Elements> getAllElements() {
        return elementsRepository.findAll();
    }
}
