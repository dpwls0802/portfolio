drop table tbl_attach;
drop table tbl_reply;
drop table tbl_board;

drop sequence seq_reply;
drop sequence seq_board;

create sequence seq_board;
create sequence seq_reply;

--bno, title, content, writer, regdate, updatedate
create table tbl_board(
	bno			number(10,0),
	title		varchar2(200) not null,
	content		varchar2(2000) not null,
	writer		varchar2(50) not null,
	regdate		date	default sysdate,
	updatedate	date	default sysdate
);
alter table tbl_board add constraint pk_board primary key (bno); 
alter table tbl_board add (reply_cnt number default 0);
UPDATE
       (
        SELECT b.reply_cnt,
               r.r_cnt
          FROM tbl_board b,
               (select bno, count(rno) r_cnt
                  from tbl_reply
                 group by bno) r
         WHERE b.bno = r.bno
       )
   SET reply_cnt = r_cnt;

UPDATE tbl_board b
   SET b.reply_cnt = (
       SELECT count(rno)
         FROM tbl_reply r
        WHERE b.bno = r.bno)
 WHERE EXISTS (
       SELECT 0 
         FROM tbl_reply r
        WHERE b.bno = r.bno);
        
MERGE INTO tbl_board b
USING tbl_reply r
ON (b.bno = r.bno)
WHEN MATCHED THEN
UPDATE SET b.reply_cnt = (SELECT count(rno)
         FROM tbl_reply r
        WHERE b.bno = r.bno)
;
--sample data
insert into tbl_board(bno, title, content, writer)
	values(seq_board.nextval, '제목 테스트1', '내용 테스트2', '홍길동');
insert into tbl_board(bno, title, content, writer)
	values(seq_board.nextval, '다음 제목을 적어주세요', '다음 내용을 적어 주세요', '홍도동');
	


--rno, bno, content, replyer, regdate, updatedate
create table tbl_reply(
    rno     INTEGER primary key,
    bno     number(10,0) references tbl_board(bno),
    content varchar2(1000),
    replyer varchar2(50),
	regdate		date	default sysdate,
	updatedate	date	default sysdate
);

--tbl_reply은 bno를 기준으로 그 하위 댓글 조회가 주된 기능이므로 bno로 인덱스를 만들어서 성능을 높여준다.
create index idx_reply_bno on tbl_reply(bno, rno asc);

--master_name, uuid, master_id, upload_path, file_name, file_type
create table tbl_attach(
	master_name varchar2(100) not null,
	uuid		varchar2(100) not null,
	master_id	number(10,0),
	upload_path	varchar2(200) not null,
	file_name   varchar2(200) not null,
	file_type	char(1) default 'I',
	primary key(master_id, master_name, uuid)
);








