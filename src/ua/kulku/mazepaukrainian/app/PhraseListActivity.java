
package ua.kulku.mazepaukrainian.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import ua.kulku.mazepaukrainian.R;
import ua.kulku.mazepaukrainian.data.Phrase;
import ua.kulku.mazepaukrainian.data.Phrase.CardLearningState;
import ua.kulku.mazepaukrainian.db.DbHelper;
import ua.kulku.mazepaukrainian.ui.SelectionCallback;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;

public class PhraseListActivity extends ListActivity {
    class PhraseAdapter extends CursorAdapter {

        // TODO optimize with reuse
        private final int mTextViewResourceId;
        private final Context mContext;
        private Integer mLearnWordIndex;
        private Integer mLearnPhraseIndex;
        private Integer mUserWordIndex;
        private Integer mUserPhraseIndex;

        // private final List<Phrase> mObjects;

        public PhraseAdapter(Context context, int textViewResourceId,
                Cursor cursor) {
            super(context, cursor);
            this.mContext = context;
            this.mTextViewResourceId = textViewResourceId;
            // this.mObjects = objects;
            if (mLearnWordIndex == null) {
                mLearnWordIndex = cursor.getColumnIndex(Phrase.LEARN_WORD);
                mLearnPhraseIndex = cursor.getColumnIndex(Phrase.LEARN_PHRASE);
                mUserWordIndex = cursor.getColumnIndex(Phrase.USER_WORD);
                mUserPhraseIndex = cursor.getColumnIndex(Phrase.USER_PHRASE);
            }
        }

        @Override
        public void bindView(View rowView, Context arg1, Cursor cursor) {

            View cardTranslateButton = (rowView
                    .findViewById(R.id.card_translate_btn));

            TextView field;
            field = ((TextView) rowView.findViewById(R.id.learn_word));
            field.setText(cursor.getString(mLearnWordIndex));
            field.setCustomSelectionActionModeCallback(new SelectionCallback(
                    mContext, field, UKRAINIAN, ENGLISH, cardTranslateButton));

            field = ((TextView) rowView.findViewById(R.id.user_word));
            field.setText(cursor.getString(mUserWordIndex));
            field.setCustomSelectionActionModeCallback(new SelectionCallback(
                    mContext, field, ENGLISH, UKRAINIAN, cardTranslateButton));

            field = ((TextView) rowView.findViewById(R.id.learn_phrase));
            field.setText(Html.fromHtml(cursor.getString(mLearnPhraseIndex)));
            field.setCustomSelectionActionModeCallback(new SelectionCallback(
                    mContext, field, UKRAINIAN, ENGLISH, cardTranslateButton));

            field = ((TextView) rowView.findViewById(R.id.user_phrase));
            field.setText(Html.fromHtml(cursor.getString(mUserPhraseIndex)));
            field.setCustomSelectionActionModeCallback(new SelectionCallback(
                    mContext, field, ENGLISH, UKRAINIAN, cardTranslateButton));

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(mTextViewResourceId, parent, false);
            // Animation animation = AnimationUtils.loadAnimation(context,
            // R.anim.left_up_animation);
            // rowView.startAnimation(animation);
            return rowView;
        }
    }

    private static final String UKRAINIAN = "uk";
    private static final String ENGLISH = "en";

    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_list);

        List<Phrase> phraseList = new ArrayList<Phrase>();
        // try {
        Dao<Phrase, ?> phraseDao;
        try {
            phraseDao = DaoManager.createDao(
                    ((MazepaApplication) getApplication()).getDb()
                            .getConnectionSource(), Phrase.class);

            //
            // phraseList = phraseDao.queryForAll();

            // QueryBuilder<Phrase, String> qb = fooDao.queryBuilder();
            // qb.where()...;
            QueryBuilder<Phrase, ?> qb = phraseDao.queryBuilder();
            qb.where().eq(Phrase.STATE, CardLearningState.IN_PROGRESS);
            // when you are done, prepare your query and build an iterator
            CloseableIterator<Phrase> iterator = phraseDao.iterator(qb.prepare());
            try {
                // get the raw results which can be cast under Android
                AndroidDatabaseResults results = (AndroidDatabaseResults) iterator
                        .getRawResults();
                mCursor = results.getRawCursor();

                View footer = getLayoutInflater().inflate(
                        R.layout.part_phrase_list_footer, null);
                getListView().addFooterView(footer, null, true);

                PhraseAdapter phraseAdapter = new PhraseAdapter(this,
                        R.layout.part_phrase_list_item, mCursor);
                setListAdapter(phraseAdapter);
            } finally {
                // iterator.closeQuietly();
            }
            // } catch (SQLException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static final long DEFAULT_SHOW_MORE_PHRASES_NUMBER = 3;

    public int setMoreInProgressCards() {
        try {
            int addedCards = TransactionManager.callInTransaction(
                    ((MazepaApplication) getApplication()).getDb()
                            .getConnectionSource(),
                    new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            int addedCards = 0;
                            final Dao<Phrase, ?> phraseDao = DaoManager.createDao(DbHelper
                                    .getInstance()
                                    .getConnectionSource(), Phrase.class);
                            List<Phrase> phrasesToSetAsInProgress = phraseDao.queryBuilder()
                                    .limit(DEFAULT_SHOW_MORE_PHRASES_NUMBER).where()
                                    .eq(Phrase.STATE, CardLearningState.NOT_SHOWN).query();

                            for (Phrase phrase : phrasesToSetAsInProgress) {
                                phrase.setState(CardLearningState.IN_PROGRESS);
                                addedCards += phraseDao.update(phrase);
                            }
                            return addedCards;
                        }
                    });
            return addedCards;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        boolean isFooter = (position == l.getCount() - 1);
        if (isFooter) {
            int cardsAdded = setMoreInProgressCards();
            mCursor.requery();
            getListView().smoothScrollToPositionFromTop(
                    mCursor.getCount() - cardsAdded, 0);
        }
        super.onListItemClick(l, v, position, id);
    }

}
