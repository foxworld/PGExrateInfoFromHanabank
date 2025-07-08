package ksnet.pginfo.pgexrate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="pgusd01")
public class PgUsd01 {
    @Id
    @Column(name="tr_date")
    private String tradeDate;
    @Column(name="usd_rate")
    private double usdRate;
}
