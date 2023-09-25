/*
 *  Asmin Pothula 1001904488 Coding Assignment 1 
 */

package code1_1001904488;

import java.util.Scanner;

public class Code1_1001904488
{
    enum ACTION
    {
        DISPENSECHANGE, INSUFFICIENTCHANGE, INSUFFICIENTFUNDS, EXACTPAYMENT;
    }

    public static String displayMoney (int amount)
    {
        String dollars = String.valueOf(amount/100);
        String cents = String.valueOf(amount%100);
        
        return "$" + dollars + "." + (cents.length() == 1? "0":"") + cents;
    }
    
    public static int PencilMenu()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("\nPlease choose from the following options\n\n0. No pencils for me today\n1. Purchase pencils\n2. Check inventory level\n3. Check change level ");
        System.out.print("\nChoice : ");
        int userChoice = in.nextInt();
        
        while(userChoice<0 || userChoice>3)
        {
            System.out.println("Enter a valid choice:");
            userChoice = in.nextInt();
        }
        
        return userChoice;
        
    }
    
    public static ACTION buyPencils(int pencilCost, int payment, int numberOfPencils, int levels[], String changeArray[])
    {
        ACTION action = ACTION.EXACTPAYMENT;
        
        if (payment == pencilCost*numberOfPencils)
        {
            action =  ACTION.EXACTPAYMENT;
          
            levels[0] = levels[0] - numberOfPencils;
            
            levels[1] = levels[1]+ payment;
            
        }
        else if(payment > pencilCost*numberOfPencils)
        {
            if (levels[1] >= (payment - (pencilCost*numberOfPencils)))
            {
                action = ACTION.DISPENSECHANGE;
                
                int change = payment - (pencilCost*numberOfPencils);
                changeArray[0] = displayMoney(change);
                
                levels[0] = levels[0] - numberOfPencils;
                levels[1] = (levels[1] - change) + payment;
                
            }
            else
            {
                action = ACTION.INSUFFICIENTCHANGE;
            }
        }
        else 
        {
            if (payment < pencilCost*numberOfPencils)
            {
                action = ACTION.INSUFFICIENTFUNDS;
            }
        }
        
        return action;
    }
    
    public static void main(String[] args)
    {   
       final int pencilCost = 60;
       
       int inventory_level = 100, change_level = 500;
       int levels[] = {inventory_level, change_level};
       int payment; 
       String changeArray[] = {""};
       
       Scanner in = new Scanner(System.in);
       
       System.out.println("Welcome to my Pencil Machine");
       
       int userChoice = 0;
       
       do
       {
           userChoice = PencilMenu();
           switch(userChoice)
           {
               case 0:
               {
                   break;
               }
               case 1:
               {
                   if (inventory_level == 0)
                   {
                       System.out.println("This Pencil Dispenser is out of inventory");
                   }
                   else
                   {
                        System.out.printf("\nA pencil costs %s", displayMoney(pencilCost));
                        System.out.print("\n\nHow many pencils would you like to purchase? ");
                        int numberOfPencils = in.nextInt();
                      
                     
                        while(numberOfPencils<1 || numberOfPencils>inventory_level)
                        {
                            System.out.print("\nCannot sell that amount of pencils. Please reenter quantity: ");
                            numberOfPencils = in.nextInt();
                        }
                      
                        System.out.printf("\n\nYour total is %s", displayMoney(pencilCost*numberOfPencils));
                        System.out.print("\n\nEnter your payment (in cents): ");
                        payment = in.nextInt();
                          
                        ACTION action = buyPencils(pencilCost, payment, numberOfPencils, levels, changeArray );
                        inventory_level = levels[0];
                        change_level = levels[1];
                      
                        switch(action)
                        {
                            case EXACTPAYMENT:
                            {
                                System.out.println("Here's your pencils. Thank you for the exact payment.");
                                break;
                            }
                            case DISPENSECHANGE:
                            {
                                System.out.printf("\nHere's your pencils and change of %s\n", changeArray[0]);
                                break;
                            }
                            case INSUFFICIENTCHANGE:
                            {
                                System.out.println("This Pencil Dispenser does not have enough change and cannot accept your payment.\n");
                                break;
                            }
                            case INSUFFICIENTFUNDS:
                            {
                                System.out.println("You did not enter sufficient payment. No pencils for you.\n");
                                break;
                            }
                            default :
                            {
                                break;
                            }
                        }
                    }
                    break;
                }
                case 2:
                {
                    System.out.printf("\nThe current inventory level is %d\n", inventory_level);
                    break;
                }
                case 3:
                {
                    System.out.printf("\nThe current change level is %s\n", displayMoney(change_level));
                    break;
                }
                default:
                {
                    System.out.println("\nInvalid menu option");
                    break;
                }
            }
        }
        while(userChoice != 0);
    }
    
}

