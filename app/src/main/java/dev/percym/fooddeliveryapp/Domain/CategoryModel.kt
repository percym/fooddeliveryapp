package dev.percym.fooddeliveryapp.Domain

data class CategoryModel(
    var Id: Int ,
    var ImagePath: String,
    var Name: String ,

)
{
    constructor() : this(0, "", "")
}