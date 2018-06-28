# Cardea
Cardea is designed to be a high reliable and extremely fast TCP reverse proxy able to handle
multiple backends with a load balancing strategy. We currently support two types of balancing 
policies: 
- Round Robin [ROUND_ROBIN]
- Random Selection [RANDOM]

# Build Status & Code Coverage
dev:

[![Build Status](https://travis-ci.org/D3adspaceEnterprises/cardea.svg?branch=dev)](https://travis-ci.org/D3adspaceEnterprises/cardea)
[![codecov](https://codecov.io/gh/D3adspaceEnterprises/cardea/branch/dev/graph/badge.svg)](https://codecov.io/gh/D3adspaceEnterprises/cardea)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/D3adspaceEnterprises/cardea/edit/dev/README.md)
# Installation / Usage

You can use Cardea as a standalone application or embedded in your application. Start 
cardea as a standalone application using command line [COMING SOON]: 
```bash
java -jar cardea-server-1.0-SNAPSHOT.jar -p 8081 -bp ROUND_ROBIN -b host:port,host:port,host:port... 
```

Embedded: 
```java
import de.d3adspace.cardea.CardeaServer;
import de.d3adspace.cardea.CardeaServerFactory;
import de.d3adspace.cardea.backend.Backend;
import de.d3adspace.cardea.backend.BackendBalancingType;
import de.d3adspace.cardea.config.CardeaConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class CardeaServerTest {
	
	public static void main(String[] args) {
		List<Backend> backends = new ArrayList<>();
		backends.add(new Backend("Backend #1", "{host}", 1234));
		backends.add(new Backend("Backend #2", "{host}", 1235));
		backends.add(new Backend("Backend #3", "{host}", 1236));
		
		CardeaConfig cardeaConfig = new CardeaConfig(8081, backends, BackendBalancingType.ROUND_ROBIN);
		
		CardeaServer cardeaServer = CardeaServerFactory.createCardeaServer(cardeaConfig);
		cardeaServer.start();
	}
}
```
