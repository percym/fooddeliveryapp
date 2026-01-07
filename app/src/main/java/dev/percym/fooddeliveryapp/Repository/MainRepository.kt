package dev.percym.fooddeliveryapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.percym.fooddeliveryapp.Domain.BannerModel

class MainRepository {
    private val firebaseDatabase= FirebaseDatabase.getInstance()

    fun loadBanner(): LiveData<MutableSet<BannerModel>> {
        val listData= MutableLiveData<MutableSet<BannerModel>>()
        val ref= firebaseDatabase.getReference("Banners ")

        ref.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableSetOf<BannerModel>()
                    for (dataSnapshot in snapshot.children) {
                        val item = dataSnapshot.getValue(BannerModel::class.java)
                        item?.let { list.add(item) }
                    }
                    listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    return listData

    }


}