package ksnet.pginfo.pgexrate.controller;

import ksnet.pginfo.pgexrate.service.ExRateService;
import ksnet.pginfo.pgexrate.service.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExRateController implements ApplicationRunner {

    private final ExRateService exRateService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("PG Exchange rate Form HanaBank!!!");

        exRateService.getExRateFirst("USD");
        exRateService.getExRateFirst("JPY");
        exRateService.getExRate10Hour("USD");

        String usdExRate = exRateService.getExRateFirst("USD", "01"); //외화수표파실때
        String jpyExRate = exRateService.getExRateFirst("JPY", "01"); //외화수표파실때
        String usdBasicExRate = exRateService.getExRateFirst("USD", "00"); // 매매기준율
        String jpyBasicExRate = exRateService.getExRateFirst("JPY", "00"); // 매매기준율
        log.info("usdExRate={},jpyExRate={}, usdBasicExRate={}, jpyBasicExRate={}", usdExRate, jpyExRate, usdBasicExRate, jpyBasicExRate);

        log.info("Successfully retrieved exchange rate data from HanaBank!!!");
    }
}
