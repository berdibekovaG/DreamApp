package kz.kolesateam.dreamapp.VoiceNote

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.dreamapp.R
import java.io.File
import java.nio.file.Files

class AudioViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

         val list_image: ImageView = itemView.findViewById(R.id.ic_play_list_imageview)
         val list_title: TextView = itemView.findViewById(R.id.list_title)
         val list_date: TextView = itemView.findViewById(R.id.list_date)

}