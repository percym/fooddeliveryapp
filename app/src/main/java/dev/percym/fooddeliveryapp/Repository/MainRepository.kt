package dev.percym.fooddeliveryapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.percym.fooddeliveryapp.Domain.BannerModel
import dev.percym.fooddeliveryapp.Domain.CategoryModel

class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanner(): LiveData<List<BannerModel>> {
        val listData = MutableLiveData<List<BannerModel>>()
        val ref = firebaseDatabase.getReference("Banners")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BannerModel>()
                if (snapshot.exists()) {
                    for (child in snapshot.children) {
                        val item = child.getValue(BannerModel::class.java)
                        if (item != null && !item.image.isNullOrBlank()) {
                            list.add(item)
                        } else {
                            Log.d(
                                "MainRepository",
                                "Skipping banner at ${child.key}: missing or empty image"
                            )
                        }
                    }
                } else {
                    Log.d("MainRepository", "No banners found at Banners reference")
                }
                listData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(
                    "MainRepository",
                    "loadBanner cancelled: ${error.message}",
                    error.toException()
                )
            }
        })

        return listData
    }


    fun loadCategory(): LiveData<List<CategoryModel>> {
        val listData = MutableLiveData<List<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                if (!snapshot.exists()) {
                    Log.d("MainRepository", "No categories found")
                } else {
                    for (child in snapshot.children) {
                        val item = child.getValue(CategoryModel::class.java)
                        if (item != null) {
                            list.add(item)
                            Log.d("MainRepository", "Category: ${item.Name}, Image: ${item.ImagePath}")
                        }
                    }
                }
                listData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainRepository", "loadCategory cancelled: ${error.message}", error.toException())
            }
        })

        return listData
    }
}
