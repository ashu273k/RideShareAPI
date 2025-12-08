package org.ashu.rideshare.controller;

import org.ashu.rideshare.dto.CreateRideRequest;
import org.ashu.rideshare.dto.RideResponse;
import org.ashu.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    // POST /api/v1/rides  (ROLE_USER)
    @PostMapping("/rides")
    public ResponseEntity<RideResponse> createRide(
            Authentication authentication,
            @Valid @RequestBody CreateRideRequest request
    ) {
        String username = authentication.getName(); // from JWT
        // for now, we'll use username as userId; later we can switch to actual userId
        RideResponse response = rideService.createRide(username, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/user/rides  (ROLE_USER)
    @GetMapping("/user/rides")
    public ResponseEntity<List<RideResponse>> getUserRides(Authentication authentication) {
        String username = authentication.getName();
        List<RideResponse> rides = rideService.getUserRides(username);
        return ResponseEntity.ok(rides);
    }
}