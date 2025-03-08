package com.example.property.service.implementation;

import com.example.property.config.StripeConfig;
import com.example.property.request.BankAccountDetails;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.TokenCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    @Autowired
    private StripeConfig stripeConfig;

    public Token createToken(BankAccountDetails bankAccountDetails) throws StripeException {
        // Create bank account token parameters
        TokenCreateParams params = TokenCreateParams.builder()
                .setBankAccount(
                        TokenCreateParams.BankAccount.builder()
                                .setCountry("US")
                                .setCurrency("usd")
                                .setAccountHolderName(bankAccountDetails.getAccountHolderName())
                                .setAccountHolderType(TokenCreateParams.BankAccount.AccountHolderType.INDIVIDUAL)
                                .setRoutingNumber(bankAccountDetails.getRoutingNo())
                                .setAccountNumber(bankAccountDetails.getAccountNo())
                                .build()
                )
                .build();

        // Create and return the token
        return Token.create(params);
    }
     public Customer createCustomer(String name, String email) throws StripeException {
         CustomerCreateParams params =
                 CustomerCreateParams.builder()
                         .setName(name)
                         .setEmail(email)
                         .build();
         Customer customer = Customer.create(params);
         return customer;
     }
}
