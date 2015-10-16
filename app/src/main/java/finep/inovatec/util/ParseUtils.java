package finep.inovatec.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestionOption;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;

/**
 * @author Nivaldo Bondança
 */
public class ParseUtils {

	public static Group parseGroupJSON(String jsonString) throws JSONException {
		Group data = new Group();

		JSONObject json = new JSONObject(jsonString);
		data.setId(json.getLong("id"));
		data.setName(json.getString("name"));

		JSONArray forms = json.getJSONArray("forms");
		for (int i = 0; i < forms.length(); i++) {
			// Just check the forms validity
			parseFormJSON(forms.getJSONObject(i).toString());
		}

		return data;
	}

	public static Form parseFormJSON(String jsonString) throws JSONException {
		Form data = new Form();

		JSONObject json = new JSONObject(jsonString);
		data.setId(json.optLong("id"));
		data.setCodeName(json.getString("codeName"));
		data.setName(json.getString("name"));

		JSONArray sections = json.getJSONArray("sections");
		List<FormSection> list = new ArrayList<>();
		for (int i = 0; i < sections.length(); i++) {
			FormSection item = parseSectionJSON(sections.getJSONObject(i).toString());
			list.add(item);
		}
		data.setSections(list);

		return data;
	}

	public static FormSection parseSectionJSON(String jsonString) throws JSONException {
		FormSection data = new FormSection();

		JSONObject json = new JSONObject(jsonString);
		data.setId(json.optLong("id"));
		data.setCodeName(json.getString("codeName"));
		data.setName(json.getString("name"));

		JSONArray questions = json.getJSONArray("questions");
		List<FormQuestion> list = new ArrayList<>();
		for (int i = 0; i < questions.length(); i++) {
			FormQuestion item = parseQuestionJSON(questions.getJSONObject(i).toString());
			list.add(item);
		}
		data.setQuestions(list);

		return data;
	}

	public static FormQuestion parseQuestionJSON(String jsonString) throws JSONException {
		FormQuestion data = new FormQuestion();

		JSONObject json = new JSONObject(jsonString);
		data.setId(json.optLong("id"));
		data.setCodeName(json.optString("codeName"));
		data.setText(json.getString("text"));
		data.setObservation(json.optString("observation"));
		data.setHint(json.optString("hint"));

		JSONArray options = json.getJSONArray("options");
		List<FormQuestionOption> list = new ArrayList<>();
		for (int i = 0; i < options.length(); i++) {
			FormQuestionOption item = parseQuestionOptionJSON(options.getJSONObject(i).toString());
			list.add(item);
		}

		data.setOptions(list);

		return data;
	}

	private static FormQuestionOption parseQuestionOptionJSON(String jsonString) throws JSONException {
		FormQuestionOption data = new FormQuestionOption();

		JSONObject json = new JSONObject(jsonString);
		data.setId(json.optLong("id"));
		data.setTitle(json.optString("title"));

		String selection = json.optString("selection");
		switch (selection) {
			case "single":
			case "multiple":
				data.setSelection(selection);
				break;

			default:
				throw new JSONException("Invalid type '" + selection + "' for field \"selection\"");
		}

		JSONArray items = json.getJSONArray("items");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < items.length(); i++) {
			String item = items.getString(i);
			list.add(item);
		}

		data.setItems(list);

		return data;
	}

	public static String toJSONString(FormQuestion question) {
		JSONObject jsonObject = new JSONObject(question);
		return jsonObject.toString();
	}
}
