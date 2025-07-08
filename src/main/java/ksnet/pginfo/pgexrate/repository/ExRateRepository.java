package ksnet.pginfo.pgexrate.repository;

import jakarta.transaction.Transactional;
import ksnet.pginfo.pgexrate.domain.PgExchangeRate;
import ksnet.pginfo.pgexrate.domain.PgExchangeRateKey;
import ksnet.pginfo.pgexrate.service.CurrencyCode;
import ksnet.pginfo.pgexrate.service.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class ExRateRepository {

    private final JpaExRateRepository jpaExRateRepository;
    private final PgUsd01Repository pgUsd01Repository;


    @Value("${ksnet.pginfo.trade_date}") String tradeDate;

    public void saveFirst(ExchangeRate exchangeRate) {

        PgExchangeRate pgExchangeRate = new PgExchangeRate();
        String currencyCode = CurrencyCode.getNumericCodeByAlpha(exchangeRate.getCurrencyCode());
        log.info("exchangeRate.getCurrencyCode()={}, currencyCode={}", exchangeRate.getCurrencyCode(), currencyCode);

        delete(tradeDate, currencyCode, "01");
        double sellForeignCheck = Double.parseDouble(exchangeRate.getSellForeignCheck().replace(",", ""));
        pgExchangeRate.insert(tradeDate, currencyCode, "01", sellForeignCheck);
        jpaExRateRepository.save(pgExchangeRate);

        if(exchangeRate.getCurrencyCode().equals("USD")) {
            pgUsd01Repository.save(tradeDate, sellForeignCheck);
        }

        delete(tradeDate, currencyCode, "00");
        double basicRate = Double.parseDouble(exchangeRate.getBaseRate().replace(",", ""));
        pgExchangeRate.insert(tradeDate, currencyCode, "00", basicRate);
        jpaExRateRepository.save(pgExchangeRate);
    }

    public void saveChange(ExchangeRate exchangeRate) {
        PgExchangeRate pgExchangeRate = new PgExchangeRate();
        String currencyCode = CurrencyCode.getNumericCodeByAlpha(exchangeRate.getCurrencyCode());

        delete(tradeDate, currencyCode, "10");
        double basicRate = Double.parseDouble(exchangeRate.getBaseRate().replace(",", ""));
        pgExchangeRate.insert(tradeDate, currencyCode, "10", basicRate);
        jpaExRateRepository.save(pgExchangeRate);
    }

    private void delete(String tradeDate, String currencyCode, String currencyType) {
        PgExchangeRateKey key = new PgExchangeRateKey(tradeDate, currencyCode, currencyType);
        jpaExRateRepository.deleteById(key);
    }

}
