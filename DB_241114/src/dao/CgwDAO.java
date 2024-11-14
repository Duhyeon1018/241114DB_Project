package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CgwDAO {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String userid = "scott";
    private static final String passwd = "tiger";

    public static boolean executeUpdateSql(String sql) {
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql); // SQL 실행
            System.out.println("SQL executed successfully: " + sql);
            return true;

        } catch (SQLException e) {
            System.err.println("SQL execution failed: " + sql);
            e.printStackTrace();
            return false;
        }
    }

    public static LinkedHashMap<String, ArrayList<String>> getData(ArrayList<String> inputData, ArrayList<Boolean> inputType, String sql) {
        LinkedHashMap<String, ArrayList<String>> data = new LinkedHashMap<>();

        // 유효성 검사
        if (inputData == null || inputType == null || sql == null || sql.isEmpty()) {
            System.err.println("Input data, input type, or SQL is invalid.");
            return data;
        }

        if (inputData.isEmpty() || inputType.isEmpty()) {
            System.err.println("Input data or input type is empty.");
            return data;
        }

        if (inputData.size() != inputType.size()) {
            System.err.println("Input data and input type sizes do not match.");
            return data;
        }

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // 입력값 설정
            for (int i = 0; i < inputData.size(); i++) {
                if (inputType.get(i)) stmt.setString(i + 1, inputData.get(i));
                else stmt.setInt(i + 1, Integer.parseInt(inputData.get(i)));
            }

            // SQL 실행 및 결과 처리
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String columnValue = rs.getString(i);
                        if (columnValue == null) {
                            columnValue = "NULL";
                        }
                        data.computeIfAbsent(columnName, k -> new ArrayList<>()).add(columnValue);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL execution failed: " + sql);
            e.printStackTrace();
        }

        return data;
    }

    public static Map<String, ArrayList<String>> getData(String sql) {
        Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = rs.getString(i);
                    if (columnValue == null) {
                        columnValue = "NULL";
                    }
                    data.computeIfAbsent(columnName, k -> new ArrayList<>()).add(columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data.isEmpty()) System.out.println("Data is empty");
        return data;
    }
}
