drop table if exists PropertyByteArrayListCreatePersistenceIndex;
create table PropertyByteArrayListCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArrayList blob array
);