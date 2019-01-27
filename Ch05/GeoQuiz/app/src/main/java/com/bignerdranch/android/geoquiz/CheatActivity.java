package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String TAG = "CheatActivity";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String KEY_ANSWER_SHOWN = "key_answer_shown";
    private static final String KEY_ANSWER_IS_TRUE = "key_answer_is_true";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerBtn;
    private boolean mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       initUI();

        if (savedInstanceState != null) {
            mShowAnswer = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false);
            setAnswerShownResult(mShowAnswer);

            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_IS_TRUE, false);
            updateAnswer();
        }
    }

    private void initUI() {
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerBtn = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer();
                setAnswerShownResult(true);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ANSWER_IS_TRUE, mAnswerIsTrue);
        outState.putBoolean(KEY_ANSWER_SHOWN, mShowAnswer);
    }

    private void updateAnswer() {
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        mShowAnswer = isAnswerShown;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
