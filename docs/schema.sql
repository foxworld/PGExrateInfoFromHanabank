create table pgexrate (
    tr_date char(8) not null,
    money_code char(3) default '840' not null,
    ex_sele char(2) default '01' not null,
    ex_rate double not null,
    chan_date char(8) not null,
    chan_time char(6) not null
);
create unique index pgexrate_pk on pgexrate(tr_date, money_code, ex_sele);

create table pgusd01 (
tr_date char(8) not null,
usd_rate decimal(6,2) not null
);
create unique index pgusd01_pk on pgusd01(tr_date);