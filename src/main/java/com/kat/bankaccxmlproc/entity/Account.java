package com.kat.bankaccxmlproc.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Account {

    @JacksonXmlProperty(isAttribute = true)
    private String iban;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private String currency;

    @JacksonXmlProperty
    private Double balance;

    @JacksonXmlProperty
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate closingDate;

    public Account() {
    }

    public Account(String iban, String name, String currency, Double balance, LocalDate closingDate) {
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.closingDate = closingDate;
    }
}