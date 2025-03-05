package com.example.belgorodtravelguide.view.entertainmentView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.belgorodtravelguide.R
import com.example.belgorodtravelguide.databinding.FragmentDetailBinding

import com.example.belgorodtravelguide.model.modelEntertainment.CategoriesListData as CategoriesListData1

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setupDetailCard()
        return binding.root
    }

    private fun setupDetailCard() {
        val category = arguments?.getParcelable<CategoriesListData1>("category")
        category?.let {

            it.detailCat.forEach { description ->
                // надуваем макет карточки
                val cardView = LayoutInflater.from(requireContext()).inflate(R.layout.card_item, null) as CardView
                // отступы для карточки
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 16, 0, 16)  // Устанавливаем отступы сверху и снизу (16dp)
                cardView.layoutParams = params

                val descriptionTextView = cardView.findViewById<TextView>(R.id.card_description)
                descriptionTextView.text = description

                cardView.setOnClickListener {
                    //заглушка
                    Toast.makeText(requireContext(), "Выбран: $description", Toast.LENGTH_SHORT).show()
                }
                binding.descriptionLayout.addView(cardView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}