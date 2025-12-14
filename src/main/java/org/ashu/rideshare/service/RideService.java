package org.ashu.rideshare.service;

import org.ashu.rideshare.dto.CreateRideRequest;
import org.ashu.rideshare.dto.RideResponse;
import org.ashu.rideshare.model.Ride;
import org.ashu.rideshare.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    // Create a ride for a given userId (from JWT)
    public RideResponse createRide(String userId, CreateRideRequest request) {
        Ride ride = new Ride(
                userId,
                request.getPickupLocation(),
                request.getDropLocation()
        );

        Ride saved = rideRepository.save(ride);
        return mapToResponse(saved);
    }

    // Get all rides for a given userId
    public List<RideResponse> getUserRides(String userId) {
        return rideRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RideResponse mapToResponse(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getUserId(),
                ride.getDriverId(),
                ride.getPickupLocation(),
                ride.getDropLocation(),
                ride.getStatus(),
                ride.getCreatedAt()
        );
    }
}