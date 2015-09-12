package info.nivaldobondanca.backend.techform;

import com.google.appengine.api.datastore.DatastoreService;

import java.io.IOException;

/**
 * @author Nivaldo Bondança
 */
public class Startup extends NewData {

	public Startup(DatastoreService dataStore) throws IOException {
		super(dataStore, NewData.mapper().readTree(
				Startup.class.getResourceAsStream("/startup.json")));
	}

}

