package dao;

import java.util.ArrayList;
import java.util.List;

public class CgwSQL {
    public static String searchSql = "SELECT * FROM KBOOKTABLE WHERE BOOKNAME LIKE ?";
    public static ArrayList<Boolean> serachSqlType = new ArrayList<>(List.of(true));
}
