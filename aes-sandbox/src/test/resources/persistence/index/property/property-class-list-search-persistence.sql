drop table if exists PropertyClassListSearchPersistenceIndex;
create table PropertyClassListSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueClassList varchar(512) array
);