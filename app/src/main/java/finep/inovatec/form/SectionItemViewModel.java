package finep.inovatec.form;

import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;

/**
 * @author Nivaldo Bondança
 */
public class SectionItemViewModel {

	private FormSection mFormSection;

	public SectionItemViewModel(FormSection formSection) {
		mFormSection = formSection;
	}

	public String getText() {
		return String.format("%s - %s", mFormSection.getCodeName(), mFormSection.getName());
	}

	public boolean getComplete() {
		return mFormSection.getQuestions().size() % 2 == 0;
	}

}
