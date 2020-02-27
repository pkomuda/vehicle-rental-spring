package pl.lodz.p.it.vehiclerental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class AuthResponse implements Serializable {

    private final String jwt;
}
