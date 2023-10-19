drop table if exists PropertyEntityReferenceListDeletePersistenceIndex;
create table PropertyEntityReferenceListDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueEntityReferenceListId varchar(256) array,
	valueEntityReferenceListType varchar(256) array
);