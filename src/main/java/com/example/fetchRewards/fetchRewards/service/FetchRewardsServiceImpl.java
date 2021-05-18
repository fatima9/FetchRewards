package com.example.fetchRewards.fetchRewards.service;

import static com.example.fetchRewards.fetchRewards.util.FetchRewardsServiceConstants.RUNTIME_EXCEPTION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.map.MultiValueMap;
import org.springframework.stereotype.Service;

import com.example.fetchRewards.fetchRewards.exception.FetchRewardsServiceException;
import com.example.fetchRewards.fetchRewards.model.SpendPointsRequest;
import com.example.fetchRewards.fetchRewards.model.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FetchRewardsServiceImpl implements FetchRewardsService {

	private static final String PAYER = "payer";

	private static final String POINTS = "points";

	private static MultiValueMap<String, TransactionRequest> PAYERMAP = new MultiValueMap<>();

	private static Map<String, String> POINTSMAP = new HashMap<String, String>();

	@Override
	public void addTransaction(TransactionRequest transactionRequest) {
		log.info("Storing the transaction");
		PAYERMAP.put(transactionRequest.getPayer(), transactionRequest);
		for (Entry<String, Object> entry : PAYERMAP.entrySet()) {
			String key = entry.getKey();
			ArrayList<TransactionRequest> requestList = (ArrayList<TransactionRequest>) entry.getValue();
			int calculatedPoints = 0;
			for (TransactionRequest transReq : requestList) {
				calculatedPoints += Integer.valueOf(transReq.getPoints());
			}
			POINTSMAP.put(key, String.valueOf(calculatedPoints));
		}
	}

	@Override
	public String getPoints() {
		String points = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			points = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(POINTSMAP);
		} catch (Exception ex) {
			log.error("An error occurred while retrieving the points " + ex);
			throw new FetchRewardsServiceException(RUNTIME_EXCEPTION);
		}
		return points;
	}

	@Override
	public List<Map<String, String>> spendPoints(SpendPointsRequest spendPointsRequest) {
		List<Map<String, String>> spendList = new ArrayList<Map<String, String>>();
		int pointsToSpend = Integer.valueOf(spendPointsRequest.getPoints());
		List<TransactionRequest> requestList = new ArrayList<TransactionRequest>();
		for (Entry<String, Object> entry : PAYERMAP.entrySet()) {
			ArrayList<TransactionRequest> valueList = (ArrayList<TransactionRequest>) entry.getValue();
			requestList.addAll(valueList);
		}
		Collections.sort(requestList);
		Map<String, Integer> map = new HashMap<String, Integer>();

		for (TransactionRequest req : requestList) {
			int pointsToStore = 0;
			int reqPoints = Integer.valueOf(req.getPoints());
			if (pointsToSpend > reqPoints) {
				pointsToStore = reqPoints;
			} else {
				pointsToStore = pointsToSpend;
			}
			if (!map.containsKey(req.getPayer())) {
				map.put(req.getPayer(), pointsToStore);
			} else {
				int pnts = Integer.valueOf(map.get(req.getPayer())) + pointsToStore;
				map.put(req.getPayer(), Integer.valueOf(String.valueOf(pnts)));
			}

			if (pointsToSpend > reqPoints) {
				pointsToSpend -= reqPoints;
			} else {
				pointsToSpend = 0;
				break;
			}
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			Map<String, String> spendMap = new HashMap<String, String>();
			spendMap.put(PAYER, entry.getKey());
			spendMap.put(POINTS, String.valueOf(entry.getValue() * -1));
			int updatedPoints = Integer.valueOf(POINTSMAP.get(entry.getKey())) - entry.getValue();
			POINTSMAP.replace(entry.getKey(), String.valueOf(updatedPoints));
			spendList.add(spendMap);
		}

		return spendList;
	}

}
