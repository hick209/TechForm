package finep.inovatec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import finep.inovatec.app.CacheAgent;
import finep.inovatec.data.Filling;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;

/**
 * @author Nivaldo Bondança
 */
public class FormFillingManager {

	private static FormFillingManager instance;

	public static void init(CacheAgent cacheAgent) {
		instance = new FormFillingManager(cacheAgent);
	}

	public static FormFillingManager getInstance() {
		return instance;
	}


	private Filling    mFilling;
	private CacheAgent mCacheAgent;
	private Group      mGroup;

	private FormFillingManager(CacheAgent cacheAgent) {
		mCacheAgent = cacheAgent;
	}

	public Group getGroup() {
		return mGroup;
	}

	public void setGroup(Group group) {
		mGroup = group;
	}

	public Filling getFilling() {
		return mFilling;
	}

	public void setFilling(Filling filling) {
		mFilling = filling;
	}

	public boolean save() {
		long groupId = mFilling.getGroupId();
		Set<Filling> fillings;
		try {
			// Load
			fillings = mCacheAgent.loadFillings(groupId);
			fillings.remove(mFilling);
		}
		catch (IOException e) {
			fillings = new HashSet<>();
		}

		fillings.add(mFilling);

		try {
			// Save
			mCacheAgent.saveFillings(groupId, fillings);

		}
		catch (IOException ignored) {
		}

		return false;
	}

//	private Form JSONStringToForm(String jsonString) {
//		try {
//			Form form = new Form();
//			JSONObject json = new JSONObject(jsonString);
//
//			form.setName(json.optString("name"));
//			form.setCodeName(json.optString("codeName"));
//
//			JSONArray sectionArray = json.getJSONArray("sections");
//			List<FormSection> sections = parseFormSections(sectionArray);
//
//			form.setSections(sections);
//
//			return form;
//		} catch (JSONException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private List<FormSection> parseFormSections(JSONArray sectionArray) throws JSONException {
//		List<FormSection> sections = new ArrayList<>(sectionArray.length());
//		for (int i = 0; i < sectionArray.length(); i++) {
//			JSONObject json = sectionArray.getJSONObject(i);
//			FormSection item = new FormSection();
//
//			item.setName(json.optString("name"));
//			item.setCodeName(json.optString("codeName"));
//
//			JSONArray questionsArray = json.getJSONArray("questions");
//			List<FormQuestion> questions = parseFormQuestions(questionsArray);
//
//			item.setQuestions(questions);
//
//			sections.add(item);
//		}
//		return sections;
//	}
//
//	private List<FormQuestion> parseFormQuestions(JSONArray questionsArray) throws JSONException {
//		List<FormQuestion> questions = new ArrayList<>(questionsArray.length());
//		for (int i = 0; i < questionsArray.length(); i++) {
//			JSONObject json = questionsArray.getJSONObject(i);
//			FormQuestion item = new FormQuestion();
//
//			item.setCodeName(json.optString("codeName"));
//			item.setType(json.optInt("type"));
//			item.setText(json.optString("text"));
//			item.setHint(json.optString("hint"));
//			item.setObservation(json.optString("observation"));
//
//			questions.add(item);
//		}
//		return questions;
//	}

//	private String formToJSONString() {
//		return mForm.toString();
//	}
//
//	public Form getForm() {
//		return mForm;
//	}
//
//	public int getCurrentSection() {
//		return mCurrentSection;
//	}
}
