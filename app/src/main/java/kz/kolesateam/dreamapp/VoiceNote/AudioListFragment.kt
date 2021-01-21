package kz.kolesateam.dreamapp.VoiceNote

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kz.kolesateam.dreamapp.R
import java.io.File
import java.lang.Exception

class AudioListFragment : Fragment(), OnItemListClick {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    //private lateinit var playerSheep: ConstraintLayout
    private lateinit var audioList: RecyclerView
    private lateinit var allFiles: Array<File>
    private lateinit var audioListAdapter: AudioListAdapter
    private lateinit var fileToPlay: File
    private lateinit var mediaPlayer: MediaPlayer

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

        playButton = view.findViewById(R.id.ic_play)
        playerHeader = view.findViewById(R.id.header_player_title)
        playerFileName = view.findViewById(R.id.player_textView_fileName)
        playerSeekBar = view.findViewById(R.id.player_seekBar)

        val path: String = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val directory = File(path)
        allFiles = directory.listFiles()


        audioListAdapter = AudioListAdapter(allFiles, this)
        audioList.adapter = audioListAdapter
        audioList.setHasFixedSize(true)
        audioList.layoutManager = LinearLayoutManager(context)
        //audioList.adapter = audioListAdapter


        if (playerSheep != null)
        {
            bottomSheetBehavior = BottomSheetBehavior.from(playerSheep)

            bottomSheetBehavior.addBottomSheetCallback(
                    object : BottomSheetBehavior.BottomSheetCallback()
                    {

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

    override fun onClickListener(file: File, position: Int) {
        if (isPlaying) {
            stopAudio()
            playAudio(fileToPlay)
        } else {
            fileToPlay = file
            playAudio(fileToPlay)
            isPlaying = true
        }
    }

    private fun stopAudio() {
        playButton.setImageResource(R.drawable.ic_play)
        playerHeader.text = "Остановлено"
        isPlaying = false
        mediaPlayer.stop()
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

        }catch (e: Exception){
            e.printStackTrace()
        }
        playButton.setImageResource(R.drawable.ic_pause)
        playerFileName.text = fileToPlay.name
        playerHeader.text = "Проигрывается"
        isPlaying = true

        mediaPlayer.setOnCompletionListener{
            stopAudio()
            playerHeader.text = "Finished"
        }

        playerSeekBar.max(mediaPlayer.duration)

        //? looper вместо  handler
        seekBarHandler = Handler()

        updateSeekBar = Runnable {
                playerSeekBar.setProgress(mediaPlayer.currentPosition)
                seekBarHandler.postDelayed(this,500)
            }
            seekBarHandler.postDelayed(updateSeekBar, 0)
        }
    }

//ContextCompat.getDrawable(this, android.R.drawable.ic_media_play)