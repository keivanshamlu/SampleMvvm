package com.shamlou.sample

import com.shamlou.featureone.ui.MainActivity
import com.shamlou.sample.di.FragmentBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityScoped

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    internal abstract fun mainActivity(): MainActivity
}