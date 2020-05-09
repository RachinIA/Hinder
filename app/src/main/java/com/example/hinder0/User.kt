package com.example.hinder0

class User {
    var id: Int? = null
    var fio: String? = null
    var bio: String? = null
    var mail: String? = null
    var phone: String? = null
    var likeroom: Array<Int>? = null
    var ownroom: Array<Int>? = null
    var age = 0

    fun User() {}

    fun User(
        fio: String?,
        bio: String?,
        mail: String?,
        phone: String?,
        likeroom: Array<Int>?,
        ownroom: Array<Int>?,
        age: Int
    ) {
        this.fio = fio
        this.bio = bio
        this.mail = mail
        this.phone = phone
        this.likeroom = likeroom
        this.ownroom = ownroom
        this.age = age
    }
}