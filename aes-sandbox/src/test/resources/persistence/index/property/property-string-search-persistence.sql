drop table if exists PropertyStringSearchPersistenceIndex;
create table PropertyStringSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueString varchar(512)
);