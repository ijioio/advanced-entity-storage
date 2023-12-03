drop table if exists PropertyByteArrayDeletePersistenceIndex;
create table PropertyByteArrayDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArray blob
);