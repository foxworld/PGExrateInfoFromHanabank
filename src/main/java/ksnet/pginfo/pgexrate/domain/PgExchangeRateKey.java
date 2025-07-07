package ksnet.pginfo.pgexrate.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PgExchangeRateKey {
    private String tradeDate;
    private String currencyCode;
    private String exchangeType;
}
