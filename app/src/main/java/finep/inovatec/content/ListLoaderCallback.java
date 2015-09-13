package finep.inovatec.content;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import finep.inovatec.common.BasicListAdapter;

/**
 * @author Nivaldo Bondança
 */
public abstract class ListLoaderCallback<T> implements LoaderManager.LoaderCallbacks<List<T>> {

	private BasicListAdapter<T> mAdapter;
	private Context             mContext;

	public ListLoaderCallback(BasicListAdapter<T> adapter, Context context) {
		mAdapter = adapter;
		mContext = context;
	}

	@Override
	public Loader<List<T>> onCreateLoader(int id, Bundle args) {
		return new ApiLoader<>(mContext, getCaller());
	}

	@Override
	public void onLoadFinished(Loader<List<T>> loader, List<T> data) {
		mAdapter.changeData(data);
	}

	@Override
	public void onLoaderReset(Loader<List<T>> loader) {
		mAdapter.changeData(null);
	}

	protected abstract ApiCall<List<T>> getCaller();
}
