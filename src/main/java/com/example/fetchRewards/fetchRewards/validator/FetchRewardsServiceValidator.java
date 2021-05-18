package com.example.fetchRewards.fetchRewards.validator;

import static com.example.fetchRewards.fetchRewards.util.FetchRewardsServiceConstants.TIMESTAMP_INCORRECT;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.example.fetchRewards.fetchRewards.exception.FetchRewardsServiceValidationException;
import com.example.fetchRewards.fetchRewards.model.TransactionRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FetchRewardsServiceValidator {

	public void validateTransactionRequest(TransactionRequest transactionRequest) {
		String timeStamp = transactionRequest.getTimestamp();
		isValidTimeStamp(timeStamp);
	}

	private void isValidTimeStamp(String timeStamp) {
		log.info("Validating treansaction request time stamp");
		String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		try {
			dateTimeFormatter.parse(timeStamp);
		} catch (Exception ex) {
			log.info("Parse exception occurred for the value " + timeStamp);
			throw new FetchRewardsServiceValidationException(TIMESTAMP_INCORRECT);
		}
	}

}
