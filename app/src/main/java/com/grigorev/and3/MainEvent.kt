package com.grigorev.and3

interface MainEvent

class LoadNewsEvent(val category: String) : MainEvent