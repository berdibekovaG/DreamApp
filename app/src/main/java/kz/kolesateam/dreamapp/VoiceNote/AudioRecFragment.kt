package kz.kolesateam.dreamapp.VoiceNote


import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.pm.PackageManager.*
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kz.kolesateam.dreamapp.R
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val RECORD_PERMISSION = android.Manifest.permission.RECORD_AUDIO
const val PERMISSION_CODE = 1

class AudioRecFragment : Fragment(), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var listButton: ImageButton
    private lateinit var recordButton: ImageButton
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var recordFile: String
    private lateinit var recordPath: String
    private lateinit var date: Date
    private lateinit var timer: Chronometer
    private lateinit var fileNameText: TextView

    private var isRecording: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_rec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        listButton = view.findViewById(R.id.record_list_button)
        recordButton = view.findViewById(R.id.record_btn)
        timer = view.findViewById(R.id.chronometer_timer)
        fileNameText = view.findViewById(R.id.record_fileName)
        listButton.setOnClickListener(this)
        recordButton.setOnClickListener(this) //{
//            if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
//                    && ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                ActivityCompat.requestPermissions(this, permissions, 0)
//            } else {
//                //  startRecording()
//            }
//            onRecClick()
//        }
    }

    override fun onClick(v: View?) {
        val rec = requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSION_CODE)
        when (v!!.id) {
            listButton.id ->if(isRecording) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setPositiveButton("Да"){
                    dialog, which ->
                    navController.navigate(R.id.action_audioRecFragment_to_audioListFragment)
                    isRecording = false
                }
                alertDialog.setNegativeButton("Нет", null)
                alertDialog.setTitle("Записывается аудио")
                alertDialog.setMessage("Вы уверены, что хотите завершить аудио?")
                alertDialog.create().show()
            }
                else{
                navController.navigate(R.id.action_audioRecFragment_to_audioListFragment)
                }
            recordButton.id -> onRecClick()
        }
    }

    private fun checkPermission() {
        if (context?.let {
                    ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                } != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(
                    Manifest.permission.READ_CONTACTS),
                    PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_CODE) {
            if (permissions[0] == Manifest.permission.RECORD_AUDIO && grantResults[0] == PERMISSION_GRANTED) {
                Log.d(TAG, "Проверка")
            }
        }
    }

    private fun onRecClick() {
        if (!isRecording) {
            checkPermission()
            recordButton.setImageResource(R.drawable.ic_stop)
            startRecording()
            isRecording = true
        } else {
            recordButton.setImageResource(R.drawable.ic_play)
            stopRecording()
            isRecording = false
        }
    }

    private fun stopRecording() {
        timer.stop()

        fileNameText.setText("Recording Stopped, File Saved $recordFile")
        mediaRecorder.stop()
        mediaRecorder.release()
        false
    }

    private fun startRecording() {
        timer.base = SystemClock.elapsedRealtime()
        timer.start()
        recordPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        simpleDateFormat = SimpleDateFormat("yyyyMMddhhss", Locale.CANADA)
        date = Date()

        recordFile = "Dream_" + simpleDateFormat.format(date) + ".3gp"

        fileNameText.setText("Recording, File Name: $recordFile")
        mediaRecorder = MediaRecorder()
        with(mediaRecorder) {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile("$recordPath / $recordFile")
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
        }
    }

    override fun onStop() {
        super.onStop()
        if(isRecording)
        stopRecording()
    }
}




