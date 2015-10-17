package finep.inovatec.form;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import finep.inovatec.BR;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;

/**
 * @author Nivaldo Bondança
 */
public class SectionItemViewModel extends BaseObservable {

	private FormSection mFormSection;
	private boolean mComplete;

	public SectionItemViewModel(FormSection formSection) {
		mFormSection = formSection;
	}

	public String getText() {
		return String.format("%s - %s", mFormSection.getCodeName(), mFormSection.getName());
	}

	@Bindable
	public boolean getComplete() {
		return mComplete;
	}

	public void setComplete(boolean complete) {
		mComplete = complete;
		notifyPropertyChanged(BR.complete);
	}
}
