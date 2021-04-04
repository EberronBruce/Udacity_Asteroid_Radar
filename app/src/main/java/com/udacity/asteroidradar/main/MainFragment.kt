package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = MainAsteriodAdapter(AsteroidListener {
                asteroid -> viewModel.onAsteroidClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = adapter

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.onAsteroidNavigated()
            }

        })

        viewModel.asteriodData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                binding.statusLoadingWheel.visibility = View.GONE
                //binding.asteroidRecycler.smoothScrollToPosition(0) //Scroll to the top, some reason with deleteOldAsteroids it wants to start on the next day
            }
            if(it == null || it.isEmpty()) {
                binding.statusLoadingWheel.visibility = View.VISIBLE
            }
        })

        viewModel.dailyImage.observe(viewLifecycleOwner, Observer { dailyImage ->
            dailyImage?.let {
                Picasso.get().load(dailyImage.url).into(binding.activityMainImageOfTheDay)
                binding.activityMainImageOfTheDay.contentDescription = dailyImage.title
            }

            if(dailyImage == null) {
                binding.activityMainImageOfTheDay.setImageResource(R.drawable.placeholder_picture_of_day)
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
