package com.ascendlearning.jbl.uglys.utils;

import android.text.TextUtils;

public class CompositeKey {

	private String mFilter;

	public CompositeKey(String filter) {
		mFilter = filter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((TextUtils.isEmpty(mFilter)) ? 0 : mFilter.hashCode());
		return Math.abs(result);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		} else {
			CompositeKey other = (CompositeKey) obj;
		 if (!TextUtils.isEmpty(mFilter)
					&& !(mFilter.equals(other.getFilter()))) {
				return false;
			}
		}
		
		return true;
	}


	public String getFilter() {
		return mFilter;
	}
}