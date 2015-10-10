package finep.inovatec.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v4.util.ArrayMap;

import java.util.List;
import java.util.Map;

import finep.inovatec.R;
import finep.inovatec.common.BasicListAdapter;
import finep.inovatec.components.TwoLineAvatar;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ItemFillingBinding;
import finep.inovatec.util.Utils;

/**
 * @author Nivaldo Bondança
 */
public class FillingAdapter extends BasicListAdapter<Filling, ItemFillingBinding> {

	private Map<Filling, TwoLineAvatar> mViewModels = new ArrayMap<>();

	private Drawable mCompleteDrawable;
	private Drawable mIncompleteDrawable;

	public FillingAdapter(Context context) {
		super(context);

		mCompleteDrawable = Utils.getDrawable(context, R.drawable.avatar_filling_complete);
		mIncompleteDrawable = Utils.getDrawable(context, R.drawable.avatar_filling_incomplete);
	}

	@Override
	public List<Filling> changeData(List<Filling> data) {
		mViewModels.clear();
		for (Filling item : data) {
			mViewModels.put(item, new TwoLineAvatar(
					item.getEndingTimestamp() > 0 ?
							mCompleteDrawable :
							mIncompleteDrawable,
					item.getCode(),
					item.getAddress()
			));
		}

		return super.changeData(data);
	}

	@Override
	protected void bindView(ItemFillingBinding binding, Filling item) {
		binding.setViewModel(mViewModels.get(item));
	}

	@Override
	protected @LayoutRes int getItemLayoutResource() {
		return R.layout.item_filling;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
