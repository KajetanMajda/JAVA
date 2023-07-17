package database.Proxmus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Proxmus.Swing.icon;

public class Podzial_redukcji{
    private JButton asymetryczneButton;
    private JPanel panel1;
    private JButton symetryczneButton;

    public Podzial_redukcji(){
        asymetryczneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Redukcje_asymetryczne.showTable();
            }
        });
        symetryczneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Redukcje_symetryczne.showTable();

            }
        });

    }


    public void redukcjeButtonAction() {
        asymetryczneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Redukcje_asymetryczne.showTable();
            }
        });
        symetryczneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Redukcje_symetryczne.showTable();

            }
        });


        JFrame frame = new JFrame("Podzial_redukcji");
        frame.setContentPane(new Podzial_redukcji().panel1);
        frame.setIconImage(icon);
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(560,340,800,400);
    }
}
