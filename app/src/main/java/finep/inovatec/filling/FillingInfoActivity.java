package finep.inovatec.filling;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ActivityFillingInfoBinding;

/**
 * @author Nivaldo Bondança
 */
public class FillingInfoActivity extends BaseActivity {

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

	private boolean mNewFilling;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String json = getIntent().getStringExtra(EXTRA_FILLING);
		Gson gson = new Gson();

		Filling filling = gson.fromJson(json, Filling.class);
		if (filling == null) {
			mNewFilling = true;
			filling = new Filling();
			filling.setGroupId(getIntent().getLongExtra(EXTRA_GROUP_ID, -1));
		}
		else {
			mNewFilling = false;
		}

		mViewModel = new FillingDetailsViewModel(this, filling);

		ActivityFillingInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filling_info);
		binding.setViewModel(mViewModel);

		mViewModel.setToolbar(mNewFilling ? getText(R.string.title_toolbar_newFilling) : filling.getCode(),
				"Subtitle!"); // TODO change this text to the correct one

		setupToolbar();
		//noinspection ConstantConditions
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(mViewModel.getToolbarTitle());
		getSupportActionBar().setSubtitle(mViewModel.getToolbarSubtitle());
	}
}
