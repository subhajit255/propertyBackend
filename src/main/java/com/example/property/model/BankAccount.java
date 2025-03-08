package com.example.property.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private User user;
    @Column(name = "bank_account_id")
    private String bankAccountId;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "account_holder_name")
    private String accountHolderName;
    @Column(name = "bank_name")
    private String bankName;
    private String country;
    private String currency;
    @Column(name = "customer_id")
    private String customerId;
    private String fingerPrint;
    private String last4;
    @Column(name = "routing_no")
    private String routingNo;
    @Column(name = "bank_token_id")
    private String bankTokenId;
    private boolean status = false;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
