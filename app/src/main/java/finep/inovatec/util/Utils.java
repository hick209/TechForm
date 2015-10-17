package finep.inovatec.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;

import java.util.List;

import finep.inovatec.data.FillingForm;
import finep.inovatec.data.FillingFormSection;
import finep.inovatec.data.FillingQuestion;
import finep.inovatec.data.FillingQuestionOption;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestionOption;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;

/**
 * @author Nivaldo Bondança
 */
public class Utils {

	public static Drawable getDrawable(Context context, @DrawableRes int resource) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return context.getDrawable(resource);
		}
		else {
			return context.getResources().getDrawable(resource);
		}
	}

	public static boolean isSectionComplete(FormSection section, FillingFormSection fillingSection) {
		List<FillingQuestion> fillingQuestions = fillingSection.getQuestions();
		List<FormQuestion> questions = section.getQuestions();
		for (FormQuestion question : questions) {
			FillingQuestion fillingQuestion = fillingSection.getQuestion(question.getCodeName());

			List<FillingQuestionOption> optionFillings = fillingQuestion.getOptions();
			List<FormQuestionOption> options = question.getOptions();

			if (options.size() != optionFillings.size()) {
				return false;
			}

			for (int i = 0; i < options.size(); i++) {
				FormQuestionOption option = options.get(i);
				FillingQuestionOption optionFilling = optionFillings.get(i);

				if ("single".equalsIgnoreCase(option.getSelection())) {
					if (optionFilling.getItems().isEmpty()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static boolean isFormComplete(Form form, FillingForm fillingForm) {
		List<FormSection> sections = form.getSections();
		for (FormSection section : sections) {
			isSectionComplete(section, fillingForm.getSection(section.getCodeName()));
		}

		return true;
	}
}
