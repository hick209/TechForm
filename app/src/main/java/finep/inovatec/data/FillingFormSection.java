package finep.inovatec.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public class FillingFormSection {

	@SerializedName("codeName")
	private String mCodeName;

	@SerializedName("questions")
	private List<FillingQuestion> mQuestions = new ArrayList<>();

	public String getCodeName() {
		return mCodeName;
	}

	public void setCodeName(String codeName) {
		mCodeName = codeName;
	}

	public List<FillingQuestion> getQuestions() {
		return mQuestions;
	}

	public void setQuestions(List<FillingQuestion> list) {
		if (list == null) {
			list = Collections.emptyList();
		}
		mQuestions = list;
	}

	public FillingQuestion getQuestion(String codeName) {
		for (FillingQuestion item : mQuestions) {
			if (item.getCodeName().equals(codeName)) {
				return item;
			}
		}

		FillingQuestion question = new FillingQuestion();
		question.setCodeName(codeName);

		putQuestion(question);

		return question;
	}

	public void putQuestion(FillingQuestion item) {
		for (FillingQuestion question : mQuestions) {
			if (question.getCodeName().equals(item.getCodeName())) {
				mQuestions.remove(question);
				break;
			}
		}

		mQuestions.add(item);
	}


	public FillingFormSection createCopy() {
		FillingFormSection clone = new FillingFormSection();
		clone.setCodeName(getCodeName());
		List<FillingQuestion> questions = getQuestions();
		List<FillingQuestion> questionFillings = new ArrayList<>(questions.size());
		for (FillingQuestion q : questions) {
			questionFillings.add(q.createCopy());
		}
		clone.setQuestions(questionFillings);

		return clone;
	}

}
