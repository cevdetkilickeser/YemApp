package com.cevdetkilickeser.yemapp.data.entity

data class UserInfo(var image:String = "",
                    var name:String = "",
                    var address:String = "",
                    var district:String = "",
                    var city:String = "") {
    // Parametresiz constructor ekleyin
    constructor() : this("", "", "","","")
}