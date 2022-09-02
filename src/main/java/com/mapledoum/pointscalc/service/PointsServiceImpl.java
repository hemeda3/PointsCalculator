package com.mapledoum.pointscalc.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.entity.Transaction;
import com.mapledoum.pointscalc.exceptions.NoRecordFoundException;
import com.mapledoum.pointscalc.model.ApiResponseDTO;
import com.mapledoum.pointscalc.model.PointsRuleConfig;
import com.mapledoum.pointscalc.repository.CustomerRepository;
import com.mapledoum.pointscalc.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mapledoum.pointscalc.utils.PointsUtils.CUSTOMER_NOT_EXISTS;

@Service
public class PointsServiceImpl implements IPointsService {


	TransactionRepository transactionRepository;
	CustomerRepository customerRepository;


	@Autowired
	public PointsServiceImpl(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
		this.transactionRepository = transactionRepository;
		this.customerRepository = customerRepository;
	}

	public ApiResponseDTO calcPointsTotalFromTo(Long customerId, LocalDate from, LocalDate to) {


		checkCustomerOrThrow(customerId);

		List<Transaction> transactionsByMonths =
				transactionRepository
						.findAllByCustIdAndTransDateBetween(
								customerId, from,to);


		int totalsofAllMonths = transactionsByMonths.stream()
				.mapToInt(this::calcTierPointsByThresholdForTransaction)
				.sum();




		ApiResponseDTO responseDTO =
				ApiResponseDTO
				.builder()
				.total(totalsofAllMonths)
				.build();

 		System.out.println(responseDTO.toString());
		return responseDTO;
	}
	public ApiResponseDTO calcPointsForMonth(Long customerId, LocalDate month) {

		checkCustomerOrThrow(customerId);

		Transaction transactionByMonth =
				transactionRepository
						.findAllByCustIdAndTransDateIs(
								customerId, month);


		return ApiResponseDTO
				.builder()
				.total(calcTierPointsByThresholdForTransaction(transactionByMonth))
				.build();

	}

	private void checkCustomerOrThrow(Long customerId) {
		Customer customer = customerRepository.findCustomerByCustID(customerId);

		if(customer == null)
		{
			throw new NoRecordFoundException(CUSTOMER_NOT_EXISTS);
		}
	}


	public int calcTierPointsByThresholdForTransaction(Transaction transaction) {

		List<PointsRuleConfig> ruleConfigs = loadRules();

		int result = ruleConfigs.stream()
				.mapToInt(rule -> calcTierPoints(rule.getTierThreshold(),
						rule.getTier2MultiplierPoints(),
						transaction.getTransAmounnt()))
				.sum();
		System.out.println("calc for "+transaction.getTransAmounnt() +" tot "+result);


		return result;
	}


	public int calcTierPoints(BigDecimal tierThreshold, BigDecimal tier2MultiplierPoints, BigDecimal amount) {
        BigDecimal totalPointsForSpentOverTier = BigDecimal.ZERO;
        if (amount.compareTo(tierThreshold) > 0) {
            BigDecimal totalSpentOverTier = amount.subtract(tierThreshold);
            totalPointsForSpentOverTier = totalSpentOverTier.multiply(tier2MultiplierPoints);
        }
        return totalPointsForSpentOverTier.intValue();
    }

	public List<PointsRuleConfig> loadRules()  {

		PointsRuleConfig pointsRuleConfigRule1 = new PointsRuleConfig();
		PointsRuleConfig pointsRuleConfigRule2 = new PointsRuleConfig();

		pointsRuleConfigRule1.setTier2MultiplierPoints(new BigDecimal(2));
		pointsRuleConfigRule1.setTierThreshold(new BigDecimal(100));


		pointsRuleConfigRule2.setTier2MultiplierPoints(new BigDecimal(1));
		pointsRuleConfigRule2.setTierThreshold(new BigDecimal(50));

		List<PointsRuleConfig>  pointsRuleConfigsList= new ArrayList<>();
		pointsRuleConfigsList.add(pointsRuleConfigRule1);
		pointsRuleConfigsList.add(pointsRuleConfigRule2);
		return  pointsRuleConfigsList;
	}



}
