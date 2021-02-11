package com.ylozhangaf.ppjoke.utils

class StringConvert{
    companion object{
        fun convertFeedUgc(count : Int) : String {
            if (count<10000) {
                return count.toString()
            }

            return "${count/10000}万"
        }
    }
}