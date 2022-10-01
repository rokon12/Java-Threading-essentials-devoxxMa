package ca.bazlur;

import java.math.BigDecimal;

public class Transaction {
	private String transactionId;
	private boolean credit;
	private BigDecimal amount;

	public Transaction(String transactionId, boolean credit, BigDecimal amount) {
		this.transactionId = transactionId;
		this.credit = credit;
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public boolean isCredit() {
		return credit;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
