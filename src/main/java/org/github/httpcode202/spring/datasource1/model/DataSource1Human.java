package org.github.httpcode202.spring.datasource1.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class DataSource1Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    public DataSource1Human() {
    }

    public DataSource1Human(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer id() {
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
        DataSource1Human human = (DataSource1Human) object;
        return Objects.equals(id, human.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}