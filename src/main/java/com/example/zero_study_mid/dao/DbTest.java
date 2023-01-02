package com.example.zero_study_mid.dao;

import com.example.zero_study_mid.api.GetDistance;
import com.example.zero_study_mid.api.GetWifi;
import com.example.zero_study_mid.dto.WifiValue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbTest {
//    public static void main(String[] args) {
//        connectDB();
//        selectDb(37.5026529,126.7635544);
//        insertDB();
//        deleteDb();
//    }

    public static int insertDB() {
        String url = "jdbc:sqlite://Users//jigi//publicWifi.sqlite3";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rs;


        int totalCount = 0;
        try {
            connection = DriverManager.getConnection(url);

//            statement = connection.createStatement(); // 이건 파라미터 처리 안됨
            // 데이터 베이스 쿼리를 실행하기위해 (파라미터 이슈, 처리하기 위해서)

            String sql = " INSERT INTO WIFI_INFO(X_SWIFI_MGR_NO, x_swifi_wrdofc, x_swifi_main_nm,\n" +
                    " x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor,\n" +
                    " x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se,\n" +
                    " x_swifi_cmcwr, x_swifi_cnstc_year, x_swifi_inout_door,\n" +
                    " x_swifi_remars3, lat, lnt, work_dttm)\n" +
                    " values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);

//            PreparedStatement preparedStatement = connection.prepareStatement();

            GetWifi getWifi = new GetWifi();
            String str = getWifi.getWifiInfo(1, 1);
            JsonObject jsonObject = new Gson().fromJson(str, JsonObject.class);
            JsonObject TbPublicWifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
            JsonPrimitive listTotalCount = TbPublicWifiInfo.getAsJsonPrimitive("list_total_count");
            totalCount = Integer.valueOf(String.valueOf(listTotalCount));
            List<WifiValue> list = getWifi.Gsonpaser(totalCount);

            for (WifiValue value : list) {
                preparedStatement.setString(1, value.getX_SWIFI_MGR_NO().replace("\"",""));
                preparedStatement.setString(2, value.getX_SWIFI_WRDOFC().replace("\"",""));
                preparedStatement.setString(3, value.getX_SWIFI_MAIN_NM().replace("\"",""));
                preparedStatement.setString(4, value.getX_SWIFI_ADRES1().replace("\"",""));
                preparedStatement.setString(5, value.getX_SWIFI_ADRES2().replace("\"",""));
                preparedStatement.setString(6, value.getX_SWIFI_INSTL_FLOOR().replace("\"",""));
                preparedStatement.setString(7, value.getX_SWIFI_INSTL_TY().replace("\"",""));
                preparedStatement.setString(8, value.getX_SWIFI_INSTL_MBY().replace("\"",""));
                preparedStatement.setString(9, value.getX_SWIFI_SVC_SE().replace("\"",""));
                preparedStatement.setString(10, value.getX_SWIFI_CMCWR().replace("\"",""));
                preparedStatement.setString(11, value.getX_SWIFI_CNSTC_YEAR().replace("\"",""));
                preparedStatement.setString(12, value.getX_SWIFI_INOUT_DOOR().replace("\"",""));
                preparedStatement.setString(13, value.getX_SWIFI_REMARS3().replace("\"",""));
                preparedStatement.setString(14, value.getLAT().replace("\"",""));
                preparedStatement.setString(15, value.getLNT().replace("\"",""));
                preparedStatement.setString(16, value.getWORK_DTTM().replace("\"",""));
                rs = preparedStatement.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
//            try {
//                if (rs != null && !rs.isClosed()) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return totalCount;
//        System.out.println("Opened database successfully");
    }

    public static List<WifiValue> selectDb(double lat, double lnt){
        List<WifiValue> list = new ArrayList<>();
        GetDistance getDistance = new GetDistance();
        String url = "jdbc:sqlite://Users//jigi//publicWifi.sqlite3";


        try {
            Class.forName("org.sqlite.JDBC");
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        double LATs = lat;
        double LNTs = lnt;

        try {
            connection = DriverManager.getConnection(url);

//            statement = connection.createStatement(); // 이건 파라미터 처리 안됨
            // 데이터 베이스 쿼리를 실행하기위해 (파라미터 이슈, 처리하기 위해서)

            String sql = " SELECT * " +
                    ", (6371 * acos(cos(radians(?)) * cos(radians(LAT)) * " +
                    "cos(radians(LNT) - radians(?)) + sin(radians(?)) * sin(radians(LAT)))) as distance " +
                    " FROM WIFI_INFO" +
                    " ORDER BY distance, X_SWIFI_MGR_NO" +
                    " limit 20 ";
//                    " HAVING 5 ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(LATs));
            preparedStatement.setString(2, String.valueOf(LNTs));
            preparedStatement.setString(3, String.valueOf(LATs));

//            PreparedStatement preparedStatement = connection.prepareStatement();

            rs = preparedStatement.executeQuery();

            double distance;

            while (rs.next()) {
                String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY");
                String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
                String LAT = rs.getString("LAT");
                String LNT = rs.getString("LNT");
                String WORK_DTTM = rs.getString("WORK_DTTM");


                distance = getDistance.distance(lat, lnt, LAT, LNT);

                list.add(new WifiValue(distance ,X_SWIFI_MGR_NO ,X_SWIFI_WRDOFC
                        ,X_SWIFI_MAIN_NM ,X_SWIFI_ADRES1  ,X_SWIFI_ADRES2
                        ,X_SWIFI_INSTL_FLOOR ,X_SWIFI_INSTL_TY ,X_SWIFI_INSTL_MBY
                        ,X_SWIFI_SVC_SE ,X_SWIFI_CMCWR ,X_SWIFI_CNSTC_YEAR
                        ,X_SWIFI_INOUT_DOOR ,X_SWIFI_REMARS3, LNT, LAT, WORK_DTTM));

            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Opened database successfully");
        return list;
    }
    public static void deleteDb(){
        String url = "jdbc:sqlite://Users//jigi//publicWifi.sqlite3";


        try {
            Class.forName("org.sqlite.JDBC");
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int rs;

        try {
            connection = DriverManager.getConnection(url);

//            statement = connection.createStatement(); // 이건 파라미터 처리 안됨
            // 데이터 베이스 쿼리를 실행하기위해 (파라미터 이슈, 처리하기 위해서)

            String sql = "DELETE FROM WIFI_INFO";

            preparedStatement = connection.prepareStatement(sql);



            rs = preparedStatement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        System.out.println("delete successfully");
    }
}

