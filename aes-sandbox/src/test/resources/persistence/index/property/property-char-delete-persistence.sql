drop table if exists PropertyCharDeletePersistenceIndex;
create table PropertyCharDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueChar char
);