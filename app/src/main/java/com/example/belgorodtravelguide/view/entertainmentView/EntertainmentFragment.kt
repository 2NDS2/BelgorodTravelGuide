package com.example.belgorodtravelguide.view.entertainmentView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.belgorodtravelguide.R
import com.example.belgorodtravelguide.databinding.FragmentEntertainmentBinding
import com.example.belgorodtravelguide.model.modelEntertainment.CategoriesListData
import com.example.belgorodtravelguide.model.modelEntertainment.CategoryDetail
import com.example.belgorodtravelguide.model.modelEntertainment.HeaderData
import com.example.belgorodtravelguide.viewModel.entertainmentVM.CategoriesAdapter

class EntertainmentFragment : Fragment() {
    private var _binding: FragmentEntertainmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentEntertainmentBinding.inflate(inflater,container, false)

        AutoImageSlider()
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val categoryDetails = listOf(
            CategoryDetail(R.array.class_museum_detail, R.drawable.museum_24px, getString(R.string.class_museum)),
            CategoryDetail(R.array.class_sport_detail, R.drawable.sports_and_outdoors_24px, getString(R.string.class_sport)),
            CategoryDetail(R.array.class_park_detail, R.drawable.park_24px, getString(R.string.class_park)),
            CategoryDetail(R.array.class_theater_detail, R.drawable.theater_comedy_24px, getString(R.string.class_theater)),
            CategoryDetail(R.array.class_cinema_detail, R.drawable.theaters_24px, getString(R.string.class_cinema)),
            CategoryDetail(R.array.class_attractions_detail, R.drawable.attractions_24px, getString(R.string.class_attractions)),
            CategoryDetail(R.array.class_shopping_detail, R.drawable.store_24px, getString(R.string.class_shopping))
        )

        val categories = mutableListOf(HeaderData("Категории")) + categoryDetails.mapIndexed { index, category ->
            CategoriesListData(index, category.iconResId, category.categoryName, resources.getStringArray(category.detailArrayResId))
        }

        val adapter = CategoriesAdapter(categories) { category ->
            val action = EntertainmentFragmentDirections.actionEntertainmentFragmentToDetailFragment(category)
            findNavController().navigate(action)
        }

        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = adapter
    }

    private fun AutoImageSlider() {
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(R.drawable.img_zoo, "Зоопарк", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_king, "Памятник «Князь Владимир»", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_kaleydoscop, "Парк аттракционов «Калейдоскоп»", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_filarmonia, "Государственная филармония", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_diorama, "Музей-диорама «Курская битва. Белгородское направление»", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_city_moll, "Торговый центр «Сити Молл»", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_cinema, "Кинотеатр «Русич»", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_arena, "Белгород-Арена", ScaleTypes.CENTER_CROP))
            add(SlideModel(R.drawable.img_aqua_parkjpg, "Аквапарк «Лазурный»", ScaleTypes.CENTER_CROP))
        }
        binding.imageSlider.setImageList(imageList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
