#  하나은행 환율정보 스크래핑

## 환율정보  
* 달러,엔화 매매기준율, 외화수표파실때
* 달러 10시경 변동환율 매매기준율   

## Usage : 
```
java -jar PGExrateInfoFromHanabank.jar --ksnet.pginfo.trade_date=yyyymmdd --ksnet.pginfo.rate_flag=FIRST
```
* --ksnet.pginfo.trade_date=거래일
* --ksnet.pginfo.rate_flag=FIRST(최초고시일)
* --ksnet.pginfo.rate_flag=CHANGE(10시변동환율)