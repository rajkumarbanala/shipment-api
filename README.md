# ShipmentApi

### Pre-requisites

MYSQL database: Update the config.yml if your configuration differs. In this example, we use

	database: test_shipment
	user: root
	pass: root

### Build:

	mvn clean package
	

### Database creation:

	java -jar target/shipment-api-1.0-SNAPSHOT.jar db migrate config.yml
	
	
### Run:

	java -jar target/shipment-api-1.0-SNAPSHOT.jar server config.yml
	
	
### Open browser pointing at

	http://localhost:9002
