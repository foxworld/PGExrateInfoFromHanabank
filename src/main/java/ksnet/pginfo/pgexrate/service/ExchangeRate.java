package ksnet.pginfo.pgexrate.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeRate {
    private String currencyCode;
    private String currencyName;
    private String baseRate;
    private String buyRate;
    private String sellRate;
    private String sellForeignCheck;

//    private String sendRate;
//    private String recvRate;
//    private String basicRate;
//    private String discountRate;
//    private String usdConversionRate;


}
