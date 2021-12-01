select * from tbl_board order by bno desc;
select * from tbl_board order by bno + 1 desc;

insert into tbl_board(bno, title, content, writer)
select seq_board.nextval, title, content, writer 
  from tbl_board;
  
select count(*) from tbl_board;


create table tbl_board2(
	bno			number(10,0),
	title		varchar2(200) not null,
	content		varchar2(2000) not null,
	writer		varchar2(50) not null,
	regdate		date	default sysdate,
	updatedate	date	default sysdate
);

alter table tbl_board2 add constraint pk_board primary key (bno); 

insert into tbl_board2
select * from tbl_board;

drop table tbl_board;

RENAME tbl_board2 TO tbl_board;



select /*+ index_desc(tbl_board pk_board) */
 *
  from tbl_board;
  
select *
  from tbl_board
  order by bno desc;
  
select /*+ FULL(tbl_board)  */
 rownum rn, bno, title
  from tbl_board
where bno > 0
order by bno desc;

select /*+ index_desc(tbl_board pk_board)  */
 rownum rn, bno, title, content, writer
  from tbl_board
where rownum <= 10;

select /*+ index_desc(tbl_board pk_board)  */
 rownum rn, bno, title, content, writer
  from tbl_board
where rownum > 10 and rownum <= 20;

select *
from (select /*+ index_desc(tbl_board pk_board)  */
 rownum rn, bno, title, content, writer
  from tbl_board
where rownum <= 20) inv
where inv.rn >10
;


select * 
  from ( 
       select rownum rn, * 
         from tbl_board 
        where rownum <= 5 * 15 )
 where rn > (5 - 1) * 15;