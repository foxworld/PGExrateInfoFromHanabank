package ksnet.pginfo.pgexrate.service;

import jakarta.transaction.Transactional;
import ksnet.pginfo.pgexrate.domain.PgCal02;
import ksnet.pginfo.pgexrate.domain.PgCal02Key;
import ksnet.pginfo.pgexrate.repository.PgCal02Repository;
import ksnet.pginfo.pgexrate.utils.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeCalendarService {

    private final PgCal02Repository repository;
    private final KoreaHolidayScraper koreaHolidayScraper;

    public void makeCalendar(int year) {

        LocalDate startDate = LocalDate.parse(year+"0101", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate endDate = LocalDate.parse(year+"1231", DateTimeFormatter.ofPattern("yyyyMMdd"));

        int i=0;
        while (!startDate.isAfter(endDate)) {
            PgCal02 pgCal02 = new PgCal02(
                    CurrencyCode.KRW.getNumericCode(),
                    startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                    Integer.toString(startDate.getDayOfWeek().getValue()-1),
                    (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY?"Y":"N")
            );

            repository.save(pgCal02);
            log.info("{}:{}", i++, pgCal02);
            startDate = startDate.plusDays(1); // 하루 증가
        }
    }

    public void setHoliday(int year) throws Exception {
        List<Holiday> holidayList = koreaHolidayScraper.getHolidays(2025);
        for(Holiday holiday : holidayList) {
            repository.setHoliday("410", holiday.getDate(), "Y");
        }




    }


}
