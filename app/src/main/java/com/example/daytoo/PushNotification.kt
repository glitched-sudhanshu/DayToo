package com.example.daytoo

import com.example.daytoo.models.NotificationData

data class PushNotification(
    val data: NotificationData,
    val to: String
)