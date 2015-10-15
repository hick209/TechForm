package finep.inovatec.filling;

import android.content.Context;
import android.databinding.Bindable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import finep.inovatec.BR;
import finep.inovatec.R;
import finep.inovatec.common.ViewModel;
import finep.inovatec.components.SimpleTextWatcher;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ActivityFillingInfoBinding;

/**
 * @author Nivaldo Bondança
 */
public class FillingDetailsViewModel extends ViewModel {

	public interface FillingDetailsCallbacks {
		void pickDeliverDate(long initialTimestamp);

		void saveAndStartFilling(Filling filling);
	}

	private Filling      mFilling;
	private boolean      mNewFilling;
	private DateFormat   mDateFormatter;
	private DateFormat   mTimeFormatter;

	private String mCodeError;
	private String mAddressError;
	private String mResponsibleError;

	private final String mErrorRequired;

	private final TextWatcher mCodeValidator = new SimpleTextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			mFilling.setCode(s.toString());
			setCodeError(TextUtils.isEmpty(s) ? mErrorRequired : null);
		}
	};
	private final TextWatcher mAddressValidator = new SimpleTextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			mFilling.setAddress(s.toString());
			setAddressError(TextUtils.isEmpty(s) ? mErrorRequired : null);
		}
	};
	private final TextWatcher mResponsibleValidator = new SimpleTextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			mFilling.setInspectionResponsible(s.toString());
			setResponsibleError(TextUtils.isEmpty(s) ? mErrorRequired : null);
		}
	};

	private FillingDetailsCallbacks mCallbacks;

	public FillingDetailsViewModel(Context context, ActivityFillingInfoBinding binding, Filling filling, boolean newFilling) {
		mFilling = filling;
		mNewFilling = newFilling;
		mDateFormatter = android.text.format.DateFormat.getDateFormat(context);
		mTimeFormatter = android.text.format.DateFormat.getTimeFormat(context);

		mErrorRequired = context.getString(R.string.error_requiredField);
	}

	public void setCallbacks(FillingDetailsCallbacks callbacks) {
		mCallbacks = callbacks;
	}

	public void setFilling(Filling filling) {
		mFilling = filling;
		notifyPropertyChanged(BR.code);
		notifyPropertyChanged(BR.address);
		notifyPropertyChanged(BR.deliverTimestamp);
		notifyPropertyChanged(BR.inspectionResponsible);
		notifyPropertyChanged(BR.beginningTimestamp);
		notifyPropertyChanged(BR.endingTimestamp);
	}

	@Bindable
	public CharSequence getCode() {
		return mFilling.getCode();
	}

	@Bindable
	public CharSequence getAddress() {
		return mFilling.getAddress();
	}

	@Bindable
	public CharSequence getDeliverTimestamp() {
		return formatDateTimestamp(mFilling.getDeliverTimestamp());
	}

	public View.OnClickListener getDeliverClick() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCallbacks.pickDeliverDate(mFilling.getDeliverTimestamp());
			}
		};
	}

	@Bindable
	public CharSequence getInspectionResponsible() {
		return mFilling.getInspectionResponsible();
	}

	@Bindable
	public CharSequence getBeginningTimestamp() {
		return formatDatetimeTimestamp(mFilling.getBeginningTimestamp());
	}

	@Bindable
	public CharSequence getEndingTimestamp() {
		if (mFilling.getEndingTimestamp() == 0) {
			return "";
		}
		return formatDatetimeTimestamp(mFilling.getEndingTimestamp());
	}

	public boolean getComplete() {
		return mFilling.getEndingTimestamp() > 0;
	}

	public TextWatcher getCodeValidator() {
		return mCodeValidator;
	}

	@Bindable
	public String getCodeError() {
		return mCodeError;
	}

	public void setCodeError(String codeError) {
		mCodeError = codeError;
		notifyPropertyChanged(BR.codeError);
		notifyPropertyChanged(BR.formOk);
	}

	public TextWatcher getAddressValidator() {
		return mAddressValidator;
	}

	@Bindable
	public String getAddressError() {
		return mAddressError;
	}

	public void setAddressError(String addressError) {
		mAddressError = addressError;
		notifyPropertyChanged(BR.addressError);
		notifyPropertyChanged(BR.formOk);
	}

	public TextWatcher getResponsibleValidator() {
		return mResponsibleValidator;
	}

	@Bindable
	public String getResponsibleError() {
		return mResponsibleError;
	}

	public void setResponsibleError(String responsibleError) {
		mResponsibleError = responsibleError;
		notifyPropertyChanged(BR.responsibleError);
		notifyPropertyChanged(BR.formOk);
	}

	public boolean getEditable() {
		return mNewFilling;
	}

	@Bindable
	public boolean getFormOk() {
		return mCodeError == null
				&& mAddressError == null
				&& mResponsibleError == null;
	}

	public View.OnClickListener getFinish() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCallbacks.saveAndStartFilling(mFilling);
			}
		};
	}

	private String formatDateTimestamp(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);

		return mDateFormatter.format(calendar.getTime());
	}

	private String formatDatetimeTimestamp(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);

		Date date = calendar.getTime();
		return String.format("%s  %s", mDateFormatter.format(date), mTimeFormatter.format(date));
	}
}
