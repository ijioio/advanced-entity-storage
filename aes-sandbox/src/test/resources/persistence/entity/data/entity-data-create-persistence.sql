drop table if exists EntityData;
create table EntityData(
	id varchar(256) primary key,
	entityType varchar(512) not null,
	data blob not null
);