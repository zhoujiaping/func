package guava

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.google.common.cache.LoadingCache
import com.google.common.cache.RemovalNotification

import java.util.concurrent.TimeUnit

def testCache1(){
    Cache cache = CacheBuilder.newBuilder().concurrencyLevel(Runtime.runtime.availableProcessors())
            .initialCapacity(10_000)
            .maximumSize(100_000)
            .recordStats()
            .weakKeys()
            .weakValues()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build()

    def map = [
            zh:'中国',
            us:'美国'
    ]
    def key = 'zh'
    println cache.get(key){
        //可以定义key的计算逻辑
        map[key]
    }
}
/**
 * 如果没有对key有特殊的计算逻辑,推荐用这种方式,这样缓存一致性更好维护
 * */
def testCache2(){
    def map = [
            zh:'中国',
            us:'美国'
    ]
    LoadingCache cache = CacheBuilder.newBuilder().concurrencyLevel(Runtime.runtime.availableProcessors())
            .initialCapacity(10_000)
            .maximumSize(100_000)
            .recordStats()
            .weakKeys()
            .weakValues()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .removalListener(){
                RemovalNotification notification->
                    println "${notification.key},${notification.value} be removal..."
            }
            .build(){
                key->
                    map[key]
            }

    def key = 'zh'
    println cache.get(key)
}
