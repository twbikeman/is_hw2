import java.util.Scanner;

public class CTR_SDES {

    public static void main(String[] args) {
        SDES sdes = new SDES();
        int counter = 0x0;
        char key = 0x1FD;
        // char c = 0x46;
        // char p = sdes.decrypt(c, key);
        // System.out.println(Integer.toString(p, 16));

        System.out.print("plaintext(hexadecimal): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int iters0 = input.length() / 2;
        System.out.print("ciphertext(hexadecimal): ");
        int encrypt_counter;
        for (int i = 0; i < iters0; i++) {
            int c = Integer.valueOf(input.substring(0, 2), 16);
            encrypt_counter = sdes.encrypt((char)counter, key);
            counter++;
            c = c ^ encrypt_counter;
            System.out.print(Integer.toString(c, 16));
            input = input.substring(2,input.length());
        }
        System.out.println();

        counter = 0;
        System.out.print("ciphertext(hexadecimal): ");
        String inputcipher = scanner.nextLine();
        scanner.close();
        int iters1 = inputcipher.length() / 2;
        System.out.print("plaintext(hexadecimal): ");
        int decrypt_counter;

        for (int i = 0; i < iters1; i++) {
            int p = Integer.valueOf(inputcipher.substring(0, 2), 16);
            decrypt_counter = sdes.encrypt((char)counter, key);
            counter++;
            p = p ^ decrypt_counter;
            System.out.print("00".substring(0, 2 - Integer.toString(p, 16).length()) + Integer.toString(p, 16));
            inputcipher = inputcipher.substring(2,inputcipher.length());
        }
        System.out.println();

    }
}
