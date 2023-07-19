package database.Proxmus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static database.Proxmus.Swing.icon;

public class Trojniki_redukcyjne_krotkie {

    public static void showTable() {
        JFrame tableFrame = new JFrame("Tabela_Trojnokow_Redukcyjnych_Krotkich");
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM trojniki_redukcyjne_krotkie");

            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class;
                    }
                    return super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0;
                }
            };

            table.setModel(tableModel);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            tableModel.addColumn("wybierz");

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnLabel(i));
            }

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            table.setRowSorter(sorter);

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount + 1];
                rowData[0] = false;
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i] = resultSet.getObject(i);
                }
                tableModel.addRow(rowData);
            }

            table.getColumnModel().getColumn(columnCount).setMinWidth(0);
            table.getColumnModel().getColumn(columnCount).setMaxWidth(0);
            table.getColumnModel().getColumn(columnCount).setWidth(0);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            if (columnIndex == 0 || columnIndex == table.getColumnCount() - 1) {
                continue;
            }
            JLabel columnLabel = new JLabel(table.getColumnName(columnIndex));
            JTextArea searchArea = new JTextArea(1, 5);
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
                    if (columnIndex == 0 || columnIndex == table.getColumnCount() - 1) {
                        continue;
                    }
                    String searchValue = searchAreas.get(columnIndex - 1).getText();
                    if (!searchValue.isEmpty()) {
                        try {
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

                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                for (int i = 0; i < table.getRowCount(); i++) {
                    tableModel.setValueAt(false, i, 0);
                }
            }
        });

        String FilePath = "Trojnik_redukcyjny_krotki_zbiór";
        Path sourceFolder = Paths.get(FilePath);

        List<Integer> selectedRows = new ArrayList<>();

        final List<File> selectedFiles = new ArrayList<>();

        JButton copyButton = new JButton("Kopiuj");
        searchPanel.add(copyButton);

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRows.clear();
                selectedFiles.clear();

                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean isSelected = (boolean) table.getValueAt(i, 0);
                    if (isSelected) {
                        selectedRows.add(i);
                    }
                }

                for (int rowIndex : selectedRows) {
                    String name = table.getValueAt(rowIndex, table.getColumnCount() - 1).toString();
                    String targetFileName = name + ".ipt";

                    try {
                        Path filePath = findFileInFolder(sourceFolder, targetFileName);
                        if (filePath != null) {
                            File file = filePath.toFile();
                            selectedFiles.add(file);

                        } else {
                            JFrame nieOdnaleziono = new JFrame();
                            nieOdnaleziono.setSize(300, 100);

                            nieOdnaleziono.setLocationRelativeTo(null);
                            nieOdnaleziono.setIconImage(icon);

                            JLabel label = new JLabel("Nie odnaleziono pliku");
                            label.setHorizontalAlignment(JLabel.CENTER);

                            nieOdnaleziono.add(label);
                            nieOdnaleziono.setVisible(true);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if (!selectedFiles.isEmpty()) {
                    try {
                        Mufy.ListTransferable transferable = new Mufy.ListTransferable(selectedFiles);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(transferable, null);

                        JFrame Odnaleziono = new JFrame();
                        Odnaleziono.setSize(300, 100);

                        Odnaleziono.setLocationRelativeTo(null);
                        Odnaleziono.setIconImage(icon);

                        JLabel label = new JLabel("Odnaleziono plik");
                        label.setHorizontalAlignment(JLabel.CENTER);

                        Odnaleziono.add(label);
                        Odnaleziono.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        tableFrame.setVisible(true);
    }

    static class ListTransferable implements Transferable {
        private final List<File> fileList;

        public ListTransferable(List<File> fileList) {
            this.fileList = fileList;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.javaFileListFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                return fileList;
            }
            throw new UnsupportedFlavorException(flavor);
        }
    }

    private static Path findFileInFolder(Path folder, String targetFileName) throws IOException {
        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + targetFileName);

        class FileFinder extends SimpleFileVisitor<Path> {
            private Path foundFile;

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path name = file.getFileName();
                if (matcher.matches(name)) {
                    foundFile = file;
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
        }

        FileFinder finder = new FileFinder();
        Files.walkFileTree(folder, finder);
        return finder.foundFile;
    }
}