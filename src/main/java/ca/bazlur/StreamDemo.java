package ca.bazlur;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class StreamDemo {
	public static void main(String[] args) {

		var transactionList
						= FakeTransactionGenerator.generate(100_000_000);

		calculateTime(() -> processInSequential(transactionList), "Sequential Calculation: ", 10);
		calculateTime(() -> processInParallel(transactionList), "parallel Calculation: ", 10);
	}

	public static BigDecimal processInParallel(List<Transaction> transactions) {

		return transactions
						.parallelStream()
						.filter(Transaction::isCredit)
						.map(Transaction::getAmount)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public static BigDecimal processInSequential(List<Transaction> transactions) {

		return transactions
						.stream()
						.filter(Transaction::isCredit)
						.map(Transaction::getAmount)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public static void calculateTime(Supplier<BigDecimal> supplier,
																	 String name, int iteration) {
		System.out.println("\nStarting: " + name);

		var statistics = IntStream.range(0, iteration)
						.mapToLong(value -> execute(supplier, value))
						.summaryStatistics();

		System.out.println("\nTime to complete in " + name + " is: ");
		System.out.println("Average: " + statistics.getAverage() + " msecs");
		System.out.println("Maximum: " + statistics.getMax() + " msecs");
		System.out.println("Minimum: " + statistics.getMin() + " msecs");
	}

	private static long execute(Supplier<BigDecimal> supplier, int value) {
		long start = System.nanoTime();
		supplier.get();
		long duration = (System.nanoTime() - start);
		long msecs = (duration / 1_000_000);

		System.out.println("Iteration: " + value
						+ ", execution time: " + msecs);
		return msecs;
	}
}
