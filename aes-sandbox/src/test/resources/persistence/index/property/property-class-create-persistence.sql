drop table if exists PropertyClassCreatePersistenceIndex;
create table PropertyClassCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueClass varchar(512)
);