package com.example.property.controller;

import com.example.property.model.PropertyKind;
import com.example.property.service.PropertyKindService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/property-kind/")
public class PropertyKindController extends BaseController{
    @Autowired
    private PropertyKindService propertyKindService;

    @GetMapping("/")
    public ResponseEntity<?> getAll(){

        try{
            List<PropertyKind> allPropertyKinds =  propertyKindService.getAllPropertyKinds();
            return ResponseEntity.ok(Map.of(
               "status",!allPropertyKinds.isEmpty(),
               "message", !allPropertyKinds.isEmpty() ? "data found" : "no data found",
               "data",  allPropertyKinds
            ));
        }catch(Exception e){
            System.out.println(e.getMessage() + " -- "+e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","Something wrong happen"
            ));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPropertyKind(@Valid @RequestBody PropertyKind propertyKind, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(Map.of(
                    "status", false,
                    "message", result.getAllErrors().stream()
                            .map(error -> error.getDefaultMessage())
                            .toList()
            ));
        }
        try{
            PropertyKind isStored = propertyKindService.add(propertyKind);
            return ResponseEntity.ok(Map.of(
                    "status",true,
                    "message","data added",
                    "data",isStored
            ));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong"
            ));
        }
    }

    @GetMapping("/get-details/{id}")
    public ResponseEntity<?> getDetails(@PathVariable UUID id){
        try{
            PropertyKind isExistPropertyKind = propertyKindService.getById(id);
            if(isExistPropertyKind != null) {
                return ResponseEntity.ok(Map.of(
                    "status", true,
                    "message", "data added",
                    "data", isExistPropertyKind
                ));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", false,
                    "message", "No data found for the provided ID"
            ));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong"
            ));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody PropertyKind propertyKind, BindingResult result){
        System.out.println("id -----> "+id);
        System.out.println("propertykind"+propertyKind);
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(Map.of(
                    "status", false,
                    "message", result.getAllErrors().stream()
                            .map(error -> error.getDefaultMessage())
                            .toList()
            ));
        }
        try{
            PropertyKind isUpdated = propertyKindService.update(id,propertyKind);
            if(isUpdated != null) {
                return ResponseEntity.ok(Map.of(
                        "status", true,
                        "message", "data added",
                        "data", isUpdated
                ));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", false,
                    "message", "No data found for the provided ID"
            ));
        }catch(Exception e){
            System.out.println("error ----->"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong"
            ));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        try{
            propertyKindService.delete(id);
            return ResponseEntity.ok(Map.of(
                    "status",true,
                    "message","data deleted"
            ));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong"
            ));
        }
    }

}
