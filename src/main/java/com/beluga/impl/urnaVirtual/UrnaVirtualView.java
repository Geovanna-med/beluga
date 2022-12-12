package com.beluga.impl.urnaVirtual;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.beluga.framework.mvc.Controller;
import com.beluga.framework.mvc.Model;
import com.beluga.framework.mvc.View;

public class UrnaVirtualView extends View {

    private JFrame frame;

    JLabel label1;
    JLabel label2;
    JLabel label3;
    int contador = 0;

    public UrnaVirtualView(Model model, Controller controller) {
        super(model, controller);
        this.frame = new JFrame("Urna Virtual, haz valer tu voto.");
        this.initComponents();

    }

    private void initComponents() {
        // Set size
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(null);
        // set the jframe size and location
        this.frame.setPreferredSize(new Dimension(600, 200));
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);

        // Create buttons
        JButton btnCnddt1 = new JButton(((UrnaVirtual) this.model).getCandidatos().get(0).getNombre());
        JButton btnCnddt2 = new JButton(((UrnaVirtual) this.model).getCandidatos().get(1).getNombre());
        JButton btnCnddt3 = new JButton(((UrnaVirtual) this.model).getCandidatos().get(2).getNombre());

        btnCnddt1.setBounds(100, 40, 100, 35);
        btnCnddt2.setBounds(250, 40, 100, 35);
        btnCnddt3.setBounds(400, 40, 100, 35);

        btnCnddt1.addActionListener(this.controller);
        btnCnddt2.addActionListener(this.controller);
        btnCnddt3.addActionListener(this.controller);

        this.frame.add(btnCnddt1);
        this.frame.add(btnCnddt2);
        this.frame.add(btnCnddt3);

        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();

        label1.setText("0");
        label2.setText("0");
        label3.setText("0");

        label1.setBounds(100, 90, 100, 35);
        label2.setBounds(250, 90, 100, 35);
        label3.setBounds(400, 90, 100, 35);

        this.frame.add(label1);
        this.frame.add(label2);
        this.frame.add(label3);

    }

    public static void main(String[] args) {
    }

    @Override
    public void notifyChage() {

        UrnaVirtual uv = (UrnaVirtual) this.model;
        label1.setText(String.valueOf(uv.getCandidatos().get(0).getVotos()));
        label2.setText(String.valueOf(uv.getCandidatos().get(1).getVotos()));
        label3.setText(String.valueOf(uv.getCandidatos().get(2).getVotos()));

    }

    @Override
    public void show() {
        this.frame.setVisible(true);
    }
}
