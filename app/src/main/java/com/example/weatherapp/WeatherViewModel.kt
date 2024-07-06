package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.Constant
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    // Получаем экземпляр API для работы с сетевыми запросами
    private val weatherApi = RetrofitInstance.weatherApi
    // Создаем MutableLiveData для хранения результата сетевого запроса
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    // Преобразуем MutableLiveData в LiveData, чтобы его могли наблюдать другие компоненты
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city : String) {

        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(
                    Constant.apiKey,
                    city
                )  // Выполняем сетевой запрос к API с использованием ключа и названия города
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed")
                }
            }
            catch (e: Exception){
                _weatherResult.value = NetworkResponse.Error("Failed")
            }
        }
    }
}