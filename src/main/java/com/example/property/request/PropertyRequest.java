package com.example.property.request;

import com.example.property.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest {

    @NotBlank(message = "property kind must not be empty")
    private UUID propertyKindId;
    @NotBlank(message = "property type must not be empty")
    private UUID propertyTypeId;
    @NotBlank(message = "name cannot be empty")
    @Size(min = 3, max = 50, message = "name should be within 3 to 50 characters")
    public String name;
    private String description;
    @NotBlank(message = "latitude cannot be empty")
    private String latitude;
    @NotBlank(message = "longitude cannot be empty")
    private String longitude;
    @NotBlank(message = "street address cannot be empty")
    private String streetAddress1;
    private String streetAddress2;
    @NotBlank(message = "city cannot be empty")
    private String city;
    @NotBlank(message = "state cannot be empty")
    private int stateId;
    @NotBlank(message = "country cannot be empty")
    private int countryId;
    @NotBlank(message = "zipcode cannot be empty")
    private String zipCode;
    @NotBlank(message = "bank name cannot be empty")
    private String bankName;
    @NotBlank(message = "branch name cannot be empty")
    private String branchName;
    @NotBlank(message = "account no cannot be empty")
    private String accNo;
    @NotBlank(message = "routing no cannot be empty")
    private String routingNo;
}
