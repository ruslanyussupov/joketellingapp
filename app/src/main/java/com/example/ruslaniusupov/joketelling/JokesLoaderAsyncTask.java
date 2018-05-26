package com.example.ruslaniusupov.joketelling;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.ruslaniusupov.backend.myApi.MyApi;
import com.example.ruslaniusupov.displayjoke.model.Joke;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class JokesLoaderAsyncTask extends AsyncTask<Void, Void, List<Joke>> {

    public interface OnLoadedListener {
        void OnLoaded(List<Joke> jokes);
    }

    private static final String LOG_TAG = JokesLoaderAsyncTask.class.getSimpleName();

    private final WeakReference<OnLoadedListener> listenerWeakReference;
    private static MyApi mMyApiService;


    public JokesLoaderAsyncTask(WeakReference<OnLoadedListener> listenerWeakReference) {
        this.listenerWeakReference = listenerWeakReference;
    }

    @Override
    protected List<Joke> doInBackground(Void... voids) {

        if (mMyApiService == null) {
            // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            mMyApiService = builder.build();

        }

        String json = null;
        List<Joke> result = null;

        try {
            json =  mMyApiService.getJokesJson().execute().getJson();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can't get data from API Service. " + e.getMessage());
        }

        if (!TextUtils.isEmpty(json)) {
            result = Utils.getJokes(json);
        }

        return result;

    }

    @Override
    protected void onPostExecute(List<Joke> jokes) {
        listenerWeakReference.get().OnLoaded(jokes);
    }

}
