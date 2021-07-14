package guava

import com.google.common.base.Ascii
import spock.lang.Specification

/**
 * 字符串截取
 * */
class AsciiSpec extends Specification{

    void testToLowerCase(){
        when:
        def str = "İ"
        str.toLowerCase() == 'i'
        Ascii.toLowerCase(str) == str
        then:
        noExceptionThrown()
    }
    void testTruncate(){
        when:
        def msg = Ascii.truncate('hello world! hello java! hello groovy!',10,'...')
        then:
        msg == 'hello w...'
    }
    void testTruncateCN(){
        when:
        def msg = Ascii.truncate('嘿,蛋炒饭,既简单又困难,饭要粒粒分开',10,'...')
        then:
        msg == '嘿,蛋炒饭,既...'
    }
}
