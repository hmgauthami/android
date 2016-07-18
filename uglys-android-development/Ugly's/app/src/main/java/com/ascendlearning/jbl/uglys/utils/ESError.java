package com.ascendlearning.jbl.uglys.utils;


public class ESError {

	public enum ERROR_CODES_CLIENT{
		ERROR_NO_ERROR,
		ERROR_INTERNAL
	}

	private ERROR_CODES_CLIENT mErrorCodeClient = ERROR_CODES_CLIENT.ERROR_NO_ERROR;
	private String mErrorMessageClient = "";

	public void setErrorCodeFromClient(ERROR_CODES_CLIENT clientErrorCode){
		mErrorCodeClient = clientErrorCode;
	}

	public void setErrorMessageFromClient(String clientErrorMessage){
		mErrorMessageClient = clientErrorMessage;
	}

}
