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
class UserResourceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private ShipmentDao shipmentDao;

    @InjectMocks
    private UserResource userResource;

    private User user;
    private Shipment shipment;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setEmail("user1@example.com");
        user.setUserType(UserType.DELIVERY_PARTNER);
        user.setUserStatus(UserStatus.ACTIVE);

        shipment = new Shipment();
        shipment.setItemName("Item1");
        shipment.setSourceLocation("Location1");
        shipment.setTargetLocation("Location2");
        shipment.setShipmentStatus(ShipmentStatus.CREATED);
    }

    @Test
    void testList() {
        List<User> users = Arrays.asList(user);
        when(userDao.list()).thenReturn(users);

        List<User> result = userResource.list();
        assertEquals(users, result);
    }

    @Test
    void testCreate() {
        Mockito.doNothing().when(userDao).create(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        );

        User result = userResource.create(user);
        assertEquals(user, result);
        verify(userDao).create(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserType().toString(),
                user.getUserStatus().toString()
        );
    }

    @Test
    void testFetchMyShipments() {
        Long userId = 1L;
        List<Shipment> shipments = Arrays.asList(shipment);
        when(shipmentDao.fetchMyShipments(userId)).thenReturn(shipments);

        List<Shipment> result = userResource.fetchMyShipments(userId);
        assertEquals(shipments, result);
    }

    @Test
    void testUpdateShipmentStatus() {
        Long shipmentId = 1L;
        ShipmentStatus shipmentStatus = ShipmentStatus.DELIVERED;

        shipment.setId(shipmentId);
        when(shipmentDao.get(shipmentId)).thenReturn(shipment);
        Mockito.doNothing().when(shipmentDao).updateShipmentStatus(Mockito.anyLong(), Mockito.anyString());
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("shipmentId", shipmentId);
        payload.put("shipmentStatus", shipmentStatus.toString());

        Shipment result = userResource.updateShipmentStatus(payload);
        assertEquals(shipment, result);
        verify(shipmentDao).updateShipmentStatus(shipmentId, shipmentStatus.toString());
        verify(shipmentDao, times(2)).get(shipmentId); // once before update, once after
    }
}
