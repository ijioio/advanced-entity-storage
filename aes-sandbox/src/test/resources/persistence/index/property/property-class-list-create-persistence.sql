drop table if exists PropertyClassListCreatePersistenceIndex;
create table PropertyClassListCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceSearchId varchar(256) not null,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueClassList varchar(512) array
);