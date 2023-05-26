package com.ozan.exchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CurrencyExchange {

    @Id
    private String transactionId;
    private String source;
    private String target;
    private int amount;
    private double result;
    @CreatedDate
    private LocalDateTime createdAt;

}
