package springbook.user.dao;

import java.sql.SQLException;
import java.sql.PreparedStatement;

public class StatementStrategy {


	public PreparedStatement makePreparedStatement(String s) throws SQLException {

		return null;
	}

	private void jdbcContextWithStatementStrategy(StatementStrategy s) {

	}



	public void add(final String str) throws SQLException {

		String s = "test";

		jdbcContextWithStatementStrategy(
new StatementStrategy() {

						@Override
						public PreparedStatement makePreparedStatement(String s) throws SQLException {
							return null;
						}
		});
	}

}
