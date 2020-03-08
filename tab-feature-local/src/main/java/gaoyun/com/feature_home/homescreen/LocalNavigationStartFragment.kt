package gaoyun.com.feature_home.homescreen

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gaoyun.com.feature_home.R

class LocalNavigationStartFragment : Fragment(R.layout.fragment_local_start) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.about_btn).setOnClickListener {
            findNavController().navigate(R.id.action_local_start_to_destination)
        }
    }

}
