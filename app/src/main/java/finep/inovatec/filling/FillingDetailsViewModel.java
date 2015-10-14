package finep.inovatec.filling;

import android.content.Context;
import android.databinding.Bindable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import finep.inovatec.BR;
import finep.inovatec.common.ViewModel;
import finep.inovatec.data.Filling;

/**
 * @author Nivaldo Bondança
 */
public class FillingDetailsViewModel extends ViewModel {

	private CharSequence mToolbarTitle;
	private CharSequence mToolbarSubtitle;

	private Filling      mFilling;
	private DateFormat   mDateFormatter;
	private DateFormat   mTimeFormatter;

	public FillingDetailsViewModel(Context context, Filling filling) {
		mFilling = filling;
		mDateFormatter = android.text.format.DateFormat.getDateFormat(context);
		mTimeFormatter = android.text.format.DateFormat.getTimeFormat(context);
	}

	@Bindable
	public CharSequence getToolbarTitle() {
		return mToolbarTitle;
	}

	@Bindable
	public CharSequence getToolbarSubtitle() {
		return mToolbarSubtitle;
	}

	public void setToolbar(CharSequence title, CharSequence subtitle) {
		mToolbarTitle = title;
		mToolbarSubtitle = subtitle;
		notifyPropertyChanged(BR.toolbarTitle);
		notifyPropertyChanged(BR.toolbarSubtitle);
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
		return formatDatetimeTimestamp(mFilling.getEndingTimestamp());
	}

	public boolean getComplete() {
		return mFilling.getEndingTimestamp() > 0;
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
