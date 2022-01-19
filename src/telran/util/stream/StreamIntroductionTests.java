package telran.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamIntroductionTests {

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
		// done
		return new Random().ints(min, max).distinct().limit(nNumbers).boxed().toArray(Integer[]::new);
	}

	@Test
	void lotoTest() {
		Integer[] lotoNum = getLotoNumbers(7, 1, 49);
		assertEquals(7, lotoNum.length);
		assertEquals(7, new HashSet<Integer>(Arrays.asList(lotoNum)).size());
		Arrays.stream(lotoNum).forEach(n -> assertTrue(n >= 1 && n <= 49));
	}

//return true is array contains two numbers, the sum of which equals half of all arrays numbers O[N]
	private boolean isHalfhSum(int[] array) {
		// TODO done
		List<Integer>list = Arrays.stream(array).sorted().boxed().collect(Collectors.toList());
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
		int[] ar1 = { 4, 5, 6, 17, 18, 20 };
		assertTrue(isHalfhSum(ar1));
		int[] ar2 = {11,11,11,11};
		assertTrue(isHalfhSum(ar2));
		int[] arrayfalse = { 1, 2, 10, 7 };
		assertFalse(isHalfhSum(arrayfalse));
	}
}
