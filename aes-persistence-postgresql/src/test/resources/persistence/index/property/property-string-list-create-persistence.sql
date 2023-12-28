drop table if exists PropertyStringListCreatePersistenceIndex;
create table PropertyStringListCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueStringList varchar(512) array
);