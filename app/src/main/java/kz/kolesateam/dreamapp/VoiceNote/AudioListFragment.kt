package kz.kolesateam.dreamapp.VoiceNote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kz.kolesateam.dreamapp.R
import java.io.File


class AudioListFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
 //private lateinit var playerSheep: ConstraintLayout
    private lateinit var audioList: RecyclerView
    private lateinit var allFiles: Array<File>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playerSheep = view.findViewById<ConstraintLayout>(R.id.playerSheet)
        audioList = view.findViewById(R.id.audio_list_recyclerView)


        val path: String = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val directory = File(path)
        allFiles = directory.listFiles()


         val audioListAdapter = AudioListAdapter(allFiles)
        audioList.adapter = AudioListAdapter(allFiles)
        audioList.setHasFixedSize(true)
        audioList.layoutManager = LinearLayoutManager(context)
      //  audioList.adapter = audioListAdapter

        if(playerSheep != null){
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheep)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> BottomSheetBehavior.STATE_COLLAPSED
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Toast.makeText(context, "Тоаст", Toast.LENGTH_SHORT).show()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Toast.makeText(context, "Тоаст", Toast.LENGTH_SHORT).show()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Toast.makeText(context, "Тоаст", Toast.LENGTH_SHORT).show()
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Toast.makeText(context, "Тоаст", Toast.LENGTH_SHORT).show()
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        Toast.makeText(context, "Тоаст", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //пока ничего не делаем
            }

        })
    }
    }
}
