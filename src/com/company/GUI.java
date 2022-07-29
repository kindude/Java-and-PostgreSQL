package com.company;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import static com.company.SQL.getAllDocument;
import static com.company.SQL.getAllSymbol;

public class GUI extends JFrame implements ActionListener {
    private Connection DB;
    private JScrollPane jspDoc,jsp;
    private JFrame jf;
    JButton jbutton1;
    private JTable documentTable,symbolTable;
    private Container container;
    private DefaultTableModel dtmSymbol = new DefaultTableModel(){
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;

        };

    };
    private DefaultTableModel dtmDocument = new DefaultTableModel(){
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;

        };
    };
    private ResultSet rsSymbol,rsDocument;
    private ResultSetMetaData rsmdSymbol,rsmdDocument;

    public GUI(Connection DB) throws SQLException {
        super("");
        this.DB = DB;

        symbolTable = new JTable();
        documentTable = new JTable();
        symbolTable.getTableHeader().setReorderingAllowed(false);
        documentTable.getTableHeader().setReorderingAllowed(false);
        FrameOptions();
        SettingTables();
        rsDocument = getAllDocument(DB);
        rsmdDocument = rsDocument.getMetaData();
        rsSymbol = getAllSymbol(DB);
        rsmdSymbol = rsSymbol.getMetaData();
        TableDocumentResult();
        TableSymbolResult();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbutton1)
        {

        }
    }

    private void SettingTables()
    {
        dtmSymbol.addColumn("Индекс символа");
        dtmSymbol.addColumn("Индекс документ");
        dtmSymbol.addColumn("Символ");
        dtmSymbol.addColumn("Печатаемый");
        dtmDocument.addColumn("Индекс документа");
        dtmDocument.addColumn("Название документа");
        dtmDocument.addColumn("Максимальное количество символов");
    }
    private void TableSymbolResult() throws SQLException {
        while(rsSymbol.next())
        {
            final Object[] data = new Object[]{
                    rsSymbol.getInt("id_Symbol"),rsSymbol.getInt("id_Document"),rsSymbol.getString("Character"),rsSymbol.getBoolean("Printable")
            };
            dtmSymbol.addRow(data);
        }
    }

    private void TableDocumentResult() throws SQLException {

        while(rsDocument.next())
        {
            final Object[] data = new Object[]{
                    rsDocument.getInt("id_Document"),rsDocument.getString("Document_Name"),rsDocument.getInt("Max_Length")
            };
            dtmDocument.addRow(data);
        }
    }


    private void FrameOptions()
    {
        jf = new JFrame("Работа с базой данных");
        container = getContentPane();
        jspDoc =  new JScrollPane((TableDocumentOptions()));
        jsp = new JScrollPane(TableSymbolOptions());
        GridLayout grid =  new GridLayout(4,1,80,30);
        Container containerinside = new Container();
        containerinside.setLayout(new GridLayout(1,3,0,0));
        container.setLayout(grid);
        container.add(jspDoc);
        container.add(jsp);
        jf.setSize(800, 600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(container);
        jf.setVisible(true);
    }

    private void Update(int d) throws SQLException {
        rsSymbol = getAllSymbol(DB,d);
        rsmdSymbol = rsSymbol.getMetaData();
        DefaultTableModel dm = (DefaultTableModel)symbolTable.getModel();
        dm.getDataVector().removeAllElements();
        TableSymbolResult();
        dtmSymbol.fireTableStructureChanged();
    }
    private JTable TableDocumentOptions()
    {
        documentTable.setModel(dtmDocument);
        documentTable.setRowHeight(40);
        documentTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int d = (Integer) (documentTable.getValueAt(documentTable.getSelectedRow(),0));
                try {
                   Update(d);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        return documentTable;
    }
    private JTable TableSymbolOptions()
    {
        symbolTable.setModel(dtmSymbol);
        symbolTable.setRowHeight(35);
        return symbolTable;
    }

}
