package com.eru.service.android.di

import com.eru.service.android.fish.notification.INotificationManager
import com.eru.service.android.fish.services.IServiceManager
import com.eru.service.android.notification.NotificationManager
import com.eru.service.android.fish.services.ServiceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds
    @Singleton
    fun bindNotificationManager(notificationManager: NotificationManager): INotificationManager

    @Binds
    @Singleton
    fun bindServiceManager(serviceManager: ServiceManager): IServiceManager
}
