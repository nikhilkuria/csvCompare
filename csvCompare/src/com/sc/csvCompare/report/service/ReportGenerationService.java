package com.sc.csvCompare.report.service;

import java.io.IOException;

import com.sc.csvCompare.bean.CompareOutput;

public interface ReportGenerationService {

	public void generateReport(CompareOutput output) throws IOException;
}
