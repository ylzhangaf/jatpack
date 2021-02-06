package com.ylozhangaf.ppjoke.model

class BottomBar {
    var activeColor : String = ""
    var inActiveColor : String = ""
    var tabs = mutableListOf<Tab>()
    var selectTab : Int = 0 // 默认选中项
}

class Tab{
    var size : Int = 0
    var enable : Boolean = false
    var index : Int = 0
    var pageUrl : String = ""
    var title : String = ""
    var tintColor : String = ""
}