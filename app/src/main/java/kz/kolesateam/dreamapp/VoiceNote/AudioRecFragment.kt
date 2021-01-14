package kz.kolesateam.dreamapp.VoiceNote


import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.pm.PackageManager.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kz.kolesateam.dreamapp.R


const val RECORD_PERMISSION = android.Manifest.permission.RECORD_AUDIO
const val PERMISSION_CODE = 1

class AudioRecFragment : Fragment(), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var listButton: ImageButton
    private lateinit var recordButton: ImageButton

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
        listButton.setOnClickListener(this)
        recordButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
              //  startRecording()
            }
            onRecClick()
        }

        }

    override fun onClick(v: View?) {
       val rec = requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSION_CODE)
        when (v!!.id) {
            listButton.id -> navController.navigate(R.id.action_audioRecFragment_to_audioListFragment)

        }
    }

    private fun checkPermission() {
        if (context?.let {
                    ActivityCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_CODE){
            if(permissions[0] == Manifest.permission.RECORD_AUDIO && grantResults[0] == PERMISSION_GRANTED){
            Log.d(TAG, "ЛОГ")
            }
        }
    }


    private fun onRecClick(){
        if (!isRecording) {
            checkPermission()
            recordButton.setImageResource(R.drawable.ic_stop)
            isRecording = true
        } else {
            recordButton.setImageResource(R.drawable.ic_play)
            isRecording = false
        }
    }
}




