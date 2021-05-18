package com.example.fetchRewards.fetchRewards.controller;

import static com.example.fetchRewards.fetchRewards.util.FetchRewardsServiceConstants.FETCH_REWARDS_SERVICE_URI;
import static com.example.fetchRewards.fetchRewards.util.FetchRewardsServiceConstants.POINTS_URI;
import static com.example.fetchRewards.fetchRewards.util.FetchRewardsServiceConstants.TRANSACTIONS_URI;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fetchRewards.fetchRewards.model.SpendPointsRequest;
import com.example.fetchRewards.fetchRewards.model.TransactionRequest;
import com.example.fetchRewards.fetchRewards.service.FetchRewardsService;
import com.example.fetchRewards.fetchRewards.validator.FetchRewardsServiceValidator;

@Validated
@RestController
@RequestMapping(FETCH_REWARDS_SERVICE_URI)
public class FetchRewardsServiceController {

	@Autowired
	private FetchRewardsServiceValidator fetchRewardsServiceValidator;

	@Autowired
	private FetchRewardsService fetchRewardsService;

	@PostMapping(TRANSACTIONS_URI)
	public ResponseEntity addTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {

		fetchRewardsServiceValidator.validateTransactionRequest(transactionRequest);

		fetchRewardsService.addTransaction(transactionRequest);

		return new ResponseEntity(HttpStatus.CREATED);

	}

	@GetMapping(POINTS_URI)
	public ResponseEntity<String> getPoints() {

		String points = fetchRewardsService.getPoints();

		return new ResponseEntity(points, HttpStatus.OK);

	}

	@PostMapping(POINTS_URI)
	public ResponseEntity<List<Map<String, String>>> spendPoints(
			@RequestBody @Valid SpendPointsRequest spendPointsRequest) {

		List<Map<String, String>> spendList = fetchRewardsService.spendPoints(spendPointsRequest);

		return new ResponseEntity(spendList, HttpStatus.OK);

	}
}
