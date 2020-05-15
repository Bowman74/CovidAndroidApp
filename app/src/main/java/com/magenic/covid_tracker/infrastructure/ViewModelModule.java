package com.magenic.covid_tracker.infrastructure;

import androidx.lifecycle.ViewModelProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;
import com.magenic.covid_tracker.viewmodels.BaseViewModel;
import com.magenic.covid_tracker.viewmodels.MainViewModel;
import com.magenic.covid_tracker.viewmodels.SettingsViewModel;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelMapKey(MainViewModel.class)
    abstract BaseViewModel provideMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelMapKey(SettingsViewModel.class)
    abstract BaseViewModel provideSettingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DIViewModelFactory viewModelFactory);
}

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
@interface ViewModelMapKey {
    Class<? extends BaseViewModel> value();
}

