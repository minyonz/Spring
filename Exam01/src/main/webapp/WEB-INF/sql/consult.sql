create table tbl_consult(
    c_no number primary key,
    sno varchar2(8) references tbl_student(sno),
    c_content varchar2(500) not null,
    c_date timestamp default sysdate not null
);

create sequence seq_cno;

insert into tbl_consult(c_no, sno, c_content)
values(seq_cno.nextval, '1001', '상담1');
insert into tbl_consult(c_no, sno, c_content)
values(seq_cno.nextval, '1002', '상담3');
insert into tbl_consult(c_no, sno, c_content)
values(seq_cno.nextval, '1003', '상담1');

select * from tbl_consult;

commit;

