package com.example.property.resource;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankDetailResource {
    private String bankAccountId;
    private String bankName;
    private String country;
    private String currency;
    private String customerId;
    private String fingerPrint;
    private String last4;
    private String routingNo;
    private String bankTokenId;
}
