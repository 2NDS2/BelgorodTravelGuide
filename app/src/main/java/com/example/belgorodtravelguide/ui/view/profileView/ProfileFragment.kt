package com.example.belgorodtravelguide.ui.view.profileView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.belgorodtravelguide.R
import com.example.belgorodtravelguide.data.local.db.AppDatabase
import com.example.belgorodtravelguide.databinding.FragmentProfileBinding
import com.example.belgorodtravelguide.data.repository.ProfileRepositoryImpl
import com.example.belgorodtravelguide.domain.repository.ProfileRepository
import com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM.ProfileAndMarketViewModel
import com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM.ProfileViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
//    private val repository: ProfileRepository by lazy { ProfileRepositoryImpl(requireContext()) }
    private val repository: ProfileRepository by lazy {ProfileRepositoryImpl(AppDatabase.getInstance(requireContext()))}

    private val viewModel: ProfileAndMarketViewModel by viewModels {
        ProfileViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        showDataBirday()                                           //данные о дате и возрасте
        loadProfile()                                              //Загружаем данные профиля из БД
        binding.fabEditProfile.setOnClickListener {openEditCard()} //открыть редактирование профиля
        return binding.root
    }

    private fun loadProfile() {
        viewModel.editData { profile ->
            profile?.let {
                binding.textUserName.text = it.nameUser
                binding.ratingBar.rating = it.rating
                binding.moneyText.text = "${it.money} Rur"
                binding.aboutMeText.text = it.aboutMe
                viewModel.setBirthdayWithAge(it.birthday)
                binding.cityText.text = it.city
                binding.statusText.text = it.status
                binding.emailText.text = it.email
                binding.phoneNumberText.text = it.phoneNumber
                binding.socialNetworkText.text = it.socialNetwork
            }
        }
    }

    private fun openEditCard() {
        //навигация на фрагмент редактирования
        findNavController().navigate(R.id.action_profileFragment_to_editCardFragment)
    }

    fun showDataBirday(){
        viewModel.birthdayWithAge.observe(viewLifecycleOwner) { dateWithAge -> //данные о дате и возрасте
            binding.birthdayText.text = dateWithAge
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
