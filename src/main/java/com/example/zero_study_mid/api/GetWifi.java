package com.example.zero_study_mid.api;

import com.example.zero_study_mid.dto.WifiValue;
import com.google.gson.*;
import okhttp3.*;

import java.util.ArrayList;
import java.util.List;

public class GetWifi {



    public static void main(String[] args) {

        GetWifi getWifi = new GetWifi();
        System.out.println(getWifi.Gsonpaser(1));
        System.out.println();

//        System.out.println(getWifi.Gsonpaser(1, 2).get(0).getX_SWIFI_ADRES1());

    }

    public String getWifiInfo(int start, int end) {
        try {
            String pageStart = String.valueOf(start);
            String pageEnd = String.valueOf(end);
            String key = "766f5058596168723132365349655657";
            String url = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/"+ pageStart +"/" + pageEnd ;

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(url).get();
            builder.addHeader("KEY", key);
            Request request = builder.build();
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    String str = body.string();
                    body.close();
                    return str;
                }
            } else {
                System.out.println("Error Occurred");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<WifiValue> Gsonpaser(int totalCount) {
        int cnt = 0;
        List<WifiValue> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String str = getWifiInfo(1 + cnt, 999 + cnt);
//        JsonArray jsonArray = new Gson().fromJson(str, JsonObject.class).getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");
            JsonObject jsonObject = new Gson().fromJson(str, JsonObject.class);
            JsonObject TbPublicWifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
            JsonArray row = TbPublicWifiInfo.getAsJsonArray("row");

            for (JsonElement jsonElement : row) {
                WifiValue wifiValue = new WifiValue();
                wifiValue.setX_SWIFI_MGR_NO(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_MGR_NO")));
                wifiValue.setX_SWIFI_WRDOFC(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_WRDOFC")));
                wifiValue.setX_SWIFI_MAIN_NM(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_MAIN_NM")));
                wifiValue.setX_SWIFI_ADRES1(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_ADRES1")));
                wifiValue.setX_SWIFI_ADRES2(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_ADRES2")));
                wifiValue.setX_SWIFI_INSTL_FLOOR(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_INSTL_FLOOR")));
                wifiValue.setX_SWIFI_INSTL_TY(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_INSTL_TY")));
                wifiValue.setX_SWIFI_INSTL_MBY(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_INSTL_MBY")));
                wifiValue.setX_SWIFI_SVC_SE(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_SVC_SE")));
                wifiValue.setX_SWIFI_CMCWR(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_CMCWR")));
                wifiValue.setX_SWIFI_CNSTC_YEAR(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_CNSTC_YEAR")));
                wifiValue.setX_SWIFI_INOUT_DOOR(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_INOUT_DOOR")));
                wifiValue.setX_SWIFI_REMARS3(String.valueOf(jsonElement.getAsJsonObject().get("X_SWIFI_REMARS3")));
                wifiValue.setLNT(String.valueOf(jsonElement.getAsJsonObject().get("LNT")));
                wifiValue.setLAT(String.valueOf(jsonElement.getAsJsonObject().get("LAT")));
                wifiValue.setWORK_DTTM(String.valueOf(jsonElement.getAsJsonObject().get("WORK_DTTM")));
                list.add(wifiValue);
                cnt++;
                if (cnt == totalCount) {
                    break;
                }
            }
        }

        return list;
    }

}
