drop table if exists PropertyByteArrayListFinalDeletePersistenceIndex;
create table PropertyByteArrayListFinalDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArrayList blob array
);