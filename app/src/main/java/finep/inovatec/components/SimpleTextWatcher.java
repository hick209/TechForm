package finep.inovatec.components;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author Nivaldo Bondança
 */
public abstract class SimpleTextWatcher implements TextWatcher {
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
	}
}
