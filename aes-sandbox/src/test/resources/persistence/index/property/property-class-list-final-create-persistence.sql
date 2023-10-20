drop table if exists PropertyClassListFinalCreatePersistenceIndex;
create table PropertyClassListFinalCreatePersistenceIndex(
	id varchar(256) primary key,
	sourceId varchar(256) not null,
	sourceType varchar(256) not null,
	valueClassList varchar(512) array
);