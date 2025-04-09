package org.mathieu.cleanrmapi.data

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.R
import org.mathieu.cleanrmapi.domain.SoundPlayer

class AndroidSoundPlayer(private val context: Context) : SoundPlayer {
    override fun playClickSound() {
        MediaPlayer.create(context, R.raw.soundeffect)?.start()
    }
}