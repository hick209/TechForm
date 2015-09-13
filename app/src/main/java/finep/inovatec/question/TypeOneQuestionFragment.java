package finep.inovatec.question;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finep.inovatec.R;

/**
 * @author Nivaldo Bondança
 */
public class TypeOneQuestionFragment extends QuestionFragment {

	@Override
	protected View getContentView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_question_type_one, container, false);
	}

}
