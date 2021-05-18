package com.example.fetchRewards.fetchRewards.service;

import java.util.List;
import java.util.Map;

import com.example.fetchRewards.fetchRewards.model.SpendPointsRequest;
import com.example.fetchRewards.fetchRewards.model.TransactionRequest;

public interface FetchRewardsService {

	public void addTransaction(TransactionRequest transactionRequest);

	public String getPoints();

	public List<Map<String, String>> spendPoints(SpendPointsRequest spendPointsRequest);
}
