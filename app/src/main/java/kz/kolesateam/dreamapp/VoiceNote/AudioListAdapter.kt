package kz.kolesateam.dreamapp.VoiceNote

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.dreamapp.R
import java.io.File

class AudioListAdapter(private val allFiles: Array<File>) : RecyclerView.Adapter<AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        return AudioViewHolder(
            view = View.inflate(parent.context, R.layout.single_list_item, null))
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {


        holder.list_title.text = allFiles[position].name
        holder.list_date.text = "" + allFiles[position].lastModified()
    }

    override fun getItemCount(): Int {
        return allFiles.size
    }
    }
