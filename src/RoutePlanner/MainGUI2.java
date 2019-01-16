/*
 * Created by JFormDesigner on Tue Sep 25 19:23:29 CLST 2018
 */

package RoutePlanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class MainGUI2 extends JFrame {
    public MainGUI2() {
        initComponents();
        String description = "SIMULATION";
        textField4.setText(DataSaver.getXLSFileName(description));

        try {
            dialogPane.setBorder(new LineBorder(Color.GRAY));
        } catch (RuntimeException e) {
            //nothing
        }
        //dialogPane.remove(dialogPane.getComponentCount() - 1);
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        System.exit(0);
    }

    private void thisWindowClosed(WindowEvent e) {
        System.exit(0);
    }

    private void startButtonMouseClicked(MouseEvent e) {
        int PERIODOS = Integer.parseInt(textField1.getText());
        int NUMERO_AGENTES = Integer.parseInt(textField2.getText());
        int NUMERO_SIMULACIONES = Integer.parseInt(textField3.getText());
        String DESCRIPTION = textField4.getText()+"_"+NUMERO_SIMULACIONES;

        String[] parameters = new String[]{NUMERO_AGENTES+"",PERIODOS+"",NUMERO_SIMULACIONES+"", DESCRIPTION};

        MainExperiment.main(parameters);

        /*
        Simulation s = new Simulation();

        for (int i = 1; i <= NUMERO_SIMULACIONES; ++i) {
            if (i%5 == 0) {System.out.println("Simulacion: " + i);}
            s.reinit(NUMERO_AGENTES, PERIODOS, textField4.getText()+ ":" + NUMERO_SIMULACIONES, false);
            s.ejecutar();
            //s.generarExcel();
        }

        s.generarExcel();
        */
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Paul Leger
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label2 = new JLabel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label3 = new JLabel();
        textField2 = new JTextField();
        label4 = new JLabel();
        textField3 = new JTextField();
        label5 = new JLabel();
        label6 = new JLabel();
        textField4 = new JTextField();
        label7 = new JLabel();
        checkBox1 = new JCheckBox();
        label8 = new JLabel();
        label9 = new JLabel();
        checkBox2 = new JCheckBox();
        label10 = new JLabel();
        checkBox3 = new JCheckBox();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("Route Planner");
        //setIconImage(new ImageIcon(getClass().getResource("/SelectTur/favicon1.png")).getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "insets dialog,hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- label2 ----
                label2.setText("Simulation Configuration");
                label2.setFont(new Font(".SF NS Text", Font.BOLD, 13));
                contentPanel.add(label2, "cell 0 0");

                //---- label1 ----
                label1.setText("Periods");
                contentPanel.add(label1, "cell 0 1");

                //---- textField1 ----
                textField1.setText("23");
                contentPanel.add(textField1, "cell 1 1");

                //---- label3 ----
                label3.setText("Agents");
                contentPanel.add(label3, "cell 0 2");

                //---- textField2 ----
                textField2.setText("7");
                contentPanel.add(textField2, "cell 1 2");

                //---- label4 ----
                label4.setText("Simulations");
                contentPanel.add(label4, "cell 0 3");

                //---- textField3 ----
                textField3.setText("800");
                contentPanel.add(textField3, "cell 1 3");

                //---- label5 ----
                label5.setText("Output Configuration");
                label5.setFont(new Font(".SF NS Text", Font.BOLD, 13));
                contentPanel.add(label5, "cell 0 4");

                //---- label6 ----
                label6.setText("Base Name File");
                contentPanel.add(label6, "cell 0 5");

                //---- textField4 ----
                textField4.setText("                             ");
                textField4.setEditable(true);
                contentPanel.add(textField4, "cell 1 5");

                //---- label7 ----
                label7.setText("Verbose");
                contentPanel.add(label7, "cell 0 6");

                //---- checkBox1 ----
                checkBox1.setSelected(true);
                contentPanel.add(checkBox1, "cell 1 6");

                //---- label8 ----
                /*
                label8.setText("Databases Loaded");
                label8.setFont(new Font(".SF NS Text", Font.BOLD, 13));
                contentPanel.add(label8, "cell 0 7");

                //---- label9 ----
                label9.setText("Map");
                contentPanel.add(label9, "cell 0 8");

                //---- checkBox2 ----
                checkBox2.setSelected(true);
                contentPanel.add(checkBox2, "cell 1 8");

                //---- label10 ----
                label10.setText("Province");
                contentPanel.add(label10, "cell 0 9");

                //---- checkBox3 ----
                checkBox3.setSelected(true);
                contentPanel.add(checkBox3, "cell 1 9");
                */
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));

                //---- okButton ----
                okButton.setText("Start");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        startButtonMouseClicked(e);
                    }
                });
                buttonBar.add(okButton, "cell 0 0");

                //---- cancelButton ----
                cancelButton.setText("Close");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked(e);
                    }
                });
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Paul Leger
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label2;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label3;
    private JTextField textField2;
    private JLabel label4;
    private JTextField textField3;
    private JLabel label5;
    private JLabel label6;
    private JTextField textField4;
    private JLabel label7;
    private JCheckBox checkBox1;
    private JLabel label8;
    private JLabel label9;
    private JCheckBox checkBox2;
    private JLabel label10;
    private JCheckBox checkBox3;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
