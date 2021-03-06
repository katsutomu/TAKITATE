package io.github.reservationbytom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.reservationbytom.BuildConfig
import io.github.reservationbytom.service.model.GNaviResponse
import io.github.reservationbytom.service.repository.GNaviRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestListViewModel(application: Application) : AndroidViewModel(application) {
  private val repository = GNaviRepository.instance // TODO: Factoryの省略ルールについて

  // LiveDataについて: https://qiita.com/takahirom/items/3f012d46e15a1666fa33
  // TODO: ObservableField と比較検討
  var restListLiveData: MutableLiveData<GNaviResponse> = MutableLiveData()

  init {
    loadRests()
    println("In RestListViewModel iniload!!")
  }

  private fun loadRests() {
    println("Let's Get LiveData!!")
    // Coroutine that will be canceled when the ViewModel is cleared.

    viewModelScope.launch {
      println("res: STARTING........")
      val data: GNaviResponse = repository.getRests(
        BuildConfig.GNAVI_API_KEY,
        1,
        139.6353565, // TODO: 外部から取得
        35.6994197 // TODO: 外部から取得
      )
      restListLiveData.postValue(data)
    }
  }
}
