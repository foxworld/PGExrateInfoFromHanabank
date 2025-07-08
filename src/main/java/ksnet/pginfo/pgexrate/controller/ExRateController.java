package ksnet.pginfo.pgexrate.controller;

import ksnet.pginfo.pgexrate.service.ExRateService;
import ksnet.pginfo.pgexrate.service.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExRateController implements ApplicationRunner {
    @Value("${ksnet.pginfo.rate_flag}") String rateFlag;

    private final ExRateService exRateService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("PG Exchange rate Form HanaBank!!!");

        switch (rateFlag) {
            case "FIRST" :
                exRateService.getExRateFirst("USD");
                exRateService.getExRateFirst("JPY");
                break;
            case "CHANGE" :
                exRateService.getExRateChangeHour("USD");
                break;

            case "USD01":
                String usdExRate = exRateService.getExRateFirst("USD", "01"); //외화수표파실때
                log.info("usdExRate={}", usdExRate);
                break;
            case "JPY01":
                String jpyExRate = exRateService.getExRateFirst("JPY", "01"); //외화수표파실때
                log.info("jpyExRate={}", jpyExRate);
                break;
            case "USD00":
                String usdBasicExRate = exRateService.getExRateFirst("USD", "00"); // 매매기준율
                log.info("usdBasicExRate={}", usdBasicExRate);
                break;
            case "JPY00":
                String jpyBasicExRate = exRateService.getExRateFirst("JPY", "00"); // 매매기준율
                log.info("jpyBasicExRate={}", jpyBasicExRate);
                break;
            case "TEST" :
                break;
        }

        log.info("Successfully retrieved exchange rate data from HanaBank!!!");
    }
}
