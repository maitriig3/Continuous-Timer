package in_.mai3.continuoustimer.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import in_.mai3.continuoustimer.databinding.FragmentSplashBinding
import in_.mai3.continuoustimer.ui.base.BaseFragment
import in_.mai3.continuoustimer.utils.navigate

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            navigate(SplashFragmentDirections.actionSplashFragmentToScheduleFragment())
        },2*1000)
    }

}