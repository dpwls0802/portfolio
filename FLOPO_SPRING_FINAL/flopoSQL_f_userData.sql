drop table f_userinfo;
create table F_USERINFO(
    bno			number,
	name		varchar2(200),
    email       varchar2(200),
	ageRange	varchar2(200),
	gender		varchar2(200),
    CONSTRAINT pk_userinfo PRIMARY KEY (email)
);  
create sequence seq_userinfo;

drop table f_traceinfo;
create table F_TRACEINFO(
    bno number,
    recodedDate varchar2(200),
    email		varchar2(200) references f_userinfo(email),
    areainfo number references f_area(id),
    CONSTRAINT pk_traceinfo PRIMARY KEY (recodedDate, email)
);
create sequence seq_traceinfo;


drop sequence seq_userinfo;
drop sequence seq_traceinfo;