package com.magenic.covid_tracker.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.magenic.covid_tracker.R;
import com.magenic.covid_tracker.constants.EventNames;
import com.magenic.covid_tracker.infrastructure.DIViewModelFactory;
import com.magenic.covid_tracker.infrastructure.Event;
import com.magenic.covid_tracker.infrastructure.EventPayload;
import com.magenic.covid_tracker.viewmodels.BaseViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class BaseView<T extends BaseViewModel> extends Fragment {
    protected T _viewModel;

    @Inject
    DIViewModelFactory _dIViewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    protected void createViewModel(Class<T> classRtType) {
        ViewModelProvider viewModelProvider = new ViewModelProvider(getActivity(), _dIViewModelFactory);
        this.createViewModel(viewModelProvider, classRtType);
    }

    protected void createViewModel(@NonNull ViewModelStoreOwner owner, @NonNull Class<T> classRtType) {
        ViewModelProvider viewModelProvider = new ViewModelProvider(owner, _dIViewModelFactory);
        this.createViewModel(viewModelProvider, classRtType);
    }

    private void createViewModel(@NonNull ViewModelProvider viewModelProvider, Class<T> classRtType) {
        _viewModel = viewModelProvider.get(classRtType);
        _viewModel.get_event().observe(this.getViewLifecycleOwner(), event -> {
            if (event != null) {
                handleEvent(event);
            }
        });
    }

    protected void handleEvent(Event event) {
        EventPayload payload = event.get_payload();
        if (payload != null) {
            switch (payload.get_eventType()) {
                case EventNames.Notification:
                    String title = payload.get_eventItems().get(EventNames.Title);
                    String body = payload.get_eventItems().get(EventNames.Body);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(body)
                            .setTitle(title)
                            .setCancelable(true)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                    break;
            }
        }
    }

}
