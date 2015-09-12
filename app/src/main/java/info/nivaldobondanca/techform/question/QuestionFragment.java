package info.nivaldobondanca.techform.question;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.util.ParseUtils;

/**
 * @author Nivaldo Bondança
 */
public abstract class QuestionFragment extends Fragment {

	private static final String ARG_QUESTION = "arg.QUESTION";

	public static QuestionFragment instantiate(FormQuestion question) {
		Bundle args = new Bundle();
		// FIXME question.toString() not behaving as it should!!!
		args.putString(ARG_QUESTION, ParseUtils.toJSONString(question));

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

	private FormQuestion mQuestion;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			String json = getArguments().getString(ARG_QUESTION);
			mQuestion = ParseUtils.parseQuestionJSON(json);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_question, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((TextView) view.findViewById(R.id.question_text)).setText(getQuestion().getText());
		setTextAndVisibility(view, R.id.question_observation, getQuestion().getObservation());
		setTextAndVisibility(view, R.id.question_hint, getQuestion().getHint());

		SectionQuestionsActivity activity = (SectionQuestionsActivity) getActivity();

		ViewGroup content = (ViewGroup) view.findViewById(R.id.question_content);
		View contentView = getContentView(LayoutInflater.from(activity), content);
		if (contentView != null) {
			content.addView(contentView);
		}

		view.findViewById(R.id.question_next).setOnClickListener(activity);
	}

	protected abstract View getContentView(LayoutInflater inflater, ViewGroup container);

	private void setTextAndVisibility(View view, @IdRes int id, String observation) {
		if (!TextUtils.isEmpty(observation)) {
			TextView textView = (TextView) view.findViewById(id);
			textView.setText(observation);
			textView.setVisibility(View.VISIBLE);
		}
	}

	public FormQuestion getQuestion() {
		return mQuestion;
	}
}
