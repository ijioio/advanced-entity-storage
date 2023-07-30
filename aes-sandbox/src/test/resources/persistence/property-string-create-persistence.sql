drop table if exists PropertyStringCreatePersistenceIndex;
create table PropertyStringCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueString varchar(512)
);