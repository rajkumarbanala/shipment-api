package com.example.demo.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.example.demo.core.Shipment;
import com.example.demo.core.User;
import com.example.demo.mapper.UserMapper;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

public interface UserDao {

	@SqlQuery("select * from user")
	@Mapper(UserMapper.class)
	public List<User> list();

	@SqlQuery("select * from user where id = :id")
	@Mapper(UserMapper.class)
	Shipment get(@Bind("id") Long id);

	@SqlUpdate("insert into user (username, password, first_name, last_name, email, user_type, user_status) values (:username, :password, :firstName, :lastName, :email, :userType, :userStatus)")
	void create(@Bind("username") String username, @Bind("password") String password, @Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("email") String email, @Bind("userType") String userType, @Bind("userStatus") String userStatus);
	
	@SqlQuery("select * from user where user_type = :userType and user_status = :userStatus")
	@Mapper(UserMapper.class)
	public List<User> fetchDeliveryPartners(@Bind("userType") String userType, @Bind("userStatus") String userStatus);


}
