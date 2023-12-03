drop table if exists PropertyByteArrayListSearchPersistenceIndex;
create table PropertyByteArrayListSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArrayList blob array
);