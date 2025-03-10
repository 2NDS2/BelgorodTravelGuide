package com.example.belgorodtravelguide.view.profileView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.belgorodtravelguide.R
import com.example.belgorodtravelguide.databinding.FragmentProfileBinding
import com.example.belgorodtravelguide.data.repository.ProfileRepository
import com.example.belgorodtravelguide.viewModel.profileAndMarketVM.ProfileAndMarketViewModel
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileAndMarketViewModel by viewModels()
    private val profileRepository = ProfileRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        LoadProfileInBD()                                          //Загружаем данные профиля из БД
        showDataBirday()                                           //данные о дате и возрасте
        binding.fabEditProfile.setOnClickListener {OpenEditCard()} //открыть редактирование профиля
        return binding.root
    }

    private fun LoadProfileInBD() {
        lifecycleScope.launch {
            profileRepository.getProfile(requireContext())?.let { profile ->
                //отображаем данные в UI
                binding.textUserName.text = profile.nameUser
                binding.ratingBar.rating = profile.rating
                binding.moneyText.text = "${profile.money} Rur"
                binding.aboutMeText.text = profile.aboutMe
                viewModel.setBirthdayWithAge(profile.birthday)
                binding.cityText.text = profile.city
                binding.statusText.text = profile.status
                binding.emailText.text = profile.email
                binding.phoneNumberText.text = profile.phoneNumber
                binding.socialNetworkText.text = profile.socialNetwork
            }
        }
    }

    private fun OpenEditCard() {
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
