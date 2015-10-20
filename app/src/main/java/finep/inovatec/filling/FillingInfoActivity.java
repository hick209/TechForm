package finep.inovatec.filling;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import finep.inovatec.FormFillingManager;
import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.components.DatePickerFragment;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ActivityFillingInfoBinding;
import finep.inovatec.form.FormsActivity;

/**
 * @author Nivaldo Bondança
 */
public class FillingInfoActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener,
		FillingDetailsViewModel.FillingDetailsCallbacks {

	public static Intent newInstance(Context context) {
		return new Intent(context, FillingInfoActivity.class);
	}


	private FillingDetailsViewModel mViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FormFillingManager fillingManager = FormFillingManager.getInstance();
		Filling filling = fillingManager.getFilling();
		boolean newFilling;
		if (filling == null) {
			newFilling = true;

			filling = new Filling();
			filling.setGroupId(fillingManager.getGroup().getId());

			fillingManager.setFilling(filling);
		}
		else {
			newFilling = false;
		}


		ActivityFillingInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filling_info);
		mViewModel = new FillingDetailsViewModel(this, binding, filling, newFilling);
		mViewModel.setCallbacks(this);

		binding.setViewModel(mViewModel);

		CharSequence subtitle = newFilling ? getText(R.string.title_toolbar_newFilling) :
				String.format("%s - %s", filling.getCode(), filling.getAddress());

		setupToolbar();
		//noinspection ConstantConditions
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(fillingManager.getGroup().getName());
		getSupportActionBar().setSubtitle(subtitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_filling_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_sendResults:
				// Send the request
				// TODO this is a fake-one
				final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Enviando...");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
							Toast.makeText(FillingInfoActivity.this, "Formulário enviado!", Toast.LENGTH_SHORT).show();
						}
					}
				}, 3000);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void saveAndStartFilling(Filling filling) {
		FormFillingManager manager = FormFillingManager.getInstance();
		manager.setFilling(filling);
		manager.save();

		finish();
		startActivity(FormsActivity.newInstance(this));
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

		Filling filling = FormFillingManager.getInstance().getFilling();

		filling.setDeliverTimestamp(c.getTimeInMillis());
		mViewModel.setFilling(filling);
	}
}
