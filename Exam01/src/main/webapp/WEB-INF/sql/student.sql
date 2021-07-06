create table tbl_student(
    sno varchar2(8) primary key,
    sname varchar2(10) not null,
    syear number(1) not null,
    gender varchar2(2) not null,
    major varchar2(10) not null,
    score number(3) default 0 not null
);

select * from tbl_student;

