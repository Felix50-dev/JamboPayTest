package com.example.jambopaytest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.jambopaytest.databinding.FragmentUserBinding
import com.example.jambopaytest.presentation.view.UserAdapter
import com.example.jambopaytest.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private const val TAG = "UserFragment"
@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val userAdapter = UserAdapter(emptyList())
    private lateinit var viewModel: UserViewModel
    private var ageCheckJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: started")
        binding = FragmentUserBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Setup RecyclerView
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewUsers.adapter = userAdapter

        // Handle empty list
        userAdapter.onEmptyList = {
            binding.textViewNoMatches.visibility = View.VISIBLE
        }

        // Observe LiveData and update the adapter
        viewModel.matchedUsersLiveData.observe(viewLifecycleOwner) { users ->
            if (users.isNullOrEmpty()) {
                binding.recyclerViewUsers.visibility = View.GONE
                binding.textViewNoMatches.visibility = View.VISIBLE
            } else {
                binding.recyclerViewUsers.visibility = View.VISIBLE
                binding.textViewNoMatches.visibility = View.GONE
                userAdapter.updateUsers(users)
            }
        }

        startAgeChecking()
        return binding.root
    }

    private fun startAgeChecking() {
        ageCheckJob = lifecycleScope.launch {
            while (isActive) {
                viewModel.checkRandomAge()
                delay(2500) // Wait for 2.5 seconds before generating another age
            }
        }
    }

    override fun onPause() {
        super.onPause()
        ageCheckJob?.cancel()
    }

    override fun onResume() {
        super.onResume()
        startAgeChecking()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(url)
                    .into(view)
            }
        }
    }
}