package com.eru.service.android.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eru.service.android.databinding.FragmentMainBinding
import com.eru.service.android.services.IServiceManager
import com.eru.service.android.ui.example2.Example2Activity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        const val LOG_FILTER = "MainFragment: "
        fun newInstance() = MainFragment().apply {
            //Bundle
        }
    }

    @Inject
    lateinit var serviceManager: IServiceManager

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("$LOG_FILTER onCreate")
        binding.tvStartForegroundService.setOnClickListener {
            Timber.i("$LOG_FILTER start foreground service")
            serviceManager.startForegroundService(requireContext())
        }

        binding.tvStopForegroundService.setOnClickListener {
            Timber.i("$LOG_FILTER stop foreground service")
            serviceManager.stopForegroundService(requireContext())
        }

        binding.tvExample2.setOnClickListener {
            val example2Intent = Intent(requireContext(), Example2Activity::class.java)
            startActivity(example2Intent)
        }
    }
}