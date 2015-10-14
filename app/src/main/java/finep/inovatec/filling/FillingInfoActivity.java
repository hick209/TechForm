package finep.inovatec.filling;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ActivityFillingInfoBinding;

/**
 * @author Nivaldo Bondança
 */
public class FillingInfoActivity extends BaseActivity {

	public static Intent newInstance(Context context) {
		return new Intent(context, FillingInfoActivity.class);
	}

	// TODO

	private FillingDetailsViewModel mViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Filling filling = null; // TODO
		mViewModel = new FillingDetailsViewModel(this, filling);

		ActivityFillingInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filling_info);
		binding.setViewModel(mViewModel);
	}
}
