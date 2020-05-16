package com.souptik.maiti.goscaleassignment.utils

data class Resource<out T> (val data: T?, val status: Status, val msg: String?) {

    companion object{

        fun <T> success(data: T?): Resource<T>{
            return Resource(data, Status.SUCCESS, null)
        }

        fun <T> error(data: T?, msg:String): Resource<T>{
            return Resource(data, Status.ERROR, msg)
        }

        fun <T> loading(data: T?): Resource<T>{
            return Resource(data, Status.LOADING, null)
        }

        fun <T> noData(data: T?): Resource<T>{
            return Resource(data, Status.NODATA, null)
        }

        fun <T> reachEnd(data: T?): Resource<T>{
            return Resource(data, Status.REACHEND, null)
        }

    }
}