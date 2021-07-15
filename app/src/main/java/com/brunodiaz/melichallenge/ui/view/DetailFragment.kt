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
import com.brunodiaz.melichallenge.data.model.ItemInfo
import com.brunodiaz.databinding.FragmentDetailBinding
import com.brunodiaz.melichallenge.ui.tools.DataTools.setAvailableItemsText
import com.brunodiaz.melichallenge.ui.tools.DataTools.setConditionText
import com.brunodiaz.melichallenge.ui.tools.DataTools.setPriceText
import com.brunodiaz.melichallenge.ui.tools.DataTools.setSoldText
import com.brunodiaz.melichallenge.other.Status
import com.brunodiaz.melichallenge.ui.tools.UiTools
import com.brunodiaz.melichallenge.ui.viewModel.DetailViewModel
import com.squareup.picasso.Picasso


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()
    private var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiTools.closeSoftKeyboard(requireContext(), binding.svFind)
        searchQuery = args.searchField
        binding.svFind.setQuery(searchQuery, false)

        setupViewModelAndObserve(args.itemId)
        binding.svFind.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (!query.isNullOrEmpty()) {
                    searchQuery = query
                    findNavController().navigate(
                        DetailFragmentDirections.backToResultsFragment(searchQuery)
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }

    private fun setupViewModelAndObserve(query: String) {

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        viewModel.getItemData(query)
        viewModel.itemDescription.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.LOADING -> {
                        binding.noInternetCoponent.isVisible = false
                        binding.pbLoading.isVisible = true
                    }
                    Status.ERROR -> {
                        binding.pbLoading.isVisible = false
                        binding.noInternetCoponent.isVisible = true
                        Toast.makeText(requireContext(),
                            "Ha ocurrido un problema",
                            Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        binding.noInternetCoponent.isVisible = false
                        binding.pbLoading.isVisible = false
                        updateUI(result.data!!)
                    }
                    Status.EMPTY -> {
                        binding.pbLoading.isVisible = false
                        Toast.makeText(requireContext(),
                            "Ha ocurrido un problema",
                            Toast.LENGTH_SHORT).show()
                    }

                }

            }
        })
    }

    private fun updateUI(itemInfo: ItemInfo) {
        binding.tvDescriptionTittle.isVisible = true
        binding.tvCondition.text = setConditionText(itemInfo.item.condition)
        binding.tvSold.text = setSoldText(itemInfo.item.sold)
        binding.tvTitle.text = itemInfo.item.title
        if (itemInfo.item.pictures.isNotEmpty())
            Picasso.get().load(itemInfo.item.pictures[0].url).into(binding.ivProduct)
        binding.tvPrice.text = setPriceText(itemInfo.item.price)
        binding.tvAvailable.text = setAvailableItemsText(itemInfo.item.available)
        binding.tvDescription.text = itemInfo.description.plain_text
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


}

