package ksnet.pginfo.pgexrate.repository;

import jakarta.transaction.Transactional;
import ksnet.pginfo.pgexrate.domain.PgCal02;
import ksnet.pginfo.pgexrate.domain.PgCal02Key;
import ksnet.pginfo.pgexrate.utils.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class PgCal02RepositoryTest {

    @Autowired PgCal02Repository repository;

    @Test
    void saveCalendarTest() {
        PgCal02 pgCal02 = new PgCal02(CurrencyCode.KRW.getNumericCode(), "20250101", "2", "N");
        repository.save(pgCal02);

        Optional<PgCal02> findPgCal02 = repository.findById(new PgCal02Key(CurrencyCode.KRW.getNumericCode(), "20250101"));
        log.info("findById={}", findPgCal02);

        assertThat(pgCal02).isEqualTo(findPgCal02.get());
    }

}