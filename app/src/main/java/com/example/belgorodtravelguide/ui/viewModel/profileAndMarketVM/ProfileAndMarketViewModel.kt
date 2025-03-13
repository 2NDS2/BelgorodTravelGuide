package com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.content.Context
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.belgorodtravelguide.domain.modelMarket.Item
import com.example.belgorodtravelguide.domain.modelMarket.MarketResponse
import com.example.belgorodtravelguide.data.local.entity.Profile
import com.example.belgorodtravelguide.domain.repository.ProfileRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.util.*

class ProfileAndMarketViewModel(private val Repository: ProfileRepository) : ViewModel() {
    private val _birthdayWithAge = MutableLiveData<String>().apply { value = "Дата не выбрана" }
    val birthdayWithAge: LiveData<String> get() = _birthdayWithAge

    //проверка наличия профиля и создание нового, если его нет
    fun createProfileIfNeeded() {
        viewModelScope.launch {
            val profile = Repository.getProfile()
            if (profile == null) {
                val newProfile = Profile(
                    profileId = 0L,
                    nameUser = "Имя пользователя",
                    aboutMe = "Расскажите о себе",
                    birthday = "01.01.1900",
                    city = "Из какого вы города?",
                    status = "Какой у вас социальный статус?",
                    email = "Укажите почту",
                    phoneNumber = "Укажите номер телефона",
                    socialNetwork = "Оставьте ссылку на социальные сети, чтобы мы могли с вами связаться",
                    rating = 2f,
                    money = 0
                )
                Repository.insertProfile(newProfile)
            }
        }
    }

    //получение данных из бд
    fun editData(onProfileLoaded: (Profile?) -> Unit) {
        viewModelScope.launch {
            val profile = Repository.getProfile()
            onProfileLoaded(profile)
        }
    }
    //сохранение данных в бд
    fun saveData(updatedProfile: Profile, onDataSaved: () -> Unit) {
        viewModelScope.launch {
            Repository.updateProfile(updatedProfile)
            onDataSaved()
        }
    }
    //открытие диалога выбора даты
    fun showDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
        // Получаем текущую дату
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Создаем диалог для выбора даты
        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Форматируем выбранную дату
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                // Возвращаем дату
                onDateSelected("$formattedDate")
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()// Показываем диалог
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)?.setTextColor(Color.LTGRAY)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.setTextColor(Color.LTGRAY)
    }
    //установка даты с возрастом
    fun setBirthdayWithAge(birthday: String) {
        if (birthday == "Введите дату рождения") {
            _birthdayWithAge.value = "Введите дату рождения"
        } else {
            val age = calculateAge(birthday)
            _birthdayWithAge.value = if (age < 0) {
                "Укажите корректную дату"
            } else {
                "$birthday (возраст $age)"
            }
        }
    }
    //вычисление возраста
    private fun calculateAge(birthday: String): Int {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val birthDate = dateFormat.parse(birthday)
            val birthCalendar = Calendar.getInstance()
            birthCalendar.time = birthDate

            val currentCalendar = Calendar.getInstance()

            var age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
            if (currentCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (currentCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        currentCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))
            ) {
                age-- // Если день рождения еще не был в текущем году
            }
            return age
    }
    //////////////////////////////////////MarketFragment///////////////////////////////////////////
    //добавил LiveData для денег
    private val _money = MutableLiveData<Int>()
    val money: LiveData<Int> get() = _money

    // Загружаем профиль и устанавливаем деньги в LiveData   (надо обдумать)
    fun loadMoneyFromDatabase() {
        viewModelScope.launch {
            val profile = Repository.getProfile()
            _money.value = profile?.money ?: 0  // Устанавливаем деньги, если профиль найден
        }
    }
    // Функция для обновления денег после покупки               (надо обдумать)
    fun updateMoney(updatedMoney: Int) {
        viewModelScope.launch {
            val profile = Repository.getProfile()
            profile?.let {
                val updatedProfile = it.copy(money = updatedMoney)
                Repository.updateProfile(updatedProfile)
                _money.value = updatedMoney
            }
        }
    }
    //получение Json
    fun loadMarketData(context: Context): List<Item> {
        val inputStream = context.assets.open("database.json")
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val marketResponse = gson.fromJson(reader, MarketResponse::class.java)
        return marketResponse.items
    }
}