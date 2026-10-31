package org.github.httpcode202.spring.datasource2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class DataSource2Human {
    @Id
    private String id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    public DataSource2Human() {
    }

    public DataSource2Human(String id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String id() {
        return id;
    }

    public String firstname() {
        return firstname;
    }

    public String lastname() {
        return lastname;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        DataSource2Human human = (DataSource2Human) object;
        return Objects.equals(id, human.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}