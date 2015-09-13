package info.nivaldobondanca.techform.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONException;

import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.util.ParseUtils;
import info.nivaldobondanca.techform.util.Utils;

/**
 * @author Nivaldo Bondança
 */
public class SectionQuestionsActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String EXTRA_FORM             = "extra.FORM";
	private static final String EXTRA_SECTION_POSITION = "extra.SECTION_POSITION";

	private ViewPager mViewPager;

	public static Intent newInstance(Context c, Form form, int sectionPosition) {
		Intent intent = new Intent(c, SectionQuestionsActivity.class);
		intent.putExtra(EXTRA_FORM, form.toString());
		intent.putExtra(EXTRA_SECTION_POSITION, sectionPosition);

		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_questions);
		Bundle extras = getIntent().getExtras();

		String formJSON = extras.getString(EXTRA_FORM);
		int currentSection = extras.getInt(EXTRA_SECTION_POSITION);

		Form form;
		try {
			form = ParseUtils.parseFormJSON(formJSON);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		Toolbar toolbar = Utils.setupToolbar(this);
		String sectionCode = getSectionCode(form, currentSection);
		toolbar.setTitle(sectionCode);

		QuestionsAdapter adapter = new QuestionsAdapter(
				form.getSections().get(currentSection), sectionCode);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(adapter);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

		tabLayout.setupWithViewPager(mViewPager);
		findViewById(R.id.pager).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SectionQuestionsActivity.this, info.nivaldobondanca.techform.group.details.GroupDetailsActivity.class));
			}
		});
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
