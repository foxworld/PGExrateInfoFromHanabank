package ksnet.pginfo.pgexrate.repository;

import ksnet.pginfo.pgexrate.utils.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ExRateRepositoryTest {

    @Test
    void currencyTest() {
        log.info("{}", CurrencyCode.getNumericCodeByAlpha("USD"));
    }

}