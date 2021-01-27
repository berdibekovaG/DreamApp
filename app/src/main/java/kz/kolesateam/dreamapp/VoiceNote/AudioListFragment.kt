package kz.kolesateam.dreamapp.VoiceNote

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kz.kolesateam.dreamapp.R
import java.io.File

class AudioListFragment : Fragment() , OnItemListClick {


    //private lateinit var playerSheep: ConstraintLayout
    private lateinit var audioList: RecyclerView
    private lateinit var allFiles: Array<File>
    private lateinit var audioListAdapter: AudioListAdapter
    private lateinit var fileToPlay: File
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var isPlaying: Boolean = false


    //UI elements
    private lateinit var playButton: ImageButton
    private lateinit var playerHeader: TextView
    private lateinit var playerFileName: TextView
    private lateinit var playerSeekBar: SeekBar
    private lateinit var seekBarHandler: Handler
    private lateinit var updateSeekBar: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
            inflater: LayoutInflater , container: ViewGroup? ,
            savedInstanceState: Bundle? ,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_list , container , false)
    }


    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        val playerSheep = view.findViewById<ConstraintLayout>(R.id.playerSheet)
        audioList = view.findViewById(R.id.audio_list_recyclerView)

        playButton = view.findViewById(R.id.ic_play)
        playerHeader = view.findViewById(R.id.header_player_title)
        playerFileName = view.findViewById(R.id.player_textView_fileName)
        playerSeekBar = view.findViewById(R.id.player_seekBar)

        val path: String = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val directory = File(path)
        allFiles = directory.listFiles()


        audioListAdapter = AudioListAdapter(allFiles , this)
        audioList.adapter = audioListAdapter
        audioList.setHasFixedSize(true)
        audioList.layoutManager = LinearLayoutManager(context)
        //audioList.adapter = audioListAdapter


        if (playerSheep != null) {
            bottomSheetBehavior = BottomSheetBehavior.from(playerSheep)

            bottomSheetBehavior.addBottomSheetCallback(
                    object : BottomSheetBehavior.BottomSheetCallback() {

                        override fun onStateChanged(bottomSheet: View , newState: Int) {
                            when (newState) {
                                BottomSheetBehavior.STATE_HIDDEN -> BottomSheetBehavior.STATE_COLLAPSED
//                                BottomSheetBehavior.STATE_COLLAPSED -> {
//                                    Toast.makeText(context , "Тоаст" , Toast.LENGTH_SHORT).show()
//                                }
//                                BottomSheetBehavior.STATE_DRAGGING -> {
//                                    Toast.makeText(context , "Тоаст" , Toast.LENGTH_SHORT).show()
//                                }
//                                BottomSheetBehavior.STATE_EXPANDED -> {
//                                    Toast.makeText(context , "Тоаст" , Toast.LENGTH_SHORT).show()
//                                }
//                                BottomSheetBehavior.STATE_HALF_EXPANDED -> {
//                                    Toast.makeText(context , "Тоаст" , Toast.LENGTH_SHORT).show()
//                                }
//                                BottomSheetBehavior.STATE_SETTLING -> {
//                                    Toast.makeText(context , "Тоаст" , Toast.LENGTH_SHORT).show()
                                // }
                            }
                        }

                        override fun onSlide(bottomSheet: View , slideOffset: Float) {
                            //пока ничего не делаем
                        }
                    })
            playButton.setOnClickListener{
                if(isPlaying){
                    pauseAudio()
                }else{
                    resumeAudio()
                }
            }
        }
        playerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (fileToPlay != null) {
                    pauseAudio()
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (fileToPlay != null) {
                    var progress = seekBar!!.progress
                    mediaPlayer.seekTo(progress)
                    resumeAudio()
                }
            }
        })
    }


    override fun onClickListener(file: File , position: Int) {
        fileToPlay = file
        if (isPlaying) {
            stopAudio()
            playAudio(fileToPlay)
        } else {
            if (fileToPlay != null) {
                playAudio(fileToPlay)
                isPlaying = true
            }
        }
    }

    private fun pauseAudio() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.ic_play)
        isPlaying = false
        seekBarHandler.removeCallbacks(updateSeekBar)
    }

    private fun resumeAudio() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.ic_pause)
        isPlaying= true

        updateRunnable()
        seekBarHandler.postDelayed(updateSeekBar, 0)
    }

    private fun stopAudio() {
        playButton.setImageResource(R.drawable.ic_play)
        playerHeader.text = "Остановлено"
        isPlaying = false
        mediaPlayer.stop()
        seekBarHandler.removeCallbacks(updateSeekBar)
    }

    private fun playAudio(fileToPlay: File) {
        mediaPlayer = MediaPlayer()

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        try {
            mediaPlayer.run {
                setDataSource(fileToPlay.absolutePath)
                prepare()
                start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        playButton.setImageResource(R.drawable.ic_pause)
        playerFileName.text = fileToPlay.name
        playerHeader.text = "Проигрывается"
        isPlaying = true

        mediaPlayer.setOnCompletionListener {
            stopAudio()
            playerHeader.text = "Finished"
        }

// надо доработать - не работает
        playerSeekBar.max = mediaPlayer.duration
//
//        //? looper вместо  handler
        seekBarHandler = Handler()
        updateRunnable()
        seekBarHandler.postDelayed(updateSeekBar , 0)
    }

    private fun updateRunnable() {
        updateSeekBar = Runnable {
            playerSeekBar.progress = mediaPlayer.currentPosition

            seekBarHandler.postDelayed(updateSeekBar , 500)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isPlaying) {
            stopAudio()
        }
    }
}

//ContextCompat.getDrawable(this, android.R.drawable.ic_media_play)