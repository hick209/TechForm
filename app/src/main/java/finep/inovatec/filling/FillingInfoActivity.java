package finep.inovatec.filling;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.app.CacheAgent;
import finep.inovatec.components.DatePickerFragment;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ActivityFillingInfoBinding;

/**
 * @author Nivaldo Bondança
 */
public class FillingInfoActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener,
		FillingDetailsViewModel.FillingDetailsCallbacks {

	private static final String EXTRA_FILLING  = "extra.FILLING";
	private static final String EXTRA_GROUP_ID = "extra.GROUP_ID";

	public static Intent newInstance(Context context, @NonNull Filling item) {
		String json = new Gson().toJson(item);
		return new Intent(context, FillingInfoActivity.class)
				.putExtra(EXTRA_FILLING, json);
	}

	public static Intent newInstance(Context context, long groupId) {
		return new Intent(context, FillingInfoActivity.class)
				.putExtra(EXTRA_GROUP_ID, groupId);
	}

	// TODO

	private FillingDetailsViewModel mViewModel;

	private Filling mFilling;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String json = getIntent().getStringExtra(EXTRA_FILLING);
		Gson gson = new Gson();

		mFilling = gson.fromJson(json, Filling.class);
		boolean newFilling;
		if (mFilling == null) {
			newFilling = true;
			mFilling = new Filling();
			mFilling.setGroupId(getIntent().getLongExtra(EXTRA_GROUP_ID, -1));
		}
		else {
			newFilling = false;
		}


		ActivityFillingInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filling_info);
		mViewModel = new FillingDetailsViewModel(this, binding, mFilling, newFilling);
		mViewModel.setCallbacks(this);

		binding.setViewModel(mViewModel);

		mViewModel.setToolbar(newFilling ? getText(R.string.title_toolbar_newFilling) : mFilling.getCode(),
				"Subtitle!"); // TODO change this text to the correct one

		setupToolbar();
		//noinspection ConstantConditions
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(mViewModel.getToolbarTitle());
		getSupportActionBar().setSubtitle(mViewModel.getToolbarSubtitle());
	}

	@Override
	public void saveAndStartFilling(Filling filling) {
		long groupId = filling.getGroupId();
		CacheAgent cacheAgent = getTechFormApplication().getCacheAgent();

		List<Filling> fillings;
		try {
			// Load
			fillings = cacheAgent.loadFillings(groupId);
		}
		catch (IOException e) {
			fillings = new ArrayList<>();
		}

		fillings.add(filling);

		try {
			// Save
			cacheAgent.saveFillings(groupId, fillings);
			finish();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pickDeliverDate(long initialTimestamp) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(initialTimestamp);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		DatePickerFragment.instantiate(year, month, day)
				.show(getSupportFragmentManager(), "datePicker");
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, monthOfYear);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		mFilling.setDeliverTimestamp(c.getTimeInMillis());
		mViewModel.setFilling(mFilling);
	}

}
