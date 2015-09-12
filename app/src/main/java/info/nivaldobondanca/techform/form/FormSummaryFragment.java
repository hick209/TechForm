package info.nivaldobondanca.techform.form;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.content.FormContentObserver;
import info.nivaldobondanca.techform.group.GroupDetailsActivity;
import info.nivaldobondanca.techform.question.SectionQuestionsActivity;
import info.nivaldobondanca.techform.widget.BasicListAdapter;

import static com.google.common.base.Objects.firstNonNull;

/**
 * @author Nivaldo Bondança
 */
public class FormSummaryFragment extends Fragment implements FormContentObserver {

	public static FormSummaryFragment instantiate() {
		Bundle args = new Bundle();

		FormSummaryFragment fragment = new FormSummaryFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private SectionSummaryAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GroupDetailsActivity activity = (GroupDetailsActivity) getActivity();
		activity.registerFormDataObserver(this);

		mAdapter = new SectionSummaryAdapter();
		onChange(activity, activity.getData());

		setHasOptionsMenu(true);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		((GroupDetailsActivity) getActivity()).unregisterFormDataObserver(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_form_summary, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ListView formList = (ListView) view.findViewById(android.R.id.list);
		formList.setAdapter(mAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_form_summary, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.form_start:
				GroupDetailsActivity activity = (GroupDetailsActivity) getActivity();
				// TODO figure out a way to navigate through the questions
				Intent intent = SectionQuestionsActivity.newInstance(activity, activity.getForm(), 0);
				startActivity(intent);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onChange(GroupDetailsActivity activity, List<Form> newData) {
		mAdapter.changeData(activity.getForm().getSections());
	}

	private class SectionSummaryAdapter extends BasicListAdapter<FormSection> {

		public SectionSummaryAdapter() {
			super(getActivity());
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}

		@Override
		protected int getItemLayoutResource() {
			return R.layout.item_section_summary;
		}

		@Override
		protected void bindView(View view, FormSection item) {
			String code = firstNonNull(item.getCodeName(), String.valueOf(item.getCode()));
			((TextView) view.findViewById(R.id.section_code)).setText(code);

			((TextView) view.findViewById(R.id.section_name)).setText(item.getName());

			int count = firstNonNull(item.getQuestions(),
					Collections.<FormQuestion>emptyList()).size();
			CharSequence text = getResources().getQuantityText(R.plurals.question, count);
			String summary = String.format("%d %s", count, text);
			((TextView) view.findViewById(R.id.section_summary)).setText(summary);
		}
	}
}
