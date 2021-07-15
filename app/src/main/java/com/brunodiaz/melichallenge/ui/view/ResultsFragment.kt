package com.brunodiaz.melichallenge.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.brunodiaz.melichallenge.MainActivity
import com.brunodiaz.melichallenge.ui.tools.DataTools.setResultsText
import com.brunodiaz.melichallenge.ui.adapter.ResultsAdapter
import com.brunodiaz.databinding.FragmentResultsBinding
import com.brunodiaz.melichallenge.other.Status
import com.brunodiaz.melichallenge.ui.tools.UiTools
import com.brunodiaz.melichallenge.ui.tools.UiTools.Companion.hideKeyboard
import com.brunodiaz.melichallenge.ui.viewModel.ResultsViewModel


class ResultsFragment : Fragment() {

    // Para Fragment
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ResultsViewModel

    private val args: ResultsFragmentArgs by navArgs()
    private var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiTools.closeSoftKeyboard(requireContext(), binding.svFind)
        searchQuery = args.searchField
        binding.svFind.setQuery(searchQuery, false)

        setupViewModelAndObserve(searchQuery)
        binding.svFind.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (!query.isNullOrEmpty()) {
                    hideKeyboard(activity as MainActivity)
                    searchQuery = query
                    viewModel.getItemList(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }

    private fun setupViewModelAndObserve(query: String) {

        viewModel = ViewModelProvider(requireActivity()).get(ResultsViewModel::class.java)
        viewModel.getItemList(query)
        // ViewLifecycleOwner maneja el observer segun ciclo de vida de Fragment
        viewModel.items.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.LOADING -> {
                        binding.pbLoading.isVisible = true
                    }
                    Status.ERROR -> {
                        UiQuerySuccess(false)
                        Toast.makeText(requireContext(),
                            "Ha ocurrido un problema",
                            Toast.LENGTH_SHORT).show()
                    }
                    Status.EMPTY -> {
                        binding.rvResults.isVisible = false
                        binding.tvResults.text = "No se encontraron resultados para ${searchQuery}"
                        binding.pbLoading.isVisible = false
                    }
                    Status.SUCCESS -> {
                        UiQuerySuccess(true)
                        binding.tvResults.text = setResultsText(result.data!!.paging.total)
                        binding.rvResults.layoutManager = LinearLayoutManager(activity)
                        binding.rvResults.adapter = ResultsAdapter(result.data.results) { item ->
                            findNavController().navigate(ResultsFragmentDirections.navigateToDetailFragment(
                                item.id ?: return@ResultsAdapter,
                                searchQuery))
                        }
                    }

                }
            }
        })
    }

    private fun UiQuerySuccess(status: Boolean) {
        if (status) {
            binding.noInternetCoponent.isVisible = false
            binding.rvResults.isVisible = true
            binding.pbLoading.isVisible = false
            binding.tvResults.isVisible = true
        } else {
            binding.noInternetCoponent.isVisible = true
            binding.pbLoading.isVisible = false
            binding.rvResults.isVisible = false
            binding.tvResults.isVisible = false
        }
    }

    override fun onDestroyView() {
        // previene memory leak, la instancia de adaptador retiene el recyclerview
        binding.rvResults.adapter = null
        super.onDestroyView()
    }
}

