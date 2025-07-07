package ksnet.pginfo.pgexrate.service;

import ksnet.pginfo.pgexrate.repository.ExRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExRateService {

    private final ExRateRepository repository;

    @Value("${ksnet.pginfo.exchange_rate.url}") String url;
    @Value("${ksnet.pginfo.exchange_rate.change.url}") String changeUrl;
    @Value("${ksnet.pginfo.exchange_rate.change.hour}") Integer changeHour;
    @Value("${ksnet.pginfo.trade_date}") String tradeDate;

    public ExchangeRate getExRateFirst(String moneyCode) throws Exception {
        ExchangeRate result = getExchangeRate(moneyCode, tradeDate);
        log.info("ExchangeRateFirst={}", result);

        repository.saveFirst(result);

        return result;
    }

    public ExchangeRate getExRateChangeHour(String moneyCode) throws Exception {
        //10시 변동환율
        ExchangeRate result = getExchangeRateChange(moneyCode, tradeDate, changeHour);
        log.info("getExRateChangeHour={}", result);

        repository.saveChange(result);

        return result;
    }

    public String getExRateFirst(String moneyCode, String exchangeType) throws Exception {
        ExchangeRate result = getExchangeRate(moneyCode, tradeDate);
        log.info("ExchangeRateTarget={}", result);

        return switch (exchangeType) {
            case "01" -> result.getSellForeignCheck(); //초회차 외화수표파실때
            case "00" -> result.getBaseRate();         //초회차 매매기준율
            default -> null;
        };
    }

    public ExchangeRate getExchangeRate(String curCd, String inqStrDt) throws Exception {
        // 날짜 포맷 변경 (yyyyMMdd → yyyy-MM-dd)
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inqStrDt, inputFormat);
        String tmpInqStrDt = date.format(outputFormat);

        Map<String, String> params = new HashMap<>();
        params.put("ajax", "true");
        params.put("curCd", curCd);
        params.put("tmpInqStrDt", tmpInqStrDt);
        params.put("inqStrDt", inqStrDt);
        params.put("pbldDvCd", "1");
        params.put("inqKindCd", "1");
        params.put("requestTarget", "searchContentDiv");

        Connection.Response response = Jsoup.connect(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .data(params)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute();

        Document doc = Jsoup.parse(response.body());

        String base = Objects.requireNonNull(doc.selectFirst("p.txtRateBox span strong")).select("strong").get(0).text();
        String tradeDate = base.replace("년","").replace("월","").replace("일","");
        log.info("초회차:요청일자={}, 스크래핑일자={}", inqStrDt, tradeDate);
        if(!tradeDate.equals(inqStrDt)) {
            throw new Exception("해당 통화 정보를 찾을 수 없습니다. 거래일:"+inqStrDt);
        }

        Element thead = doc.selectFirst("table thead");
        if (thead != null) {
            Elements cols = thead.select("tr th");
            if(!cols.get(3).text().replace(" ", "").equals("외화수표파실때") &&
                    !cols.get(4).text().replace(" ", "").equals("매매기준율")) {
                log.error("first:cols3={}", cols.get(3).text().replace(" ", ""));
                log.error("first:cols4={}", cols.get(4).text().replace(" ", ""));
                throw new Exception("해당 통화 정보를 찾을 수 없습니다. 거래일:"+inqStrDt);
            }
        }

        Element tbody = doc.selectFirst("table tbody");
        if (tbody != null) {
            for (Element row : tbody.select("tr")) {
                Elements cols = row.select("td");
                if (cols.size() > 5 && cols.get(0).text().contains(curCd)) {
                    return new ExchangeRate(
                        curCd,
                        cols.get(0).text(), // 통화명
                        cols.get(8).text(), // 매매 기준율
                        cols.get(6).text(), // 송금 받을 때
                        cols.get(5).text(), // 송금 보낼 때
                        cols.get(7).text()  // 외화수표파실때
                    );
                }
            }
        }
        throw new Exception("해당 통화 정보를 찾을 수 없습니다. 거래일:"+inqStrDt);
    }

    public ExchangeRate getExchangeRateChange(String curCd, String inqStrDt, int hour) throws Exception {
        String strHour = String.format("%02d", hour);

        // 날짜 포맷 변경 (yyyyMMdd → yyyy-MM-dd)
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inqStrDt, inputFormat);
        String tmpInqStrDt = date.format(outputFormat);

        Map<String, String> params = new HashMap<>();
        // 개발자도구 > Network > Name(wpfxd651_07i_01.do)클릭 > Payload에서 항목 확인 가능.
        params.put("ajax", "true");
        params.put("inqType", "0");
        params.put("tmpInqStrDt_d", inqStrDt);
        params.put("tmpInqStrDtY_m", inqStrDt.substring(0, 4));
        params.put("tmpInqStrDtM_m", inqStrDt.substring(4, 6));
        params.put("tmpInqStrDt_p", inqStrDt);
        params.put("tmpInqEndDt_p", inqStrDt);
        params.put("curCd", curCd);
        params.put("tmpPbldDvCd", "1");
        params.put("hid_key_data", "");
        params.put("tmpInqStrDt", tmpInqStrDt);
        params.put("inqDt", inqStrDt);
        params.put("inqDvCd", "1");
        params.put("hid_enc_data", "");
        params.put("requestTarget", "searchContentDiv");

        Connection.Response response = Jsoup.connect(changeUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .data(params)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute();

        Document doc = Jsoup.parse(response.body());

        String base = Objects.requireNonNull(doc.selectFirst("p.txtRateBox span strong")).select("strong").get(0).text();
        String tradeDate = base.replace("년","").replace("월","").replace("일","");
        log.info("변동환율정보:요청일자={}, 스크래핑일자={}", inqStrDt, tradeDate);
        if(!tradeDate.equals(inqStrDt)) {
            throw new Exception("해당 통화 변경정보를 찾을 수 없습니다. 거래일:"+inqStrDt);
        }

        Element thead = doc.selectFirst("table thead");
        if (thead != null) {
            Elements cols = thead.select("tr th");
            if(!cols.get(4).text().replace(" ", "").equals("외화수표파실때") &&
                    !cols.get(5).text().replace(" ", "").equals("매매기준율")) {
                log.error("change:cols4={}", cols.get(4).text().replace(" ", ""));
                log.error("change:cols5={}", cols.get(5).text().replace(" ", ""));
                throw new Exception("해당 통화 정보를 찾을 수 없습니다. 거래일:"+inqStrDt);
            }
        }

        Element tbody = doc.selectFirst("table tbody");
        log.trace("tbody={}", tbody);
        if (tbody != null) {
            ExchangeRate result = null;
            for (Element row : tbody.select("tr")) {
                Elements cols = row.select("td");
                if (cols.size() > 5 && cols.get(1).text().startsWith(strHour)) {
                    result = new ExchangeRate(
                            curCd,
                            cols.get(1).text(), // 통화명
                            cols.get(7).text(), // 매매 기준율
                            cols.get(5).text(), // 송금 받을 때
                            cols.get(4).text(), // 송금 보낼 때
                            cols.get(6).text()  // 외화수표파실때
                    );
                }
            }
            if(result != null) {
                return result;
            }
        }
        throw new Exception("해당 통화 변경정보를 찾을 수 없습니다. 거래일:"+inqStrDt);
    }

}
