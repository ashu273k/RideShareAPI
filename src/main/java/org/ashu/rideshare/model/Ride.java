package org.ashu.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rides")
public class Ride {

    @Id
    private String id;

    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private String status; // "REQUESTED", "ACCEPTED", "COMPLETED"
    private LocalDateTime createdAt;

    // Custom constructor for creating a new ride request
    public Ride(String userId, String pickupLocation, String dropLocation) {
        this.userId = userId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.status = "REQUESTED";
        this.driverId = null;
        this.createdAt = LocalDateTime.now();
    }
}
