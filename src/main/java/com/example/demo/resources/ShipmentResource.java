package com.example.demo.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.core.Shipment;
import com.example.demo.core.User;
import com.example.demo.db.ShipmentDao;
import com.example.demo.db.UserDao;
import com.example.demo.enums.EnumsUtil.ShipmentStatus;
import com.example.demo.enums.EnumsUtil.UserStatus;
import com.example.demo.enums.EnumsUtil.UserType;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

@Path("/shipment")
public class ShipmentResource {

	private static final Logger LOG = LoggerFactory.getLogger(ShipmentResource.class);

	private ShipmentDao shipmentDao;

	private UserDao userDao;

	public ShipmentResource(ShipmentDao shipmentDao, UserDao userDao) {
		super();
		this.shipmentDao = shipmentDao;
		this.userDao = userDao;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Shipment> list() {
		LOG.debug("list()");
		
		return shipmentDao.list();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Shipment create(Shipment shipment) {
		LOG.debug("create()");

		shipment.setShipmentStatus(ShipmentStatus.CREATED);

		shipmentDao.create(shipment.getItemName(), shipment.getSourceLocation(), shipment.getTargetLocation(),
				shipment.getShipmentStatus().toString());
		
		return shipment;
	}

	@Path("fetch-delivery-partners")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> fetchDeliveryPartners() {
		LOG.debug("fetchDeliveryPartners()");

		return userDao.fetchDeliveryPartners(UserType.DELIVERY_PARTNER.toString(), UserStatus.ACTIVE.toString());
	}

	@Path("assign-to-delivery-partner")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Shipment assignToDeliveryPartner(Map<String, Object> payload) {
		LOG.debug("assignToDeliveryPartner()");

		Long shipmentId = Long.parseLong(payload.get("shipmentId").toString());
		Long userId = Long.parseLong(payload.get("userId").toString());

		Shipment shipment = shipmentDao.get(shipmentId);

		if (shipment == null) {
			return null;
		}

		shipmentDao.assignToDeliveryPartner(shipmentId, ShipmentStatus.ASSIGNED_TO_USER.toString(), userId);
		shipment = shipmentDao.get(shipmentId);

		return shipment;
	}
}
