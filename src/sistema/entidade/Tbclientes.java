package sistema.entidade;
// Generated 25/08/2017 19:03:45 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Tbclientes generated by hbm2java
 */
public class Tbclientes  implements java.io.Serializable {


     private Integer idcli;
     private String nomecli;
     private String endcli;
     private String bairrocli;
     private String cidadecli;
     private String cepcli;
     private String fonecli;
     private String emailcli;
     private String cpfcli;
     private int status;
     private Set tboses = new HashSet(0);

    public Tbclientes() {
    }

	
    public Tbclientes(String nomecli, String fonecli, int status) {
        this.nomecli = nomecli;
        this.fonecli = fonecli;
        this.status = status;
    }
    public Tbclientes(String nomecli, String endcli, String bairrocli, String cidadecli, String cepcli, String fonecli, String emailcli, String cpfcli, int status, Set tboses) {
       this.nomecli = nomecli;
       this.endcli = endcli;
       this.bairrocli = bairrocli;
       this.cidadecli = cidadecli;
       this.cepcli = cepcli;
       this.fonecli = fonecli;
       this.emailcli = emailcli;
       this.cpfcli = cpfcli;
       this.status = status;
       this.tboses = tboses;
    }
   
    public Integer getIdcli() {
        return this.idcli;
    }
    
    public void setIdcli(Integer idcli) {
        this.idcli = idcli;
    }
    public String getNomecli() {
        return this.nomecli;
    }
    
    public void setNomecli(String nomecli) {
        this.nomecli = nomecli;
    }
    public String getEndcli() {
        return this.endcli;
    }
    
    public void setEndcli(String endcli) {
        this.endcli = endcli;
    }
    public String getBairrocli() {
        return this.bairrocli;
    }
    
    public void setBairrocli(String bairrocli) {
        this.bairrocli = bairrocli;
    }
    public String getCidadecli() {
        return this.cidadecli;
    }
    
    public void setCidadecli(String cidadecli) {
        this.cidadecli = cidadecli;
    }
    public String getCepcli() {
        return this.cepcli;
    }
    
    public void setCepcli(String cepcli) {
        this.cepcli = cepcli;
    }
    public String getFonecli() {
        return this.fonecli;
    }
    
    public void setFonecli(String fonecli) {
        this.fonecli = fonecli;
    }
    public String getEmailcli() {
        return this.emailcli;
    }
    
    public void setEmailcli(String emailcli) {
        this.emailcli = emailcli;
    }
    public String getCpfcli() {
        return this.cpfcli;
    }
    
    public void setCpfcli(String cpfcli) {
        this.cpfcli = cpfcli;
    }
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    public Set getTboses() {
        return this.tboses;
    }
    
    public void setTboses(Set tboses) {
        this.tboses = tboses;
    }




}

