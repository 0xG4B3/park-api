package com.example.parkapi.services;
import com.example.parkapi.models.ParkingModel;
import com.example.parkapi.repositories.ParkingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ParkingService {

    final ParkingRepository _parkingRepository;

    public ParkingService(ParkingRepository _parkingRepository) {
        this._parkingRepository = _parkingRepository;
    }
    @Transactional
    public ParkingModel save(ParkingModel parkingModel)
    {
        return _parkingRepository.save(parkingModel);
    }
    public boolean existsByLicensePlateCar(String licensePlate)
    {
        return _parkingRepository.existsByLicensePlateCar(licensePlate);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return _parkingRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartamentAndBlock(String apartament, String block) {
        return _parkingRepository.existsByApartamentAndBlock(apartament, block);
    }

    public List<ParkingModel> GetAll() {
        return _parkingRepository.findAll();
    }

    public Optional<ParkingModel> FindById(UUID id) {
        return _parkingRepository.findById(id);
    }

    @Transactional
    public void delete(ParkingModel parkingModel) {
        _parkingRepository.delete(parkingModel);
    }
}
