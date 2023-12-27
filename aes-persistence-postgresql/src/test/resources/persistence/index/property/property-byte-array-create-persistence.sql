drop table if exists PropertyByteArrayCreatePersistenceIndex;
create table PropertyByteArrayCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArray oid
);