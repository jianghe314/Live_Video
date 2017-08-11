package com.szreach.ybolotv.jsonMsg;

public class HeaderItem {

	private boolean result = false;			// 成功与否
	private String returnCode = "";		// 错误码(暂时不使用)
	private String errorInfo = "";			// 错误信息提示

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
