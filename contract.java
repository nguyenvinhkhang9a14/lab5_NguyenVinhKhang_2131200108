import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Interface for contract
interface Contract {
    void buildContractID(int contractID);
    void buildPropertyID(int propertyID);
    void buildTenantID(int tenantID);
    void buildRentAmount(double rentAmount);
    Contract signContract();
    String getContractType();
}


class PermanentContract implements Contract {
    private int contractID;
    private int propertyID;
    private int tenantID;
    private double rentAmount;

    @Override
    public void buildContractID(int contractID) {
        this.contractID = contractID;
    }

    @Override
    public void buildPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    @Override
    public void buildTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    @Override
    public void buildRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    @Override
    public Contract signContract() {
        return this;
    }
    
    @Override
    public String toString() {
        return "PermanentContract{" +
                "contractID=" + contractID +
                ", propertyID=" + propertyID +
                ", tenantID=" + tenantID +
                ", rentAmount=" + rentAmount +
                '}';
    }

    @Override
    public String getContractType() {
        return "Permanent";
    }
}


class LongTermContract implements Contract {
    private int contractID;
    private int propertyID;
    private int tenantID;
    private double rentAmount;

    @Override
    public void buildContractID(int contractID) {
        this.contractID = contractID;
    }

    @Override
    public void buildPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    @Override
    public void buildTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    @Override
    public void buildRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    @Override
    public Contract signContract() {
        return this;
    }
    
    @Override
    public String toString() {
        return "LongTermContract{" +
                "contractID=" + contractID +
                ", propertyID=" + propertyID +
                ", tenantID=" + tenantID +
                ", rentAmount=" + rentAmount +
                '}';
    }

    @Override
    public String getContractType() {
        return "LongTerm";
    }
}

class ShortTermContract implements Contract {
    private int contractID;
    private int propertyID;
    private int tenantID;
    private double rentAmount;

    @Override
    public void buildContractID(int contractID) {
        this.contractID = contractID;
    }

    @Override
    public void buildPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    @Override
    public void buildTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    @Override
    public void buildRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    @Override
    public Contract signContract() {
        return this;
    }
    
    @Override
    public String toString() {
        return "ShortTermContract{" +
                "contractID=" + contractID +
                ", propertyID=" + propertyID +
                ", tenantID=" + tenantID +
                ", rentAmount=" + rentAmount +
                '}';
    }

    @Override
    public String getContractType() {
        return "ShortTerm";
    }
}


class Client {

    // Method to create a contract with user input
    public static Contract requestCreateRentalContract(String contractType, Scanner scanner) {
        Contract contract = null;
        
        switch (contractType.toLowerCase()) {
            case "permanent":
                contract = new PermanentContract();
                break;
            case "longterm":
                contract = new LongTermContract();
                break;
            case "shortterm":
                contract = new ShortTermContract();
                break;
            default:
                throw new IllegalArgumentException("Invalid contract type");
        }

        // Taking user input for contract details
        System.out.print("Enter Contract ID: ");
        int contractID = scanner.nextInt();
        
        System.out.print("Enter Property ID: ");
        int propertyID = scanner.nextInt();
        
        System.out.print("Enter Tenant ID: ");
        int tenantID = scanner.nextInt();
        
        System.out.print("Enter Rent Amount: ");
        double rentAmount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        contract.buildContractID(contractID);
        contract.buildPropertyID(propertyID);
        contract.buildTenantID(tenantID);
        contract.buildRentAmount(rentAmount);
        return contract.signContract();
    }

    // Save contract details to a text file
    public static void saveContractToFile(Contract contract) {
        try {
            File directory = new File("contracts");
            if (!directory.exists()) {
                directory.mkdir(); // Create "contracts" folder if it doesn't exist
            }

            String fileName = "contracts/" + contract.getContractType() + "_Contract.txt";
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(contract.toString() + "\n");
            writer.close();

            System.out.println("Contract saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving contract to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Contract Type (Permanent, LongTerm, ShortTerm): ");
        String contractType = scanner.nextLine();

        Contract contract = requestCreateRentalContract(contractType, scanner);
        System.out.println("\nRental contract created successfully!");
        System.out.println(contract.toString());

        // Save contract to file
        saveContractToFile(contract);

        scanner.close();
    }
}