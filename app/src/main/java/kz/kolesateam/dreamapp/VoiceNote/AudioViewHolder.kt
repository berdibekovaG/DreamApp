package kz.kolesateam.dreamapp.VoiceNote

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.dreamapp.R
import java.io.File

class AudioViewHolder(
        view: View,
        private val onItemClickListener: OnItemListClick,
) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private lateinit var allFiles: Array<File>


    val list_image: ImageView = itemView.findViewById(R.id.ic_play_list_imageview)
    val list_title: TextView = itemView.findViewById(R.id.list_title)
    val list_date: TextView = itemView.findViewById(R.id.list_date)

    override fun onClick(v: View?) {
        itemView.setOnClickListener {
            onItemClickListener.onClickListener(allFiles[adapterPosition], adapterPosition)
        }

//        list_image.setOnClickListener {
//           list_image.onClickListener(allFiles[adapterPosition], adapterPosition)
//        }
    }
}