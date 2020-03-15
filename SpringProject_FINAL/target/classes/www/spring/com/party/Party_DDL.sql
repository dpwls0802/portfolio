drop table tbl_authority;
drop table tbl_party;

--	user_id, user_pwd, name, reg_date, update_date, enabled
create table tbl_party(
	user_id		varchar2(50) not null primary key,
	user_pwd	varchar2(100) not null,
	name		varchar2(500),
	reg_date		date	default sysdate,
	update_date	date	default sysdate,
	enabled		char(1) default '1'
);

select user_id username, user_pwd password, enabled
  from tbl_party
 where user_id = ?
 
--사용자 별 권한 정보
create table tbl_authority(
	party_id		varchar2(50) not null references tbl_party(user_id),
	auth		varchar2(50) not null
);
select user_id username, auth authority
  from tbl_authority
 where user_id = ?

 
 
 
 
 
 
 
 
 
 
 
 