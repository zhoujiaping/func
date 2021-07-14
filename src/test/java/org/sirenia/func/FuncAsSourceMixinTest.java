package org.sirenia.func;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sirenia.func.tuple.Tuple2;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.sirenia.func.Func.*;

/**
 * tests
 */
public class FuncAsSourceMixinTest {

    @Test
    public void testArrayOf() {
        Integer[] intArr = arrayOf(Integer.class, 1, 2, 3);
        Assertions.assertArrayEquals(intArr, new Integer[]{1, 2, 3});

        val strArr = arrayOf(String.class, "hello", "world");
        Assertions.assertArrayEquals(strArr, new String[]{"hello", "world"});
    }

    @Test
    public void testNewArray() {
        String[] arr = newArray(String.class, 10);
        Assertions.assertArrayEquals(arr, new String[10]);
    }

    @Test
    public void testArrayStream() {
        List<String> list = listOf();
        stream(arrayOf(String.class, "hello", "world"))
                .forEach(it -> list.add(it));
        Assertions.assertTrue(list.size() == 2);
        Assertions.assertEquals(list.get(0), "hello");
        Assertions.assertEquals(list.get(1), "world");
    }

    @Test
    public void testEachWithIndex4Array() {
        List<String> list = listOf();
        eachWithIndex(arrayOf(String.class, "one", "two"), (i, it) -> {
            list.add(i + "=>" + it);
        });
        Assertions.assertTrue(list.size() == 2);
        Assertions.assertEquals(list.get(0), "0=>one");
        Assertions.assertEquals(list.get(1), "1=>two");
    }

    @SneakyThrows
    @Test
    public void testProxy() {
        List<String> calls = listOf();
        Class<? extends User> proxyClass = proxy(User.class, (target, method, superMethod, args) -> {
            calls.add(format("%s(%s)", method.getName(), Arrays.toString(args)));
            if (method.getName().equals("getName")) {
                return "JOKER";
            }
            return sneakySupplier(() -> superMethod.invoke(target, args)).get();
        });
        User user = proxyClass.newInstance();
        user.setName("joker");
        String name = user.getName();
        Assertions.assertEquals(name, "JOKER");
        //this will not call proxy method.
        println(user.hello());
        //this will call proxy method.
        println(proxyClass.getDeclaredMethod("hello").invoke(null));
        Assertions.assertEquals(calls.toString(), "[setName([joker]), getName([]), hello([])]");
    }

    @Test
    public void testListOf() {
        val list = listOf("hello", "java");
        Assertions.assertEquals(list.size(), 2);
        Assertions.assertEquals(list.get(0), "hello");
        Assertions.assertEquals(list.get(1), "java");

        List<String> list0 = listOf();
        Assertions.assertEquals(list0.size(), 0);
    }

    @Test
    public void testSetOf() {
        val set = setOf("hello", "java");
        Assertions.assertEquals(set.size(), 2);
        Assertions.assertTrue(set.contains("hello"));
        Assertions.assertTrue(set.contains("java"));
    }

    @Test
    public void testMapOf() {
        Map<Integer, String> map0 = mapOf(1, "ONE",
                2, "TWO",
                3, "THREE"
        );
        Assertions.assertEquals("{1=ONE, 2=TWO, 3=THREE}", map0.toString());

        val map = mapOf("am", 'a',
                "bm", 'b',
                "cm", 'c');
        Assertions.assertEquals(map.size(), 3);
        Assertions.assertEquals(map.get("am"), 'a');
        Assertions.assertEquals(map.get("bm"), 'b');
        Assertions.assertEquals(map.get("cm"), 'c');
    }

    @Test
    public void testZip() {
        val keys = listOf("am", "bm", "cm");
        val values = listOf('a', 'b', 'c');
        val map = zip(keys, values);

        Assertions.assertEquals(map.size(), 3);
        Assertions.assertEquals(map.get("am"), 'a');
        Assertions.assertEquals(map.get("bm"), 'b');
        Assertions.assertEquals(map.get("cm"), 'c');
    }

    @Test
    public void testUnzip() {
        val map = mapOf("am", 'a',
                "bm", 'b',
                "cm", 'c');
        val pairs = unzip(map);
        Assertions.assertEquals(pairs._1, listOf("am", "bm", "cm"));
        Assertions.assertEquals(pairs._2, listOf('a', 'b', 'c'));
    }

