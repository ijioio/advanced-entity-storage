drop table if exists PropertyClassCreatePersistenceIndex;
create table PropertyClassCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceSearchId varchar(256) not null,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueClass varchar(512)
);