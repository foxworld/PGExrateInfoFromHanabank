package ksnet.pginfo.pgexrate.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SingaporeHolidayScraper {
    public List<Holiday> getHolidays(int year) throws Exception {
        String url = "https://www.mom.gov.sg/employment-practices/public-holidays";
        Document doc = Jsoup.connect(url).get();

        List<Holiday> list = new ArrayList<>();

        Elements tables = doc.select("table"); // 여러 테이블 존재
        for (Element table : tables) {
            Elements rows = table.select("tbody tr");

            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() >= 2) {
                    String date = Holiday.toYyyyMMdd(cols.get(0).text().trim());
                    String name = cols.get(1).text();

                    if (date!=null && date.contains(String.valueOf(year))) {
                        list.add(new Holiday(date, name, ""));
                    }
                }
            }
        }

        return list;
    }
}
