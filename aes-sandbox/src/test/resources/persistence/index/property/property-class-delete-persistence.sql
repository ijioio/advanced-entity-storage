drop table if exists PropertyClassDeletePersistenceIndex;
create table PropertyClassDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueClass varchar(512)
);