package telran.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamIntroductionTests {

	private static final long N_RUNS = 1000000;
	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = Integer.MAX_VALUE;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void streamsSouresTest() {
		int arr[] = { 10, -8, 17, 13, 10 };
		int exp[] = { -8, 10 };
		int actual[] = Arrays.stream(arr).filter(n -> n % 2 == 0).distinct().sorted().toArray();
		assertArrayEquals(exp, actual);
	}

	@Test
	void streamRandomSourceTest() {
		Random gen = new Random();
		// gen.ints().limit(10).forEach(System.out::println);
		assertEquals(10, gen.ints().limit(10).toArray().length);
		gen.ints(10, 10, 25).forEach(n -> assertTrue(n >= 10 && n < 25));// count, from.to

	}

	@Test
	void streamCollectionSourceTest() {
		List<Integer> list = Arrays.asList(10, -8, 30);
		Integer[] actual = list.stream().filter(n -> n < 30).sorted().toArray(Integer[]::new);
		Integer[] exp = { -8, 10 };
		assertArrayEquals(exp, actual);
	}

	@Test
	void streamStringSourceTest() {
		String str = "Hello";
		str.chars().forEach(n -> System.out.printf("%c;", n));
	}

	@Test
	void conversionFromIntToInteger() {
		List<Integer> exp = Arrays.asList(1, 2, 3);
		int arr[] = { 1, 2, 3 };
		List<Integer> act = Arrays.stream(arr).boxed().toList();
		assertIterableEquals(exp, act);
	}

	@Test
	void conversionFromIntegerToInt() {
		List<Integer> list = Arrays.asList(1, 2, 3);
		assertEquals(6, list.stream().mapToInt(n -> n).sum());
		assertArrayEquals(new int[] { 1, 2, 3 }, list.stream().mapToInt(n -> n).toArray());
	}

	private Integer[] getLotoNumbers(int nNumbers, int min, int max) {
		// TODO using one stream to get array of unique random numbers in the given
		// range;
		return new Random().ints(min, max + 1).distinct().limit(nNumbers).boxed().toArray(Integer[]::new);
	}

	@Test
	void lotoTest() {
//		Integer[] lotoNum = getLotoNumbers(7, 1, 49);
//		assertEquals(7, lotoNum.length);
//		assertEquals(7, new HashSet<Integer>(Arrays.asList(lotoNum)).size());
		// Arrays.stream(lotoNum).forEach(n -> assertTrue(n >= 1 && n <= 49));

		Set<Integer> allnumbers = new HashSet<>();
		for (int i = 0; i < 10000; i++) {
			Integer[] lotoNum = getLotoNumbers(7, 1, 49);
			Arrays.stream(lotoNum).forEach(n -> allnumbers.add(n));
		}
		for (int number = 1; number <= 49; number++) {
			assertTrue(allnumbers.contains(number));
		}

	}

	/**
	 * 
	 * @param array
	 * @return true if array contains two numbers, the sum of which equals half of
	 *         all arrays numbers O[N]
	 */

	private boolean isHalfhSum(int[] array) {// hash
		int halfSum = Arrays.stream(array).sum() / 2;
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < array.length; i++) {
			int serchedVol = halfSum - array[i];
			if (set.contains(serchedVol)) {
				return true;
			}
			set.add(array[i]);
		}
		return false;
	}

	private boolean isHalfhSum2(int[] array) {// 2 pint
		int halfSum = Arrays.stream(array).sum() / 2;
		Arrays.sort(array);
		int i = 0;
		int j = array.length - 1;
		while (i < j) {
			if (array[i] + array[j] == halfSum) {
				return true;
			}
			if (array[i] + array[j] > halfSum) {
				j--;
			} else {
				i++;
			}
		}
		return false;
	}

	private boolean isHalfhSum3(int[] array) {
		List<Integer> list = Arrays.stream(array).sorted().boxed().toList();
		int halfSum = Arrays.stream(array).sum() / 2;
		int lastIndexToCheck = array.length - 1;
		List<Integer> subList = list.subList(0, lastIndexToCheck);
		while (lastIndexToCheck > 0) {
			if (subList.contains(halfSum - list.get(lastIndexToCheck--))) {
				return true;
			}
			subList = list.subList(0, lastIndexToCheck);
		}
		return false;
	}

	@Test
	void isHalfSumTest() {
		int[] ar = { 1, 2, 10, -7 };
		assertTrue(isHalfhSum(ar));
		assertTrue(isHalfhSum2(ar));
		assertTrue(isHalfhSum3(ar));
		int[] ar1 = { 4, 5, 6, 17, 18, 20 };
		assertTrue(isHalfhSum(ar1));
		assertTrue(isHalfhSum2(ar1));
		assertTrue(isHalfhSum3(ar1));
		int[] ar2 = { 11, 11, 11, 11 };
		assertTrue(isHalfhSum(ar2));
		assertTrue(isHalfhSum2(ar2));
		assertTrue(isHalfhSum3(ar2));
		int[] arrayfalse = { 1, 2, 10, 7 };
		assertFalse(isHalfhSum(arrayfalse));
		assertFalse(isHalfhSum2(arrayfalse));
		assertFalse(isHalfhSum3(arrayfalse));
	}

	private long sum(int arr[][]) {
		// return Arrays.stream(arr).mapToLong(a ->
		// Arrays.stream(a).asLongStream().sum()).sum();
		return Arrays.stream(arr).flatMapToInt(Arrays::stream).asLongStream().sum();
	}

	@Test
	void groupsSumTest() {
		int ar[][] = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
		assertEquals(21, sum(ar));
	}
	@Test
	void evennOddTest() {
		Map<String, List<Integer>> mapEvenOdd;
		mapEvenOdd = new Random().ints(10,1,100). boxed().collect(Collectors.groupingBy(n -> n % 2 == 0 ? "even" : "odd"));
		System.out.println("\n"+mapEvenOdd);
		mapEvenOdd.forEach((k,v) -> {
			if (k.equals("even")) {
				v.forEach(n -> assertTrue(n % 2 == 0));
			} else { v.forEach(n -> assertTrue(n % 2 == 1));
			}
			
		});
		
	}
	@Test
	void evenOddDOwnCounts() {
		List<Integer> list = Arrays.asList(1,2,3,4,8,10);
		Map<String, Long>  mapOddEven = list.stream().
				collect(Collectors.groupingBy( n -> n % 2 == 0 ?"even" : "odd", Collectors.counting()));
				assertEquals(2, mapOddEven.get("odd"));
				assertEquals(4, mapOddEven.get("even"));
	}
	@Test
	void digitsGroupTest() {
		List<Integer> list = Arrays.asList(10, 300, 500, 1, 2,  -100);
		Map<Integer, Integer> mapDigits = list.stream().collect(Collectors.groupingBy(n -> Integer.toString(Math.abs(n)).length(), Collectors.summingInt(n -> n)));
		assertEquals(3, mapDigits.get(1));
		assertEquals(10, mapDigits.get(2));
		assertEquals(700, mapDigits.get(3));
	}
	
	@Test
	void testOccurrencesCount() {
		String str = "lmn ab lmn aa; a, lmn ab. aa";
		String outPutExp = "lmn -> 3\naa -> 2\nab -> 2\na -> 1";
		String outputActual = getOccurrences(str);
		assertEquals(outPutExp, outputActual);
	}

	private String getOccurrences(String str) {

		return Arrays.stream(str.split("[^a-zA-Z]+")).collect(Collectors.groupingBy(s -> s, TreeMap::new, Collectors.counting()))
				.entrySet().stream().sorted((e1,e2)-> Long.compare(e2.getValue(),e1.getValue()))
				.map(e -> String.format("%s -> %d", e.getKey(), e.getValue()))
				.collect(Collectors.joining("\n"));
				}

