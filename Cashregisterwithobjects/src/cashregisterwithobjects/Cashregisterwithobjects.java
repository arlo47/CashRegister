/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashregisterwithobjects;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
/**
 *
 * @author gregf
 */
public class Cashregisterwithobjects {
    public static Scanner in = new Scanner(System.in);
    public static Register register = new Register();
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) 
    throws IOException {
        double totalCost = 0;
        int option = -1;
        do {
            System.out.println("1) Open Day\n"
                             + "2) Register Transaction\n"
                             + "3) Do Payment\n"
                             + "4) Close Day/Exit\n"
                             + "5) Object Data");
            option = in.nextInt();
            switch(option) {
                case 1:
                    openDay();
                    System.out.println(register.getDate());
                    break;
                case 2:
                    if(register.getIsOpen()) {
                        totalCost = registerTransaction();          
                    }
                    else {
                        System.out.println("Must open day first.");
                    }
                    break;
                case 3:
                    if(register.getTransactions().size() > 0) {
                        totalCost = doPayment(totalCost);
                    }
                    else {
                        System.out.println("Must register transactions first.");
                    }   
                    break;
                case 4:
                    if(totalCost == 0) {
                      closeDay();  
                    }
                    else {
                        System.out.println("Cannot close day with money owed.");
                        option = -1;
                    }
                    break;
                case 5:
                    System.out.println("Object Data:");
                    System.out.println(register.toString());
                    System.out.println();
                    System.out.println(register.listTransactions());
                    break;
                default:
                    System.out.println("Default case invoked.");
                    break;
            }
        } while(option != 4); 
    } //main
    
    public static double registerTransaction() {
        int option = -1;
        double totalCost = 0;
        
        do {
            System.out.println("Enter code: ");
            String productCode = in.next();
            System.out.println("Enter price: ");
            double amount = in.nextDouble();
            System.out.println("Enter quantity: ");
            int quantity = in.nextInt();

            totalCost = (amount * quantity) + totalCost;
            register.setTransactions(new Transaction(productCode, amount, quantity));
            
            System.out.println("Transaction Menu\n"
                             + "1) Enter Transaction\n"
                             + "2) Back to main menu");
            option = in.nextInt();
        } while(option != 2);
        
        return totalCost;
    }
    
    public static double doPayment(double totalCost) {
        do {
            System.out.printf("You owe: %.2f\n", totalCost);
            System.out.println("You pay: ");
            double amountPaid = in.nextDouble();
            
            if(totalCost > amountPaid) {
                totalCost -= amountPaid;
            }
            else if(totalCost < amountPaid) {
                amountPaid -= totalCost;
                totalCost = 0;
                System.out.printf("Thanks! Your change is: %.2f\n", amountPaid);
            }
            else if(totalCost == amountPaid) {
                totalCost -= amountPaid;
                System.out.println("Thanks!");
            }
            else {
                System.out.println("Something went wrong is paymentMenu()");
            }         
        } while(totalCost > 0);
        return totalCost;
    }
    
    public static void openDay() {
        register.setDate(new Date());
        register.setIsOpen(true);
    }
    
    public static void closeDay() 
    throws IOException {
        double totalRevenue = 0;
        System.out.printf("%-8s %8s %8s %8s\n", "CODE", "PRICE", "QTY", "TOTAL");
        for(int i = 0; i < register.getTransactions().size(); i++) {
            String code = register.getTransactions().get(i).getCode();
            double amount = register.getTransactions().get(i).getAmount();
            int quantity = register.getTransactions().get(i).getQuantity();
            double lineTotal = register.getTransactions().get(i).getAmount() * register.getTransactions().get(i).getQuantity();
            totalRevenue += lineTotal;
            
            System.out.printf("%-8s %8.2f %8d %8.2f\n", code, amount, quantity, lineTotal);
        }
        
        Date closeTime = new Date();
        System.out.printf("Opened: " + register.getDate() + "\n");
        System.out.printf("Closed: " + closeTime + "\n");
        System.out.printf("Total Revenue: " + totalRevenue + "\n");
        register.setTotalRevenue(totalRevenue);
        saveToFile(closeTime);
        register.setIsOpen(false);
    }
    
    public static void saveToFile(Date closeTime) 
    throws IOException {
        FileWriter fw = new FileWriter("transactions.txt", true);
        PrintWriter output = new PrintWriter(fw);
        
        output.printf("%-8s %8s %8s %8s\n", "CODE", "PRICE", "QTY", "TOTAL");
        for(int i = 0; i < register.getTransactions().size(); i++) {
            String code = register.getTransactions().get(i).getCode();
            double amount = register.getTransactions().get(i).getAmount();
            int quantity = register.getTransactions().get(i).getQuantity();
            double total = register.getTransactions().get(i).getAmount() * register.getTransactions().get(i).getQuantity();
            
            output.printf("%-8s %8.2f %8d %8.2f\n", code, amount, quantity, total);
        }
        output.printf("Opened: " + register.getDate() + "\n");
        output.printf("Closed: " + closeTime + "\n");
        output.printf("\nTotal Revenue: %.2f \n\n\n", register.getTotalRevenue());
        
        output.close();
    }
} //Cashregisterwithobjects
