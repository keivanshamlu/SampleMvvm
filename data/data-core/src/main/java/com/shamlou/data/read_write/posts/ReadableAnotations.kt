package com.shamlou.data.read_write.posts

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalReadable

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteReadable