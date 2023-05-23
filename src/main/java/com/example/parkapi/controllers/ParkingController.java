package com.example.parkapi.controllers;
import com.example.parkapi.DTOs.ParkingDTO;
import com.example.parkapi.models.ParkingModel;
import com.example.parkapi.services.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingController {
    final ParkingService _parkingService;
    public ParkingController(ParkingService _parkingService) {
        this._parkingService = _parkingService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingDTO parkingDTO) {
        if(_parkingService.existsByLicensePlateCar(parkingDTO.getLicensePlateCar()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("");
        }
        if(_parkingService.existsByParkingSpotNumber(parkingDTO.getParkingSpotNumber()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("");
        }
        if(_parkingService.existsByApartamentAndBlock(parkingDTO.getApartament(), parkingDTO.getBlock()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("");
        }

        var _parkingModel = new ParkingModel();
        BeanUtils.copyProperties(parkingDTO, _parkingModel);
        _parkingModel.setRegisterDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(_parkingService.save(_parkingModel));
    }
    @GetMapping
    public ResponseEntity<List<ParkingModel>> getAllParkingSpots()
    {
        return ResponseEntity.status(HttpStatus.OK).body(_parkingService.GetAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkById(@PathVariable(value = "id") UUID id){
        Optional<ParkingModel> parkingModelOptional = _parkingService.FindById(id);
        if (!parkingModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("could not find item: ID does not exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingModelOptional.get());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkById(@PathVariable(value = "id") UUID id) {
        Optional<ParkingModel> parkingModelOptional = _parkingService.FindById(id);
        if(!parkingModelOptional.isPresent())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("unable to delete item: ID not found");
        }
        _parkingService.delete(parkingModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("successfully deleted item!");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkSlot(@PathVariable(value =" id") UUID id, @RequestBody @Valid ParkingDTO parkingDTO)
    {
        Optional<ParkingModel> parkingModelOptional = _parkingService.FindById(id);
        if(!parkingModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("unable to update item: ID not found");
        }
        var parkingSpotModel = parkingModelOptional.get();
        BeanUtils.copyProperties(parkingDTO, parkingSpotModel);
        parkingSpotModel.setId(parkingModelOptional.get().getId());
        parkingSpotModel.setRegisterDate(parkingModelOptional.get().getRegisterDate());
        return ResponseEntity.status(HttpStatus.OK).body(_parkingService.save(parkingSpotModel));
    }
}
