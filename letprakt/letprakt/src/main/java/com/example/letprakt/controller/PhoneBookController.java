package com.example.letprakt.controller;


import com.example.letprakt.model.PhoneBook;
import com.example.letprakt.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PhoneBookController {

    @Autowired
    private PhoneBookService phoneBookService;

    @GetMapping("/phonebook")
    public String showPhoneBook(Model model) {
        model.addAttribute("contacts", phoneBookService.findAll());
        return "phonebook";
    }

    @PostMapping("/phonebook")
    public String addContact(@RequestParam String lastName, @RequestParam String phoneNumber, Model model) {
        if (phoneBookService.findByPhoneNumber(phoneNumber).isPresent()) {
            model.addAttribute("error", "Phone number already exists!");
        } else {
            PhoneBook contact = new PhoneBook();
            contact.setLastName(lastName);
            contact.setPhoneNumber(phoneNumber);
            phoneBookService.save(contact);
        }
        model.addAttribute("contacts", phoneBookService.findAll());
        return "phonebook";
    }}
