package ksnet.pginfo.pgexrate.repository;

import jakarta.transaction.Transactional;
import ksnet.pginfo.pgexrate.domain.PgCal02;
import ksnet.pginfo.pgexrate.domain.PgCal02Key;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class PgCal02Repository {

    private final JpaPgCal02Repository repository;

    public void save(PgCal02 pgCal02) {
        repository.deleteById(new PgCal02Key(pgCal02.getCountryCode(), pgCal02.getTradeDate()));
        repository.save(pgCal02);
    }

    public Optional<PgCal02> findById(PgCal02Key key) {
        return repository.findById(key);
    }
}
