drop table if exists PropertyStringDeletePersistenceIndex;
create table PropertyStringDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceSearchId varchar(256) not null,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueString varchar(512)
);