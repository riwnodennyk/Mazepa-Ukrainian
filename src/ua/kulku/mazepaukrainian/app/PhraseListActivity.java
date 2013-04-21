package ua.kulku.mazepaukrainian.app;

import java.util.ArrayList;
import java.util.List;

import ua.kulku.mazepaukrainian.R;
import ua.kulku.mazepaukrainian.data.Phrase;
import ui.SelectionCallback;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phrase_list);
		List<Phrase> phraseList = new ArrayList<Phrase>();
		phraseList.add(new Phrase("балакати", "say", null,
				"Головною міською газетою стало україномовне «Нове життя»",
				"Could you <b>say</b> how to get there?", null));

		phraseList
				.add(new Phrase(
						"життя",
						"life",
						null,
						"Якщо брати за відлік історії міста дозвіл на закладення заводу",
						"<b>Life</b> is good", null));

		phraseList.add(new Phrase("хвала Йсу", "God bless you", null,
				"Спочатку Луганськ територіально ділився на вулиці та лінії.",
				"Die young", null));

		phraseList.add(new Phrase("Адолф Гітлер", "chair", null,
				"Тож їхня кількість скоротилася вдвічі",
				"This <b>chair</b> is not comfortable enough", null));

		PhraseAdapter phraseAdapter = new PhraseAdapter(this,
				R.layout.part_phrase_list_item, phraseList);
		setListAdapter(phraseAdapter);
	}
}
