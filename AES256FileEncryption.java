import java.io.*;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AES256FileEncryption {

    // Generate AES-256 key
    private static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // Encrypt string using AES
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt AES-encoded string
    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    // Read contract data from a file
    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
            return null;
        }
        return content.toString().trim();
    }

    // Write encrypted data to a file
    public static void writeFile(String filePath, String data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Generate AES Key
            SecretKey key = generateKey();

            // Let user enter file path
            System.out.print("Enter the file path to encrypt: ");
            String contractFilePath = scanner.nextLine();

            // Read file content
            String contractData = readFile(contractFilePath);
            if (contractData == null) {
                System.out.println("No contract data found!");
                return;
            }

            // Encrypt data
            String encryptedData = encrypt(contractData, key);
            System.out.println("Encrypted Contract: " + encryptedData);

            // Define encrypted file path
            String encryptedFilePath = contractFilePath + "_encrypted.txt";
            writeFile(encryptedFilePath, encryptedData);
            System.out.println("Encrypted contract saved to: " + encryptedFilePath);

            // Ask user if they want to decrypt
            System.out.print("Do you want to decrypt the file? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                // Read encrypted file
                String readEncryptedData = readFile(encryptedFilePath);
                if (readEncryptedData != null) {
                    String decryptedData = decrypt(readEncryptedData, key);
                    System.out.println("Decrypted Contract: \n" + decryptedData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}