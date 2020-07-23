package com.huobi.model.account;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLedger {

	/**
	 * Account ID
	 */
	private Long accountId;

	/**
	 * Currency
	 */
	private String currency;

	/**
	 * Amount change (positive value if income, negative value if outcome)
	 */
	private BigDecimal transactAmt;

	/**
	 * Amount change types
	 */
	private String transactType;

	private String transferType;
	/**
	 * transaction Id
	 */
	private Long transactId;

	/**
	 * Acccount transferred from
	 */
	private Long transferer;

	/**
	 * Acccount transferred to
	 */
	private Long transferee;

	/**
	 * Transaction time (database time)
	 */
	private Long transactTime;

}
