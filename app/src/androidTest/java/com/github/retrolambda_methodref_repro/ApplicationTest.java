package com.github.retrolambda_methodref_repro;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAsyncTaskBuilder() {
        AsyncTaskBuilder.<Integer, Integer>asyncTask()
                .doInBackground(observer -> {
                    observer.progressChanged(new Integer[]{100});
                    return 1;
                })
                .build()
                .execute();
    }
}
