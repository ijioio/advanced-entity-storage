drop table if exists PropertyStringListSearchPersistenceIndex;
create table PropertyStringListSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueStringList varchar(512) array,
	otherValueStringList varchar(512) array,
	otherValueStringSingle varchar(512)
);