package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.AdapterData
import com.example.myapplication.data.remote.api.Resource
import com.example.myapplication.io.ApiInteractor
import com.example.myapplication.model.ItemApp
import kotlinx.coroutines.launch

/**
 * Created by Samira Salah
 */
class FirstViewModel(private val apiRepository: ApiInteractor) : BaseViewModel() {
    lateinit var adapter: AdapterData
    private val itemsLiveData: LiveData<PagedList<ItemApp>>
    val loadMoreProgressLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {

        val activitiesDataFactory = ActivitiesDataFactory()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(1)
            .setPageSize(20)
            .build()

        itemsLiveData = LivePagedListBuilder(activitiesDataFactory, pagedListConfig)
            .build()

        itemsLiveData.observeForever { activities ->
            adapter.submitList(activities)
        }

    }

    fun refreshList() {
        itemsLiveData.value?.dataSource?.invalidate()
    }

    inner class ActivityDataPagedSource : PositionalDataSource<ItemApp>() {
        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<ItemApp>
        ) {
            resourceLiveData.postValue(Resource.Loading)
            getData(1) {
                callback.onResult(it, 1)
            }
        }

        override fun loadRange(
            params: LoadRangeParams,
            callback: LoadRangeCallback<ItemApp>
        ) {
            loadMoreProgressLiveData.postValue(true)
            getData((adapter.itemCount / 20) + 1) {
                callback.onResult(it)
                loadMoreProgressLiveData.postValue(false)
            }
        }

    }

    fun getData(page: Int, callback: (ArrayList<ItemApp?>) -> Unit) {
        viewModelScope.launch {

            apiRepository.getData(page).let {
                resourceLiveData.value = it
                if (it is Resource.Success) callback.invoke(it.value.results)
            }

        }
    }

    fun getAdapterMethode(recyclerView: RecyclerView, callback: (ItemApp) -> Unit) {
        adapter = AdapterData(callback)
        recyclerView.adapter = adapter
    }

    inner class ActivitiesDataFactory : DataSource.Factory<Int, ItemApp>() {
        override fun create(): DataSource<Int, ItemApp> {
            return ActivityDataPagedSource()
        }
    }
}
