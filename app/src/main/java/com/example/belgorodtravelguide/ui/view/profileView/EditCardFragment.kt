package com.example.belgorodtravelguide.ui.view.profileView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.belgorodtravelguide.R
import com.example.belgorodtravelguide.data.local.db.AppDatabase
import com.example.belgorodtravelguide.databinding.FragmentEditCardBinding
import com.example.belgorodtravelguide.data.local.entity.Profile
import com.example.belgorodtravelguide.data.repository.ProfileRepositoryImpl
import com.example.belgorodtravelguide.domain.repository.ProfileRepository
import com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM.ProfileAndMarketViewModel
import com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM.ProfileViewModelFactory
import kotlinx.coroutines.launch

class EditCardFragment : Fragment() {
    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!
//    private val repository: ProfileRepository by lazy { ProfileRepositoryImpl(requireContext()) }
    private val repository: ProfileRepository by lazy {ProfileRepositoryImpl(AppDatabase.getInstance(requireContext()))}

    private val viewModel: ProfileAndMarketViewModel by viewModels {
        ProfileViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentEditCardBinding.inflate(inflater, container, false)

        viewModel.createProfileIfNeeded()               // Проверяем, есть ли профиль в бд и создаём его, если нет
        getAndPack()                                                    // Получаем данные из бд и заполняем UI
        binding.birthdayText.setOnClickListener{callDataPicerDialog()}  // Обработчик для поля ввода даты рождения
        binding.saveButton.setOnClickListener {saveData()}              // Обработчик для кнопки "Сохранить"

        return binding.root
    }

    fun callDataPicerDialog(){
        viewModel.showDatePickerDialog(requireContext()) { dateWithAge ->
            binding.birthdayText.setText(dateWithAge)
        }
    }

    fun getAndPack() {
        viewModel.editData { profile ->
            profile?.let {
                binding.textUserName.setText(it.nameUser)
                binding.aboutMeText.setText(it.aboutMe)
                binding.birthdayText.setText(it.birthday)
                binding.cityText.setText(it.city)
                binding.statusText.setText(it.status)
                binding.emailText.setText(it.email)
                binding.phoneNumberText.setText(it.phoneNumber)
                binding.socialNetworkText.setText(it.socialNetwork)
            }
        }
    }

    private fun saveData() {
        lifecycleScope.launch {
            // Получаем значение money из текущего профиля
            viewModel.editData { profile ->
                val updatedProfile = Profile(
                    profileId = 1L,
                    money = profile?.money ?: 0,  // Сохраняем текущее значение money без изменений
                    nameUser = binding.textUserName.text.toString(),
                    aboutMe = binding.aboutMeText.text.toString(),
                    birthday = binding.birthdayText.text.toString(),
                    city = binding.cityText.text.toString(),
                    status = binding.statusText.text.toString(),
                    email = binding.emailText.text.toString(),
                    phoneNumber = binding.phoneNumberText.text.toString(),
                    socialNetwork = binding.socialNetworkText.text.toString(),
                )
                viewModel.saveData(updatedProfile) {
                    findNavController().navigate(R.id.action_editCardFragment_to_profileFragment) // закрыл фрагмент как сохранил
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
