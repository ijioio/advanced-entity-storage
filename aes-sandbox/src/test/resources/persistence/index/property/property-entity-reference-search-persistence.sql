drop table if exists PropertyEntityReferenceSearchPersistenceIndex;
create table PropertyEntityReferenceSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceSearchId varchar(256) not null,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueEntityReferenceSearchId varchar(256),
	valueEntityReferenceId varchar(256),
	valueEntityReferenceType varchar(256)
);