package ui;

import dao.CgwDAO;
import dao.CgwSQL;

import javax.swing.*;
import java.awt.Dimension;
import java.util.*;

public class Search extends JPanel {
    JTextField searchTextField = new JTextField();
    JButton searchButton = new JButton("검색");

    public Search() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        searchTextField.setPreferredSize(new Dimension(550, 30));
        searchTextField.setMaximumSize(new Dimension(550, 30));

        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));
        searchButton.addActionListener(e -> {
            clickEvent();
        });

        add(searchTextField);
        add(searchButton);
    }

    public void clickEvent() {
        String searchText = searchTextField.getText();
        if (!searchText.isEmpty()) {
            ArrayList<String> inputData = new ArrayList<>(List.of("%" + searchText + "%"));
            LinkedHashMap<String, ArrayList<String>> dataMaps = CgwDAO.getData(inputData, CgwSQL.serachSqlType, CgwSQL.searchSql);
            if (!dataMaps.isEmpty()) {
                String[][] dataArrays = convertMapTo2DArray(dataMaps);
                for (String[] data : dataArrays) {
                    for (String key : data) {
                        System.out.print(key + ", ");
                    }
                    System.out.println();
                }
            }

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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 200); // 프레임 크기 설정
        Search search = new Search();
        frame.add(search);

        frame.setVisible(true);
    }
}
