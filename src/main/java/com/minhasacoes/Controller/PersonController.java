package com.minhasacoes.Controller;

import com.minhasacoes.DTO.PersonDTO;
import com.minhasacoes.Model.Person;
import com.minhasacoes.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

   @PostMapping("/create")
   @ResponseStatus(HttpStatus.CREATED)
   public void createPerson(@RequestBody PersonDTO personDTO) {
        personService.create(personDTO);
   }

}
