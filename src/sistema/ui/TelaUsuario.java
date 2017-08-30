/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.ui;

import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import sistema.entidade.Tbusuarios;
import sistema.util.HibernateUtil;

/**
 *
 * @author gui1_
 */
public class TelaUsuario extends javax.swing.JFrame {
    private String ativo = "1";
    public String perfil;
    public String senha;
    public String ConfirmaSenha;
    public String[] aux = new String[4]; 
    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
    }
    //variável que guarda a pesquisa ao banco from Tbusuarios usu where usu.usuario like '%'
    private static String query_nome="from Tbusuarios usu where usu.usuario like '";
    //Método que executa a query
    private void busca_Usuario()
    {
        //completando a query com o campo de texto e setando para encontrar apenas com status=1
        executaBusca(query_nome + txtUsuPesquisa.getText() +"%' and usu.status='"+ativo.toString()+"'");
        btnUsuAltera.setEnabled(true);
        
    }
    //Vai executar a busca do usuário e colocar os dados na tabela
    private void executaBusca(String hql)
    {
        try
        {
        //testar JOptionPane.showMessageDialog(null, "-w vou executar a busca");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        session.getTransaction().commit();
        List resultado = query.list();
        for (Object o : resultado)
        {
            Tbusuarios usu = (Tbusuarios)o;
            senha  = usu.getSenha();
            perfil = usu.getPerfil();
        }
        //Colocando dados na tabela
        preencheTabela(resultado);
        //testar JOptionPane.showMessageDialog(null, "-w executei e mandei para preenchertabela");
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }        
    }
    //Método para preencher a tabela
    private void preencheTabela(List resultado)
    {
        //testar JOptionPane.showMessageDialog(null, "-w estou colocando no vetor para começar a preencher");
        Vector<String> cabecaTabela = new Vector<String>();
        Vector dadoTabela = new Vector();
        cabecaTabela.add("Identificador");
        cabecaTabela.add("Nome");
        cabecaTabela.add("Login");
        cabecaTabela.add("Perfil");
        
        for (Object o : resultado)
        {
            Tbusuarios usu = (Tbusuarios)o;
            Vector<Object> umaLinha = new Vector<Object>();
            umaLinha.add(usu.getIdusuario());
            umaLinha.add(usu.getUsuario());
            umaLinha.add(usu.getLogin());
            umaLinha.add(usu.getPerfil());
            dadoTabela.add(umaLinha);
        }
        tblUsu.setModel(new DefaultTableModel(dadoTabela, cabecaTabela));
    }
    //Método para dicionar usuários
    public void adicionar_usuario()
    {
        if (txtUsuNome.getText().isEmpty() || txtUsuLogin.getText().isEmpty() || txtUsuSenha.getText().isEmpty() || txtUsuFone.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Existem campos obrigatórios em branco!");
        }
        else if (txtUsuSenha.getText().equals(txtUsuConfSenha.getText()))
        {
            executa_Adicionar_Usuario("INSERT INTO Tbusuarios (usuario,fone,login,senha,perfil,status)"+"values('"+txtUsuNome.getText()+"','"+txtUsuFone.getText()+"','"+txtUsuLogin.getText()+"','"+txtUsuSenha.getText()+"','"+cmbUsuPerfil.getSelectedItem().toString()+"','1')"); 
        }
        else
        {
            JOptionPane.showMessageDialog(null, "As senhas não são iguais!");
        }
    }
    
    private void executa_Adicionar_Usuario(String hql)
    {
        try
        {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        //List resultado = query.list();
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        txtUsuNome.setText(null);
        txtUsuId.setText(null);
        txtUsuFone.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha.setText(null);
        txtUsuConfSenha.setText(null);
        JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
    }
    //Método para alterar o usuário
        public void alterar_usuario()
    {
        if (txtUsuNome.getText().isEmpty() || txtUsuLogin.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Existem campos obrigatórios em branco!");
        }
        else
        {
            executa_Alterar_Usuario("UPDATE `tbusuarios` SET `usuario` = '"+txtUsuNome.getText()+"', `fone` ='"+txtUsuFone.getText()+"', `login` ='"+txtUsuLogin.getText()+"', `perfil` = '"+cmbUsuPerfil.getSelectedItem().toString()+"')"); 
        }
    }
    
    private void executa_Alterar_Usuario(String sql)
    {
        try
        {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        txtUsuNome.setText(null);
        txtUsuId.setText(null);
        txtUsuFone.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha.setText(null);
        txtUsuConfSenha.setText(null);
        JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
    }
    //Executa a alteração de senha do usuário selecionado
    public void altera_senha()
    {
        if (txtUsuSenha.getText().equals(txtUsuConfSenha.getText()))
        {
        try
        {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("UPDATE `tbusuarios` SET `senha` = '"+txtUsuSenha.getText()+"' where `idusuario`='"+txtUsuId.getText()+"'");
        query.executeUpdate();
        session.getTransaction().commit();
        JOptionPane.showMessageDialog(null, "Alteração feita com sucesso!");
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "A confirmação da senha tem que estar igual a senha!");
        }
    }
    //setando o cliente na tabela
        public void setar_campos()
    {
        int setar = tblUsu.getSelectedRow();
        txtUsuId.setText(tblUsu.getModel().getValueAt(setar, 0).toString());
        txtUsuNome.setText(tblUsu.getModel().getValueAt(setar, 1).toString());
        txtUsuLogin.setText(tblUsu.getModel().getValueAt(setar, 2).toString());
        cmbUsuPerfil.setSelectedItem(tblUsu.getModel().getValueAt(setar,3).toString());
        btnUsuAltSenha.setEnabled(true);
        /*for (int i=0;i<aux.length;i++)
        {
            JOptionPane.showMessageDialog(this, aux[i]);
            aux[i] = tblUsu.getModel().getValueAt(setar, i).toString();
            lblUsuId.setText(aux[0]);
            txtUsuNome.setText(aux[1]);
            txtUsuFone.setText(aux[2]);
            txtUsuLogin.setText(aux[3]);
            txtUsuSenha.setText(senha);
            cmbUsuPerfil.setSelectedItem(perfil);
        }*/
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jFrame1 = new javax.swing.JFrame();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        radioUsuAtivo = new javax.swing.JRadioButton();
        radioUsuInativo = new javax.swing.JRadioButton();
        txtUsuNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuFone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsuLogin = new javax.swing.JTextField();
        cmbUsuPerfil = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnUsuAltera = new javax.swing.JButton();
        btnUsuApaga = new javax.swing.JButton();
        txtUsuPesquisa = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtUsuConfSenha = new javax.swing.JPasswordField();
        btnUsuAdd = new javax.swing.JButton();
        txtUsuId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsu = new javax.swing.JTable();
        btnUsuAltSenha = new javax.swing.JButton();
        btnUsuAjudaSenha = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Funcionários");

        buttonGroup1.add(radioUsuAtivo);
        radioUsuAtivo.setSelected(true);
        radioUsuAtivo.setText("Ativos");
        radioUsuAtivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioUsuAtivoActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioUsuInativo);
        radioUsuInativo.setText("Inativos");
        radioUsuInativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioUsuInativoActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome");

        jLabel2.setText("Telefone");

        jLabel3.setText("Login");

        jLabel4.setText("Senha");

        cmbUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));
        cmbUsuPerfil.setToolTipText("");

        jLabel5.setText("Perfil");

        btnUsuAltera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/imagens/alterabttn.png"))); // NOI18N
        btnUsuAltera.setToolTipText("Clique para executar a alteração");
        btnUsuAltera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuAltera.setEnabled(false);
        btnUsuAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuAlteraActionPerformed(evt);
            }
        });

        btnUsuApaga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/imagens/cancelabttn.png"))); // NOI18N
        btnUsuApaga.setToolTipText("Clique para inativar");
        btnUsuApaga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuApaga.setEnabled(false);

        txtUsuPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuPesquisaKeyReleased(evt);
            }
        });

        jLabel6.setText("Identificador");

        jLabel7.setText("Confirmar Senha");

        btnUsuAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/imagens/novobttn.png"))); // NOI18N
        btnUsuAdd.setToolTipText("Para novo usuário, clique aqui");
        btnUsuAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuAddActionPerformed(evt);
            }
        });

        txtUsuId.setEditable(false);

        tblUsu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Identificador", "Nome", "Login", "Perfil"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsu);

        btnUsuAltSenha.setText("Altera Senha");
        btnUsuAltSenha.setToolTipText("");
        btnUsuAltSenha.setEnabled(false);
        btnUsuAltSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuAltSenhaActionPerformed(evt);
            }
        });

        btnUsuAjudaSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/imagens/ajudamenor.png"))); // NOI18N
        btnUsuAjudaSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuAjudaSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(txtUsuPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(jLabel3)))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtUsuConfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUsuAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUsuAltera, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUsuApaga, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioUsuInativo)
                    .addComponent(radioUsuAtivo)
                    .addComponent(btnUsuAltSenha)
                    .addComponent(btnUsuAjudaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtUsuPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(radioUsuAtivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioUsuInativo))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUsuAjudaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtUsuConfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuAltSenha))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUsuApaga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUsuAltera)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(302, 302, 302))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUsuAdd)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        setSize(new java.awt.Dimension(622, 411));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void radioUsuAtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioUsuAtivoActionPerformed
        ativo = "1";
    }//GEN-LAST:event_radioUsuAtivoActionPerformed

    private void radioUsuInativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioUsuInativoActionPerformed
        ativo = "0";
    }//GEN-LAST:event_radioUsuInativoActionPerformed

    private void txtUsuPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuPesquisaKeyReleased
        busca_Usuario();
        btnUsuAdd.setEnabled(false);
        btnUsuAltera.setEnabled(true);
    }//GEN-LAST:event_txtUsuPesquisaKeyReleased

    private void btnUsuAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuAlteraActionPerformed
        
    }//GEN-LAST:event_btnUsuAlteraActionPerformed

    private void btnUsuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuAddActionPerformed
        adicionar_usuario();
    }//GEN-LAST:event_btnUsuAddActionPerformed

    private void tblUsuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblUsuMouseClicked

    private void btnUsuAjudaSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuAjudaSenhaActionPerformed
        JOptionPane.showMessageDialog(null, "Para alterar a senha, insira a senha e confirmação, depois clique no botão alterar logo abaixo.");
    }//GEN-LAST:event_btnUsuAjudaSenhaActionPerformed

    private void btnUsuAltSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuAltSenhaActionPerformed
        altera_senha();
    }//GEN-LAST:event_btnUsuAltSenhaActionPerformed

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
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuAdd;
    private javax.swing.JButton btnUsuAjudaSenha;
    private javax.swing.JButton btnUsuAltSenha;
    private javax.swing.JButton btnUsuAltera;
    private javax.swing.JButton btnUsuApaga;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbUsuPerfil;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JRadioButton radioUsuAtivo;
    public static javax.swing.JRadioButton radioUsuInativo;
    private javax.swing.JTable tblUsu;
    private javax.swing.JPasswordField txtUsuConfSenha;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuPesquisa;
    private javax.swing.JPasswordField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
