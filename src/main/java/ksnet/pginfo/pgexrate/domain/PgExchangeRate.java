package ksnet.pginfo.pgexrate.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(PgExchangeRateKey.class)
@Table(name="pgexrate")
public class PgExchangeRate {

    @Id
    @Column(name="tr_date")
    private String tradeDate;
    @Id
    @Column(name="money_code")
    private String currencyCode;
    @Id
    @Column(name="ex_sele")
    private String exchangeType;

    @Column(name="ex_rate")
    private double exchangeRate;

    @Column(name="chan_date")
    private String changeDate;
    @Column(name="chan_time")
    private String changeTime;

    public void insert(String tradeDate, String currencyCode, String exchangeType, double exchangeRate) {
        this.tradeDate = tradeDate;
        this.currencyCode = currencyCode;
        this.exchangeType = exchangeType;
        this.exchangeRate = exchangeRate;

        LocalDateTime now = LocalDateTime.now();
        this.changeDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.changeTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));
    }
}
