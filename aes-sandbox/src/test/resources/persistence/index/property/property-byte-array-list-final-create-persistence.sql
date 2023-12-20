drop table if exists PropertyByteArrayListFinalCreatePersistenceIndex;
create table PropertyByteArrayListFinalCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArrayList blob array
);