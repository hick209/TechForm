package info.nivaldobondanca.backend.techform.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;

/**
 * @author Nivaldo Bondança
 */
public class FormQuestion {

	private long   mId;
	private long   mCode;
	private String mCodeName;
	private int    mType;
	private String mText;
	private String mHint;
	private String mObservation;

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

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		mType = type;
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

	public static FormQuestion fromEntity(DatastoreService dataStore, Entity entity) {
		FormQuestion question = new FormQuestion();

		long id = entity.getKey().getId();
		question.setId(id);

		long code = (long) entity.getProperty("code");
		question.setCode(code);

		String codeName = (String) entity.getProperty("codeName");
		question.setCodeName(codeName);

		long type = (long) entity.getProperty("type");
		question.setType((int)type);

		String text = (String) entity.getProperty("text");
		question.setText(text);

		String hint = (String) entity.getProperty("hint");
		question.setHint(hint);

		String observation = (String) entity.getProperty("observation");
		question.setObservation(observation);

		return question;
	}
}
