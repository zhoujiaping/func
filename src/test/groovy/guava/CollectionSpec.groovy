package guava

import com.google.common.collect.*
import spock.lang.Specification

import java.util.concurrent.ConcurrentMap
import java.util.stream.Collector
import java.util.stream.IntStream

class CollectionSpec extends Specification {
    void testLists() {
        when:
        println Lists.newArrayList(1, 2, 3)

        println Lists.transform([1,2,3,4]){
            it*2
        }

        println([1,2,3,4].collect{it*2})
        then:
        noExceptionThrown()
    }

    void testSets() {
        when:
        println(Sets.newTreeSet([1, 2, 3]))
        then:
        noExceptionThrown()
    }

    void testSetsOp() {
        when:
        def goodMan = Sets.newHashSet(['jay', 'jj', 'zhou'])
        def beautifulMan = Sets.newHashSet(['jay','ping'])
        then:
        Sets.difference(goodMan,beautifulMan) == ['jj','zhou'] as Set
        Sets.intersection(goodMan,beautifulMan) == ['jay'] as Set
        Sets.union(goodMan,beautifulMan) == ['jay','jj','zhou','ping'] as Set
        Sets.symmetricDifference(goodMan,beautifulMan) == ['jj','zhou','ping'] as Set
    }

    void testBiMap() {
        given:
        def map = HashBiMap.create()
        when:
        map.put("a", "A")
        def map2 = map.inverse()
        println map2
        then:
        map2['A'] == 'a'
    }

    void testTables() {
        when:
        /*
         *  Company: IBM, Microsoft, TCS
         *  IBM         -> {101:Mahesh, 102:Ramesh, 103:Suresh}
         *  Microsoft     -> {101:Sohan, 102:Mohan, 103:Rohan }
         *  TCS         -> {101:Ram, 102: Shyam, 103: Sunil }
         *
         * */
        //create a table
        Table<String, String, String> employeeTable = HashBasedTable.create();
        // new HashMap<String,Map<String,String>>();

        //initialize the table with employee details
        employeeTable.put("IBM", "101","Mahesh");
        employeeTable.put("IBM", "102","Ramesh");
        employeeTable.put("IBM", "103","Suresh");

        employeeTable.put("Microsoft", "111","Sohan");
        employeeTable.put("Microsoft", "112","Mohan");
        employeeTable.put("Microsoft", "113","Rohan");

        employeeTable.put("TCS", "121","Ram");
        employeeTable.put("TCS", "102","Shyam");
        employeeTable.put("TCS", "123","Sunil");

        //所有行数据
        System.out.println(employeeTable.cellSet());
        //所有公司
        System.out.println(employeeTable.rowKeySet());
        //所有员工编号
        System.out.println(employeeTable.columnKeySet());
        //所有员工名称
        System.out.println(employeeTable.values());
        //公司中的所有员工和员工编号
        System.out.println(employeeTable.rowMap());
        //员工编号对应的公司和员工名称
        System.out.println(employeeTable.columnMap());
        //row+column对应的value
        System.out.println(employeeTable.get("IBM","101"));
        //IBM公司中所有信息
        Map<String,String> ibmEmployees =  employeeTable.row("IBM");

        System.out.println("List of IBM Employees");
        for(Map.Entry<String, String> entry : ibmEmployees.entrySet()){
            System.out.println("Emp Id: " + entry.getKey() + ", Name: " + entry.getValue());
        }

        //table中所有的不重复的key
        Set<String> employers = employeeTable.rowKeySet();
        System.out.print("Employers: ");
        for(String employer: employers){
            System.out.print(employer + " ");
        }
        System.out.println();

        //得到员工编号为102的所有公司和姓名
        Map<String,String> EmployerMap =  employeeTable.column("102");
        for(Map.Entry<String, String> entry : EmployerMap.entrySet()){
            System.out.println("Employer: " + entry.getKey() + ", Name: " + entry.getValue());
        }
        then:
        noExceptionThrown()
    }

    void testMultimap() {
        given:
        def map = HashMultimap.create()
        when:
        map.put("1", "a")
        map.put("1", "A")
        then:
        map.get("1") == ['a', 'A'] as Set
    }

    void testMultiset() {
        given:
        def set = HashMultiset.create(["a", "a", "b", "d", "a", "b"])
        when:
        println set.count("a")
        then:
        noExceptionThrown()
    }

    void testRange() {
        when:
        println com.google.common.collect.Range.open(10,20)
        println com.google.common.collect.Range.open(10.1,10.5)
        //vs java8 stream
        IntStream.range(10,20).each {println it}
        then:
        noExceptionThrown()
    }

    void testOrdering() {
        //Comparators.greatest()
        Collections.sort([]){
            o1,o2->
                ComparisonChain.start().compare(o1.f1,o2.f1)
                .compare(o1.f2,o2.f2).compare(o1.f3,o2.f3).result()
        }
    }

    void testImmutableSet() {
        given:
        def set = ImmutableSet.of(1, 2, 3)
        when:
        set << 4
        then:
        thrown(Exception)
    }

    void testMapMaker() {
        given:
        ConcurrentMap<String, Object> mapAll = new MapMaker()
                .concurrencyLevel(8)
                .weakKeys()
                .weakValues()
                .makeMap()
        when:
        mapAll.put("hello","world")
        then:
        mapAll['hello'] == 'world'
    }
    void testTopK(){
        when:
        final List<Integer> numbers = Lists.newArrayList(1, 3, 8, 2, 6, 4, 7, 5, 9, 0);
        final Collector<Integer, ?, List<Integer>> collector = com.google.common.collect.Comparators.greatest(5,
                Comparator.<Integer>naturalOrder());
        final List<Integer> top = numbers.stream().collect(collector);
        println top
        then:
        noExceptionThrown()
    }
}
