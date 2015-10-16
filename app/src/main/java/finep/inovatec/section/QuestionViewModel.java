package finep.inovatec.section;

import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;

/**
 * @author Nivaldo Bondança
 */
public class QuestionViewModel {

	private FormQuestion mQuestion;

	public QuestionViewModel(FormQuestion question) {
		mQuestion = question;
	}

	public String getText() {
		return mQuestion.getText();
	}

	public String getObservation() {
		return mQuestion.getObservation();
	}

	public String getHint() {
		return mQuestion.getHint();
	}

}
