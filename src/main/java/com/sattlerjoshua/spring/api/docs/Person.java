package com.sattlerjoshua.spring.api.docs;

import com.fasterxml.jackson.annotation.*;

/**
 * Immutable class that represents a person resource.
 *
 * @author joshua.sattler@mailbox.org
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    private final Long id;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    Person(@JsonProperty("id") Long id,
           @JsonProperty("firstName") String firstName,
           @JsonProperty("lastName") String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonGetter
    public Long id() {
        return id;
    }

    @JsonGetter
    public String firstName() {
        return firstName;
    }

    @JsonGetter
    public String lastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
