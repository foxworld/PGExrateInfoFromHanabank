package ksnet.pginfo.pgexrate.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class HolidayScraperTest {
    @Autowired ChinaHolidayScraper chinaHolidayScraper;
    @Autowired SingaporeHolidayScraper singaporeHolidayScraper;
    @Autowired HongKongHolidayScraper hongKongHolidayScraper;
    @Autowired UsHolidayScraper usHolidayScraper;
    @Autowired KoreaHolidayScraper koreaHolidayScraper;


    @Test
    void getChinaHolidayTest() throws Exception {
        log.info("{}", chinaHolidayScraper.getHolidays(2025));
    }

    @Test
    void getSingaporeHolidayTest() throws Exception {
        log.info("{}", singaporeHolidayScraper.getHolidays(2025));
    }

    @Test
    void getHongkongHolidayTest() throws Exception {
        log.info("{}", hongKongHolidayScraper.getHolidays(2025));
    }

    @Test
    void getUsHolidayTest() throws Exception {
        log.info("{}", usHolidayScraper.getFederalHolidays(2025));
    }

    @Test
    void getKoreaHolidayTest() throws Exception {
        List<Holiday> holidayList = koreaHolidayScraper.getHolidays(2025);
         for(Holiday holiday : holidayList) {
            log.info("{} : {}", holiday.getDate(), holiday.getName());
        }
    }


    @Test
    void convert() {
        //String str = "1 January 2025";
        String str = "2025년 1월 1일";
        log.info("{}", Holiday.toYyyyMMdd(str));
    }


}