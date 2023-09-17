drop table if exists PropertyStringListCreatePersistenceIndex;
create table PropertyStringListCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceSearchId varchar(256) not null,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueStringList varchar(512) array
);