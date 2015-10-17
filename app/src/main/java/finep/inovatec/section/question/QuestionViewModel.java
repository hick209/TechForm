package finep.inovatec.section.question;

import finep.inovatec.data.FillingQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;

/**
 * @author Nivaldo Bondança
 */
public class QuestionViewModel {

	private FormQuestion mQuestion;

	public QuestionViewModel(FormQuestion question, FillingQuestion questionFilling) {
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
