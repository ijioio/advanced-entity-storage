drop table if exists PropertyCharCreatePersistenceIndex;
create table PropertyCharCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueChar char
);