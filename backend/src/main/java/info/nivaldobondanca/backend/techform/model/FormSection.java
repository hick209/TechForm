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
public class FormSection {

	private long   mId;
	private long   mCode;
	private String mCodeName;
	private String mName;

	private List<FormQuestion> mQuestions;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public long getCode() {
		return mCode;
	}

	public void setCode(long code) {
		mCode = code;
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

	public List<FormQuestion> getQuestions() {
		return mQuestions;
	}

	public void setQuestions(List<FormQuestion> questions) {
		mQuestions = questions;
	}

	public static FormSection fromEntity(DatastoreService dataStore, Entity entity)
			throws EntityNotFoundException {
		FormSection section = new FormSection();

		long id = entity.getKey().getId();
		section.setId(id);

		long code = (long) entity.getProperty("code");
		section.setCode(code);

		String codeName = (String) entity.getProperty("codeName");
		section.setCodeName(codeName);

		String name = (String) entity.getProperty("name");
		section.setName(name);

		//noinspection unchecked
		List<Key> keys = (List<Key>) entity.getProperty("questions");
		keys = firstNonNull(keys, Collections.<Key>emptyList());

		List<FormQuestion> questions = new ArrayList<>();
		for (Key k : keys) {
			FormQuestion q = FormQuestion.fromEntity(dataStore, dataStore.get(k));
			questions.add(q);
		}

		section.setQuestions(questions);

		return section;
	}
}
