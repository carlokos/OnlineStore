package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.dtos.Client_AddressDto;
import com.javaschool.OnlineStore.services.Client_AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class Client_AddressController {
    private final Client_AddressService client_AddressService;

    @GetMapping
    public ResponseEntity<List<Client_AddressDto>> getAllAddressses(){
		List<Client_AddressDto> result = client_AddressService.getAllAdress();
		return ResponseEntity.ok(result);
	}

    @GetMapping("/{id}")
    public ResponseEntity<Client_AddressDto> getAddressById(@PathVariable Long id){
		Client_AddressDto result = client_AddressService.getAddressById(id);
		return ResponseEntity.ok(result);
	}

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Client_AddressDto>> getAllUserAddresses(@PathVariable Long id){
		List<Client_AddressDto> result = client_AddressService.getClientAddressbyUser(id);
		return ResponseEntity.ok(result);
	}
    
    @PostMapping
	public ResponseEntity<String> createNewProduct(@RequestBody Client_AddressDto dto){
		client_AddressService.createNewAddress(dto);
		return ResponseEntity.status(201).body("Address register for " + dto.getUser_email() + " succesfully");
	}

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable Long id, @RequestBody Client_AddressDto dto){
        client_AddressService.updateAddress(id, dto);
        return ResponseEntity.ok("Address changed succesfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id){
        client_AddressService.deleteAddress(id);
        return ResponseEntity.status(204).body("Address remove succesfully");
    }
}
