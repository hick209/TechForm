package info.nivaldobondanca.techform;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;

/**
 * @author Nivaldo Bondança
 */
public class FormFillingManager implements Parcelable {

	private Form mForm;
	private int  mCurrentSection;

	public FormFillingManager(Form form) {
		mForm = form;
		mCurrentSection = 0;
	}

	public FormFillingManager(Parcel in) {
		mCurrentSection = in.readInt();
		mForm = JSONStringToForm(in.readString());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mCurrentSection);
		dest.writeString(formToJSONString());
	}

	private Form JSONStringToForm(String jsonString) {
		try {
			Form form = new Form();
			JSONObject json = new JSONObject(jsonString);

			form.setName(json.optString("name"));
			form.setCodeName(json.optString("codeName"));

			JSONArray sectionArray = json.getJSONArray("sections");
			List<FormSection> sections = parseFormSections(sectionArray);

			form.setSections(sections);

			return form;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private List<FormSection> parseFormSections(JSONArray sectionArray) throws JSONException {
		List<FormSection> sections = new ArrayList<>(sectionArray.length());
		for (int i = 0; i < sectionArray.length(); i++) {
			JSONObject json = sectionArray.getJSONObject(i);
			FormSection item = new FormSection();

			item.setName(json.optString("name"));
			item.setCodeName(json.optString("codeName"));

			JSONArray questionsArray = json.getJSONArray("questions");
			List<FormQuestion> questions = parseFormQuestions(questionsArray);

			item.setQuestions(questions);

			sections.add(item);
		}
		return sections;
	}

	private List<FormQuestion> parseFormQuestions(JSONArray questionsArray) throws JSONException {
		List<FormQuestion> questions = new ArrayList<>(questionsArray.length());
		for (int i = 0; i < questionsArray.length(); i++) {
			JSONObject json = questionsArray.getJSONObject(i);
			FormQuestion item = new FormQuestion();

			item.setCodeName(json.optString("codeName"));
			item.setType(json.optInt("type"));
			item.setText(json.optString("text"));
			item.setHint(json.optString("hint"));
			item.setObservation(json.optString("observation"));

			questions.add(item);
		}
		return questions;
	}

	private String formToJSONString() {
		return mForm.toString();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public FormFillingManager createFromParcel(Parcel in) {
			return new FormFillingManager(in);
		}

		public FormFillingManager[] newArray(int size) {
			return new FormFillingManager[size];
		}
	};

	public Form getForm() {
		return mForm;
	}

	public int getCurrentSection() {
		return mCurrentSection;
	}
}
