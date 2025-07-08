package ksnet.pginfo.pgexrate.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ChinaHolidayScraper {
    public List<Holiday> getHolidays(int year) throws Exception {

        String url = "https://www.timeanddate.com/holidays/china/" + year;
        Document doc = Jsoup.connect(url).get();

        List<Holiday> holidays = new ArrayList<>();

        Element table = doc.selectFirst("table.table--left.table--inner-borders-rows");
        if (table == null) throw new Exception("Holiday table not found");

        Elements rows = table.select("tbody tr");

        for (Element row : rows) {
            String date = null;
            Elements ths = row.select("th");
            if (!ths.isEmpty()) {
                date = Holiday.toYyyyMMdd(year + "년 " + ths.get(0).text().trim());
                log.info("{}:{}", ths.get(0).text().trim()+ " " + year, date);
            }
            Elements tds = row.select("td");
            if (tds.size() >= 3) {
                //String date = tds.get(0).text();      // 날짜
                String name = tds.get(1).text();      // 휴일명
                String type = tds.get(2).text();      // 유형 (공휴일, 관측일 등)
                if ("National holiday".equalsIgnoreCase(type)) {
                    holidays.add(new Holiday(date, name, type));
                }
            }
        }
        return holidays;
    }

    private String dateFormat(String input) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy년 M월 d일");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        try {
            Date date = inputFormat.parse(input);
            String result = outputFormat.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
