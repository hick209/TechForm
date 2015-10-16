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
public class FormQuestion {

	private long   mId;
	private String mCodeName;
	private String mText;
	private String mHint;
	private String mObservation;

	private List<FormQuestionOption> mOptions;

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

	public String getText() {
		return mText;
	}

	public void setText(String text) {
		mText = text;
	}

	public String getHint() {
		return mHint;
	}

	public void setHint(String hint) {
		mHint = hint;
	}

	public String getObservation() {
		return mObservation;
	}

	public void setObservation(String observation) {
		mObservation = observation;
	}

	public List<FormQuestionOption> getOptions() {
		return mOptions;
	}

	public void setOptions(List<FormQuestionOption> options) {
		this.mOptions = options;
	}

	public static FormQuestion fromEntity(DatastoreService dataStore, Entity entity) throws EntityNotFoundException {
		FormQuestion question = new FormQuestion();

		long id = entity.getKey().getId();
		question.setId(id);

		String codeName = (String) entity.getProperty("codeName");
		question.setCodeName(codeName);

		String text = (String) entity.getProperty("text");
		question.setText(text);

		String hint = (String) entity.getProperty("hint");
		question.setHint(hint);

		String observation = (String) entity.getProperty("observation");
		question.setObservation(observation);

		//noinspection unchecked
		List<Key> keys = (List<Key>) entity.getProperty("options");
		keys = firstNonNull(keys, Collections.<Key>emptyList());

		List<FormQuestionOption> options = new ArrayList<>();
		for (Key k : keys) {
			FormQuestionOption o = FormQuestionOption.fromEntity(dataStore, dataStore.get(k));
			options.add(o);
		}

		question.setOptions(options);

		return question;
	}
}