    @Test
    public void testConcat() {
        val list1 = listOf("A", "B", "C");
        val list2 = listOf("D", "E");
        val list3 = concat(list1, list2);
        Assertions.assertTrue(list3.size() == 5);
        Assertions.assertEquals(list3.get(0), "A");
        Assertions.assertEquals(list3.get(1), "B");
        Assertions.assertEquals(list3.get(2), "C");
        Assertions.assertEquals(list3.get(3), "D");
        Assertions.assertEquals(list3.get(4), "E");

        Assertions.assertTrue(list1.size() == 3);
        Assertions.assertTrue(list2.size() == 2);
    }

    @Test
    public void testReverse() {
        val list1 = listOf("A", "B", "C");
        Assertions.assertEquals(reverse(list1), listOf("C", "B", "A"));
    }

    @Test
    public void testInverse() {
        val list1 = listOf("A", "B", "C");
        val list2 = listOf("a", "b", "c");
        val map = zip(list1, list2);
        val pairs = unzip(inverse(map));
        Assertions.assertEquals(pairs._1, list2);
        Assertions.assertEquals(pairs._2, list1);
    }

    @Test
    public void testSlice() {
        val list = listOf("1", "2", "3", "4", "5");
        val list2 = slice(list, -4, -2);
        Assertions.assertIterableEquals(list2, listOf("2", "3", "4"));
        Assertions.assertIterableEquals(slice(list, 0, -1), list);
    }

    @Test
    public void testDiff() {
        val list1 = listOf("a", "b", "c");
        val set1 = setOf("a", "c", "d");
        Assertions.assertTrue(diff(list1, set1).containsAll(listOf("b")));
        Assertions.assertTrue(diff(list1, set1).size() == 1);
        Assertions.assertTrue(diff(set1, list1).containsAll(listOf("d")));
        Assertions.assertTrue(diff(set1, list1).size() == 1);
    }

    @Test
    public void testIntersect() {
        val list1 = listOf("a", "b", "c");
        val set1 = setOf("a", "c", "d");
        Assertions.assertTrue(intersect(list1, set1).containsAll(listOf("a", "c")));
        Assertions.assertTrue(intersect(list1, set1).size() == 2);
    }

    @Test
    public void testUnion() {
        val list1 = listOf("a", "b", "c");
        val set1 = setOf("a", "c", "d");
        Assertions.assertTrue(union(list1, set1).containsAll(listOf("a", "c", "d", "b")));
        Assertions.assertTrue(union(list1, set1).size() == 4);
    }

    @Test
    public void testEachWithIndex4Iter() {
        val list = listOf("A", "B", "C", "D");
        val result = listOf();
        eachWithIndex(list, (i, it) -> {
            result.add(i + "=>" + it);
        });
        Assertions.assertIterableEquals(result, listOf("0=>A", "1=>B", "2=>C", "3=>D"));
    }

