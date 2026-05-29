package fa.training.view;

public class MainView {
    public static void showMenu(){
        String menu = """
                ===== DCS =====
                Choose service that you want to use:
                1. User management
                2. Customer management
                3. Transaction history management
                4. Exit
                """;
        System.out.print(menu);
    }
    public static void showUserManagementMenu(){
        String menu = """
                ===== User Management =====
                Choose service that you want to use:
                1. Add user
                2. Update user
                3. Delete user
                4. View all users
                5. View an user
                6. View number of transactions by each user
                """;
        System.out.print(menu);
    }
    public static void showCustomerManagementMenu(){
        String menu = """
                ===== Customer Management =====
                Choose service that you want to use:
                1. Add customer
                2. Update customer
                3. Delete customer
                4. View all customers
                """;
        System.out.print(menu);
    }
    public static void showTransactionHistoryMenu(){
        String menu = """
                ===== Transaction History =====
                Choose service that you want to use:
                1. Add transaction
                2. Update transaction
                3. Delete transaction
                4. View all transactions
                5. View all transactions by user id
                """;
        System.out.print(menu);
    }
}
