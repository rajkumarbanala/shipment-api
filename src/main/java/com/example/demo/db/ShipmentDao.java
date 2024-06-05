package com.example.demo.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.example.demo.core.Shipment;
import com.example.demo.mapper.ShipmentMapper;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

public interface ShipmentDao {

	@SqlQuery("select * from shipment")
	@Mapper(ShipmentMapper.class)
	public List<Shipment> list();

	@SqlQuery("select * from shipment where id = :id")
	@Mapper(ShipmentMapper.class)
	Shipment get(@Bind("id") Long id);

	@SqlUpdate("insert into shipment (item_name, source_location, target_location, shipment_status) values (:itemName, :sourceLocation, :targetLocation, :shipmentStatus)")
	void create(@Bind("itemName") String itemName, @Bind("sourceLocation") String sourceLocation, @Bind("targetLocation") String targetLocation, @Bind("shipmentStatus") String shipmentStatus);

	@SqlUpdate("update shipment set shipment_status = :shipmentStatus, user_id = :userId where id = :id")
	void assignToDeliveryPartner(@Bind("id") Long id, @Bind("shipmentStatus") String shipmentStatus, @Bind("userId") Long userId);

	@SqlQuery("select * from shipment where user_id=:userid")
	@Mapper(ShipmentMapper.class)
	public List<Shipment> fetchMyShipments(@Bind("userid") Long userid);

	@SqlUpdate("update shipment set shipment_status = :shipmentStatus where id = :id")
	void updateShipmentStatus(@Bind("id") Long id, @Bind("shipmentStatus") String shipmentStatus);

}
