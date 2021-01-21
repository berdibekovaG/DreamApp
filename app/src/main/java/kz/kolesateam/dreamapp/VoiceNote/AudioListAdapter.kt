package kz.kolesateam.dreamapp.VoiceNote

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.dreamapp.R
import kz.kolesateam.dreamapp.utils.TimeFormat
import java.io.File

class AudioListAdapter(
        private val allFiles: Array<File>,
        private val onItemClickListener: OnItemListClick,
)
    : RecyclerView.Adapter<AudioViewHolder>() {

    val timeAgo = TimeFormat()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        return AudioViewHolder(
                view = View.inflate(parent.context, R.layout.single_list_item, null),
                onItemClickListener = onItemClickListener)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {

        holder.list_title.text = allFiles[position].name
        holder.list_date.text = timeAgo.getTimeAgo(allFiles[position].lastModified())
    }

    override fun getItemCount(): Int {
        return allFiles.size
    }

}
