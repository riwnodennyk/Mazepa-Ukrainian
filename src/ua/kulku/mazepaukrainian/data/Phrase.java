
package ua.kulku.mazepaukrainian.data;

import java.util.Date;

import android.provider.BaseColumns;
import android.text.Html;
import android.text.Spanned;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Phrase {
    public enum CardLearningState
    {
        NOT_SHOWN, IN_PROGRESS, LEARNED, DECLINED
    }

    public static final String LEARN_WORD = "learn_word";
    public static final String LEARN_PHRASE = "learn_phrase";
    public static final String WORD_SOUND = "word_sound";
    public static final String USER_WORD = "user_word";
    public static final String USER_PHRASE = "user_phrase";
    public static final String PHRASE_SOUND = "phrase_sound";
    public static final String STATE = "state";
    public static final String CHANGE_STATE_DATE = "change_state_date";

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

    @DatabaseField(columnName = STATE, dataType = DataType.ENUM_INTEGER)
    private CardLearningState mState;

    @DatabaseField(columnName = CHANGE_STATE_DATE)
    private Date mChangeStateDate;

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

    public Date getChangeStateDate() {
        return mChangeStateDate;
    }

    public Spanned getLearnPhrase() {
        return Html.fromHtml(mLearnPhrase);
    }

    public Spanned getLearnWord() {
        return Html.fromHtml(mLearnWord);
    }

    public String getPhraseSound() {
        return mPhraseSound;
    }

    public CardLearningState getState() {
        return mState;
    }

    public Spanned getUserPhrase() {
        return Html.fromHtml(mUserPhrase);
    }

    public Spanned getUserWord() {
        return Html.fromHtml(mUserWord);
    }

    public String getWordSound() {
        return mWordSound;
    }

    public void setChangeStateDate(Date changeStateDate) {
        this.mChangeStateDate = changeStateDate;
    }

    public void setLearnPhrase(String mLearnPhrase) {
        this.mLearnPhrase = mLearnPhrase;
    }

    public void setLearnWord(String mLearnWord) {
        this.mLearnWord = mLearnWord;
    }

    public void setmPhraseSound(String mPhraseSound) {
        this.mPhraseSound = mPhraseSound;
    }

    public void setState(CardLearningState state) {
        this.mState = state;
    }

    public void setUserPhrase(String mUserPhrase) {
        this.mUserPhrase = mUserPhrase;
    }

    public void setUserWord(String mUserWord) {
        this.mUserWord = mUserWord;
    }

    public void setWordSound(String mWordSound) {
        this.mWordSound = mWordSound;
    }
}
