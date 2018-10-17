package com.angad.apptour.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan

class Helpers {

    companion object {
        /**
         * bold the text
         * @param str: string to make bold
         * @param start: start index
         * @param end: end index
         */
        fun makeTextBold(str:String, start:Int, end:Int): SpannableStringBuilder {
            val sb = SpannableStringBuilder(str)

            val bss = StyleSpan(Typeface.BOLD)
            sb.setSpan(bss, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            return sb
        }
    }
}