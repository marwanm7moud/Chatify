package com.awesome.network.utils

import android.text.TextUtils

fun getOccupantsIdsStringFromList(occupantIdsList: Collection<Int>): String {
    return TextUtils.join(",", occupantIdsList)
}