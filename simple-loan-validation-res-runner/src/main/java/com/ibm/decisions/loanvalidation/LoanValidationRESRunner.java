/*
* Copyright IBM Corp. 1987, 2018
* 
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
* 
* http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
* 
**/

package com.ibm.decisions.loanvalidation;

import ilog.rules.res.model.IlrPath;
import ilog.rules.res.session.IlrJ2SESessionFactory;
import ilog.rules.res.session.IlrSessionRequest;
import ilog.rules.res.session.IlrSessionResponse;
import ilog.rules.res.session.IlrStatelessSession;
import ilog.rules.res.session.config.IlrPersistenceType;
import ilog.rules.res.session.config.IlrSessionFactoryConfig;
import ilog.rules.res.session.config.IlrXUConfig;
import java.util.Map;

import loan.Borrower;
import loan.LoanRequest;

public class LoanValidationRESRunner {

	private static IlrJ2SESessionFactory ruleSessionFactory = null;
	

	private static IlrJ2SESessionFactory GetRuleSessionFactory() {
		IlrSessionFactoryConfig factoryConfig = IlrJ2SESessionFactory.createDefaultConfig();
		IlrXUConfig xuConfig = factoryConfig.getXUConfig();
		xuConfig.setLogAutoFlushEnabled(true);
		xuConfig.getPersistenceConfig().setPersistenceType(IlrPersistenceType.MEMORY);
		xuConfig.getManagedXOMPersistenceConfig().setPersistenceType(IlrPersistenceType.MEMORY);
		return new IlrJ2SESessionFactory(factoryConfig);
	}
	
	public static void main(String[] args) {
		LoanValidationRESRunner runner = new LoanValidationRESRunner();
		
		//Borrower
		java.util.Date birthDate = loan.DateUtil.makeDate(1950, 1, 1);
		loan.Borrower borrower = new loan.Borrower("Smith", "John", birthDate,
					"123121234");
		borrower.setZipCode("12345"); //12345
		borrower.setCreditScore(800);
		borrower.setYearlyIncome(100000);
		
		//Loan
		java.util.Date loanDate = new java.util.Date();
		loan.LoanRequest loan = new loan.LoanRequest(loanDate, 48, 20000, 0.05);
		
		//LoanValidationRequest request = new LoanValidationRequest("John", "Doe", 550, 80000, birthDate, "123-121234", 250000, 240, 0.05d);
		LoanValidationRequest request = new LoanValidationRequest(borrower, loan);
		LoanValidationDecision decision = runner.execute(request);
		System.out.println("Messages:" + decision.response.report.getMessage());
	}
	
	
	public LoanValidationDecision execute(LoanValidationRequest request) {

		LoanValidationResponse response = null;

		try {
			
			IlrSessionResponse sessionResponse = execute(request.borrower, request.loanRequest);
			//long t3 = System.currentTimeMillis();
			LoanValidationResponse loanValidationResponse = new LoanValidationResponse(sessionResponse);
			response = loanValidationResponse;

		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}
		
		double yearlyRepayment = response.getReport().getMonthlyRepayment();
		
		System.out.print("Loan approved=" + response.report.isApproved() + " with a yearly repayment=" + yearlyRepayment + " insurance required:" + response.getReport().isInsuranceRequired() + " messages= " + response.getReport().getMessages());
		System.out.println(" executed in thread " + Thread.currentThread().getName());
		
		return new LoanValidationDecision(request, response);
	}
	
	
	public IlrSessionResponse execute(Borrower borrower, LoanRequest loan) {
		try {

			IlrJ2SESessionFactory sessionFactory =  GetRuleSessionFactory();

			// Creating the decision request
			IlrSessionRequest sessionRequest = sessionFactory.createRequest();
			String rulesetPath = "/loanvalidation/loan_validation_with_score_and_grade";
			sessionRequest.setRulesetPath(IlrPath.parsePath(rulesetPath));

			sessionRequest.setTraceEnabled(true);
			//sessionRequest.getTraceFilter().setInfoAllFilters(true);
			sessionRequest.getTraceFilter().setInfoRules(true);
			sessionRequest.getTraceFilter().setInfoRulesNotFired(true);
			sessionRequest.getTraceFilter().setInfoTasks(true);
			sessionRequest.getTraceFilter().setInfoTotalTasksNotExecuted(true);
			sessionRequest.getTraceFilter().setInfoExecutionEvents(true);

			Map<String, Object> inputParameters = sessionRequest
					.getInputParameters();
			inputParameters.put("loan", loan);
			inputParameters.put("borrower", borrower);

			// Creating the rule session
			IlrStatelessSession session = sessionFactory
					.createStatelessSession();

			IlrSessionResponse response = session.execute(sessionRequest);
			return response;

		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}
		return null;
	}

}
