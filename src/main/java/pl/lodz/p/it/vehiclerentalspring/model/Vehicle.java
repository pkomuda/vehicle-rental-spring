package pl.lodz.p.it.vehiclerentalspring.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "vehicles")
public abstract @Data class Vehicle {

    @Id
    private UUID id = UUID.randomUUID();
    private String make;
    private String model;
    private int productionYear;
}
