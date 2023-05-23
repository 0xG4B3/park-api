package com.example.parkapi.repositories;

import com.example.parkapi.models.ParkingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ParkingRepository extends JpaRepository<ParkingModel, UUID> {
    boolean existsByApartamentAndBlock(String apartament, String block);
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    boolean existsByLicensePlateCar(String licensePlateCar);

    void delete();
}
