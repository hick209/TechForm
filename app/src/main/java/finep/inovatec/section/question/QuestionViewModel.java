package finep.inovatec.section.question;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import finep.inovatec.BR;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;

/**
 * @author Nivaldo Bondança
 */
public class QuestionViewModel extends BaseObservable {
	public interface QuestionCallbacks {
		void onAttachPicture(FormQuestion question);
	}

	private FormQuestion      mQuestion;
	private QuestionCallbacks mCallbacks;

	private Drawable mPicture;

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

	public View.OnClickListener getAttachPicture() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCallbacks.onAttachPicture(mQuestion);
			}
		};
	}

	public void setCallbacks(QuestionCallbacks callbacks) {
		mCallbacks = callbacks;
	}

	@Bindable
	public Drawable getPicture() {
		System.out.println("Reading image...");
		return mPicture;
	}

	public void setPicture(Drawable image) {
		mPicture = image;
		System.out.println("New image");
		notifyPropertyChanged(BR.picture);
	}
}
