drop table if exists PropertyClassSearchPersistenceIndex;
create table PropertyClassSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueClass varchar(512)
);