private void arrayShuffling(int ar[]){
	//TODO done 
	//printing out array in the shuffling order
	//without any additional collections
	//one pipeline  
	new Random().ints(0, ar.length).distinct().limit(ar.length).forEach(el -> System.out.print(ar[el] + " "));
}	
@Test
void shuffingTest() {
	System.out.println("----------Shuffled array is------------");
	arrayShuffling(new int[] {1,2,3,4});
	System.out.println("\n----------------------");

}

private void digitsStatistics() {
	//TODO done
	//Generating 1_000_000 random positive numbers (1-Integer.max_VAlue)
	//display out digits and occurrences sorted by  in descending order
	//1: <occurrences value>
	//2: ...
	//4: ...
	new Random().ints(N_RUNS, MIN_VALUE ,MAX_VALUE)
	.flatMap(num -> Integer.toString(num).chars())
	.boxed()
	.collect(Collectors.groupingBy(Function.identity(),TreeMap::new, Collectors.counting()))//functIdentity returns input value c->c
	.entrySet().stream()
	.sorted(Map.Entry.<Integer,Long>comparingByValue().reversed())//(e1,e2)-> Long.compare(e2.getValue(),e1.getValue())
	.forEach(e-> System.out.printf("\n%c: %d ",e.getKey(), e.getValue()));

}

@Test
void digitsStatisticsTest() {
	System.out.println("---------Digits Statistic-----------");
	digitsStatistics();
	System.out.println("\n----------------------");

}


}