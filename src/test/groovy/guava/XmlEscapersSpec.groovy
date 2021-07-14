package guava

import com.google.common.escape.Escaper
import com.google.common.xml.XmlEscapers
import spock.lang.Specification

/**
 * xml内容转义,属性转义
 * */
class XmlEscapersSpec extends Specification{

    void testXmlContentEscaper(){
        given:

        Escaper escaper = XmlEscapers.xmlContentEscaper()
        when:
        def content = escaper.escape("<hello>")
        println content
        then:
        content == '&lt;hello&gt;'
    }

    void testXmlAttributeEscaper(){
        given:

        Escaper escaper = XmlEscapers.xmlAttributeEscaper()
        when:
        def content = escaper.escape("<hello>")
        println content
        then:
        content == '&lt;hello&gt;'
    }
}
