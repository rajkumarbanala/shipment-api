package com.example.demo.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.core.Shipment;
import com.example.demo.core.User;
import com.example.demo.db.ShipmentDao;
import com.example.demo.db.UserDao;
import com.example.demo.enums.EnumsUtil.ShipmentStatus;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

@Path("/user")
public class UserResource {

	private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

	private UserDao userDao;
	
	private ShipmentDao shipmentDao;

	public UserResource(UserDao userDao, ShipmentDao shipmentDao) {
		super();
		this.userDao = userDao;
		this.shipmentDao = shipmentDao;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> list() {
		LOG.debug("list()");
		
		return userDao.list();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public User create(User user) {
		LOG.debug("create()");
		
		userDao.create(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserType().toString(), user.getUserStatus().toString());
		return user;
	}

	
	@Path("fetch-my-shipments/{userId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Shipment> fetchMyShipments(@PathParam("userId") Long userId) {
		LOG.debug("fetchMyShipments()");
		
		return shipmentDao.fetchMyShipments(userId);
	}
	
	@Path("update-shipment-status")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Shipment updateShipmentStatus(Map<String, Object> payload) {
		LOG.debug("updateShipmentStatus()");

		Long shipmentId = Long.parseLong(payload.get("shipmentId").toString());
		ShipmentStatus shipmentStatus = ShipmentStatus.valueOf(payload.get("shipmentStatus").toString());

		Shipment shipment = shipmentDao.get(shipmentId);

		if (shipment == null) {
			return null;
		}

		shipmentDao.updateShipmentStatus(shipmentId, shipmentStatus.toString());

		return shipmentDao.get(shipmentId);
	}
}
