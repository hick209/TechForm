package finep.inovatec.home;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

import finep.inovatec.R;
import finep.inovatec.common.BasicListAdapter;
import finep.inovatec.data.Filling;

/**
 * @author Nivaldo Bondança
 */
public class FillingAdapter extends BasicListAdapter<Filling> {

	public FillingAdapter(Context context) {
		super(context);
	}

	@Override
	protected void bindView(View view, Filling item) {
		// TODO
	}

	@Override
	protected @LayoutRes int getItemLayoutResource() {
		return R.layout.item_filling;
	}

	@Override
	public long getItemId(int position) {
		// TODO
		return 0;
	}
}
