package com.example.zero_study_mid.dao;

import com.example.zero_study_mid.dto.HistoryValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Historydb {
        public static void main(String[] args) {
        selectDb();
//         saveDb(37.5026529,126.7635544);

//        withdrawDb(1);
    }

    public static void saveDb(double lat, double lnt) {
        String url = "jdbc:sqlite://Users//jigi//publicWifi.sqlite3";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rs;
        String LAT = String.valueOf(lat);
        String LNT = String.valueOf(lnt);

        try {
            connection = DriverManager.getConnection(url);

//            statement = connection.createStatement(); // 이건 파라미터 처리 안됨
            // 데이터 베이스 쿼리를 실행하기위해 (파라미터 이슈, 처리하기 위해서)

            String sql = " INSERT INTO HISTORY(X_LAT, Y_LNT)" +
                    " values ( ?,?)";

            preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, LAT);
            preparedStatement.setString(2, LNT);

            rs = preparedStatement.executeUpdate();


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

//        System.out.println("Opened database successfully");
      }
    }

    public static List<HistoryValue> selectDb(){
        List<HistoryValue> list = new ArrayList<>();

        String url = "jdbc:sqlite://Users//jigi//publicWifi.sqlite3";


        try {
            Class.forName("org.sqlite.JDBC");
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet rs = null;


        try {
            connection = DriverManager.getConnection(url);

//            statement = connection.createStatement(); // 이건 파라미터 처리 안됨
            // 데이터 베이스 쿼리를 실행하기위해 (파라미터 이슈, 처리하기 위해서)

            String sql = " SELECT *  FROM HISTORY";

            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            double distance;

            while (rs.next()) {
                int Id = rs.getInt("ID");
                String LAT = rs.getString("X_LAT");
                String LNT = rs.getString("Y_LNT");
                String Date = rs.getString("DATE");


                list.add(new HistoryValue(Id, LAT, LNT, Date));

            }

            for (HistoryValue historyValue : list) {
                System.out.println(historyValue);
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
    public static void withdrawDb(String Id){
        String url = "jdbc:sqlite://Users//jigi//publicWifi.sqlite3";


        try {
            Class.forName("org.sqlite.JDBC");
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String id = Id;

        int rs;

        try {
            connection = DriverManager.getConnection(url);

//            statement = connection.createStatement(); // 이건 파라미터 처리 안됨
            // 데이터 베이스 쿼리를 실행하기위해 (파라미터 이슈, 처리하기 위해서)

            String sql = "DELETE FROM HISTORY WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);


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

