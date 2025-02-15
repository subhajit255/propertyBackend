package com.example.property.controller;

import com.example.property.model.Property;
import com.example.property.request.PropertyRequest;
import com.example.property.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/property")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<?> addProperty(@Valid @RequestAttribute UUID userId, @RequestBody PropertyRequest propertyRequest, BindingResult result){
        // Check for validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", false,
                    "message", result.getAllErrors().stream()
                            .map(error -> error.getDefaultMessage())
                            .toList()
            ));
        }
        try {
            Property property = propertyService.add(userId, propertyRequest);
            if(property.getId() != null){
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                        "status", true,
                        "message", "property added",
                        "data", property
                ));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", false,
                    "message", "property not added"
            ));
        }catch(Exception e){
            e.printStackTrace(); // Log the exact error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Something Went Wrong"
            ));
        }
    }


}
