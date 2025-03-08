package com.example.property.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDetails {

    private String accountHolderName;
    private String routingNo;
    private String accountNo;
}
