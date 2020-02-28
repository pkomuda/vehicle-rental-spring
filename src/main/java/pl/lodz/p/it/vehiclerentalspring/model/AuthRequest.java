package pl.lodz.p.it.vehiclerental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class AuthRequest implements Serializable {

    private String login;
    private String password;
}
