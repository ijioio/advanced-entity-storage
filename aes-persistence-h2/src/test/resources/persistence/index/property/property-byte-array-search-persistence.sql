drop table if exists PropertyByteArraySearchPersistenceIndex;
create table PropertyByteArraySearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArray blob,
	otherValueByteArray blob
);