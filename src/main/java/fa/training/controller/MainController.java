package fa.training.controller;

import fa.training.dto.UserVO;
import fa.training.entity.Customer;
import fa.training.entity.TransactionHistory;
import fa.training.entity.User;
import fa.training.service.CustomerService;
import fa.training.service.TransactionHistoryService;
import fa.training.service.UserService;
import fa.training.util.Validator;
import fa.training.view.MainView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static fa.training.util.Constant.DATE_FORMATTER;
import static fa.training.util.Constant.DATE_TIME_FORMATTER;

public class MainController {
    private static final UserService userService = new UserService();
    private static final CustomerService customerService = new CustomerService();
    private static final TransactionHistoryService transactionHistoryService = new TransactionHistoryService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void start(){
        int choice;
        do{
            MainView.showMenu();
            choice = inputValidOption(1, 4);
            switch(choice){
                case 1 -> handleUserManagement();
                case 2 -> handleCustomerManagement();
                case 3 -> handleTransactionHistoryManagement();
            }
        }while(choice != 4);
    }

    private static void handleUserManagement(){
        MainView.showUserManagementMenu();
        int choice = inputValidOption(1, 6);
        switch(choice){
            case 1 -> addUser();
            case 2 -> updateUser();
            case 3 -> deleteUser();
            case 4 -> displayAllUsers();
            case 5 -> displaySpecificUser();
            case 6 -> displayTotalTransactionForUser();
        }
    }

    private static void handleCustomerManagement(){
        MainView.showCustomerManagementMenu();
        int choice = inputValidOption(1, 4);
        switch (choice){
            case 1 -> addCustomer();
            case 2 -> updateCustomer();
            case 3 -> removeCustomer();
            case 4 -> displayAllCustomers();
        }
    }

    private static void handleTransactionHistoryManagement(){
        MainView.showTransactionHistoryMenu();
        int choice = inputValidOption(1, 5);
        switch (choice){
            case 1 -> createTransaction();
            case 2 -> updateTransaction();
            case 3 -> deleteTransaction();
            case 4 -> displayAllTransactions();
            case 5 -> displayTransactionByUserId();
        }
    }

    private static void addUser(){
        System.out.println("Add user");
        String userName = inputValidStringField("User name: ", "User name can't be blank", Validator::isNotBlank);
        String email = inputValidStringField("Email: ", "Invalid email format", Validator::isValidEmail);
        long point = 0;
        LocalDate localDate = LocalDate.parse(inputValidStringField("Date of birth (dd-MM-yyyy): ", "Invalid date format", Validator::isValidDate), DATE_FORMATTER);
        User user = new User();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPoint(point);
        user.setDateOfBirth(localDate);
        userService.saveUser(user);
    }

    private static void updateUser(){
        System.out.println("Update user");
        int userId = inputValidIntField("User ID: ", "User ID must be a positive integer", id -> id > 0);
        String userName = inputValidStringField("User name: ", "User name can't be blank", Validator::isNotBlank);
        String email = inputValidStringField("Email: ", "Invalid email format", Validator::isValidEmail);
        LocalDate localDate = LocalDate.parse(inputValidStringField("Date of birth (dd-MM-yyyy): ", "Invalid date format", Validator::isValidDate), DATE_FORMATTER);
        User user = new User();
        user.setUserId(userId);
        user.setUsername(userName);
        user.setEmail(email);
        user.setDateOfBirth(localDate);
        if(userService.updateUser(user)){
            System.out.println("User updated");
        }else {
            System.out.println("User with ID " + userId + " not found");
        }
    }

    private static void deleteUser(){
        System.out.println("Delete user");
        int userId = inputValidIntField("User ID: ", "User ID must be a positive integer", id -> id > 0);
        userService.deleteUser(userId);
    }

