package ua.kulku.mazepaukrainian.ui;

import ua.kulku.mazepaukrainian.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SelectionCallback implements ActionMode.Callback {

	private final Context mContext;
	private final Intent mGTranslateIntent;
	private final TextView mSelectableView;
	private final View mCardTranslateButton;

	public SelectionCallback(Context context,
			final TextView selectableTextView, String sourceLanguage,
			String targetLanguage, View cardTranslateButton) {
		if (!selectableTextView.isTextSelectable()) {
			throw new IllegalStateException(
					"TextView's attribute 'textSelectable' should be set to true.");
		}
		this.mContext = context;
		this.mSelectableView = selectableTextView;
		this.mCardTranslateButton = cardTranslateButton;

		this.mGTranslateIntent = new Intent();

		mGTranslateIntent.setAction(Intent.ACTION_VIEW);
		// mGTranslateIntent.putExtra("key_text_output", "");
		mGTranslateIntent.putExtra("key_language_from", sourceLanguage);
		mGTranslateIntent.putExtra("key_language_to", targetLanguage);
		// mGTranslateIntent.putExtra("key_suggest_translation", "");
		mGTranslateIntent.putExtra("key_from_floating_window", true);
		mGTranslateIntent
				.setComponent(new ComponentName(
						"com.google.android.apps.translate",
						"com.google.android.apps.translate.translation.TranslateActivity"));
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.selectable_card_actionbar, menu);
		menu.removeItem(android.R.id.selectAll);

		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		mCardTranslateButton.setVisibility(View.VISIBLE);

		mCardTranslateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startGoogleTranslate();
			}
		});
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_google_translate:
			startGoogleTranslate();
			return true;
		default:
			return false;
		}
	}

	private void startGoogleTranslate() {
		mCardTranslateButton.setVisibility(View.GONE);

		int start = mSelectableView.getSelectionStart();
		int end = mSelectableView.getSelectionEnd();
		CharSequence selectedText = mSelectableView.getText().subSequence(
				start, end);

		mGTranslateIntent.putExtra("key_text_input", selectedText.toString());
		mContext.startActivity(mGTranslateIntent);
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		mCardTranslateButton.setVisibility(View.GONE);
	}
}
