package info.nivaldobondanca.techform.form.summary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormQuestion;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.content.FormContentObserver;
import info.nivaldobondanca.techform.databinding.FragmentFormSummaryBinding;
import info.nivaldobondanca.techform.group.details.GroupDetailsActivity;
import info.nivaldobondanca.techform.question.SectionQuestionsActivity;
import info.nivaldobondanca.techform.widget.BasicListAdapter;

import static com.google.common.base.Objects.firstNonNull;

/**
 * @author Nivaldo Bondança
 */
public class FormSummaryFragment extends Fragment implements FormContentObserver, AdapterView.OnItemClickListener {

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
		FragmentFormSummaryBinding binding = FragmentFormSummaryBinding.inflate(inflater, container, false);
		binding.setViewModel(this);

		return binding.getRoot();
	}

	@Override
	public void onChange(GroupDetailsActivity activity, List<Form> newData) {
		mAdapter.changeData(activity.getForm().getSections());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		GroupDetailsActivity activity = (GroupDetailsActivity) getActivity();
		Intent intent = SectionQuestionsActivity.newInstance(activity, activity.getForm(), position);
		startActivity(intent);
	}

	public ListAdapter getAdapter() {
		return mAdapter;
	}

	public boolean isEmpty() {
		return mAdapter.isEmpty();
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
			String summary = getResources().getQuantityString(R.plurals.question, count, count);

			((TextView) view.findViewById(R.id.section_summary)).setText(summary);
		}
	}
}
