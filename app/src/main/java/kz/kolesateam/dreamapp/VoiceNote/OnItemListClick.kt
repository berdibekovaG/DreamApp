package kz.kolesateam.dreamapp.VoiceNote

import java.io.File

interface OnItemListClick {

    fun onClickListener(file: File, position: Int)
}