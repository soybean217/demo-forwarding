package org.demo.dao;

import java.util.List;

import java.sql.Connection;

public interface BaseConnectionService {

	/**
	 * @return
	 */
	public Connection getConnection();

	/**
	 * @param sql
	 * @return
	 */
	public boolean execute(String sql);

	public boolean execute(List<String> sqlList);
}
