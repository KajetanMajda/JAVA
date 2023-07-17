package database.Proxmus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swing{

    private JPanel panel_main;
    private JButton kolanaButton;
    private JButton korkiButton;
    private JButton mufyButton;
    private JButton redukcjeButton;
    private JButton trojnikiButton;

    static Image icon = Toolkit.getDefaultToolkit().getImage("pingwin.jpg");

    private Podzial_redukcji podzialRedukcji;

    private Podzial_trojnikow podzialTrojnikow;




    public Swing() {
        podzialRedukcji = new Podzial_redukcji();
        podzialTrojnikow = new Podzial_trojnikow();

        kolanaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Kolana.showTable();}
        });
        korkiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Korki.showTable();
            }
        });
        mufyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Mufy.showTable();}
        });
        redukcjeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                podzialRedukcji.redukcjeButtonAction();
            }
        });
        trojnikiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                podzialTrojnikow.trojniki_podzial();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Zbior_Danych");
        frame.setContentPane(new Swing().panel_main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icon);
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(560,340,800,400);

    }
}

