package ksnet.pginfo.pgexrate.repository;

import ksnet.pginfo.pgexrate.service.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ExRateRepositoryTest {

    @Test
    void currencyTesrt() {
        log.info("{}", CurrencyCode.getNumericCodeByAlpha("USD"));
    }

}