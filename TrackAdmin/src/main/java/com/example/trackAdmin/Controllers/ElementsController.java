package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Division;
import com.example.trackAdmin.Classes.Elements;
import com.example.trackAdmin.Respositories.ElementsRepository;
import com.example.trackAdmin.Classes.Status;
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

    @PostMapping(path = "/add")
    public @ResponseBody String addNewElement(@RequestParam Integer lp,
                                                  @RequestParam String transaction,
                                                  @RequestParam String description,
                                                  @RequestParam String comment,
                                                  @RequestParam Integer statusId,
                                                  @RequestParam String accomplish,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate accomplish_date,
                                                  @RequestParam String confirm,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate confirm_date,
                                                  @RequestParam Integer divisionId) {
        Elements elements = new Elements();
        elements.setLp(lp);
        elements.setTransaction(transaction);
        elements.setDescription(description);
        elements.setComment(comment);
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

        elementsRepository.save(elements);
        return "saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Elements> getAllElements(){
        return elementsRepository.findAll();
    }
}
