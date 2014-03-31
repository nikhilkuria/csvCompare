package com.sc.csvCompare.report.style;

import com.sc.csvCompare.report.helper.ReportVariables;

public class GeneralInfo implements SectionStyle {

	@Override
	public String getFontSize() {
		// TODO Auto-generated method stub
		return "13";
	}

	@Override
	public String getFontColor() {
		// TODO Auto-generated method stub
		return ReportVariables.GENERAL_INFO_GRAY;
	}

	@Override
	public String getBackgroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAlignment() {
		// TODO Auto-generated method stub
		return "left";
	}

	@Override
	public String getFontWeight() {
		// TODO Auto-generated method stub
		return "ITALIC";
	}

	@Override
	public String getVerticalAlign() {
		// TODO Auto-generated method stub
		return "top";
	}

}
