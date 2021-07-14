package guava

import com.google.common.primitives.Longs

def converter = new Longs.LongConverter()

assert converter.convert("123") == 123