package booksystem;
// import java.util.Scanner;

import java.util.Scanner;

import dbaction.*;

public class BookSystem{

    public BookSystem(DataBase db) {

    }

    public void StartingMenu() {
        dbtime._dbtime();
        System.out.println("\n===== Welcome to Book Ordering Management System =====");
        System.out.println("===== Version 1.0 Last Updated: 27/02/2023 =====");
        System.out.println("===== Data Base Record: xxxx yyyy zzzzz !!!!!=====\n");
        System.out.println("> 1. Database Initialization\n> 2. Customer Operation\n> 3. Bookstore Operation\n> 4. Quit\n");
        System.out.println("Please Enter Your Action:");   
    }

    public void OperationCall(int i, Scanner s) {
        switch (i) {
            case 1:
                Operation_1_Menu(s);
                break;
            case 2:
                Operation_2_Menu(s);
                break;
            case 3:
                Operation_3_Menu(s);
                break;
            case 4:
                Operation_4_Menu(s);
                break;
        }
    }

    private void Operation_1_Menu(Scanner s) {
        System.out.println("\n==== Database Initization - Please choose from the following operation =====");
        System.out.println("\n> 1. sf\n> 2. f\n> 3. x");
    }

    private void Operation_2_Menu(Scanner s) {
        System.out.println("\n===== Customer Operation - Please choose from the following operation =====");
        System.out.println("\n> 1. Book Searching\n> 2. Placing Order\n> 3. Check History Order");
        //dbinput _Dbinput = new dbinput();

        // Scanner _o2scan = new Scanner(System.in);
        // System.out.println(_o2scan);
        final int a = dbinput.PrintScan(1, 3, s);
        
        switch (a) {
            case 1:
            //DataBase.BookSearching();
                break;
            case 2:
                Operation_2_Menu(s);
                break;
            case 3:
                Operation_3_Menu(s);
                break;
        }
    }

    private void Operation_3_Menu(Scanner s) {
        System.out.println("\n==== dfs - Please choose from the following operation =====");
        System.out.println("\n> 1. sf\n> 2. f\n> 3. x");
    }

    private void Operation_4_Menu(Scanner s) {
        System.out.println("\n==== dfs - Please choose from the following operation =====");
        System.out.println("\n> 1. sf\n> 2. f\n> 3. x");
    }

}

