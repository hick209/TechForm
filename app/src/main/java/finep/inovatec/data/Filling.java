package finep.inovatec.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Nivaldo Bondança
 */
public class Filling implements Parcelable {
	protected Filling(Parcel in) {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}

	public static final Creator<Filling> CREATOR = new Creator<Filling>() {
		@Override
		public Filling createFromParcel(Parcel in) {
			return new Filling(in);
		}

		@Override
		public Filling[] newArray(int size) {
			return new Filling[size];
		}
	};
}
