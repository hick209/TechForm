package finep.inovatec.section;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import finep.inovatec.FormFillingManager;
import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.data.Filling;
import finep.inovatec.data.FillingForm;
import finep.inovatec.data.FillingFormSection;
import finep.inovatec.section.question.QuestionFragment;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;

/**
 * @author Nivaldo Bondança
 */
public class SectionQuestionsActivity extends BaseActivity {

	private static final String EXTRA_FORM_POSITION    = "extra.FORM_POSITION";
	private static final String EXTRA_SECTION_POSITION = "extra.SECTION_POSITION";

	public static Intent newInstance(Context c, int formPosition, int sectionPosition) {
		Intent intent = new Intent(c, SectionQuestionsActivity.class);
		intent.putExtra(EXTRA_FORM_POSITION, formPosition);
		intent.putExtra(EXTRA_SECTION_POSITION, sectionPosition);

		return intent;
	}

	private int mFormPosition;
	private int mSectionPosition;
	private FillingFormSection mFormSection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_questions);
		Bundle extras = getIntent().getExtras();

		mFormPosition = extras.getInt(EXTRA_FORM_POSITION);
		mSectionPosition = extras.getInt(EXTRA_SECTION_POSITION);

		FormFillingManager manager = FormFillingManager.getInstance();
		Group group = manager.getGroup();
		Form form = group.getForms().get(mFormPosition);
		FormSection section = form.getSections().get(mSectionPosition);

		setupToolbar(manager, section);
		setupViewPager(form, section);

		setupFilling(manager, form, section);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_section_questions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_done:
				Filling filling = FormFillingManager.getInstance().getFilling();
				String codeName = filling.getForms().get(mFormPosition).getCodeName();
				FillingForm form = filling.getForm(codeName);
				form.putSection(mFormSection);

				FormFillingManager.getInstance().save();

				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupToolbar(FormFillingManager manager, FormSection section) {
		Filling filling = manager.getFilling();
		CharSequence subtitle = String.format("%s - %s", filling.getCode(), filling.getAddress());

		setupToolbar();
		//noinspection ConstantConditions
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(section.getName());
		getSupportActionBar().setSubtitle(subtitle);
	}

	private void setupViewPager(Form form, FormSection section) {
		QuestionsAdapter adapter = new QuestionsAdapter(section, getSectionCode(form, mSectionPosition));

		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(adapter);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setupWithViewPager(viewPager);
	}

	private void setupFilling(FormFillingManager manager, Form form, FormSection section) {
		FillingForm fillingForm = manager.getFilling().getForm(form.getCodeName());
		mFormSection = fillingForm.getSection(section.getCodeName());
	}

	private String getSectionCode(Form form, int section) {
		FormSection formSection = form.getSections().get(section);

		String formCode = form.getCodeName();
		String sectionCode = formSection.getCodeName();

		return formCode + "." + sectionCode;
	}

	public FillingFormSection getFormSection() {
		return mFormSection;
	}

	class QuestionsAdapter extends FragmentPagerAdapter {

		private final String mTitlePrefix;

		private Fragment[]         mFragments;
		private List<FormQuestion> mQuestions;

		public QuestionsAdapter(FormSection formSection, String sectionCode) {
			super(getSupportFragmentManager());
			mTitlePrefix = sectionCode + ".";
			mQuestions = formSection.getQuestions();
			populateFragments();
		}

		private void populateFragments() {
			mFragments = new Fragment[mQuestions.size()];

			for (int i = 0; i < mFragments.length; i++) {
				mFragments[i] = QuestionFragment.instantiate(mFormPosition, mSectionPosition, i);
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			FormQuestion question = mQuestions.get(position);
			return mTitlePrefix + question.getCodeName();
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
