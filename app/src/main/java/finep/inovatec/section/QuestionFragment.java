package finep.inovatec.section;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finep.inovatec.FormFillingManager;
import finep.inovatec.R;
import finep.inovatec.databinding.FragmentQuestionBinding;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;

/**
 * @author Nivaldo Bondança
 */
public abstract class QuestionFragment extends Fragment {

	private static final String ARG_FORM_POSITION     = "arg.FORM_POSITION";
	private static final String ARG_SECTION_POSITION  = "arg.SECTION_POSITION";
	private static final String ARG_QUESTION_POSITION = "arg.QUESTION_POSITION";

	public static QuestionFragment instantiate(int formPosition, int sectionPosition, int questionPosition) {
		Bundle args = new Bundle();
		args.putInt(ARG_FORM_POSITION, formPosition);
		args.putInt(ARG_SECTION_POSITION, sectionPosition);
		args.putInt(ARG_QUESTION_POSITION, questionPosition);

		Form form = FormFillingManager.getInstance().getGroup().getForms().get(formPosition);
		FormQuestion question = form.getSections().get(sectionPosition).getQuestions().get(questionPosition);

		QuestionFragment fragment = getQuestionFragment(question.getType());
		fragment.setArguments(args);

		return fragment;
	}

	private static QuestionFragment getQuestionFragment(int type) {
		QuestionFragment fragment;
		switch (type) {
			case 1:
				fragment = new TypeOneQuestionFragment();
				break;

			case 2:
				fragment = new TypeTwoQuestionFragment();
				break;

			default:
				throw new IllegalArgumentException("Question type not yet implemented!");
		}
		return fragment;
	}

	private int mFormPosition;
	private int mSectionPosition;
	private int mQuestionPosition;

	private FormQuestion mQuestion;
	private QuestionViewModel mViewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		mFormPosition = args.getInt(ARG_FORM_POSITION);
		mSectionPosition = args.getInt(ARG_SECTION_POSITION);
		mQuestionPosition = args.getInt(ARG_QUESTION_POSITION);

		Form form = FormFillingManager.getInstance().getGroup().getForms().get(mFormPosition);
		mQuestion = form.getSections().get(mSectionPosition).getQuestions().get(mQuestionPosition);

		mViewModel = new QuestionViewModel(mQuestion);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FragmentQuestionBinding binding = FragmentQuestionBinding.inflate(inflater, container, false);
		binding.setViewModel(mViewModel);

		return binding.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ViewGroup content = (ViewGroup) view.findViewById(R.id.question_content);
		View contentView = getContentView(LayoutInflater.from(getContext()), content);
		if (contentView != null) {
			content.addView(contentView);
		}
	}

	protected abstract View getContentView(LayoutInflater inflater, ViewGroup container);

	public FormQuestion getQuestion() {
		return mQuestion;
	}
}
