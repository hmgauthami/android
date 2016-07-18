package com.ascendlearning.jbl.uglys.controllers;


import android.content.Context;

import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.ESError;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class DataController {

    private static final String TAG = DataController.class.getSimpleName();
    private ArrayList<UICallback> mUICallbackList = new ArrayList<UICallback>(2);

    String jsonResponse = null;
    protected CompositeKey mCompositeKey;

    public void fetchData(Context context) {
        CompositeKey requestKey = mCompositeKey;

        String url = getUrlForType(requestKey);
        try {
            updateOperationStarting();
            InputStream is = context.getAssets().open(url);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            jsonResponse = new String(buffer, "UTF-8");
            handleData(jsonResponse);

            updateOperationCompletion(jsonResponse);

        } catch (IOException ex) {
            ESError error = new ESError();
            error.setErrorCodeFromClient(ESError.ERROR_CODES_CLIENT.ERROR_INTERNAL);
            error.setErrorMessageFromClient("Internal Error while handling/parsing reponse.");
            updateOperationFailure(error, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void updateOperationInProgress() {
        if (mUICallbackList != null) {
            for (int index = 0; index < mUICallbackList.size(); index++) {
                UICallback uicallbackInstance = mUICallbackList.get(index);
                if (uicallbackInstance != null) {
                    uicallbackInstance.operationInProgress();
                }
            }
        }
    }

    protected void updateOperationInitialised() {
        if (mUICallbackList != null) {
            for (int index = 0; index < mUICallbackList.size(); index++) {
                UICallback uicallbackInstance = mUICallbackList.get(index);
                if (uicallbackInstance != null) {
                    uicallbackInstance.operationInitialized();
                }
            }
        }
    }

    protected void updateOperationStarting() {
        if (mUICallbackList != null) {
            for (int index = 0; index < mUICallbackList.size(); index++) {
                UICallback uicallbackInstance = mUICallbackList.get(index);
                if (uicallbackInstance != null) {
                    uicallbackInstance.operationStarted();
                }
            }
        }
    }

    protected void updateOperationCompletion(Object responseId) {
        if (mUICallbackList != null) {
            for (int index = 0; index < mUICallbackList.size(); index++) {
                UICallback uicallbackInstance = mUICallbackList.get(index);
                if (uicallbackInstance != null) {
                    uicallbackInstance.operationCompleted(responseId);
                }
            }
        }
    }

    protected void updateOperationFailure(ESError error, Object object) {
        if (mUICallbackList != null) {
            for (int index = 0; index < mUICallbackList.size(); index++) {
                UICallback uicallbackInstance = mUICallbackList.get(index);
                if (uicallbackInstance != null) {
                    uicallbackInstance.operationFailed(error, object);
                }
            }
        }
    }

    public abstract String getUrlForType(CompositeKey requestKey);

    public abstract boolean handleData(String response) throws Exception;


    /**
     * This method should be used when the callback objects needs to be swapped.
     *
     * @param mUICallback
     */
    public void setUICallBack(UICallback mUICallback) {
        if (mUICallbackList != null) {
            mUICallbackList.clear();
            mUICallbackList.add(mUICallback);
        }
    }
    public String getResponse() {
        return jsonResponse;
    }

    public void setCompositeKey(CompositeKey compositeKey) {
        mCompositeKey = compositeKey;
    }
}