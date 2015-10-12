package finep.inovatec.app;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import finep.inovatec.R;
import finep.inovatec.common.ViewModel;

/**
 * @author Nivaldo Bondança
 */
public class BaseActivity extends AppCompatActivity {

	public Toolbar setupToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		return toolbar;
	}

	public ViewModel get(String key) {
		return getTechFormApplication().get(key);
	}

	public void put(String key, ViewModel viewModel) {
		getTechFormApplication().put(key, viewModel);
	}

	private TechFormApplication getTechFormApplication() {
		return (TechFormApplication) getApplication();
	}

}
