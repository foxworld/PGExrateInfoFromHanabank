package ksnet.pginfo.pgexrate.repository;

import ksnet.pginfo.pgexrate.domain.PgExchangeRate;
import ksnet.pginfo.pgexrate.domain.PgExchangeRateKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaExRateRepository extends JpaRepository<PgExchangeRate, PgExchangeRateKey> {
}
