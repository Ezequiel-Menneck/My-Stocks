package com.minhasacoes.Service;

import com.minhasacoes.DTO.PersonDTO;
import com.minhasacoes.Model.Person;
import com.minhasacoes.Repository.PersonRepository;
import com.minhasacoes.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person findById(Integer id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void create(PersonDTO personDTO) {
        personRepository.save(createPerson(personDTO));
    }

    private Person createPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());

        return person;
    }

}
