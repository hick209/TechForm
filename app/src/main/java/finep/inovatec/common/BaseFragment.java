package finep.inovatec.common;

import android.support.v4.app.Fragment;

import finep.inovatec.app.BaseActivity;

/**
 * @author Nivaldo Bondança
 */
public class BaseFragment extends Fragment {

	public BaseActivity getBaseActivity() {
		return (BaseActivity) super.getActivity();
	}

}
