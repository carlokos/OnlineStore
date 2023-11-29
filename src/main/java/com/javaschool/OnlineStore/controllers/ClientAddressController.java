package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.dtos.ClientAddressDto;
import com.javaschool.OnlineStore.services.ClientAddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class ClientAddressController {
    private final ClientAddressService clientAddressService;

    @GetMapping
    public ResponseEntity<List<ClientAddressDto>> getAllAddressses(){
		List<ClientAddressDto> result = clientAddressService.getAllAdress();
		return ResponseEntity.ok(result);
	}

    @GetMapping("/{id}")
    public ResponseEntity<ClientAddressDto> getAddressById(@PathVariable Long id){
		ClientAddressDto result = clientAddressService.getAddressById(id);
		return ResponseEntity.ok(result);
	}

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ClientAddressDto>> getAllUserAddresses(@PathVariable Long id){
		List<ClientAddressDto> result = clientAddressService.getClientAddressbyUser(id);
		return ResponseEntity.ok(result);
	}
    
    @PostMapping
	public ResponseEntity<String> createNewAddress(@RequestBody ClientAddressDto dto){
		clientAddressService.createNewAddress(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Address register succesfully");
	}

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable Long id, @RequestBody ClientAddressDto dto){
        clientAddressService.updateAddress(id, dto);
        return ResponseEntity.ok("Address changed succesfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id){
        clientAddressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Address remove succesfully");
    }
}
