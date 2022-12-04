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
    JLabel label;
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
        this.frame.setPreferredSize(new Dimension(600, 150));
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

        label = new JLabel();

        label.setBounds(250, 70, 35, 35);

        this.frame.add(label);

    }

    public static void main(String[] args) {
    }

    @Override
    public void notifyChage() {
        // TODO Auto-generated method stub
        System.out.println("Change notification from model into view: com.beluga.impl.urnaVirtual");
        System.out.println(((UrnaVirtual) this.model).getCandidatos().get(0).getNombre());
        System.out.println(((UrnaVirtual) this.model).getCandidatos().get(0).getVotos());
        System.out.println(((UrnaVirtual) this.model).getCandidatos().get(1).getNombre());
        System.out.println(((UrnaVirtual) this.model).getCandidatos().get(1).getVotos());
        System.out.println(((UrnaVirtual) this.model).getCandidatos().get(2).getNombre());
        System.out.println(((UrnaVirtual) this.model).getCandidatos().get(2).getVotos());
        label.setText(String.valueOf(++contador));

    }

    @Override
    public void show() {
        this.frame.setVisible(true);        
    }
}
