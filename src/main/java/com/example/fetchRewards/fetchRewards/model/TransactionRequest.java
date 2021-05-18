package com.example.fetchRewards.fetchRewards.model;

import static com.example.fetchRewards.fetchRewards.util.FetchRewardsServiceConstants.VALUE_NOT_EMPTY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TransactionRequest implements Comparable<TransactionRequest> {

	@NotBlank(message = VALUE_NOT_EMPTY)
	private String payer;

	@NotBlank(message = VALUE_NOT_EMPTY)
	private String points;

	@NotBlank(message = VALUE_NOT_EMPTY)
	private String timestamp;

	@Override
	public int compareTo(TransactionRequest req2) {
		int res = 0;
		String timeStamp1 = this.getTimestamp();
		String timeStamp2 = req2.getTimestamp();
		String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime date1 = LocalDateTime.parse(timeStamp1, dateTimeFormatter);
		LocalDateTime date2 = LocalDateTime.parse(timeStamp2, dateTimeFormatter);
		if(date1.equals(date2)) res = 0;
		else if (date1.isBefore(date2)) res = -1;
		else if (date1.isAfter(date2)) res = 1;
		return res;
	}
}
