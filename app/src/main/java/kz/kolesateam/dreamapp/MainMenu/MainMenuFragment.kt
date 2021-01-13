package kz.kolesateam.dreamapp.MainMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kz.kolesateam.dreamapp.R

class MainMenuFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        view.findViewById<Button>(R.id.choice_activity_voice_button).setOnClickListener(this)
        view.findViewById<Button>(R.id.choice_activity_text_button).setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.choice_activity_text_button ->navController!!.navigate(R.id.action_mainMenuFragment_to_textNoteFragment)
            R.id.choice_activity_voice_button ->navController!!.navigate(R.id.action_mainMenuFragment_to_audioRecFragment)
        }
    }
}