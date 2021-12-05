package com.example.last.ui.home

import android.util.Log
import java.util.*

class calLayout {


    /* Calendar는 월이 0~11월로 구성되어있음. */
    //객체화
    val calendar = Calendar.getInstance()
    var dateList = arrayListOf<Int>()

    val getMonth = Calendar.MONTH

    companion object{
        //7일 - 행 수
        const val WEEK = 7
        //열 수
        const val calRow = 6


    }
    var prevTailOffset = 0
    var currentOffset = 0
    var nextHeadOffset = 0

    init {
        calendar.time = Date()
    }

    fun initCal(refreshCallback: (Calendar)->Unit){
        completeCalendar(refreshCallback)
    }
    /**
        이전 달 날짜 표시
     */

    private fun makePrev(calendar: Calendar){
        Log.i("makePrev", "실행") //완료

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        Log.i("Calendar.MONTH:"+Calendar.MONTH.toString(), "calendar.get(Calendar.MONTH)-1:"+(calendar.get(Calendar.MONTH)-1).toString())
        //마지막날
        val maxDate =  calendar.getActualMaximum(Calendar.DATE) //통과
        //전날의마지막날
        var maxOffsetDate = maxDate - prevTailOffset // ??
        Log.i("maxDate -> ", maxDate.toString()) //30

        Log.i("maxOffsetDate -> ", maxOffsetDate.toString()) // 30......

        for (i in 1..prevTailOffset) {
            dateList.add(++maxOffsetDate)
            //Log.i("dateList -> ", dateList.get(prevTailOffset).toString())
        }
    }
    /**
     현재 날짜 설정 표시
     */
    private fun makeCurr(calendar: Calendar){
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) {
            dateList.add(i)
        }
    }

    /**
     다음달 날짜 표시
     */
    private fun makeNext(){
        var date = 1
        for (i in 1..nextHeadOffset) {
            dateList.add(date++)
        }
    }

    //달력 로직 만들기 + 새로고침
    private fun completeCalendar(refreshCallback: (Calendar) -> Unit){
        //캘린더 만들 때 초기화 하고 시작
        dateList.clear()

        //날짜 1부터 시작
        calendar.set(Calendar.DATE, 1)

        /* 날짜 초기화 */
        //마지막 날
        currentOffset = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        //이전
        prevTailOffset = calendar.get(Calendar.DAY_OF_WEEK) - 1
        Log.i("calendar.get(Calendar)", prevTailOffset.toString()) //통과; 5

        makePrev(calendar.clone() as Calendar)
        makeCurr(calendar)

        //이후 - 행 * 열(48) - (이전 달 마지막 날 + 현재 날짜)
        nextHeadOffset = WEEK * calRow - (prevTailOffset + currentOffset)

        makeNext()


        refreshCallback(calendar)
    }

    //다음 달로 넘어가기
    fun goNext(refreshCallback: (Calendar)->Unit){
        //현재 날짜가 12월일 때 년도 증가
        if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER){
            //현재 년도에 +1한 값 설정
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
            //해당 달 최소값 초기화(0~11 -> 1~12)
            calendar.set(Calendar.MONTH, 0)
        }else {
            //현재 달 + 1
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
        }
        //새로고침
        completeCalendar(refreshCallback)
    }
    //이전 달로 넘어가기
    fun goPrev(refreshCallback: (Calendar)->Unit){
        //현재 달 값이 0(최소값)일 때
        if(calendar.get(Calendar.MONTH) == 0){
            //현재 년도 -1
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
            //해당 달 12월 설정
            calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        }else {
            //현재 달 - 1
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        }
        //새로고침
        completeCalendar(refreshCallback)
    }
}