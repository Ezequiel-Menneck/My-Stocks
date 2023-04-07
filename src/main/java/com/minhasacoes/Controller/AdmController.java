package com.minhasacoes.Controller;


import com.minhasacoes.Entities.Person;
import com.minhasacoes.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adm")
public class AdmController {

    @Autowired
    private PersonService personService;

    @GetMapping("/all")
    public ResponseEntity<List<Person>> getAllPerson() {
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@PathVariable Integer id) {
        personService.deletePerson(id);
    }

}
