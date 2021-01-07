package otus.amogilevskiy.util

interface ETagValueConverter {

    fun parse(tag: String): String

    fun wrap(value: String): String

}