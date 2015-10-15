## Retrolambda / Proguard reproduction

[![Build Status](https://travis-ci.org/denis-itskovich/retrolambda-method-ref-repro.svg?branch=master)](https://travis-ci.org/denis-itskovich/retrolambda-method-ref-repro)
---

## Description
This simple project reproduces retrolambda/proguard problem when using method references

## Background
Proguard seems to shrink methods, referenced as method references

```java
public class AsyncTaskBuilder<Progress, Result> {

	public interface BackgroundWorkWithProgress<_Progress, _Result> {
		_Result execute(ProgressObserver<_Progress> progressObserver);
	}

	private BackgroundWorkWithProgress<Progress, Result> work;

	public AsyncTask<Void, Progress, Result> build() {

		return new AsyncTask<Void, Progress, Result>() {
			@Override
			protected Result doInBackground(Void... voids) {
				// this reference will not be taken into account by Proguard
				return work.execute(this::publishProgress);
			}
			
		};
	}
}
```
