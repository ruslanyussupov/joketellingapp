package com.example.ruslaniusupov.joketelling;

import android.os.AsyncTask;

import com.example.ruslaniusupov.displayjoke.model.Joke;
import com.example.ruslaniusupov.jokes.DataProvider;

import java.lang.ref.WeakReference;
import java.util.List;

public class JokesLoaderAsyncTask extends AsyncTask<Void, Void, List<Joke>> {

    public interface OnLoadedListener {
        void OnLoaded(List<Joke> jokes);
    }

    private final WeakReference<OnLoadedListener> listenerWeakReference;

    public JokesLoaderAsyncTask(WeakReference<OnLoadedListener> listenerWeakReference) {
        this.listenerWeakReference = listenerWeakReference;
    }

    @Override
    protected List<Joke> doInBackground(Void... voids) {
        return Utils.getJokes(DataProvider.getJokesJson());
    }

    @Override
    protected void onPostExecute(List<Joke> jokes) {
        listenerWeakReference.get().OnLoaded(jokes);
    }

}
