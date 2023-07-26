package com.ijioio.aes.core.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ijioio.aes.core.persistence.PersistenceContext;

public class JdbcPersistenceContext implements PersistenceContext {

	public static JdbcPersistenceContext of(Connection connection) {
		return new JdbcPersistenceContext(connection);
	}

	private final Connection connection;

	private PreparedStatement statement;

	private ResultSet resultSet;

	private int index = 1;

	private JdbcPersistenceContext(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

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

	public int getNextIndex() {
		return index++;
	}
}
