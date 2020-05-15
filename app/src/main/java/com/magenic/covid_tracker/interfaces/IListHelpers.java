package com.magenic.covid_tracker.interfaces;

import com.google.common.util.concurrent.ListenableFuture;

public interface IListHelpers {
    ListenableFuture<String> showStringListForResult(final String[] listItems, String title);
}
