// Copyright 2015 Denis Itskovich
// Refer to LICENSE.txt for license details
package com.github.retrolambda_methodref_repro;

import android.os.AsyncTask;

public class AsyncTaskBuilder<Progress, Result> {
    public interface ProgressObserver<P> {
        void progressChanged(P[] progress);
    }

    public interface BackgroundWorkWithProgress<_Progress, _Result> {
        _Result execute(ProgressObserver<_Progress> progressObserver);
    }

    private BackgroundWorkWithProgress<Progress, Result> work;
    private ProgressObserver<Progress> progressObserver;

    public AsyncTaskBuilder<Progress, Result> doInBackground(BackgroundWorkWithProgress<Progress, Result> work) {
        this.work = work;
        return this;
    }

    public AsyncTaskBuilder<Progress, Result> progressObserver(ProgressObserver<Progress> observer) {
        progressObserver = observer;
        return this;
    }

    public AsyncTask<Void, Progress, Result> build() {
        if (work == null) throw new IllegalArgumentException("Background work is not defined. User doInBackground() in order to define.");

        return new AsyncTask<Void, Progress, Result>() {
            @Override
            protected Result doInBackground(Void... voids) {
                return work.execute(this::publishProgress);
            }

            @Override
            @SafeVarargs
            protected final void onProgressUpdate(Progress... progress) {
                if (progressObserver != null) progressObserver.progressChanged(progress);
            }
        };
    }

    public static <Result, Progress> AsyncTaskBuilder<Result, Progress> asyncTask() {
        return new AsyncTaskBuilder<>();
    }
}
