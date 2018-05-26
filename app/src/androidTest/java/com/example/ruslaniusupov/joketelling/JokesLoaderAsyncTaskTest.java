package com.example.ruslaniusupov.joketelling;


import com.example.ruslaniusupov.displayjoke.model.Joke;

import org.junit.Assert;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.example.ruslaniusupov.joketelling.JokesLoaderAsyncTask.*;

// Solution from https://stackoverflow.com/a/3802487/7487313
public class JokesLoaderAsyncTaskTest {

    @Test
    public void resultIsNotNull() throws InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new JokesLoaderAsyncTask(new WeakReference<OnLoadedListener>(new OnLoadedListener() {
            @Override
            public void OnLoaded(List<Joke> jokes) {
                Assert.assertNotNull(jokes);
                countDownLatch.countDown();
            }
        })).execute();

        countDownLatch.await();

    }

}