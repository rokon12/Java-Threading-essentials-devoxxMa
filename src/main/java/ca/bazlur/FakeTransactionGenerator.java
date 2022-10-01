package ca.bazlur;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FakeTransactionGenerator {
	public static List<Transaction> generate(int size) {
		Random random = new Random();

		return IntStream.range(0, size)
						.mapToObj(value -> createTransaction(random))
						.collect(Collectors.toList());
	}

	private static Transaction createTransaction(Random random) {
		var uuid = UUID.randomUUID().toString();
		var isCredit = random.nextBoolean();
		var amount = getRandomAmount(BigDecimal.TEN, BigDecimal.valueOf(1000_000));

		return new Transaction(uuid, isCredit, amount);
	}

	private static BigDecimal getRandomAmount(BigDecimal min, BigDecimal max) {
		var randomBigDecimal
						= min.add(BigDecimal.valueOf(Math.random())
						.multiply(max.subtract(min)));

		return randomBigDecimal.setScale(2, RoundingMode.UP);
	}
}
