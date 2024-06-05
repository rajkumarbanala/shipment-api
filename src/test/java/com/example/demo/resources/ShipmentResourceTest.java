package com.example.demo.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class ShipmentResourceTest {

    @Mock
    private ShipmentDao shipmentDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private ShipmentResource shipmentResource;

    private Shipment shipment;
    private User user;

    @BeforeEach
    void setUp() {
        shipment = new Shipment();
        shipment.setItemName("Item1");
        shipment.setSourceLocation("Location1");
        shipment.setTargetLocation("Location2");
        shipment.setShipmentStatus(ShipmentStatus.CREATED);

        user = new User();
        user.setUserType(UserType.DELIVERY_PARTNER);
        user.setUserStatus(UserStatus.ACTIVE);
    }

    @Test
    void testList() {
        List<Shipment> shipments = Arrays.asList(shipment);
        when(shipmentDao.list()).thenReturn(shipments);

        List<Shipment> result = shipmentResource.list();
        assertEquals(shipments, result);
    }

    @Test
    void testCreate() {
        Mockito.doNothing().when(shipmentDao).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        Shipment result = shipmentResource.create(shipment);
        assertEquals(ShipmentStatus.CREATED, result.getShipmentStatus());
        verify(shipmentDao).create(shipment.getItemName(), shipment.getSourceLocation(), shipment.getTargetLocation(), ShipmentStatus.CREATED.toString());
    }

    @Test
    void testFetchDeliveryPartners() {
        List<User> users = Arrays.asList(user);
        when(userDao.fetchDeliveryPartners(Mockito.anyString(), Mockito.anyString())).thenReturn(users);

        List<User> result = shipmentResource.fetchDeliveryPartners();
        assertEquals(users, result);
    }

    @Test
    void testAssignToDeliveryPartner() {
        Long shipmentId = 1L;
        Long userId = 2L;

        shipment.setId(shipmentId);
        when(shipmentDao.get(shipmentId)).thenReturn(shipment);
        Mockito.doNothing().when(shipmentDao).assignToDeliveryPartner(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong());
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("shipmentId", shipmentId);
        payload.put("userId", userId);

        Shipment result = shipmentResource.assignToDeliveryPartner(payload);
        assertEquals(shipment, result);
        verify(shipmentDao).assignToDeliveryPartner(shipmentId, ShipmentStatus.ASSIGNED_TO_USER.toString(), userId);
        verify(shipmentDao, times(2)).get(shipmentId);  // once before assign, once after
    }
}
