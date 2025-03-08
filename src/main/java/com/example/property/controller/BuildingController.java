package com.example.property.controller;

import com.example.property.model.Building;
import com.example.property.request.BuildingRequest;
import com.example.property.request.BuildingUpdateRequest;
import com.example.property.service.BuildingService;
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
@RequestMapping("/api/building")
public class BuildingController extends BaseController{

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService){
        this.buildingService = buildingService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addBuilding(@Valid @RequestBody BuildingRequest buildingRequest, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(Map.of(
                    "status", false,
                    "message", result.getAllErrors().stream()
                            .map(error -> error.getDefaultMessage())
                            .toList()
            ));
        }
        try{
            boolean isAddBuilding = buildingService.addBuilding(buildingRequest);
            return ResponseEntity.ok(Map.of(
                    "status", isAddBuilding ? true : false,
                    "message", isAddBuilding ? "data added" : "data not added"
            ));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", e.getMessage() != null ? e.getMessage() : "Something went wrong"
            ));
        }
    }
    @GetMapping("/buildings-by-property/{property_id}")
    public ResponseEntity<?> getBuildingsByProperty(@PathVariable("property_id") UUID propertyId){
        try{
            List<Building> getBuildings = buildingService.buildingsByProperty(propertyId);
            System.out.println("buildings ------->"+getBuildings);
            if(!getBuildings.isEmpty()){
                return ResponseEntity.ok(Map.of(
                        "status",true,
                        "message","data found",
                        "data",getBuildings
                ));
            }
            return ResponseEntity.noContent().build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong"
            ));
        }
    }
    @PostMapping("/update-building/{buildingId}")
    public ResponseEntity<?> updateBuilding(@PathVariable("buildingId") UUID buildingId, @RequestBody BuildingUpdateRequest buildingUpdateRequest){
        try{
            Building building = buildingService.updateBuilding(buildingId,buildingUpdateRequest);
            if(building != null){
                return ResponseEntity.ok(Map.of(
                        "status",true,
                        "message", "data updated"
                ));
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                    "status",false,
                    "message", "not updated"
            ));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message","Internal server error"
            ));
        }
    }

    @DeleteMapping("/delete-building/{buildingId}")
    public ResponseEntity<?> deleteBuilding(@PathVariable("buildingId") UUID buildingId){
        try{
            boolean isBuildingDeleted = buildingService.deleteBuilding(buildingId);
            if(isBuildingDeleted){
                return ResponseEntity.ok(Map.of(
                        "status",true,
                        "message", "building deleted"
                ));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status",false,
                    "message", "building not found"
            ));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message","Internal server error"
            ));
        }
    }
}
