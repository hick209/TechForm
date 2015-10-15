package info.nivaldobondanca.backend.techform.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

/**
 * @author Nivaldo Bondança
 */
public class Group {

	private long   mId;
	private String mName;

	private List<Form> mForms;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public List<Form> getForms() {
		return mForms;
	}

	public void setForms(List<Form> forms) {
		mForms = forms;
	}

	public static Group fromEntity(DatastoreService dataStore, Entity entity)
			throws EntityNotFoundException {
		Group group = new Group();

		long id = entity.getKey().getId();
		group.setId(id);

		String name = (String) entity.getProperty("name");
		group.setName(name);

		//noinspection unchecked
		List<Key> keys = (List<Key>) entity.getProperty("forms");
		keys = firstNonNull(keys, Collections.<Key>emptyList());

		List<Form> forms = new ArrayList<>();
		for (Key k : keys) {
			Form f = Form.fromEntity(dataStore, dataStore.get(k));
			forms.add(f);
		}

		group.setForms(forms);

		return group;
	}
}
