package com.shamlou.sample.di

import com.shamlou.featureone.di.FeatureOneAssistedModule
import com.shamlou.featureone.di.FeatureOneScope
import com.shamlou.featureone.ui.posts.FragmentFeatureOne
import com.shamlou.sample.di.features.FeatureOneMappersModule
import com.shamlou.sample.di.features.FeatureOneModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBindingModule {

    @FeatureOneScope
    @ContributesAndroidInjector(
        modules = [
            FeatureOneAssistedModule::class,
            FeatureOneModule::class,
            FeatureOneMappersModule::class]
    )
    internal abstract fun bindFeatureOne(): FragmentFeatureOne

}