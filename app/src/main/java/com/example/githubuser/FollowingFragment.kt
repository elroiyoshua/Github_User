package com.example.githubuser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.githubuser.databinding.FragmentFollowBinding

class FollowingFragment : Fragment(R.layout.fragment__follow){
    private var _binding : FragmentFollowBinding?= null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentFollowBinding.bind(view)
    }
}