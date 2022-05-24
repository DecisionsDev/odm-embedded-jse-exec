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

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.joda.time.Instant;

import loan.*;

public class LoanValidationRequest implements Serializable  {

	private static final long serialVersionUID = 1L;

	public Borrower borrower;
	public LoanRequest loanRequest;
	
	public LoanValidationRequest() {
	}
	
	public LoanValidationRequest(Borrower borrower, LoanRequest loan) {
		this.borrower = borrower;
		this.loanRequest = loan;
	}
	
	//Borrower(String firstName, String lastName, Date birthDate,	String SSNCode)
	public LoanValidationRequest(String firstName, String lastName, int borrowerCreditScore, int borrowerYearlyIncome, Date birthDate, String SSNCode, String zipCode, int loanAmount, Date startDate, int numberOfMonthlyPayments, double loanToValue) {
		this.borrower = new Borrower(firstName, lastName, birthDate, SSNCode);
		borrower.setCreditScore(borrowerCreditScore);
		borrower.setYearlyIncome(borrowerYearlyIncome);
		borrower.setZipCode(zipCode);
		//public LoanRequest(Date startDate, int numberOfMonthlyPayments, int amount,double loanToValue)
		this.loanRequest = new LoanRequest(startDate, numberOfMonthlyPayments, loanAmount, loanToValue);
	}
	
	public Borrower getBorrower() {
		return borrower;
	}
	
	public LoanRequest getLoan() {
		return loanRequest;
	}
	
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	
	public void setLoan(LoanRequest loanRequest) {
		this.loanRequest = loanRequest;
	}

}
