package com.sattlerjoshua.spring.api.docs;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * API demonstrating basic CRUD operations on Person resources
 *
 * @author joshua.sattler@mailbox.org
 */
@RestController
@RequestMapping(PersonController.PERSON_API_PATH)
@Api(tags = "Person Controller")
class PersonController {

    public static final String PERSON_API_PATH = "/persons";

    private transient static Long personCounter = 0L;
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final Map<Long, Person> persons;

    PersonController(Map<Long, Person> persons) {
        this.persons = persons;
    }

    /**
     * Create a new person.
     * This operation is not idempotent so each request creates a new person.
     *
     * @param person the new person resource to create.
     * @return the newly created person.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Person create(@RequestBody Person person) {
        Objects.requireNonNull(person, "Person cannot be null");
        logger.info("POST request to create new person with parameters: {}", person);
        return persons.computeIfAbsent(++personCounter, (id) ->
                new Person(id, person.firstName(), person.lastName()));
    }

    /**
     * Retrieve a person by its id.
     *
     * @param id the id of the person to retrieve.
     * @return the person resource with given id.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Person retrieveById(@PathVariable Long id) {
        Objects.requireNonNull(id, "Id cannot be null");
        logger.info("GET request to retrieve person with id {}", id);
        return Optional.ofNullable(persons.get(id))
                .orElseThrow(PersonController::notfound);
    }

    /**
     * Retrieve all persons.
     *
     * @return a collection of person resources.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<Person> retrieveAll() {
        logger.info("GET request to retrieve all person resources");
        return persons.values();
    }

    /**
     * Update the person entirely.
     *
     * @param person the new Person
     * @return the updated person.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Person update(@RequestBody Person person, @PathVariable Long id) {
        Objects.requireNonNull(person, "Person cannot be null");
        logger.info("PUT request to update person with id {}", id);
        return persons.computeIfPresent(id, (key, val) -> new Person(id, person.firstName(), person.lastName()));
    }

    /**
     * Delete the person by its id.
     *
     * @param id the id of the person to delete
     */
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        logger.info("DELETE request to delete person resource with id {}", id);
        Optional.ofNullable(persons.remove(id))
                .orElseThrow(PersonController::notfound);
    }

    private static ResponseStatusException notfound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
    }

}
