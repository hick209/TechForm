package finep.inovatec.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * @author Nivaldo Bondança
 */
public class DatePickerFragment extends DialogFragment {

	public static final String ARG_DAY   = "arg.DAY";
	public static final String ARG_MONTH = "arg.MONTH";
	public static final String ARG_YEAR  = "arg.YEAR";

	public static DatePickerFragment instantiate(int year, int month, int day) {
		Bundle args = new Bundle();
		args.putInt(ARG_DAY, day);
		args.putInt(ARG_MONTH, month);
		args.putInt(ARG_YEAR, year);

		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		Bundle args = getArguments();

		DatePickerDialog.OnDateSetListener callback;

		try {
			Object parent = getParentFragment();
			if (parent == null) {
				parent = getActivity();
			}
			callback = (DatePickerDialog.OnDateSetListener) parent;
		}
		catch (ClassCastException e) {
			throw new RuntimeException("Parent must implement DatePickerDialog.OnDateSetListener", e);
		}

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), callback,
				args.getInt(ARG_YEAR, year),
				args.getInt(ARG_MONTH ,month),
				args.getInt(ARG_DAY ,day));
	}

}
