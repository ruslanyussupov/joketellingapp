package com.example.ruslaniusupov.joketelling;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.Group;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ruslaniusupov.displayjoke.JokeActivity;
import com.example.ruslaniusupov.displayjoke.model.Joke;
import com.example.ruslaniusupov.jokes.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String BUNDLE_JOKES = "jokes";

    private List<Joke> mJokes;
    private final CountingIdlingResource mIdlingResource =
            new CountingIdlingResource("JokesLoader");


    @BindView(R.id.loading_pb)ProgressBar mLoadingPb;
    @BindView(R.id.content)Group mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {

            new JokesLoaderAsyncTask().execute();

        } else {

            mJokes = savedInstanceState.getParcelableArrayList(BUNDLE_JOKES);

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_JOKES, (ArrayList<? extends Parcelable>) mJokes);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.tell_joke_btn)
    public void tellJoke(View view) {

        if (mJokes != null && mJokes.size() != 0) {

            int length = mJokes.size();

            Random random = new Random();
            int index = random.nextInt(length-1);
            Joke joke = mJokes.get(index);

            Intent launchJokeActivity = new Intent(this, JokeActivity.class);
            launchJokeActivity.putExtra(JokeActivity.EXTRA_JOKE, joke);
            startActivity(launchJokeActivity);

        } else {
            Toast.makeText(this, getString(R.string.no_joke_msg), Toast.LENGTH_SHORT).show();
        }

    }

    private class JokesLoaderAsyncTask extends AsyncTask<Void, Void, List<Joke>> {

        @Override
        protected List<Joke> doInBackground(Void... voids) {
            showProgressBar();
            return Utils.getJokes(DataProvider.getJokesJson());
        }

        @Override
        protected void onPostExecute(List<Joke> jokes) {
            hideProgressBar();
            mJokes = jokes;
        }
    }

    private void showProgressBar() {
        mContent.setVisibility(View.GONE);
        mLoadingPb.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mLoadingPb.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }

    @VisibleForTesting
    public CountingIdlingResource getIdlingResource() {
        return mIdlingResource;
    }

}