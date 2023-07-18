package database.Proxmus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static database.Proxmus.Swing.icon;

public class Trojniki_redukcyjne {

    public static void showTable() {
        JFrame tableFrame = new JFrame("Tabela_Trojnikow_Redukcyjnych");
        tableFrame.setSize(1600, 1000);
        tableFrame.setIconImage(icon);
        tableFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        tableFrame.getContentPane().add(panel);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout());
        panel.add(searchPanel, BorderLayout.NORTH);

        List<JTextArea> searchAreas = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database1.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM trojniki_redukcyjne");

            DefaultTableModel tableModel = new DefaultTableModel();
            table.setModel(tableModel);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 2; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnLabel(i));
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount + 1];
                for (int i = 2; i <= columnCount; i++) {
                    rowData[i - 2] = resultSet.getObject(i);
                }
                tableModel.addRow(rowData);

                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);

                TableColumnModel columnModel = table.getColumnModel();
                for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
                    TableColumn column = columnModel.getColumn(columnIndex);
                    int maxTextLength = 0;
                    for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
                        Object value = table.getValueAt(rowIndex, columnIndex);
                        if (value != null) {
                            int textLength = value.toString().length();
                            if (textLength > maxTextLength) {
                                maxTextLength = textLength;
                            }
                        }
                    }
                    column.setPreferredWidth(maxTextLength * 10);
                }

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            JLabel columnLabel = new JLabel(table.getColumnName(columnIndex));
            JTextArea searchArea = new JTextArea(1, 10);
            searchPanel.add(columnLabel);
            searchPanel.add(searchArea);
            searchAreas.add(searchArea);
        }

        JButton filterButton = new JButton("Filtruj");
        searchPanel.add(filterButton);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RowFilter<Object, Object>> filters = new ArrayList<>();
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
                    String searchValue = searchAreas.get(columnIndex).getText();
                    if (!searchValue.isEmpty()) {
                        try {
                            // Tworzymy filtr dla konkretnej kolumny
                            RowFilter<Object, Object> columnFilter = RowFilter.regexFilter("(?i)" + searchValue, columnIndex);
                            filters.add(columnFilter);
                        } catch (PatternSyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                sorter.setRowFilter(RowFilter.andFilter(filters));
            }
        });

        JButton resetButton = new JButton("Resetuj");
        searchPanel.add(resetButton);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                sorter.setRowFilter(null);

                for (JTextArea searchArea : searchAreas) {
                    searchArea.setText("");
                }
            }
        });
        tableFrame.setVisible(true);
    }
}