package com.sattlerjoshua.spring.api.docs;

class Person {

    private static Long personCounter = 0L;

    Long id;

    String firstName;
    String lastName;

    Person(){}

    Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = ++personCounter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
