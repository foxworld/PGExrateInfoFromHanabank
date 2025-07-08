package ksnet.pginfo.pgexrate.repository;

import jakarta.transaction.Transactional;
import ksnet.pginfo.pgexrate.domain.PgUsd01;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class PgUsd01Repository {

    private final JpaPgUsd01Repository repository;

    public void save(String tradeDate, double usdRate) {
        repository.deleteById(tradeDate);;
        repository.save(new PgUsd01(tradeDate, usdRate));
    }
}
