package com.brunodiaz.melichallenge.ui.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.brunodiaz.databinding.FragmentSearchBinding
import com.brunodiaz.melichallenge.ui.tools.UiTools

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null  // puede ser nulo por el ciclo de vida
    private val binding get() = _binding!!              // cuando se llame a binding, ya existira _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiTools.closeSoftKeyboard(requireContext(), binding.svFind)
        binding.svFind.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (!query.isNullOrEmpty()) {
                    findNavController().navigate(
                        SearchFragmentDirections.navigateToResultsFragment(query)
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }


}