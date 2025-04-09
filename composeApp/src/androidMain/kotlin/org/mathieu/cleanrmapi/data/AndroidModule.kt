package org.mathieu.cleanrmapi.data

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.mathieu.cleanrmapi.domain.SoundPlayer

val androidModule = module {
    single<SoundPlayer> { AndroidSoundPlayer(androidContext()) }
}