package com.ascendlearning.jbl.uglys.controllers;


import com.ascendlearning.jbl.uglys.utils.ESError;

/**
  * @author sonal.agarwal
 *
 */

public interface UICallback {
	
	void operationInitialized();
	
	void operationStarted();
	
	void operationInProgress();
	
	void operationCompleted(Object object);
	
	void operationUpdated(Object object);

	void operationFailed(ESError error, Object object);
}