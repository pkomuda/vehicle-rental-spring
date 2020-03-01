package pl.lodz.p.it.vehiclerentalspring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public @Data class Motorcycle extends Vehicle {

    private boolean requiresLicense;
}
