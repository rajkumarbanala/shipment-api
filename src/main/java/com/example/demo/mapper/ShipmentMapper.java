package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.example.demo.core.Shipment;
import com.example.demo.enums.EnumsUtil.ShipmentStatus;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

public class ShipmentMapper implements ResultSetMapper<Shipment> {

	public Shipment map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Shipment(
				Long.parseLong(resultSet.getString("id"))
				,resultSet.getString("item_name")
				,resultSet.getString("source_location")
				, resultSet.getString("target_location")
				,ShipmentStatus.valueOf(resultSet.getString("shipment_status"))
				,(resultSet.getString("user_id") != null ? Long.parseLong(resultSet.getString("user_id")) : null)
				);
	}

}
