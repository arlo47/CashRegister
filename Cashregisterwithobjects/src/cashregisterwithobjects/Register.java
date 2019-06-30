/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashregisterwithobjects;
import java.util.Date;
import java.util.ArrayList;


/**
 *
 * @author gregf
 */
public class Register {
    private Date date;
    private double totalRevenue;
    private ArrayList <Transaction> transactions;
    private boolean isOpen = false;

    public Register() {
        transactions = new ArrayList<>();
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public void setTotalRevenue(double revenue) {
        this.totalRevenue += revenue;
    }
    public void setTransactions(Transaction t) {
        transactions.add(t);
    }
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Date getDate() {
        return date;
    }
    public double getTotalRevenue() {
        return totalRevenue;
    }
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    public boolean getIsOpen() {
        return isOpen;
    }
    
    public void registerTransaction() {

    }
    
    public void doPayment(double totalOwed) {
        
    }
     //@return
    @Override
    public String toString() {
        String s = String.join("\n", "Date: " + this.getDate() + "", 
                                     "Total Revenue: " + this.getTotalRevenue() + "",
                                     "Open: " + this.getIsOpen() + "");
        return s;
    }
    
    public StringBuilder listTransactions() {
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < transactions.size(); i++) {
            sb.append("Code: ")
              .append(transactions.get(i).getCode())
              .append("\n")
              .append("Amount: ")
              .append(transactions.get(i).getAmount())
              .append("\n")
              .append("Quantity: ")
              .append(transactions.get(i).getQuantity())
              .append("\n\n");
        }
        return sb;
    }

}
