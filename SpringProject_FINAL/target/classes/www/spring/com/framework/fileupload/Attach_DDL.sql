drop table tbl_attach;

create table tbl_attach(
	master_name varchar2(100) not null,
	uuid		varchar2(100) not null,
	master_id	number(10,0),
	upload_path	varchar2(200) not null,
	file_name   varchar2(200) not null,
	file_type	char(1) default 'I',
	
	primary key(master_id, master_name, uuid)
);
