package ua.kulku.mazepaukrainian.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.kulku.mazepaukrainian.R;
import ua.kulku.mazepaukrainian.data.Phrase;
import ua.kulku.mazepaukrainian.ui.SelectionCallback;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class PhraseListActivity extends ListActivity {
	static class PhraseAdapter extends ArrayAdapter<Phrase> {

		// TODO optimize with reuse
		private final int mTextViewResourceId;
		private final Context mContext;
		private final List<Phrase> mObjects;

		public PhraseAdapter(Context context, int textViewResourceId,
				List<Phrase> objects) {
			super(context, textViewResourceId, objects);
			this.mContext = context;
			this.mTextViewResourceId = textViewResourceId;
			this.mObjects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(mTextViewResourceId, parent, false);
			Phrase phrase = mObjects.get(position);

			View cardTranslateButton = (rowView
					.findViewById(R.id.card_translate_btn));

			TextView field;
			field = ((TextView) rowView.findViewById(R.id.learn_word));
			field.setText(phrase.getLearnWord());
			field.setCustomSelectionActionModeCallback(new SelectionCallback(
					mContext, field, UKRAINIAN, ENGLISH, cardTranslateButton));

			field = ((TextView) rowView.findViewById(R.id.user_word));
			field.setText(phrase.getUserWord());
			field.setCustomSelectionActionModeCallback(new SelectionCallback(
					mContext, field, ENGLISH, UKRAINIAN, cardTranslateButton));

			field = ((TextView) rowView.findViewById(R.id.learn_phrase));
			field.setText(phrase.getLearnPhrase());
			field.setCustomSelectionActionModeCallback(new SelectionCallback(
					mContext, field, UKRAINIAN, ENGLISH, cardTranslateButton));

			field = ((TextView) rowView.findViewById(R.id.user_phrase));
			field.setText(phrase.getUserPhrase());
			field.setCustomSelectionActionModeCallback(new SelectionCallback(
					mContext, field, ENGLISH, UKRAINIAN, cardTranslateButton));

			return rowView;
		}
	}

	private static final String UKRAINIAN = "uk";
	private static final String ENGLISH = "en";
	private static final int DEFAULT_SHOW_MORE_PHRASES_NUMBER = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phrase_list);
		List<Phrase> phraseList = new ArrayList<Phrase>();
		try {
			Dao<Phrase, ?> phraseDao = DaoManager.createDao(
					((MazepaApplication) getApplication()).getDb()
							.getConnectionSource(), Phrase.class);

			phraseList = phraseDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		View footer = getLayoutInflater().inflate(
				R.layout.part_phrase_list_footer, null);
		// footer.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(PhraseListActivity.this, "foo",
		// Toast.LENGTH_SHORT).show();
		//
		// }
		// });
		getListView().addFooterView(footer, null, true);

		PhraseAdapter phraseAdapter = new PhraseAdapter(this,
				R.layout.part_phrase_list_item, phraseList);
		setListAdapter(phraseAdapter);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		boolean isFooter = (position == l.getCount() - 1);
		if (isFooter) {
			showMorePhrases();
		}
		super.onListItemClick(l, v, position, id);
	}

	private void showMorePhrases() {
		showMorePhrases(DEFAULT_SHOW_MORE_PHRASES_NUMBER);
	}

	private void showMorePhrases(int cardsNumber) {
		// TODO Auto-generated method stub
	}
}
