package pl.lodz.p.it.vehiclerentalspring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public @Data class Car extends Vehicle {

    private int seatNumber;
    private int trunkCapacity;
}
