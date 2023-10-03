drop table if exists PropertyEntityReferenceDeletePersistenceIndex;
create table PropertyEntityReferenceDeletePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueEntityReferenceId varchar(256),
	valueEntityReferenceType varchar(256)
);