package com.google.cloud.paflynn.samples.recaptcha.guestbook.resouces;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Greeting {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;
    private String message;

    protected Greeting() {}

    public Greeting(String username, String message) {
        this.username = username;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format(
                "Greeting[id=%d, username='%s', message='%s']",
                id, username, message);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
