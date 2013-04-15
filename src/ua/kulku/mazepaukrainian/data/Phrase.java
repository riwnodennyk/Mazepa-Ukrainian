package ua.kulku.mazepaukrainian.data;

import java.io.File;

import android.text.Html;
import android.text.Spanned;

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

	public File getmWordSound() {
		return mWordSound;
	}

	public void setmWordSound(File mWordSound) {
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

	public File getmPhraseSound() {
		return mPhraseSound;
	}

	public void setmPhraseSound(File mPhraseSound) {
		this.mPhraseSound = mPhraseSound;
	}

	private String mLearnWord;
	private String mUserWord;
	private File mWordSound;

	private String mLearnPhrase;
	private String mUserPhrase;
	private File mPhraseSound;

	public Phrase(String learnWord, String userWord, File wordSound,
			String learnPhrase, String userPhrase, File phraseSound) {
		super();
		this.mLearnWord = learnWord;
		this.mUserWord = userWord;
		this.mWordSound = wordSound;

		this.mLearnPhrase = learnPhrase;
		this.mUserPhrase = userPhrase;
		this.mPhraseSound = phraseSound;
	}
}
