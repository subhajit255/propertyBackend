package com.example.property.controller;

import com.example.property.model.Property;
import com.example.property.request.BankAccountDetails;
import com.example.property.request.PropertyRequest;
import com.example.property.service.PropertyService;
import com.example.property.service.implementation.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.BankAccount;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/property")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private StripeService stripeService;

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

    @GetMapping("my-properties")
    public ResponseEntity<?> getProperties(@RequestAttribute UUID userId){
        System.out.println("hello ");
        try{
            List<Property> myProperties = propertyService.getPropertyByUser(userId);
            if(!myProperties.isEmpty()){
                return ResponseEntity.ok(Map.of(
                        "status", true,
                        "message", "data found",
                        "data", myProperties
                ));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", false,
                    "message", "no data found"
            ));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Something went wrong"
            ));
        }

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProperty(@Valid @PathVariable UUID id, @RequestAttribute UUID userId, @RequestBody PropertyRequest propertyRequest, BindingResult result){
        try{
            Property isPropertyUpdated = propertyService.update(id,propertyRequest);
            if (isPropertyUpdated != null){
                return ResponseEntity.ok(Map.of(
                        "status",true,
                        "message","poperty updated"
                ));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status",false,
                    "message", "data not updated"
            ));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong"
            ));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable UUID id){
        try{
            boolean isPropertyDeleted = propertyService.delete(id);
            return ResponseEntity.ok(Map.of(
                    "status", isPropertyDeleted ? true : false,
                    "message", isPropertyDeleted ? "data deleted" : "data not deleted"
            ));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status",false,
                    "message","something went wrong"
            ));
        }
    }

//    @PostMapping("/create-bank-account-token")
//    public Map<String, Object> createBankAccountToken(@RequestBody BankAccountDetails details) throws StripeException {
//        try {
//            Token token = stripeService.createToken(details);
//            System.out.println("token ------------>>>>>>>>>>>");
//            System.out.println(token);
//            BankAccount bankAccount = token.getBankAccount();
//            Map<String, Object> response = new HashMap<>();
//            response.put("bankAccountId",bankAccount.getId());
//            response.put("bankName", bankAccount.getBankName());
//            response.put("country", bankAccount.getCountry());
//            response.put("currency", bankAccount.getCurrency());
//            response.put("customer", bankAccount.getCustomer());
//            response.put("fingerPrint", bankAccount.getFingerprint());
//            response.put("last4", bankAccount.getLast4());
//            response.put("routingNo", bankAccount.getRoutingNumber());
//            response.put("bankAccountToken", token.getId());
//            // Return the full token object
//            return response;
//        } catch (Exception e) {
//            // Log the error for debugging purposes
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Failed to create bank account token");
//        }
//    }
}
