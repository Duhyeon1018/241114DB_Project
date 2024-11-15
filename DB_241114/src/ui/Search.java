package ui;

import dao.CgwDAO;
import dao.CgwSQL;

import javax.swing.*;
import java.util.*;

public class Search extends JPanel {
    JTextField searchTextField = new JTextField(36);
    JButton searchButton = new JButton("검색");
    public static int userNum = 1;
    private final UI2Cgw parent;
    private String searchTitle;

    public Search(UI2Cgw parent) {
        this.parent = parent;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        searchButton.addActionListener(e -> {
            clickEvent();
        });

        add(searchTextField);
        add(searchButton);
    }

    public void clickEvent() {
        String searchText = searchTextField.getText();
        if (!searchText.isEmpty()) {
            setSearchSql();
            ArrayList<String> inputData = new ArrayList<>(Arrays.asList(userNum + "", "%" + searchText + "%"));
            String searchSql = CgwSQL.getSearchSql(searchTitle);
            LinkedHashMap<String, ArrayList<String>> dataMaps = CgwDAO.getData(inputData, CgwSQL.searchSqlType, searchSql);
            if (!dataMaps.isEmpty()) {
                String[][] dataArrays = convertMapTo2DArray(dataMaps);
                parent.tableModel.setRowCount(0);
                for (String[] data : dataArrays) {
                    parent.tableModel.addRow(data);
                    System.out.println();
                }
            }else System.err.println("data is empty");

        }else System.err.println("searchText is empty");
    }

    public static String[][] convertMapTo2DArray(LinkedHashMap<String, ArrayList<String>> data) {
        if (data == null || data.isEmpty()) {
            return new String[0][0];
        }

        int columnCount = data.size();
        int rowCount = data.values().stream().mapToInt(ArrayList::size).max().orElse(0);

        String[][] result = new String[rowCount][columnCount];

        int colIndex = 0;
        for (Map.Entry<String, ArrayList<String>> entry : data.entrySet()) {
            ArrayList<String> columnData = entry.getValue();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                result[rowIndex][colIndex] = rowIndex < columnData.size() ? columnData.get(rowIndex) : "NULL";
            }
            colIndex++;
        }
        return result;
    }

    public void setSearchSql() {
        String comboBoxString = parent.getComboBoxString();
        if (!comboBoxString.isEmpty()) {
            switch (comboBoxString) {
                case "책이름":
                    searchTitle = "B.BOOKNAME";
                    break;
                case "장르":
                    searchTitle = "G.GENRETABLENAME";
                    break;
                case "출판사":
                    searchTitle = "P.PUBLISHERNAME";
                    break;
                case "작가":
                    searchTitle = "A.AUTHORNAME";
                    break;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 200); // 프레임 크기 설정
//        Search search = new Search();
//        frame.add(search);

        frame.setVisible(true);
    }
}
