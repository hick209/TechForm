package finep.inovatec.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import finep.inovatec.FormFillingManager;
import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;

/**
 * @author Nivaldo Bondança
 */
public class SectionQuestionsActivity extends BaseActivity implements View.OnClickListener {

	private static final String EXTRA_FORM_POSITION    = "extra.FORM_POSITION";
	private static final String EXTRA_SECTION_POSITION = "extra.SECTION_POSITION";

	private ViewPager mViewPager;

	public static Intent newInstance(Context c, int formPosition, int sectionPosition) {
		Intent intent = new Intent(c, SectionQuestionsActivity.class);
		intent.putExtra(EXTRA_FORM_POSITION, formPosition);
		intent.putExtra(EXTRA_SECTION_POSITION, sectionPosition);

		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_questions);
		Bundle extras = getIntent().getExtras();

		int formPosition = extras.getInt(EXTRA_FORM_POSITION);
		int sectionPosition = extras.getInt(EXTRA_SECTION_POSITION);

		Form form = FormFillingManager.getInstance().getGroup().getForms().get(formPosition);

		Toolbar toolbar = setupToolbar();
		String sectionCode = getSectionCode(form, sectionPosition);
		toolbar.setTitle(sectionCode);

		QuestionsAdapter adapter = new QuestionsAdapter(
				form.getSections().get(sectionPosition), sectionCode);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(adapter);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setupWithViewPager(mViewPager);
	}

	private String getSectionCode(Form form, int section) {
		FormSection formSection = form.getSections().get(section);

		String formCode = form.getCodeName();
		String sectionCode = formSection.getCodeName();

		return formCode + "." + sectionCode;
	}

	@Override
	public void onClick(View v) {
		// Next question
		int nextItem = mViewPager.getCurrentItem() + 1;
		if (nextItem < mViewPager.getAdapter().getCount()) {
			mViewPager.setCurrentItem(nextItem);
		}
		else {
			// TODO go to the summary
		}
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
				mFragments[i] = QuestionFragment.instantiate(mQuestions.get(i));
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
