package com.example.belgorodtravelguide.view.marketView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belgorodtravelguide.databinding.FragmentMarketBinding
import com.example.belgorodtravelguide.data.modelMarket.Item
import com.example.belgorodtravelguide.viewModel.profileAndMarketVM.ProfileAndMarketViewModel


class MarketFragment : Fragment() {
    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileAndMarketViewModel

    private var isGridLayout = true //отслеживание текущего состояния LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileAndMarketViewModel::class.java)

        //загрузка данных с использованием ViewModel
        val marketItems = viewModel.loadMarketData(requireContext())
        setupRecyclerView(marketItems) //настройка переработки данных

        observeMoney()  // отслеживаем изменения баланса и получаем новые данные
        binding.sortBtn.setOnClickListener{toggleLayoutManager()}
        return binding.root
    }

    private fun setupRecyclerView(items: List<Item>) {
        val recyclerView: RecyclerView = binding.recyclerViewMarket
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val adapter = MarketAdapter(items, viewModel, this)
        recyclerView.adapter = adapter
    }

    private fun observeMoney() {
        viewModel.money.observe(viewLifecycleOwner) { money ->
            binding.moneyText.text = "$money Rur"  //обновляем TextView
        }
        viewModel.loadMoneyFromDatabase(requireContext())
    }

    private fun toggleLayoutManager() {
        val recyclerView: RecyclerView = binding.recyclerViewMarket
        if (isGridLayout) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
        //переключаем состояние
        isGridLayout = !isGridLayout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
