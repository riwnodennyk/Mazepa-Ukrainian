package ua.kulku.mazepaukrainian.data;

import android.provider.BaseColumns;
import android.text.Html;
import android.text.Spanned;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Phrase {
	public static final String LEARN_WORD = "LearnWord";
	public static final String LEARN_PHRASE = "LearnPhrase";
	public static final String WORD_SOUND = "WordSound";
	public static final String USER_WORD = "UserWord";
	public static final String USER_PHRASE = "UserPhrase";
	public static final String PHRASE_SOUND = "PhraseSound";

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

	@DatabaseField(generatedId = true, columnName = BaseColumns._ID)
	private int id;
	@DatabaseField(columnName = LEARN_WORD)
	private String mLearnWord;
	@DatabaseField(columnName = USER_WORD)
	private String mUserWord;
	@DatabaseField(columnName = WORD_SOUND)
	private String mWordSound; // sound file name

	@DatabaseField(columnName = LEARN_PHRASE)
	private String mLearnPhrase;
	@DatabaseField(columnName = USER_PHRASE)
	private String mUserPhrase;
	@DatabaseField(columnName = PHRASE_SOUND)
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
