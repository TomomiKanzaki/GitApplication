package com.example.gitapplication.util

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.Serializable
import java.net.UnknownHostException

/**
 * Call api default
 * @param handleCall func will execute on Job Context or [CoroutineScope]
 */
fun <T> CoroutineScope.callApiDefault(response: (response: DataResponse<T>) -> Unit,
                                      handleCall: () -> Deferred<T>) {
    this.launch(Dispatchers.Main) {
        try {
            response.invoke(DataResponse.loading())
            val result = (withContext(this@callApiDefault.coroutineContext) {
                handleCall().await()
            })
            response.invoke(DataResponse.success(result))
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            response.invoke(DataResponse.error(throwable))
        }
        response.invoke(DataResponse.complete())
    }
}
