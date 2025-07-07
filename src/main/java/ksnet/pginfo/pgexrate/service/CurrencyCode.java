package ksnet.pginfo.pgexrate.service;

public enum CurrencyCode {
    USD("840", "미국 달러"),
    JPY("392", "일본 엔"),
    EUR("978", "유로"),
    CNY("156", "중국 위안"),
    GBP("826", "영국 파운드"),
    AUD("036", "호주 달러"),
    CAD("124", "캐나다 달러"),
    CHF("756", "스위스 프랑"),
    HKD("344", "홍콩 달러"),
    KRW("410", "대한민국 원"),
    SGD("702", "싱가포르 달러");

    private final String numericCode;
    private final String description;

    CurrencyCode(String numericCode, String description) {
        this.numericCode = numericCode;
        this.description = description;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public String getDescription() {
        return description;
    }

    public static String getNumericCodeByAlpha(String alphaCode) {
        for (CurrencyCode code : CurrencyCode.values()) {
            if (code.name().equalsIgnoreCase(alphaCode)) {
                return code.getNumericCode();
            }
        }
        return null;
    }

    public static CurrencyCode fromAlpha(String alphaCode) {
        for (CurrencyCode code : CurrencyCode.values()) {
            if (code.name().equalsIgnoreCase(alphaCode)) {
                return code;
            }
        }
        return null;
    }
}
