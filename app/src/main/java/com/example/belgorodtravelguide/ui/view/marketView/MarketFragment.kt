package com.example.belgorodtravelguide.ui.view.marketView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belgorodtravelguide.data.local.db.AppDatabase
import com.example.belgorodtravelguide.databinding.FragmentMarketBinding
import com.example.belgorodtravelguide.domain.modelMarket.Item
import com.example.belgorodtravelguide.data.repository.ProfileRepositoryImpl
import com.example.belgorodtravelguide.domain.repository.ProfileRepository
import com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM.ProfileAndMarketViewModel
import com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM.ProfileViewModelFactory


class MarketFragment : Fragment() {
    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!
    private val repository: ProfileRepository by lazy {ProfileRepositoryImpl(AppDatabase.getInstance(requireContext()))}
    private val viewModel: ProfileAndMarketViewModel by viewModels {
        ProfileViewModelFactory(repository)
    }
    private var isGridLayout = true //отслеживание текущего состояния LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)

        val marketItems = viewModel.loadMarketData(requireContext())            //загрузка данных с использованием ViewModel
        setupRecyclerView(marketItems)                                          //настройка переработки данных
        observeMoney()                                                          //отслеживаем изменения баланса и получаем новые данные
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
        viewModel.loadMoneyFromDatabase()
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
