package com.example.property.service;

import com.example.property.model.Role;
import com.example.property.model.User;
import com.example.property.request.BankAccountDetails;
import com.example.property.request.UpdateUserRequest;
import com.example.property.request.UserLoginRequest;
import com.example.property.request.UserRegRequest;
import com.example.property.resource.AuthResource;
import com.example.property.resource.BankDetailResource;
import com.stripe.exception.StripeException;

import java.io.IOException;
import java.util.UUID;

public interface UserService {

    Role getRoleById(UUID id);
    User getUserById(UUID id);
    AuthResource register(UserRegRequest userRegRequest) throws IOException, StripeException;
    AuthResource verify(UserLoginRequest userLoginRequest);
    AuthResource myProfile(UUID id);
    User updateProfile(UUID id, UpdateUserRequest updateUserRequest);
    boolean addBankDetails(UUID userId,BankAccountDetails bankAccountDetails) throws StripeException;
}
