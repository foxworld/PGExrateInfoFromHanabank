package ksnet.pginfo.pgexrate.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
//@Transactional
class MakeCalendarServiceTest {

    @Autowired MakeCalendarService service;
    @Autowired KoreaHolidayScraper koreaHolidayScraper;

    @Test
    void makeCalLoopTest() throws Exception {
        service.makeCalendar(2025);
    }

}