 #!/bin/bash         

mvn clean package
java -jar target/shipment-api-1.0-SNAPSHOT.jar server config.yml
