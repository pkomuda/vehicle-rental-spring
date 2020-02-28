package pl.lodz.p.it.vehiclerentalspring.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public @Data class Account {

    @Id
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String[] permissions;
    private boolean active;
}
