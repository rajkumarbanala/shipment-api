package com.example.demo;

import org.skife.jdbi.v2.DBI;

import com.example.demo.db.ShipmentDao;
import com.example.demo.db.UserDao;
import com.example.demo.health.DatabaseHealthCheck;
import com.example.demo.resources.ShipmentResource;
import com.example.demo.resources.UserResource;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

public class ShipmentApiApplication extends Application<ShipmentApiConfiguration> {

	public static void main(final String[] args) throws Exception {
		new ShipmentApiApplication().run(args);
	}

	@Override
	public String getName() {
		return "ShipmentApi";
	}

	@Override
	public void initialize(final Bootstrap<ShipmentApiConfiguration> bootstrap) {
	    bootstrap.addBundle(new MigrationsBundle<ShipmentApiConfiguration>() {
	        @Override
	        public PooledDataSourceFactory getDataSourceFactory(final ShipmentApiConfiguration configuration) {
	          return configuration.getDataSourceFactory();
	        }
	      });
	}

	@Override
	public void run(final ShipmentApiConfiguration config, final Environment environment) {
		
	    final DBIFactory factory = new DBIFactory();
	    final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "mysql");
	    
		// Health Checks
	    environment.healthChecks().register("health", new DatabaseHealthCheck(jdbi, config.getDataSourceFactory().getValidationQuery()));
		
		// Register Resources
	    ShipmentDao shipmentDao = jdbi.onDemand(ShipmentDao.class);
	    UserDao userDao = jdbi.onDemand(UserDao.class);
	    
		ShipmentResource shipmentResource = new ShipmentResource(shipmentDao, userDao);
		environment.jersey().register(shipmentResource);
		
		UserResource userResource = new UserResource(userDao, shipmentDao);
		environment.jersey().register(userResource);
		
		
	}

}
