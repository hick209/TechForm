package info.nivaldobondanca.backend.techform;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.org.codehaus.jackson.JsonNode;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import com.google.appengine.repackaged.org.codehaus.jackson.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

import info.nivaldobondanca.backend.techform.model.FormQuestionOption;

/**
 * @author Nivaldo Bondança
 */
public class NewData {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final DatastoreService mDataStore;
	private final JsonNode         mJsonNode;

	public NewData(DatastoreService dataStore, JsonNode json) {
		this.mDataStore = dataStore;
		this.mJsonNode = json;
	}

	public Entity run(boolean singleGroup) {
		Entity root = new Entity("Root", 1);

		List<Key> keys;
		if (singleGroup) {
			keys = new ArrayList<>(1);
			Key k = processGroup(root.getKey(), mJsonNode);
			keys.add(k);
		}
		else {
			ArrayNode nodes = (ArrayNode) mJsonNode.get("groups");
			keys = new ArrayList<>(nodes.size());

			for (JsonNode node : nodes) {
				Key k = processGroup(root.getKey(), node);
				keys.add(k);
			}
		}

		root.setProperty("groups", keys);

		mDataStore.put(root);

		return root;
	}

	protected Key processGroup(Key parent, JsonNode json) {
		long id = json.get("id").asLong();
		Entity entity = new Entity("Group", id, parent);

		String name = json.get("name").asText();
		entity.setProperty("name", name);

		Key key = mDataStore.put(entity);

		ArrayNode nodes = (ArrayNode) json.get("forms");
		List<Key> keys = new ArrayList<>(nodes.size());

		for (JsonNode node : nodes) {
			Key k = processForm(key, node);
			keys.add(k);
		}

		entity.setProperty("forms", keys);

		mDataStore.put(entity);

		return key;
	}

	private Key processForm(Key parent, JsonNode json) {
		Entity entity = new Entity("Form", parent);

		String name = json.get("name").asText();
		entity.setProperty("name", name);

		String codeName = json.get("codeName").asText();
		entity.setProperty("codeName", codeName);

		Key key = mDataStore.put(entity);

		ArrayNode nodes = (ArrayNode) json.get("sections");
		List<Key> keys = new ArrayList<>(nodes.size());

		for (JsonNode node : nodes) {
			Key k = processSection(key, node);
			keys.add(k);
		}

		entity.setProperty("sections", keys);

		mDataStore.put(entity);

		return key;
	}

	private Key processSection(Key parent, JsonNode json) {
		Entity entity = new Entity("Section", parent);

		String name = json.get("name").asText();
		entity.setProperty("name", name);

		String codeName = json.get("codeName").asText();
		entity.setProperty("codeName", codeName);

		Key key = mDataStore.put(entity);

		ArrayNode nodes = (ArrayNode) json.get("questions");
		List<Key> keys = new ArrayList<>(nodes.size());

		for (JsonNode node : nodes) {
			Key k = processQuestion(key, node);
			keys.add(k);
		}

		entity.setProperty("questions", keys);

		mDataStore.put(entity);

		return key;
	}

	private Key processQuestion(Key parent, JsonNode json) {
		Entity entity = new Entity("Question", parent);

		String text = json.get("text").asText();
		entity.setProperty("text", text);

		String codeName = json.get("codeName").asText();
		entity.setProperty("codeName", codeName);

		if (json.has("hint")) {
			String hint = json.get("hint").asText();
			entity.setProperty("hint", hint);
		}

		if (json.has("observation")) {
			String observation = json.get("observation").asText();
			entity.setProperty("observation", observation);
		}

		Key key = mDataStore.put(entity);

		ArrayNode nodes = (ArrayNode) json.get("options");
		List<Key> keys = new ArrayList<>(nodes.size());

		for (JsonNode node : nodes) {
			Key k = processQuestionOption(key, node);
			keys.add(k);
		}

		entity.setProperty("options", keys);

		mDataStore.put(entity);

		return key;
	}

	private Key processQuestionOption(Key parent, JsonNode json) {
		Entity entity = new Entity("QuestionOption", parent);

		if (json.has("title")) {
			String title = json.get("title").asText();
			entity.setProperty("title", title);
		}

		String text = json.get("selection").asText();
		entity.setProperty("selection", text);

		// This will throw an error in case the value is not correct
		FormQuestionOption.Selection.valueOf(text.toLowerCase());

		Key key = mDataStore.put(entity);

		ArrayNode nodes = (ArrayNode) json.get("items");
		List<Key> keys = new ArrayList<>(nodes.size());

		for (JsonNode node : nodes) {
			Key k = processQuestionOptionItem(key, node.asText());
			keys.add(k);
		}

		entity.setProperty("options", keys);

		mDataStore.put(entity);

		return key;
	}

	private Key processQuestionOptionItem(Key parent, String value) {
		Entity entity = new Entity("QuestionOptionItem", parent);
		entity.setProperty("value", value);

		return mDataStore.put(entity);
	}

	public static ObjectMapper mapper() {
		return MAPPER;
	}
}
