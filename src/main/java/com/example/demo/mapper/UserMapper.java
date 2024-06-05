package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.example.demo.core.User;
import com.example.demo.enums.EnumsUtil.UserStatus;
import com.example.demo.enums.EnumsUtil.UserType;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

public class UserMapper implements ResultSetMapper<User> {

	public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new User(
				Long.parseLong(resultSet.getString("id"))
				,resultSet.getString("username")
				,""
				,resultSet.getString("first_name")
				, resultSet.getString("last_name")
				, resultSet.getString("email")
				,UserType.valueOf(resultSet.getString("user_type"))
				,UserStatus.valueOf(resultSet.getString("user_status"))
				);
	}

}
