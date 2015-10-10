package finep.inovatec.components;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import finep.inovatec.BR;

/**
 * @author Nivaldo Bondança
 */
public class TwoLineAvatar extends BaseObservable {

	private Drawable mAvatar;
	private String   mPrimaryText;
	private String   mSecondaryText;

	public TwoLineAvatar(Drawable avatar, String primaryText, String secondaryText) {
		mAvatar = avatar;
		mPrimaryText = primaryText;
		mSecondaryText = secondaryText;
	}

	@Bindable
	public Drawable getAvatar() {
		return mAvatar;
	}

	public void setAvatar(Drawable avatar) {
		mAvatar = avatar;
		notifyPropertyChanged(BR.avatar);
	}

	@Bindable
	public String getPrimaryText() {
		return mPrimaryText;
	}

	public void setPrimaryText(String primaryText) {
		mPrimaryText = primaryText;
		notifyPropertyChanged(BR.primaryText);
	}

	@Bindable
	public String getSecondaryText() {
		return mSecondaryText;
	}

	public void setSecondaryText(String secondaryText) {
		mSecondaryText = secondaryText;
		notifyPropertyChanged(BR.secondaryText);
	}
}
