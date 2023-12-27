package com.ijioio.aes.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ijioio.aes.persistence.PersistenceContext;

public class JdbcPersistenceContext implements PersistenceContext {

	private PreparedStatement statement;

	private ResultSet resultSet;

	private int index = 1;

	public PreparedStatement getStatement() {
		return statement;
	}

	public void setStatement(PreparedStatement statement) {
		this.statement = statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public void resetIndex() {
		index = 1;
	}

	public int getNextIndex() {
		return index++;
	}
}
