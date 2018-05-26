package com.example.ruslaniusupov.displayjoke;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ruslaniusupov.displayjoke.model.Joke;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "extra_joke";
    private static final String BUNDLE_JOKE = "bundle_joke";

    private Joke mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {

            Intent intent = getIntent();

            if (intent.hasExtra(EXTRA_JOKE)) {
                mJoke = intent.getParcelableExtra(EXTRA_JOKE);
                updateUi();
            }

        } else {

            mJoke = savedInstanceState.getParcelable(BUNDLE_JOKE);
            updateUi();

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_JOKE, mJoke);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void updateUi() {

        if (mJoke == null) {
            return;
        }

        TextView categoryTv = findViewById(R.id.category_tv);
        TextView jokeTv = findViewById(R.id.joke_tv);
        RatingBar ratingBar = findViewById(R.id.rating);

        String category = mJoke.getCategory();
        String body = mJoke.getBody();

        if (!TextUtils.isEmpty(category)) {
            categoryTv.setText(category);
        }

        if (!TextUtils.isEmpty(body)) {
            jokeTv.setText(body);
        }

        ratingBar.setRating(mJoke.getRating());

    }

}
