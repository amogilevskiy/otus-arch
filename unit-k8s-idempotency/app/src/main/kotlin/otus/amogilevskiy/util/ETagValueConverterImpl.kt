package otus.amogilevskiy.util

import org.springframework.stereotype.Service

@Service
class ETagValueConverterImpl : ETagValueConverter {

    override fun parse(tag: String): String {
        return tag.replace("\"", "")
    }

    override fun wrap(value: String): String {
        return "\"$value\""
    }
}