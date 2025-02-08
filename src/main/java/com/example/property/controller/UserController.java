package com.example.property.controller;

import com.example.property.model.User;
import com.example.property.request.UpdateUserRequest;
import com.example.property.request.UserLoginRequest;
import com.example.property.request.UserRegRequest;
import com.example.property.resource.AuthResource;
import com.example.property.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody UserRegRequest userRegRequest, BindingResult result) {
        System.out.println("Received registration request: " + userRegRequest);

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
            AuthResource authResource = userService.register(userRegRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", true,
                    "message", "Registration successful",
                    "data", authResource
            ));
        } catch (Exception e) {
            e.printStackTrace(); // Log the exact error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Something Went Wrong"
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(Map.of(
                    "status", false,
                    "message", result.getAllErrors().stream()
                            .map(error -> error.getDefaultMessage())
                            .toList()
            ));
        }
        try{
            AuthResource authResource = userService.verify(userLoginRequest);
                return ResponseEntity.ok(Map.of(
                   "status",true,
                   "message","login succcessful",
                   "data",authResource
                ));

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "status", false,
                    "message", "Invalid credentials"
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Something Went Wrong"
            ));
        }
    }

    @PostMapping(value = "/update", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateProfile(@RequestAttribute UUID userId,
                                           @RequestPart(value = "name", required = false) String name,
                                           @RequestPart(value = "email", required = false) String email,
                                           @RequestPart(value = "phone", required = false) String phone,
                                           @RequestPart(value = "address", required = false) String address,
                                           @RequestPart(value = "image", required = false) MultipartFile image){
        try {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setName(name);
            updateUserRequest.setEmail(email);
            updateUserRequest.setPhone(phone);
            updateUserRequest.setAddress(address);
            updateUserRequest.setImage(image);
            User isUserUpdate = userService.updateProfile(userId, updateUserRequest);
            return ResponseEntity.ok(Map.of(
                    "status", true,
                    "message", "data updated",
                    "data", isUserUpdate
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status",false,
                    "message","something wrong happend"
            ));
        }
    }

    @GetMapping("my-profile")
    public ResponseEntity<?> myProfile(@RequestAttribute UUID userId){
        try{
            AuthResource authResource = userService.myProfile(userId);
            return ResponseEntity.ok(Map.of(
                    "status",true,
                    "message","data found",
                    "data",authResource
            ));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "something went wrong"
            ));
        }

    }

}
