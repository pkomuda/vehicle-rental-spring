package pl.lodz.p.it.vehiclerentalspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class AuthenticationResponse implements Serializable {

    private final String jwt;
}
