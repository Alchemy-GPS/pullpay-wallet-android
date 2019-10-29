package org.payio.wallet.utils;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UrlQrcodeParser {

    public static Parameter parseString(String result) {
        Parameter parameter = new Parameter();

        if (TextUtils.isEmpty(result)) {
            return null;
        }

        String URLWithoutScheme = result.replaceFirst( ":", "");

        ArrayList<String> URLElements = new ArrayList<>(Arrays.asList(URLWithoutScheme.split("\\?")));

        if (URLElements.size() <= 1) {
            return null;
        }

        List<String> queryParametersList = Arrays.asList(URLElements.get(1).split("&"));

        HashMap<String, String> queryParametersMap = new HashMap<String, String>();

        for (String query : queryParametersList) {
            int firstEqual = query.indexOf("=");

            String key = query.substring(0, firstEqual);
            String value = query.replace(key + "=", "");

            queryParametersMap.put(key, value);
        }
        parameter.setQueryParametersMap(queryParametersMap);

        return parameter;
    }

    public static class Parameter implements Serializable {
        private String agentCode;
        private String outFlowNo;
        private HashMap<String, String> queryParametersMap;

        public HashMap<String, String> getQueryParametersMap() {
            return queryParametersMap;
        }

        public void setQueryParametersMap(HashMap<String, String> queryParametersMap) {
            this.queryParametersMap = queryParametersMap;
        }

        public String getAgentCode() {
            if (this.queryParametersMap != null) {
                String amount = queryParametersMap.get("agentCode");
                if (!TextUtils.isEmpty(amount)) {
                    return amount;
                }
            }
            return "";
        }

        public String getOutFlowNo() {
            if (this.queryParametersMap != null) {
                String amount = queryParametersMap.get("outFlowNo");
                if (!TextUtils.isEmpty(amount)) {
                    return amount;
                }
            }
            return "";
        }
    }
}
