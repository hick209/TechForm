package info.nivaldobondanca.techform.content;

import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.techform.group.details.GroupDetailsActivity;

/**
 * @author Nivaldo Bondança
 */
public interface FormContentObserver {
	void onChange(GroupDetailsActivity activity, List<Form> newData);
}
