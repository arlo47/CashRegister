/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashregisterwithobjects;
/**
 *
 * @author gregf
 */
public class Transaction {
    private String code;
    private double amount;
    private int quantity;

    public Transaction() {}
    public Transaction(String code, double amount, int quantity) {
        this.code = code;
        this.amount = amount;
        this.quantity = quantity;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }
    public double getAmount() {
        return amount;
    }
    public int getQuantity() {
        return quantity;
    }

}
