drop table if exists PropertyEntityReferenceListSearchPersistenceIndex;
create table PropertyEntityReferenceListSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceSearchId varchar(256) not null,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueEntityReferenceListSearchId varchar(256) array,
	valueEntityReferenceListId varchar(256) array,
	valueEntityReferenceListType varchar(256) array
);