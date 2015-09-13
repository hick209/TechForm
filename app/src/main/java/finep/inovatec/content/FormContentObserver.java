package finep.inovatec.content;

import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import finep.inovatec.group.details.GroupDetailsActivity;

/**
 * @author Nivaldo Bondança
 */
public interface FormContentObserver {
	void onChange(GroupDetailsActivity activity, List<Form> newData);
}
