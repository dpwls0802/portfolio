--계정생성 & 권한 
--아이디,비번 flopoAdmin
CREATE USER c##flopoAdmin IDENTIFIED BY flopoAdmin
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;
GRANT CONNECT, DBA TO c##flopoAdmin


drop sequence seq_areainfo;
drop table f_areainfo;
alter table f_areainfo drop constraint pk_areainfo;

create sequence seq_areainfo;
--래벨 테이블생성
CREATE TABLE f_areainfo(
	areainfo_id integer references f_area(id),
    recodedDate date,
    male   integer,
    female integer,
    population integer,
    age10s integer,
    age20s integer,
    age30s integer,
    age40s integer,
    age50s integer,
    ageOver60s integer,
    CONSTRAINT pk_areainfo PRIMARY KEY (areainfo_id, recodedDate)
);


--데이블 데이터 삽입

declare
begin
for i in 1 .. 274 loop
    for j in 1 .. 594 loop
        --남,여 랜덤값
        insert into f_areainfo(areainfo_id,recodedDate,male,female)
            values (seq_areainfo.nextval,to_char(sysdate,'YYYYMMDDHH24'),
            dbms_random.value(0,50),dbms_random.value(0,50));
        
       
    end loop; 
end loop; 
end;


        ---남+여를 인구값에 저장    
declare
    num number :=0;
begin
for i in 1 .. 274 loop
    for j in 1 .. 594 loop
        num := num+1;
        update f_areainfo set population = (select sum(male+female) as population  from f_areainfo where areainfo_id = num)
        where areainfo_id = num;
    end loop; 
end loop; 
end;

------------------------예상 테이블
create table tbl_areainfo(
    areainfo_id number,
    area_id number references tbl_area(id),
    date date,
    population_cnt number,
    male_cnt number,
    female_cnt number,
    age_10s_cnt number,
    age_20s_cnt number,
    age_30s_cnt number,
    age_40s_cnt number,
    age_50s_cnt number,
    age_over60s_cnt number,
    CONSTRAINT pk_areainfo PRIMARY KEY (areainfo_id,area_id, date)
);

delete from tbl_areainfo;
insert into tbl_areainfo values(1,1,TO_DATE('2019-12-19 16','YYYY-MM-DD HH24'),10,6,4,1,2,2,2,2,1);
insert into tbl_areainfo values(2,2,TO_DATE('2019-12-19 16','YYYY-MM-DD HH24'),15,5,10,1,5,5,2,2,0);
insert into tbl_areainfo values(3,3,TO_DATE('2019-12-19 16','YYYY-MM-DD HH24'),10,7,3,2,2,2,2,2,0);

insert into tbl_areainfo values(4,1,TO_DATE('2019-12-19 17','YYYY-MM-DD HH24'),10,6,4,1,2,2,2,2,1);
insert into tbl_areainfo values(5,2,TO_DATE('2019-12-19 17','YYYY-MM-DD HH24'),13,6,7,1,2,2,5,2,1);
insert into tbl_areainfo values(6,3,TO_DATE('2019-12-19 17','YYYY-MM-DD HH24'),12,7,5,1,2,3,3,2,1);

insert into tbl_areainfo values(7,1,TO_DATE('2019-12-19 18','YYYY-MM-DD HH24'),5,2,3,0,2,2,1,0,0);
insert into tbl_areainfo values(8,2,TO_DATE('2019-12-19 18','YYYY-MM-DD HH24'),10,6,4,0,0,0,0,5,5);
insert into tbl_areainfo values(9,3,TO_DATE('2019-12-19 18','YYYY-MM-DD HH24'),7,6,1,1,2,2,2,0,0);

select areainfo_id,area_id,TO_CHAR(recoded_date,'YYYY-MM-DD HH24') asrecoded_date
,population_cnt,male_cnt,female_cnt 
,age_10s_cnt,age_20s_cnt,age_30s_cnt,age_40s_cnt,age_50s_cnt,age_over60s_cnt
from tbl_areainfo;



