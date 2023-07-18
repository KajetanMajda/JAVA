package database.Proxmus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Proxmus.Swing.icon;

public class Podzial_trojnikow {
    private JButton krotkiButton;
    private JPanel panel1;
    private JButton redukcyjny_krotkiButton;
    private JButton redukcyjnyButton;
    private JButton rownoramiennyButton;


    public  Podzial_trojnikow() {
        krotkiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trojniki_krotkie.showTable();
            }
        });
        redukcyjny_krotkiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trojniki_redukcyjne_krotkie.showTable();
            }
        });
        redukcyjnyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trojniki_redukcyjne.showTable();
            }
        });
        rownoramiennyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trojnki_rownoramienne.showTable();
            }
        });

    }
public void trojniki_podzial() {
    krotkiButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Trojniki_krotkie.showTable();

        }
    });
    redukcyjny_krotkiButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Trojniki_redukcyjne_krotkie.showTable();
        }
    });
    redukcyjnyButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Trojniki_redukcyjne.showTable();
        }
    });
    rownoramiennyButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Trojnki_rownoramienne.showTable();
        }
    });

    JFrame frame = new JFrame("Podzial_trojnikow");
    frame.setContentPane(new Podzial_trojnikow().panel1);
    frame.setIconImage(icon);
    frame.pack();
    frame.setVisible(true);
    frame.setSize(1000,300);
    frame.setLocationRelativeTo(null);
}
}
