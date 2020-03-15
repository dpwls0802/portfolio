--�������� & ���� 
--���̵�,��� flopoAdmin
CREATE USER c##flopoAdmin IDENTIFIED BY flopoAdmin
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;
GRANT CONNECT, DBA TO c##flopoAdmin

drop sequence seq_area;
drop table  f_area;

--���� ���̺����
CREATE TABLE f_zoom_level(
    zoom_lvl   integer primary key,
    descript   varchar2(1000)
);

--���������� �߰�
insert into f_zoom_level values(1, '����: 20m');
insert into f_zoom_level values(2, '����: 30m');
insert into f_zoom_level values(3, '����: 50m');
insert into f_zoom_level values(4, '����: 100m');
insert into f_zoom_level values(5, '����: 250');
insert into f_zoom_level values(6, '����: 500m');
insert into f_zoom_level values(7, '����: 1km');
insert into f_zoom_level values(8, '����: 2km');



--������ �߰� 1,2,3,4.......
create sequence seq_area;

CREATE TABLE f_area(
	id   integer primary key,
    zoom_lvl  integer references f_zoom_level(zoom_lvl),
	parent_area_id integer references f_area(id),
    sw_longitude number,
    sw_latitude number,
    ne_longitude number,
    ne_latitude number
);
--long�� 0.0009 ���� 
--126.734086 ���� 126.734086 + 0.5346 ���� (594��)


--lat�� 0.0011 ����
--37.413294����  37.413294 + 0.3014 ���� (274��)


--level 1,2 ���ϴ� ����
declare
    latitude number :=37.413294;
    longitude number :=126.734086;
    lat100m number :=0.0011;
    lon100m number := 0.0009;
begin
for i in 1 .. 274 loop
    for j in 1 .. 594 loop
   													 --null�� parent id�̴�. ����� null�� �ӽ�����
       insert into f_area values (SEQ_AREA.nextval,1,null,longitude,latitude,longitude+lon100m,latitude+lat100m); 
       longitude := longitude + lon100m;
    end loop; 
     longitude :=126.734086;
     latitude:=latitude+lat100m;
end loop; 
end;

--level3,4 �䷱�� id���ϴ� �׽�Ʈ
declare
    latitude number :=37.413294;
    longitude number :=126.734086;
    lat100m number :=0.0011*3;
    lon100m number := 0.0009*3;
begin
for i in 1 .. CEIL(274/3) loop
    for j in 1 .. CEIL(594/3) loop
   													 --null�� parent id�̴�. ����� null�� �ӽ�����
       insert into f_area values (SEQ_AREA.nextval,3,null,longitude,latitude,longitude+lon100m,latitude+lat100m); 
       
       --��� insert �� �� ������ ���� : select * from f_area where zoom_lvl = 3 and sw_longitude = longitude
      update f_area
       set parent_area_id = (select id from f_area where zoom_lvl = 3 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
       where zoom_lvl = 1
       	and sw_latitude >= (select sw_latitude from f_area where zoom_lvl = 3 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
    	and sw_longitude >= (select sw_longitude from f_area where zoom_lvl = 3 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
    	and ne_latitude<=  (select ne_latitude from f_area where zoom_lvl = 3 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
    	and ne_longitude<= (select ne_longitude from f_area where zoom_lvl = 3 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m );      
       longitude := longitude + lon100m;
    end loop; 
     longitude :=126.734086;
     latitude:=latitude+lat100m;
end loop; 
end;

--level5,6 �䷱�� id���ϴ� �׽�Ʈ
declare
    latitude number :=37.413294;
    longitude number :=126.734086;
    lat100m number :=0.0011*9;
    lon100m number := 0.0009*9;
begin
for i in 1 .. CEIL(274/9) loop
    for j in 1 .. CEIL(594/9) loop
   													 --null�� parent id�̴�. ����� null�� �ӽ�����
       insert into f_area values (SEQ_AREA.nextval,5,null,longitude,latitude,longitude+lon100m,latitude+lat100m); 
       
       --��� insert �� �� ������ ���� : select * from f_area where zoom_lvl = 5 and sw_longitude = longitude
      update f_area
       set parent_area_id = (select id from f_area where zoom_lvl = 5 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
       where zoom_lvl = 3
       	and sw_latitude >= (select sw_latitude from f_area where zoom_lvl = 5 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
    	and sw_longitude >= (select sw_longitude from f_area where zoom_lvl = 5 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
    	and ne_latitude<=  (select ne_latitude from f_area where zoom_lvl = 5 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m )
    	and ne_longitude<= (select ne_longitude from f_area where zoom_lvl = 5 and sw_longitude = longitude and sw_latitude = latitude and ne_latitude = latitude+lat100m and ne_longitude = longitude+lon100m );      
       longitude := longitude + lon100m;
    end loop; 
     longitude :=126.734086;
     latitude:=latitude+lat100m;
end loop; 
end;



------------------�׽�Ʈ�� ������----------------
SELECT * FROM (SELECT * FROM f_area where zoom_lvl=1 ORDER BY id DESC) WHERE ROWNUM = 1;
SELECT * FROM (SELECT * FROM f_area where zoom_lvl=3) WHERE ROWNUM = 1;
select * from f_area where id = 112829;
--135449
select * from f_area where id = 1;

select * from f_area where parent_area_id = 168837;

delete from f_area where zoom_lvl = 3;
SELECT * FROM (SELECT * FROM f_area where zoom_lvl=1) WHERE ROWNUM = 1;






select sw_longitude from f_area where sw_longitude=(select max(sw_longitude)as max from f_area);
select ne_longitude from f_area where ne_longitude=(select max(ne_longitude)as max from f_area);
select sw_latitude from f_area where sw_latitude=(select max(sw_latitude)as max from f_area);
select ne_latitude from f_area where ne_latitude=(select max(ne_latitude)as max from f_area);

sw_long : 127.267786
ne_long : 127.268686
sw_la : 37.713594
ne_la : 37.720194

