package Server;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class MiniChatServerDlg extends javax.swing.JFrame {

    public MiniChatServerDlg() {
        initComponents();
        this.setResizable(false);
        jServerIP.setText("Nhập port để hiển thị IP");
    }

    // Chú ý khai báo
    private Boolean status = false;
    private int PortServer = 0;
    private ServerSocket serversocket = null;
    // Chú ý khai báo   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlbServerIP = new javax.swing.JLabel();
        jlbServerPort = new javax.swing.JLabel();
        jtfServerPort = new javax.swing.JTextField();
        jServerIP = new javax.swing.JLabel();
        btnTurn = new javax.swing.JButton();
        jlbStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MiniChatServer - Đóng mở Server - Kết nối Client");

        jlbServerIP.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlbServerIP.setText("Server IP:");

        jlbServerPort.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlbServerPort.setText("Server Port:");

        jtfServerPort.setText("Text Field");
        jtfServerPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfServerPortKeyPressed(evt);
            }
        });

        jServerIP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jServerIP.setText("Nhập Port để hiển thị IP");

        btnTurn.setText("Mở kết nối");
        btnTurn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTurnMouseClicked(evt);
            }
        });

        jlbStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlbStatus.setText("Server đang đóng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlbServerIP)
                        .addGap(43, 43, 43)
                        .addComponent(jServerIP)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbServerPort)
                        .addGap(18, 18, 18)
                        .addComponent(jtfServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTurn)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jServerIP)
                    .addComponent(jlbServerIP))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbServerPort)
                    .addComponent(jtfServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTurn))
                .addGap(13, 13, 13)
                .addComponent(jlbStatus)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("static-access")
// Quay lại tab Design - Bấm vào JTextField - Chọn Event ở mục Properties - Tìm KeyPressed - Click 2 lần
    private void jtfServerPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfServerPortKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int Error = 0;
            if (status == false) {
                try {
                    PortServer = Integer.parseInt(jtfServerPort.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Port Lỗi, Kiểm tra lại", "Error", JOptionPane.OK_OPTION);
                    Error++;
                }
                if (Error == 0) {
                    try {
                        serversocket = new ServerSocket(PortServer);
                        jServerIP.setText(serversocket.getInetAddress().getLocalHost().getHostAddress());
                        Server server = new Server(serversocket, PortServer);
                        btnTurn.setText("Đóng kết nối");
                        jlbStatus.setText("Server đang mở!");
                        status = true;
                    } catch (IOException ex) {
                        Logger.getLogger(MiniChatServerDlg.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                btnTurn.setText("Mở kết nối");
                jlbStatus.setText("Server đang tắt!");
                status = false;
                System.exit(0);
            }
        }
    }//GEN-LAST:event_jtfServerPortKeyPressed
    @SuppressWarnings("static-access")
    private void btnTurnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTurnMouseClicked
        // TODO add your handling code here:
        int Error = 0;
        if (status == false) {
            try {
                PortServer = Integer.parseInt(jtfServerPort.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Không để trống Port!", "Error", JOptionPane.OK_OPTION);
                Error++;
            }
            if (Error == 0) {
                try {
                    serversocket = new ServerSocket(PortServer);
                    jServerIP.setText(serversocket.getInetAddress().getLocalHost().getHostAddress());
                    Server server = new Server(serversocket, PortServer);
                    btnTurn.setText("Đóng kết nối");
                    jlbStatus.setText("Server đang mở!");
                    status = true;
                } catch (IOException ex) {
                    Logger.getLogger(MiniChatServerDlg.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            btnTurn.setText("Mở kết nối");
            jlbStatus.setText("Server đang đóng!");
            status = false;
            System.exit(0);
        }
    }//GEN-LAST:event_btnTurnMouseClicked

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(MiniChatServerDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiniChatServerDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiniChatServerDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiniChatServerDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MiniChatServerDlg dialog = new MiniChatServerDlg();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTurn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jServerIP;
    private javax.swing.JLabel jlbServerIP;
    private javax.swing.JLabel jlbServerPort;
    private javax.swing.JLabel jlbStatus;
    private javax.swing.JTextField jtfServerPort;
    // End of variables declaration//GEN-END:variables

}
