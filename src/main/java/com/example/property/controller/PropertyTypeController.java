package com.example.property.controller;

import com.example.property.model.PropertyType;
import com.example.property.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/property-type")
public class PropertyTypeController extends BaseController{

    @Autowired
    PropertyTypeService propertyTypeService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPropertyTypes(){
        try {
            List<PropertyType> propertyTypes = propertyTypeService.getAll();
            return ResponseEntity.ok(Map.of(
                    "status", !propertyTypes.isEmpty() ? true : false,
                    "message", !propertyTypes.isEmpty() ? "Data Found" : "No Data Found",
                    "data", propertyTypes
            ));
        }catch (Exception e){
            System.out.println(e.getMessage() + " -- "+e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","internal server error"
            ));
        }
    }

    @GetMapping("/get-property-type-details/{id}")
    public ResponseEntity<?> getDetails(@PathVariable UUID id){
        try {
            PropertyType propertyType = propertyTypeService.getById(id);
            if(propertyType != null){
                return ResponseEntity.ok(Map.of(
                        "status",true,
                        "message","data found",
                        "data",propertyType
                ));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status",false,
                    "message","wrong id given"
            ));
        }catch (Exception e){
            System.out.println(e.getMessage() + " -- "+e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","internal server error"
            ));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPropertyType(@RequestBody PropertyType propertyType){
        try{
            PropertyType isAddPropertyType = propertyTypeService.add(propertyType);
            return ResponseEntity.ok(Map.of(
                    "status",true,
                    "message","Property Type added",
                    "data", propertyType
            ));
        }catch(Exception e){
            System.out.println(e.getMessage() + " -- "+e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","internal server error"
            ));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePropertyType(@RequestBody PropertyType propertyType,@PathVariable UUID id){
        try{
            PropertyType isPropertyTypeUpdated = propertyTypeService.update(propertyType,id);
            return ResponseEntity.ok(Map.of(
                    "status",true,
                    "message","property type updated"
            ));
        }catch(Exception e){
            System.out.println(e.getMessage() + " -- "+e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","internal server error"
            ));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePropertyType(@PathVariable UUID id){
        try {
            boolean isPropertyTypeDeleted = propertyTypeService.delete(id);
            if(isPropertyTypeDeleted) {
                return ResponseEntity.ok(Map.of(
                        "status", true,
                        "message", "Property type deleted successfully"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", false,
                        "message", "Property type not found"
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Internal server error"
            ));
        }
    }
}
