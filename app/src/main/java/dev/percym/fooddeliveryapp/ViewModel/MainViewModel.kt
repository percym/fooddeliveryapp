package dev.percym.fooddeliveryapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.percym.fooddeliveryapp.Domain.BannerModel
import dev.percym.fooddeliveryapp.Domain.CategoryModel
import dev.percym.fooddeliveryapp.Repository.MainRepository


class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    fun loadBanner(): LiveData<List<BannerModel>> {
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<List<CategoryModel>> {
        return repository.loadCategory()
    }
}