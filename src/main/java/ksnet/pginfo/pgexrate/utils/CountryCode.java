package ksnet.pginfo.pgexrate.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountryCode {
    KOR("KR", "South Korea", "대한민국", "KRW", "원", "410"),
    USA("US", "United States", "미국", "USD", "달러", "840"),
    JPN("JP", "Japan", "일본", "JPY", "엔", "392"),
    CHN("CN", "China", "중국", "CNY", "위안", "156"),
    HKG("HK", "Hong Kong", "홍콩", "HKD", "홍콩달러", "344"),
    SGP("SG", "Singapore", "싱가포르", "SGD", "싱가포르달러", "702");

    private final String alpha2Code;
    private final String englishName;
    private final String koreanName;
    private final String currencyCode;
    private final String currencyName;
    private final String currencyNumericCode;

    public static CountryCode fromAlpha3(String code) {
        try {
            return CountryCode.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid alpha-3 country code: " + code);
        }
    }

    public static CountryCode fromAlpha2(String code) {
        for (CountryCode c : values()) {
            if (c.alpha2Code.equalsIgnoreCase(code)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid alpha-2 country code: " + code);
    }
}
