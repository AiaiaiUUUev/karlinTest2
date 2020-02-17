package com.karlin.user.common.util

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler

fun stubRxJavaSchedulers() {
    val immediateScheduler = TestScheduler()
    RxJavaPlugins.setComputationSchedulerHandler { immediateScheduler }
    RxJavaPlugins.setIoSchedulerHandler { immediateScheduler }
    RxJavaPlugins.setNewThreadSchedulerHandler { immediateScheduler }
    RxJavaPlugins.setSingleSchedulerHandler { immediateScheduler }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
}