/*
 * The MIT License
 *
 * Copyright 2023 Juliano cassimiro dos Santos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.urnaetec.view;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import br.com.urnaetec.factory.ModuloConexao;

/**
 * Tela JFrame para Inicio
 *
 * @author Juliano
 * @version 1.10
 */
public class TelaPjrinfox extends javax.swing.JFrame {

    /**
     * Creates new form TelaPjrinfox
     */
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaPjrinfox() {
        initComponents();
        conexao = ModuloConexao.conectar(); // Conecta ao banco de dados utilizando a classe ModuloConexao
    }

    private void fotoUrna() {
        ImageIcon urna = new ImageIcon("E:\\Projetos Completos\\Projeto Urna\\Urna_Eleitoral\\src\\lib\\utils\\img\\urna.png");
        Image image = urna.getImage();
        Image scaledImage = image.getScaledInstance(lblFotoUrna.getWidth(), lblFotoUrna.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        lblFotoUrna.setIcon(scaledIcon);
    }

    private void fotoAdm() {
        ImageIcon adm = new ImageIcon("E:\\Projetos Completos\\Projeto Urna\\Urna_Eleitoral\\src\\lib\\utils\\img\\adm.png");
        Image image = adm.getImage();
        Image scaledImage = image.getScaledInstance(lblFotoAdm.getWidth(), lblFotoAdm.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        lblFotoAdm.setIcon(scaledIcon);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFotoAdm = new javax.swing.JLabel();
        lblFotoUrna = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("X - Inicio");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFotoAdm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFotoAdmMouseClicked(evt);
            }
        });
        getContentPane().add(lblFotoAdm, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 121, 200, 200));

        lblFotoUrna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFotoUrnaMouseClicked(evt);
            }
        });
        getContentPane().add(lblFotoUrna, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 121, 200, 200));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 460));

        setSize(new java.awt.Dimension(590, 470));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        fotoUrna();
        fotoAdm();

    }//GEN-LAST:event_formWindowActivated

    private void lblFotoUrnaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoUrnaMouseClicked

        String sql = "SELECT * FROM tb05_eleitor WHERE tb05_cod_eleitor = ?";
        try {
            pst = conexao.prepareStatement(sql);
            String numeroDigitado = JOptionPane.showInputDialog(null, "Digite seu Numero de Eleitor:");

            if (numeroDigitado != null && !numeroDigitado.isEmpty()) {
                pst.setString(1, numeroDigitado);
                rs = pst.executeQuery();

                // Verificar se o número existe
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Número encontrado! Parece que você já votou.");
                    // Restante do código para permitir a entrada na tela
                } else {
                    String consulta = "INSERT INTO tb05_eleitor(tb05_cod_eleitor) VALUES(?)";
                    try {
                        pst = conexao.prepareStatement(consulta);
                        pst.setString(1, numeroDigitado);
                        int create = pst.executeUpdate();
                        if (create > 0) {
                            // Exibe uma mensagem de sucesso ao cadastrar o usuário
                            TelaUrna urna = new TelaUrna();
                            urna.setVisible(true);
                            this.dispose();
                        }
                        // Fechar recursos
                        rs.close();
                        pst.close();
                        conexao.close();
                    } catch (SQLException e) {
                       JOptionPane.showMessageDialog(null, "APENAS NUMERO");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e); // Exibe uma mensagem de erro caso ocorra uma exceção
        }

    }//GEN-LAST:event_lblFotoUrnaMouseClicked

    private void lblFotoAdmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoAdmMouseClicked
        TelaLogin login = new TelaLogin();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblFotoAdmMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPjrinfox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPjrinfox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPjrinfox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPjrinfox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPjrinfox().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFotoAdm;
    private javax.swing.JLabel lblFotoUrna;
    // End of variables declaration//GEN-END:variables
}
