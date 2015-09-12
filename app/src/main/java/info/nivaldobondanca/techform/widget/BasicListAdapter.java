package info.nivaldobondanca.techform.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Objects.firstNonNull;

/**
 * @author Nivaldo Bondança
 */
public abstract class BasicListAdapter<E> extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<E>        mData;

	public BasicListAdapter(Context context) {
		this(context, null);
	}

	public BasicListAdapter(Context context, List<E> data) {
		mInflater = LayoutInflater.from(context);
		mData = data;
	}

	public List<E> changeData(List<E> data) {
		List<E> oldData = this.mData;
		this.mData = data;

		// Request a reload
		notifyDataSetChanged();

		return oldData;
	}

	@Override
	public int getCount() {
		List<E> list = firstNonNull(mData, Collections.<E>emptyList());
		return list.size();
	}

	@Override
	public E getItem(int position) {
		return mData.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(getItemLayoutResource(), parent, false);
		}

		bindView(convertView, getItem(position));

		return convertView;
	}

	protected abstract void bindView(View view, E item);

	protected abstract @LayoutRes int getItemLayoutResource();
}
