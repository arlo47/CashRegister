/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashregister;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

/**
 *
 * @author gregf
 */
public class CashRegister {
    static Scanner in = new Scanner(System.in);
    static FileWriter fw;       
    static PrintWriter outputFile;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    throws IOException {
        fw = new FileWriter("transactions.txt", true); 
        outputFile = new PrintWriter(fw);
        ArrayList<Double> productList = new ArrayList<>();
        
        int option;
        double totalCost = 0;
        double totalRevenue = 0;
        String date = null;

        do {
            System.out.println("Main Menu. Pick an option:\n"
                             + "1. Open Day\n"
                             + "2. Register Transaction\n"
                             + "3. Do Payment\n"
                             + "4. Close Day");
            option = in.nextInt();
            
            switch(option) {
                case 1:
                  System.out.println("Opening Day... ");
                  date = openDay();
                  break;
                case 2:
                    System.out.println("Register Transactions: ");
                    if(date != null) {
                       totalCost = registerTransactions(productList); 
                    } else {
                        System.out.println("You must open day first.\n");
                    }
                    break;
                case 3:
                    System.out.println("Do Payment: ");
                    if(totalCost > 0) {
                        totalRevenue = doPayment(totalCost);
                    } else {
                        System.out.println("You must register transactions first.\n");
                    }
                    break;
                case 4:
                        System.out.println("Closing Day...");
                        closeDay(totalRevenue, date, productList);                        
                    break;
            }
        } while(option != 4);
    }    
    
    public static String openDay() {
        String date;
        System.out.println("Enter the date (mm/dd/yy): ");
        date = in.next();
        
        return date;
    }    
    
    /**
     * Remember you must OPEN DAY before you can register transaction
     * @param productList
     * @return 
     * @throws java.io.IOException
     */
    public static double registerTransactions(ArrayList<Double> productList) 
    throws IOException {
        int option;
        double totalCost = 0;

        do {
            System.out.println("Enter Product Code (numbers only): ");
            double productCode = in.nextDouble();

            System.out.println("Enter Price: ");
            double price = in.nextDouble();

            System.out.println("Enter quantity: ");
            double quantity = in.nextDouble();
            
            totalCost = totalCost + (price * quantity);

            productList.add(productCode);
            productList.add(price);
            productList.add(quantity);
            
            System.out.println("Register Transaction Menu\n"
                             + "1. Enter Transaction\n"
                             + "2. Main Menu");
            option = in.nextInt();
        } while(option != 2);
        return totalCost;
    }
    
    public static double doPayment(double totalCost) {
        double totalRevenue = 0;
        
        do {
            totalCost = Math.round(totalCost * 100) / 100.0;
            System.out.println("You owe: " + totalCost);

            System.out.println("You give: ");
            double amountPaid = in.nextDouble();
            amountPaid = Math.round(amountPaid * 100) / 100.0;

            if(totalCost > amountPaid) {
                totalCost -= amountPaid;
                totalRevenue += amountPaid;
            } else if(totalCost <= amountPaid) {
                amountPaid -= totalCost;
                totalRevenue += totalCost;
                totalCost = 0;
                System.out.printf("Your change is: %4.2f\n", amountPaid);
            } else {
                System.out.println("Else condition met in doPayment(). Something went wrong.");
            }    
        } while(totalCost > 0);
        
        totalRevenue = Math.round(totalRevenue * 100) / 100.0;
        return totalRevenue;
    }
    
    public static void closeDay(double totalRevenue, String date, ArrayList<Double> productList) 
    throws IOException {
        outputFile.println("Date: " + date);
        for(int i = 2; i < productList.size(); i += 3) {
            outputFile.printf("Product Code: %.0f\n", productList.get(i - 2));
            outputFile.println("Price: " + productList.get(i - 1));
            outputFile.printf("Quantity: %.0f\n", productList.get(i));
            outputFile.println();
        }

        System.out.println();
        System.out.println("-------------------------");
        System.out.println("Date: " + date);
        
        //Create new ArrayList with only productCode and quantity
        ArrayList<Double> productsSold = new ArrayList<>();
        for(int i = 2; i < productList.size(); i += 3) {
           productsSold.add(productList.get(i - 2));
           productsSold.add(productList.get(i));
        }       

        for(int i = 2; i < productList.size(); i += 3) {
            System.out.printf("%.0f" + " x " + "%.0f\n", productList.get(i - 2), productList.get(i));
        }

        System.out.println("\nTotal Revenue: " + totalRevenue);
        System.out.println("-------------------------");
        System.out.println();
        outputFile.close();        
    }
    
}
