package ua.kulku.mazepaukrainian.data;

import android.text.Html;
import android.text.Spanned;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Phrase {
	public Spanned getLearnWord() {
		return Html.fromHtml(mLearnWord);
	}

	public void setmLearnWord(String mLearnWord) {
		this.mLearnWord = mLearnWord;
	}

	public Spanned getUserWord() {
		return Html.fromHtml(mUserWord);
	}

	public void setmUserWord(String mUserWord) {
		this.mUserWord = mUserWord;
	}

	public String getmWordSound() {
		return mWordSound;
	}

	public void setmWordSound(String mWordSound) {
		this.mWordSound = mWordSound;
	}

	public Spanned getLearnPhrase() {
		return Html.fromHtml(mLearnPhrase);
	}

	public void setmLearnPhrase(String mLearnPhrase) {
		this.mLearnPhrase = mLearnPhrase;
	}

	public Spanned getUserPhrase() {
		return Html.fromHtml(mUserPhrase);
	}

	public void setmUserPhrase(String mUserPhrase) {
		this.mUserPhrase = mUserPhrase;
	}

	public String getmPhraseSound() {
		return mPhraseSound;
	}

	public void setmPhraseSound(String mPhraseSound) {
		this.mPhraseSound = mPhraseSound;
	}

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String mLearnWord;
	@DatabaseField
	private String mUserWord;
	@DatabaseField
	private String mWordSound; // sound file name

	@DatabaseField
	private String mLearnPhrase;
	@DatabaseField
	private String mUserPhrase;
	@DatabaseField
	private String mPhraseSound; // sound file name

	public Phrase() {
	}

	public Phrase(String learnWord, String userWord, String wordSound,
			String learnPhrase, String userPhrase, String phraseSound) {
		super();
		this.mLearnWord = learnWord;
		this.mUserWord = userWord;
		this.mWordSound = wordSound;

		this.mLearnPhrase = learnPhrase;
		this.mUserPhrase = userPhrase;
		this.mPhraseSound = phraseSound;
	}
}
