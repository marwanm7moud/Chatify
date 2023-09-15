package com.awesome.network.utils

import android.text.TextUtils

fun getOccupantsIdsStringFromList(occupantIdsList: Collection<Int>): String {
    return TextUtils.join(",", occupantIdsList)
}

object Constants {
    const val PROPERTY_OCCUPANTS_IDS = "current_occupant_ids"
    const val PROPERTY_DIALOG_TYPE = "type"
    const val PROPERTY_DIALOG_NAME = "room_name"
    const val PROPERTY_NOTIFICATION_TYPE = "notification_type"
    const val CREATING_DIALOG = "1"
}