    private static void displayAllUsers(){
        System.out.println("Display all users:");
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()) {
            System.out.println("No users found");
            return;
        }
        users.forEach(System.out::println);
    }

    private static void displaySpecificUser(){
        System.out.println("Display specific user");
        int userId = inputValidIntField("User ID: ", "User ID must be a positive integer", id -> id > 0);
        User user = userService.getUser(userId);
        if(user == null) {
            System.out.println("User not found");
        }else {
            System.out.println(user);
        }
    }

    private static void displayTotalTransactionForUser(){
        System.out.println("Display total transaction for user");
        List<UserVO> userVOS = userService.getTotalTransactionForUser();
        if(userVOS.isEmpty()) {
            System.out.println("No users found");
        }
        userVOS.forEach(System.out::println);
    }

    private static void addCustomer(){
        System.out.println("Add customer");
        String customerName = inputValidStringField("Customer name: ", "Customer name can't be blank", Validator::isNotBlank);
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customerService.addCustomer(customer);
    }

    private static void updateCustomer(){
        System.out.println("Update customer");
        int customerId = inputValidIntField("Customer ID: ", "Customer ID must be a positive integer", id -> id > 0);
        String customerName = inputValidStringField("Customer name: ", "Customer name can't be blank", Validator::isNotBlank);
        Customer customer = new Customer(customerId, customerName);
        if(customerService.updateCustomer(customer)){
            System.out.println("Customer updated successfully");
        }else{
            System.out.println("Customer with ID " + customerId + " not found");
        }
    }

    private static void removeCustomer(){
        System.out.println("Remove customer");
        int customerId = inputValidIntField("Customer ID: ", "Customer ID must be a positive integer", id -> id > 0);
        customerService.deleteCustomer(customerId);
    }

    private static void displayAllCustomers(){
        System.out.println("Display all customers:");
        List<Customer> customers = customerService.getAllCustomer();
        if(customers.isEmpty()) {
            System.out.println("No customers found");
            return;
        }
        customers.forEach(System.out::println);
    }


    private static void createTransaction(){
        System.out.println("Create transaction");
        int userId = inputValidIntField("User ID: ", "User ID must be a positive integer", id -> id > 0);
        int customerId = inputValidIntField("Customer ID: ", "Customer ID must be a positive integer", id -> id > 0);
        long point = inputValidIntField("Point: ", "Point must be a positive integer", id -> id > 0);
        String action = inputValidStringField("Action: ", "Action can't be blank", Validator::isValidAction);
        System.out.print("Note: ");
        String note = scanner.nextLine();
        LocalDateTime createdDateTime = LocalDateTime.parse(inputValidStringField("Created date time (dd-MM-yyyy HH:mm:ss): ", "Invalid date time format", Validator::isValidDateTime), DATE_TIME_FORMATTER);
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setUserId(userId);
        transactionHistory.setCustomerId(customerId);
        transactionHistory.setPoint(point);
        transactionHistory.setAction(action);
        transactionHistory.setNote(note);
        transactionHistory.setCreatedDateTime(createdDateTime);
        int transactionId = transactionHistoryService.add(transactionHistory);
        if(transactionId == -1){
            System.out.println("Transaction creation failed");
        }else{
            System.out.println("Transaction created with id: " + transactionId);
        }
    }

    private static void updateTransaction(){
        System.out.println("Update transaction");
        int transactionId = inputValidIntField("Transaction ID: ", "Transaction ID must be a positive integer", id -> id > 0);
        String note = scanner.nextLine();
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(transactionId);
        transactionHistory.setNote(note);
        if(transactionHistoryService.update(transactionHistory)){
            System.out.println("Transaction updated successfully");
        }else{
            System.out.println("Transaction with ID " + transactionId + " not found");
        }
    }

    private static void deleteTransaction(){
        System.out.println("Delete transaction");
        int transactionId = inputValidIntField("Transaction ID: ", "Transaction ID must be a positive integer", id -> id > 0);
        transactionHistoryService.delete(transactionId);
    }

    private static void displayAllTransactions(){
        System.out.println("Display all transactions:");
        List<TransactionHistory> transactionHistories = transactionHistoryService.getAll();
        if(transactionHistories.isEmpty()) {
            System.out.println("No transactions found");
            return;
        }
        transactionHistories.forEach(System.out::println);
    }

    private static void displayTransactionByUserId(){
        System.out.println("Display transactions by user id");
        int userId = inputValidIntField("User ID: ", "User ID must be a positive integer", id -> id > 0);
        List<TransactionHistory> transactionHistories = transactionHistoryService.getTransactionHistoryByUserId(userId);
        if(transactionHistories.isEmpty()) {
            System.out.println("No transactions found for user with ID " + userId);
            return;
        }
        transactionHistories.forEach(System.out::println);
    }

    private static int inputValidOption(int min, int max){
        while(true){
            int option = getIntInput("Please input an option from " + min + " to " + max + ": ");
            if(option < min || option > max){
                System.out.println("Invalid option. Please provide a number between " + min + " and " + max);
            }else{
                return option;
            }
        }
    }

    private static String inputValidStringField(String prompt, String message, Function<String, Boolean> validator){
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if(validator.apply(input)){
                return input;
            }
            System.out.println(message);
        }
    }

    private static int inputValidIntField(String prompt, String message, Function<Integer, Boolean> validator){
        while(true){
            int input = getIntInput(prompt);
            if(validator.apply(input)){
                return input;
            }
            System.out.println(message);
        }
    }


    private static int getIntInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter an integer");
            }
        }
    }
}
