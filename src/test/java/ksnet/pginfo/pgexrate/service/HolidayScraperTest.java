package ksnet.pginfo.pgexrate.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class HolidayScraperTest {
    @Autowired ChinaHolidayScraper chinaHolidayScraper;
    @Autowired SingaporeHolidayScraper singaporeHolidayScraper;
    @Autowired HongKongHolidayScraper hongKongHolidayScraper;

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
    void convert() {
        //String str = "1 January 2025";
        String str = "2025년 1월 1일";
        log.info("{}", Holiday.toYyyyMMdd(str));
    }


}