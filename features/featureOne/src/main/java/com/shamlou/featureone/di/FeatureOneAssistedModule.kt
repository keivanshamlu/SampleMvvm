package com.shamlou.featureone.di

import androidx.lifecycle.ViewModel
import com.shamlou.core.assisted.AssistedSavedStateViewModelFactory
import com.shamlou.core.anots.ViewModelKey
import com.shamlou.featureone.ui.posts.FeatureOneViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes = [AssistedInject_FeatureOneAssistedModule::class])
abstract class FeatureOneAssistedModule {

    @Binds
    @IntoMap
    @FeatureOneScope
    @ViewModelKey(FeatureOneViewModel::class)
    abstract fun bindVMFactory(f: FeatureOneViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}