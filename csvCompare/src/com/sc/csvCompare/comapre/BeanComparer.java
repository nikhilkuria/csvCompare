package com.sc.csvCompare.comapre;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.sc.csvCompare.bean.CompareOutput;
import com.sc.csvCompare.bean.CsvBean;
import com.sc.csvCompare.bean.HeaderElement;
import com.sc.csvCompare.bean.HeadersCompliment;
import com.sc.csvCompare.bean.RowElement;
import com.sc.csvCompare.bean.RowsCompliment;
import com.sc.csvCompare.config.ConfigurationsStore;
import com.sc.csvCompare.exception.ConfigNotFoundException;
import com.sc.csvCompare.util.CsvCompareHelper;

public class BeanComparer {
	
	public CompareOutput compareBeans(CsvBean expectedBean, CsvBean actualBean)
			throws ConfigNotFoundException {
		CsvCompareHelper.checkForConfigFile();
		CompareOutput output = new CompareOutput();

		if (ConfigurationsStore.compareHeaders) {
			HeadersCompliment headersCompliment = validateHeaders(
					expectedBean.getHeader(), actualBean.getHeader());
			output.setHeadersCompliment(headersCompliment);
		}

		RowsCompliment rowsCompliment = fetchRowsCompliment(
				expectedBean, actualBean);
		output.setRowsCompliment(rowsCompliment);
		output.setActualFileName(actualBean.getFileName());
		output.setExpectedFileName(expectedBean.getFileName());
		return output;
	}

	private RowsCompliment fetchRowsCompliment(CsvBean expectedBean, CsvBean actualBean) {
		RowsCompliment rowsCompliment = new RowsCompliment();
		List<RowElement> extraRows = getMismatchRows(expectedBean,actualBean);
		List<RowElement> missingRows = getMismatchRows(actualBean,expectedBean); 
		rowsCompliment.setExtraRows(extraRows);
		rowsCompliment.setMissingRows(missingRows);
		return rowsCompliment;
	}

	private List<RowElement> getMismatchRows(CsvBean expectedBean, CsvBean actualBean) {
		List<RowElement> mismatchRows = new ArrayList<RowElement>();
		
		List<RowElement> actualRows = actualBean.getRows();
		List<RowElement> expectedRows = expectedBean.getRows();
		
		for (RowElement actualRowElement : actualRows ) {
			List<String> actualRow = actualRowElement.getRowEntries();
			boolean rowFound = false;
			for (RowElement expectedRowElement : expectedRows) {
				List<String> expectedRow = expectedRowElement.getRowEntries();
				
				if(CollectionUtils.disjunction(actualRow, expectedRow).isEmpty()){
					rowFound = true;
					break;
				}
			}
			if(!rowFound){
				RowElement mismatchRowElement = new RowElement();
				mismatchRowElement.setRowEntries(actualRow);
				mismatchRowElement.setRowHeaders(actualBean.getHeader());
				mismatchRows.add(mismatchRowElement);
			}
		}
		
		return mismatchRows;
	}

	private HeadersCompliment validateHeaders(HeaderElement expectedHeader,
			HeaderElement actualHeader) {
		
		HeadersCompliment headersCompliment = new HeadersCompliment();
		List<String> expectedHeaders = expectedHeader.getHeaders();
		List<String> actualHeaders = actualHeader.getHeaders();

		headersCompliment.setMissingHeaders(new ArrayList<String>(
				CollectionUtils.removeAll(expectedHeaders, actualHeaders)));
		headersCompliment.setExtraHeaders(new ArrayList<String>(CollectionUtils
				.removeAll(actualHeaders,expectedHeaders)));
		headersCompliment.setCommonHeaders(new ArrayList<String>(
				CollectionUtils.intersection(expectedHeaders, actualHeaders)));
		headersCompliment.setActualHeaders(actualHeaders);
		headersCompliment.setExpectedHeaders(expectedHeaders);

		return headersCompliment;

	}

}