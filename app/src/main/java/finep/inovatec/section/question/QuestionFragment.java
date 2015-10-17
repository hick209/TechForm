package finep.inovatec.section.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import finep.inovatec.FormFillingManager;
import finep.inovatec.common.BaseFragment;
import finep.inovatec.components.SimpleTextWatcher;
import finep.inovatec.data.FillingFormSection;
import finep.inovatec.data.FillingQuestion;
import finep.inovatec.data.FillingQuestionOption;
import finep.inovatec.databinding.CellQuestionOptionMultipleBinding;
import finep.inovatec.databinding.CellQuestionOptionSingleBinding;
import finep.inovatec.databinding.FragmentQuestionBinding;
import finep.inovatec.databinding.ItemCheckboxBinding;
import finep.inovatec.databinding.ItemRadioButtonBinding;
import finep.inovatec.section.SectionQuestionsActivity;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestionOption;

/**
 * @author Nivaldo Bondança
 */
public class QuestionFragment extends BaseFragment {

	private static final String ARG_FORM_POSITION     = "arg.FORM_POSITION";
	private static final String ARG_SECTION_POSITION  = "arg.SECTION_POSITION";
	private static final String ARG_QUESTION_POSITION = "arg.QUESTION_POSITION";

	public static QuestionFragment instantiate(int formPosition, int sectionPosition, int questionPosition) {
		Bundle args = new Bundle();
		args.putInt(ARG_FORM_POSITION, formPosition);
		args.putInt(ARG_SECTION_POSITION, sectionPosition);
		args.putInt(ARG_QUESTION_POSITION, questionPosition);

		QuestionFragment fragment = new QuestionFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private int mFormPosition;
	private int mSectionPosition;
	private int mQuestionPosition;

	private QuestionViewModel mViewModel;
	private FormQuestion      mQuestion;
	private FillingQuestion   mQuestionFilling;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		mFormPosition = args.getInt(ARG_FORM_POSITION);
		mSectionPosition = args.getInt(ARG_SECTION_POSITION);
		mQuestionPosition = args.getInt(ARG_QUESTION_POSITION);

		Form form = FormFillingManager.getInstance().getGroup().getForms().get(mFormPosition);
		mQuestion = form.getSections().get(mSectionPosition).getQuestions().get(mQuestionPosition);

		mQuestionFilling = getFormSection().getQuestion(mQuestion.getCodeName());

		mViewModel = new QuestionViewModel(mQuestion);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FragmentQuestionBinding binding = FragmentQuestionBinding.inflate(inflater, container, false);
		binding.setViewModel(mViewModel);

		EditText notes = binding.notes;
		notes.setText(mQuestionFilling.getNotes());
		notes.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				mQuestionFilling.setNotes(s.toString());
			}
		});

		View view = binding.getRoot();
		ViewGroup content = binding.questionContent;

		List<FillingQuestionOption> optionFillings = mQuestionFilling.getOptions();

		List<FormQuestionOption> options = mQuestion.getOptions();
		boolean refreshFilling = options.size() != optionFillings.size();
		if (refreshFilling) {
			optionFillings.clear();
		}

		for (int i = 0; i < options.size(); i++) {
			FormQuestionOption option = options.get(i);
			FillingQuestionOption optionFilling;

			if (refreshFilling) {
				optionFilling = new FillingQuestionOption();
				optionFillings.add(optionFilling);
				optionFilling.setItems(new String[option.getItems().size()]);
			}
			else {
				optionFilling = optionFillings.get(i);
			}

			switch (option.getSelection().toLowerCase()) {
				case "single":
					content.addView(inflateSingleSelection(inflater, content, option, optionFilling));
					break;

				case "multiple":
					content.addView(inflateMultipleSelection(inflater, content, option, optionFilling));
					break;
			}
		}

		return view;
	}

	private View inflateSingleSelection(LayoutInflater inflater, ViewGroup parent,
			FormQuestionOption option, FillingQuestionOption optionFilling) {
		CellQuestionOptionSingleBinding binding = CellQuestionOptionSingleBinding.inflate(inflater, parent, false);
		binding.setViewModel(optionFilling);
		binding.setTitle(option.getTitle());

		List<String> items = option.getItems();
		String[] itemFillings = optionFilling.getItems();
		for (String item : items) {
			ItemRadioButtonBinding itemBinding = ItemRadioButtonBinding.inflate(inflater, binding.container, true);
			itemBinding.setText(item);
			itemBinding.setChecked(isChecked(itemFillings, item));
		}

		return binding.getRoot();
	}

	private View inflateMultipleSelection(LayoutInflater inflater, ViewGroup parent,
			FormQuestionOption option, FillingQuestionOption optionFilling) {
		CellQuestionOptionMultipleBinding binding = CellQuestionOptionMultipleBinding.inflate(inflater, parent, false);
		binding.setTitle(option.getTitle());

		List<String> items = option.getItems();
		String[] itemFillings = optionFilling.getItems();
		for (int i = 0; i < items.size(); i++) {
			String item = items.get(i);

			ItemCheckboxBinding itemBinding = ItemCheckboxBinding.inflate(inflater, binding.container, true);
			itemBinding.setId(i);
			itemBinding.setText(item);
			itemBinding.setChecked(isChecked(itemFillings, item));
		}

		return binding.getRoot();
	}

	private boolean isChecked(String[] itemFillings, String item) {
		boolean checked = false;
		for (String filling : itemFillings) {
			if (item.equals(filling)) {
				checked = true;
				break;
			}
		}
		return checked;
	}

	@Override
	public SectionQuestionsActivity getBaseActivity() {
		return (SectionQuestionsActivity) super.getBaseActivity();
	}

	public FillingFormSection getFormSection() {
		return getBaseActivity().getFormSection();
	}

}
