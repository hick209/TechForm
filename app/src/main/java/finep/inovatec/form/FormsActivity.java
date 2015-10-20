package finep.inovatec.form;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.List;

import finep.inovatec.FormFillingManager;
import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.data.Filling;
import finep.inovatec.util.Utils;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;

/**
 * @author Nivaldo Bondança
 */
public class FormsActivity extends BaseActivity {

	public static Intent newInstance(Context context) {
		return new Intent(context, FormsActivity.class);
	}


	private FormFillingManager mFillingManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forms);

		mFillingManager = FormFillingManager.getInstance();

		setupToolbar();
		setupViewPager();
	}

	@Override
	protected void onResume() {
		super.onResume();
		List<Form> forms = mFillingManager.getGroup().getForms();
		Filling filling = mFillingManager.getFilling();

		boolean complete = true;
		for (Form form : forms) {
			if (!Utils.isFormComplete(form, filling.getForm(form.getCodeName()))) {
				complete = false;
				break;
			}
		}
		if (complete) {
			// Mark as complete
			mFillingManager.getFilling().setEndingTimestamp(System.currentTimeMillis());
			// And save!
			mFillingManager.save();
		}
	}

	private void setupViewPager() {
		FormsAdapter adapter = new FormsAdapter(mFillingManager.getGroup());

		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(adapter);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	public Toolbar setupToolbar() {
		Filling filling = mFillingManager.getFilling();
		CharSequence subtitle = String.format("%s - %s", filling.getCode(), filling.getAddress());

		Toolbar toolbar = super.setupToolbar();
		//noinspection ConstantConditions
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(mFillingManager.getGroup().getName());
		getSupportActionBar().setSubtitle(subtitle);

		return toolbar;
	}


	class FormsAdapter extends FragmentPagerAdapter {

		private FormSectionsFragment[] mFragments;
		private List<Form>             mForms;

		public FormsAdapter(Group group) {
			super(getSupportFragmentManager());
			mForms = group.getForms();
			populateFragments();
		}

		private void populateFragments() {
			mFragments = new FormSectionsFragment[mForms.size()];

			for (int i = 0; i < mFragments.length; i++) {
				mFragments[i] = FormSectionsFragment.instantiate(i);
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Form form = mForms.get(position);
			return form.getCodeName();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments[position];
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}
	}
}
