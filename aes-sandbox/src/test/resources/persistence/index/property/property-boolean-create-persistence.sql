drop table if exists PropertyBooleanCreatePersistenceIndex;
create table PropertyBooleanCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueBoolean boolean
);