    @Test
    public void testRangeTo() {
        //int range
        Assertions.assertIterableEquals(range(1, 10, 1)
                .collect(Collectors.toList()), listOf(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Assertions.assertIterableEquals(range(1, 10, 2)
                .collect(Collectors.toList()), listOf(1, 3, 5, 7, 9));
        Assertions.assertIterableEquals(range(20, 27, 3)
                .collect(Collectors.toList()), listOf(20, 23, 26));
        //int range
        Assertions.assertIterableEquals(range(10, 1, 1)
                .collect(Collectors.toList()), listOf(10, 9, 8, 7, 6, 5, 4, 3, 2)
        );
        //char range
        Assertions.assertEquals(rangeTo('a', 'e', 1)
                .map(it -> it.toString())
                .collect(Collectors.joining(",")), "a,b,c,d,e"
        );
        Assertions.assertEquals(rangeTo('z', 'u', 1)
                .map(it -> it.toString())
                .collect(Collectors.joining(",")), "z,y,x,w,v,u"
        );
    }


    @Test
    public void testChunk() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        val chunks = chunk(list, 3);
        Assertions.assertEquals(chunks.size(), 4);
        Assertions.assertIterableEquals(chunks.get(0), listOf(1, 2, 3));
        Assertions.assertIterableEquals(chunks.get(1), listOf(4, 5, 6));
        Assertions.assertIterableEquals(chunks.get(2), listOf(7, 8, 9));
        Assertions.assertIterableEquals(chunks.get(3), listOf(10, 11));
    }

    @Test
    public void testFlatten() {
        val list = listOf(1, listOf(2, 3, listOf(4, 5, 6)), 7, listOf(8));
        val flattenList = flatten(list);
        Assertions.assertIterableEquals(flattenList, listOf(1, 2, 3, 4, 5, 6, 7, 8));
    }

    @Test
    public void testToDate() {
        /**
         * String,Date,LocalDate,LocalDateTime转换
         * */
        val date1 = toDate("2021/06/26 18:30", "yyyy/MM/dd HH:mm");
        val date2 = toDate(toLocalDate("2021/06/26", "yyyy/MM/dd"));
        //基于默认格式，更简洁
        val date3 = toDate("2021-06-26T18:30:21");
        val date4 = toDate(toLocalDateTime("2021/06/26 18:30", "yyyy/MM/dd HH:mm"));

        Assertions.assertEquals(date1, Date.from(LocalDateTime.of(2021, 06, 26, 18, 30).atZone(ZoneId.systemDefault()).toInstant()));
        Assertions.assertEquals(date2, Date.from(LocalDate.of(2021, 06, 26).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        Assertions.assertEquals(date3, Date.from(LocalDateTime.of(2021, 06, 26, 18, 30, 21).atZone(ZoneId.systemDefault()).toInstant()));
        Assertions.assertEquals(date4, Date.from(LocalDateTime.of(2021, 06, 26, 18, 30).atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Test
    public void testMemoize() {
        //lazy init，cache
        LinkedList<String> list = cast(listOf("a"));
        Supplier<String> supplier = memoize(() -> {
            String value = list.getFirst();
            list.addFirst("b");
            return value;
        });
        String value = supplier.get();
        Assertions.assertEquals(value, "a");
        Assertions.assertEquals(supplier.get(), "a");
        Assertions.assertEquals(supplier.get(), "a");
    }

    @Test
    public void testFunctionException() {
        /**
         * the exception type has been replaced.
         * */
        Assertions.assertThrows(RuntimeException.class, () -> {
            listOf(".").forEach(it -> {
                try {
                    Files.readAllLines(Paths.get(it), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        /**
         * the exception is original exception.
         * */
        Assertions.assertThrows(IOException.class, () -> {
            listOf(".").forEach(sneakyConsumer(it ->
                    Files.readAllLines(Paths.get(it), StandardCharsets.UTF_8)
            ));
        });
    }


    @Test
    public void testOrElse4String() {
        String str1 = orElse(null, "ok");
        Assertions.assertEquals(str1, "ok");
        String str2 = orElse("a", "ok");
        Assertions.assertEquals(str2, "a");
    }


    @Test
    public void testPrintln() {
        println("hello world");
    }

    @Test
    public void testPrintf() {
        /**
         * System.out#printf will throw exception when the arguments is not enough.
         * but guava Strings#lenientFormat is ok.
         * */
        printf("%s,%s", 1, 2, 3);
        printf("%s,%s,%s", 1, 2);//ok
        Assertions.assertThrows(Exception.class, () -> {
            System.out.printf("%s,%s,%s", 1, 2);//error
        });
    }

    @Test
    public void testInstanceOf() {
        Assertions.assertTrue(isInstanceOf("", CharSequence.class));
    }

    @Test
    public void testRepeat() {
        /**
         * we need not count starts any more.
         * */
        String str = repeat("*", 10);
        Assertions.assertEquals(str.length(), 10);
        Assertions.assertEquals(str, "**********");
    }

    @Test
    public void testCast() {
        //the 1st edition
        List<List<String>> listOfList0 = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("hello");
        Object obj1 = listOfList0;
        List<List<String>> listOfList1 = (List<List<String>>) obj1;
        System.out.println(listOfList1);

        //the 2nd edition
        Object obj2 = listOf(listOf("hello"));
        List<List<String>> listOfList2 = cast(obj2);
        println(listOfList2);

        //the 3rd edition
        List<List<String>> listOfList3 = listOf(listOf("hello"));
        println(listOfList3);

        //the 4th edition
        println(listOf(listOf("hello")));
    }

    @Test
    public void testTuple() {
        val tuple3 = tuple("zhou", 20, listOf("a", "b"));
        Assertions.assertEquals(tuple3.toString(), "Tuple3(_1=zhou, _2=20, _3=[a, b])");
    }

    @Test
    public void testDict() {
        val dict = dict(String.join("\n",
                " Content-Type: application/json",
                " X-Request-With: ajax ",
                "  # this is comment ",
                " token: ",
                " t"
        ), ':');
        Assertions.assertEquals(dict.size(), 4);
        Assertions.assertEquals(dict.get("Content-Type"), "application/json");
        Assertions.assertEquals(dict.get("X-Request-With"), "ajax");
        Assertions.assertEquals(dict.get("token"), "");
        Assertions.assertEquals(dict.get("t"), "");
    }


    @Test
    public void testLetAndAlso() {
        //code is separated at different block, it makes read easy.
        List<String> list1 = let(listOf("hello"), it -> {
            it.add("one");
            it.add("two");
            it.add("three");
        });
        Assertions.assertEquals(list1, listOf("hello", "one", "two", "three"));

        List<String> list2 = also(listOf("HELLO"), it -> {
            it.add("one");
            it.add("two");
            it.add("three");
            return it;
        });
        Assertions.assertEquals(list2, listOf("HELLO", "one", "two", "three"));

        User user = let(new User(), it -> {
            it.name = "zhou";
            it.age = 18;
        });

        Assertions.assertEquals(user.name, "zhou");
        Assertions.assertEquals(user.age, 18);

    }

    @Test
    public void testAsBool() {
        val values1 = listOf("", null, 0, 0.0, 0.0f, new BigDecimal("0.00"), new BigInteger("0"), false, newArray(String.class, 0), listOf(), '\0', '\u0000', mapOf());
        println(values1.stream().map(it -> asBoolean(it)).collect(Collectors.toList()));
        Assertions.assertTrue(values1.stream().allMatch(it -> !asBoolean(it)));

        val values2 = listOf(" ", new Object(), 1, 0.1, 0.2f, true, Optional.ofNullable(null), newArray(String.class, 1), listOf(0), ' ', mapOf(1, 2));
        println(values2.stream().map(it -> asBoolean(it)).collect(Collectors.toList()));
        Assertions.assertTrue(values2.stream().allMatch(it -> asBoolean(it)));

    }

    @Test
    public void testCopy() {
        //copy a object witch implemented Serializable.
        User user1 = let(new User(), it -> {
            it.name = "zhou";
            it.age = 18;
        });
        User user2 = copy(user1);
        user2.age = 20;
        Assertions.assertEquals(user2.name, "zhou");
        Assertions.assertEquals(user1.age, 18);
    }

    @Test
    public void testForceNew() {
        val tuple2 = forceNewInstance(Tuple2.class);
        println(tuple2._1);
        println(tuple2._2);
    }

    @Test
    public void testPadStart() {
        //such as generate a order no.
        Assertions.assertEquals(padStart("1", 6, '0'), "000001");
    }

    @Test
    public void testTruncate() {
        val str = truncate("Apache Groovy is a powerful, optionally typed and dynamic language.",
                20, "...");
        Assertions.assertEquals(str, "Apache Groovy is ...");
    }


    @Test
    public void testForceGetAndSet() {
        /**
         *
         * */
        val list = listOf(
                mapOf("my.friends", listOf(
                        let(new User(), it -> {
                            it.name = "jay";
                        }),
                        let(new User(), it -> it.name = "chou")
                ))
        );
        //index support array,list.
        //key support object,map.
        //field name support object.
        val name1 = field(list, "[0]['my.friends'][1]['name']").value;
        field(list, "[0]['my.friends'][1].name").setter.accept("neo");
        val name2 = field(list, "[0]['my.friends'][1].name").value;
        Assertions.assertEquals(name1, "chou");
        Assertions.assertEquals(name2, "neo");

    }


    @Test
    public void testCovariant() {
        //the 1st edition
        val users = listOf(
                let(new User(), it -> it.name = "puck"),
                let(new User(), it -> it.name = "am")
        );
        //the 2nd edition
        List<Person> persons = cast(users);
        List<Person> persons2 = (List<Person>) (Object) users;
        //
        println(persons);
        println(persons2);
        //except no exception throws
    }

    @Test
    public void testInvokeMethod() {
        /**spring's invokeMethod，we need findMethod before.
         * but findMethod match the method name, and exactly argument types.
         *
         * what if i don't know the argument types?
         * even if we can call getClass, and what if the argument value is null?
         *
         * and what if the actual argument type is the subclass of virtual argument type.
         *
         * we need a method, like responseTo in groovy lang.
         */
        //no arguments
        val methods = responseTo(this.getClass(), "testToDate");
        methods.forEach(it -> println(invokeMethod(it, this)));
        //actual argument is null
        Object[] args = new Object[]{1};
        val methods2 = responseTo(this.getClass(), "hello", args);
        methods2.forEach(it -> println(invokeMethod(it, this, args)));
    }

    private String hello(int name) {
        return "hello " + name;
    }

    private String hello(String name) {
        return "hello " + name;
    }

    private String hello(String name, String name2) {
        return "hello " + name;
    }

    @Test
    @SneakyThrows
    public void testSetTimeout(){
        val future = setTimeout(1000,()->{
            return "timeout";
        });
        Assertions.assertEquals(future.get(),"timeout");
    }

}
