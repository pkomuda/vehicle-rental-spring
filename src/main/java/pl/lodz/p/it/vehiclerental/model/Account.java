package pl.lodz.p.it.vehiclerental.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "accounts")
public @Data class Account {

    @Id
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> access = new ArrayList<>();
    private boolean active;
}
