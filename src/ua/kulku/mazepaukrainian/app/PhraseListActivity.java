package ua.kulku.mazepaukrainian.app;

import java.util.ArrayList;
import java.util.List;

import ua.kulku.mazepaukrainian.R;
import ua.kulku.mazepaukrainian.data.Phrase;
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
			((TextView) rowView.findViewById(R.id.learn_word)).setText(phrase
					.getLearnWord());
			((TextView) rowView.findViewById(R.id.user_word)).setText(phrase
					.getUserWord());
			((TextView) rowView.findViewById(R.id.learn_phrase)).setText(phrase
					.getLearnPhrase());
			((TextView) rowView.findViewById(R.id.user_phrase)).setText(phrase
					.getUserPhrase());

			return rowView;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phrase_list);
		List<Phrase> phraseList = new ArrayList<Phrase>();
		phraseList.add(new Phrase("сказати", "say", null,
				"Ти можеш <b>сказати</b>, як туди дістатись?",
				"Could you <b>say</b> how to get there?", null));

		phraseList.add(new Phrase("життя", "life", null, "<b>Життя</b> чудове",
				"<b>Life</b> is good", null));

		phraseList.add(new Phrase("Будь здоровий", "God bless you", null, "",
				"", null));

		phraseList.add(new Phrase("стілець", "chair", null,
				"Цей <b>стілець</b> не дуже зручний",
				"This <b>chair</b> is not comfortable enough", null));

		PhraseAdapter phraseAdapter = new PhraseAdapter(this,
				R.layout.part_phrase_list_item, phraseList);
		setListAdapter(phraseAdapter);
	}
}
