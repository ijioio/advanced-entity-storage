drop table if exists PropertyByteArrayListDeletePersistenceIndex;
create table PropertyByteArrayListDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArrayList blob array
);