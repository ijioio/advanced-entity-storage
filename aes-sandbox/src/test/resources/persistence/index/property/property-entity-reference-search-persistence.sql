drop table if exists PropertyEntityReferenceSearchPersistenceIndex;
create table PropertyEntityReferenceSearchPersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueEntityReferenceId varchar(256),
	valueEntityReferenceType varchar(256)
);