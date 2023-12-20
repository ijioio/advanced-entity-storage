drop table if exists EntityData;
create table EntityData(
	id varchar(256) primary key,
	entityType varchar(512) not null,
	data blob not null
);
drop table if exists SaveEntityStorageIndex;
create table SaveEntityStorageIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	number integer
);