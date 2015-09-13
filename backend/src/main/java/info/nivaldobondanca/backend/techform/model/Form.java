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
public class Form {

	private long   mId;
	private String mCodeName;
	private String mName;

	private List<FormSection> mSections;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public String getCodeName() {
		return mCodeName;
	}

	public void setCodeName(String codeName) {
		mCodeName = codeName;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public List<FormSection> getSections() {
		return mSections;
	}

	public void setSections(List<FormSection> sections) {
		mSections = sections;
	}

	public static Form fromEntity(DatastoreService dataStore, Entity entity)
			throws EntityNotFoundException {
		Form form = new Form();

		long id = entity.getKey().getId();
		form.setId(id);

		String codeName = (String) entity.getProperty("codeName");
		form.setCodeName(codeName);

		String name = (String) entity.getProperty("name");
		form.setName(name);

		//noinspection unchecked
		List<Key> keys = (List<Key>) entity.getProperty("sections");
		keys = firstNonNull(keys, Collections.<Key>emptyList());

		List<FormSection> sections = new ArrayList<>();
		for (Key k : keys) {
			FormSection f = FormSection.fromEntity(dataStore, dataStore.get(k));
			sections.add(f);
		}

		form.setSections(sections);

		return form;
	}
}
