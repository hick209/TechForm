/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package info.nivaldobondanca.backend.techform.endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import info.nivaldobondanca.backend.techform.NewData;
import info.nivaldobondanca.backend.techform.model.Form;
import info.nivaldobondanca.backend.techform.model.Group;

import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

/**
 * An endpoint class we are exposing
 */
@Api(
		name = "techFormAPI",
		version = "v0.1",
		namespace = @ApiNamespace(
				ownerDomain = "techform.backend.nivaldobondanca.info",
				ownerName = "techform.backend.nivaldobondanca.info",
				packagePath = ""
		)
)
public class TechFormEndpoint {

	private static final Logger logger = Logger.getLogger(TechFormEndpoint.class.getName());

	private DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

	@ApiMethod(name = "group.list")
	public List<Group> getGroups() throws EntityNotFoundException, IOException {
		Key rootKey = KeyFactory.createKey("Root", 1);
		// Get or create the root
		Entity root;
		try {
			root = dataStore.get(rootKey);
		} catch (EntityNotFoundException e) {
//			root = new Startup(dataStore).run(false);
			return Collections.emptyList();
		}

		//noinspection unchecked
		List<Key> keys = (List<Key>) root.getProperty("groups");
		keys = firstNonNull(keys, Collections.<Key>emptyList());

		List<Group> groups = new ArrayList<>();
		for (Key key : keys) {
			Group g = Group.fromEntity(dataStore, dataStore.get(key));
			groups.add(g);
		}

		return groups;
	}

	@ApiMethod(name = "form.list")
	public List<Form> getForms(
			@Named("groupId") Long groupId)
			throws EntityNotFoundException {

		// Get the key
		Key rootKey = KeyFactory.createKey("Root", 1);
		Key groupKey = KeyFactory.createKey(rootKey, "Group", groupId);

		// Get or create the group
		Entity group;
		group = dataStore.get(groupKey);

		//noinspection unchecked
		List<Key> keys = (List<Key>) group.getProperty("forms");
		keys = firstNonNull(keys, Collections.<Key>emptyList());

		List<Form> forms = new ArrayList<>();
		for (Key key : keys) {
			Form f = Form.fromEntity(dataStore, dataStore.get(key));
			forms.add(f);
		}

		return forms;
	}

	@ApiMethod(name = "group.save", httpMethod = ApiMethod.HttpMethod.POST)
	public void setSaveGroup(
			@Named("groupId") Long groupId,
			Group data)
			throws IOException {

		// Get the key
		Key rootKey = KeyFactory.createKey("Root", 1);
		Key groupKey = KeyFactory.createKey(rootKey, "Group", groupId);

		// Get or create the group
		Entity group;
		try {
			group = dataStore.get(groupKey);

			//noinspection unchecked
			List<Key> keys = (List<Key>) group.getProperty("forms");
			keys = firstNonNull(keys, Collections.<Key>emptyList());
			// Erase all the previous data
			dataStore.delete(keys);
		}
		catch (EntityNotFoundException e) {
			logger.fine("Creating new group");
		}

		// Saves the new data
		JsonNode json = NewData.mapper().convertValue(data, JsonNode.class);
		NewData newData = new NewData(dataStore, json);
		newData.run(true);
	}

	@ApiMethod(name = "form.get")
	public Form getForm(
			@Named("groupId") Long groupId,
			@Named("formId") Long formId)
			throws EntityNotFoundException {

		// Get the key
		Key rootKey = KeyFactory.createKey("Root", 1);
		Key groupKey = KeyFactory.createKey(rootKey, "Group", groupId);
		Key key = KeyFactory.createKey(groupKey, "Form", formId);

		Entity entity = dataStore.get(key);

		return Form.fromEntity(dataStore, entity);
	}
}
