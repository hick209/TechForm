package finep.inovatec.data;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Nivaldo Bondança
 */
public class FillingFormSection {

	private String mCodeName;

	private List<FillingQuestion> mQuestions = new ArrayList<>();

	private transient Map<String, FillingQuestion> mHelper = new ArrayMap<>();

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

		mHelper.clear();
		for (FillingQuestion f : list) {
			mHelper.put(f.getCodeName(), f);
		}
	}

	public FillingQuestion getQuestion(String codeName) {
		FillingQuestion question = mHelper.get(codeName);
		if (question == null) {
			question = new FillingQuestion();
			mQuestions.add(question);
		}

		return question;
	}

	public void putQuestion(FillingQuestion item) {
		mHelper.put(item.getCodeName(), item);
	}
}
