package pl.lodz.p.it.vehiclerentalspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class AuthenticationRequest implements Serializable {

    private String username;
    private String password;
}
