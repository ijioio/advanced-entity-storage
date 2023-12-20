drop table if exists PropertyByteArrayListFinalSearchPersistenceIndex;
create table PropertyByteArrayListFinalSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueByteArrayList blob array
);