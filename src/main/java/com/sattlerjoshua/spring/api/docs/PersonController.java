package com.sattlerjoshua.spring.api.docs;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * API demonstrating basic CRUD operations on Person resources
 *
 * @author joshua.sattler@mailbox.org
 */
@RestController
@RequestMapping("/persons")
@Api(tags = "Person Controller")
class PersonController {

    private final Map<Long, Person> persons = new HashMap<>();

    /**
     * Create a new person.
     *
     * @param person
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Person create(Person person){
        return null;
    }

    /**
     * Retrieve a person by its id.
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Person retrieveById(@PathVariable Long id){
        return null;
    }

    /**
     * Retrieve all persons.
     *
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<Person> retrieveAll(){
        return Set.of(new Person());
    }

    /**
     * Update the person entirely.
     *
     * @param person the new Person
     * @return
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Person update(Person person){
        return null;
    }

    /**
     * Delete the person by its id.
     *
     * @param id the id of the person to delete
     */
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id){
        ResponseEntity.noContent();
    }
}
