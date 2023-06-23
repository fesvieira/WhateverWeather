package com.fesvieira.whateverweather.helpers

import com.fesvieira.whateverweather.models.Location

val Location.formatLocale: String get() {
    return "${this.name}, ${this.region}"
}