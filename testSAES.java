
public class testSAES {
    public static void main(String[] args) {
        SAES saes = new SAES();
        int c = saes.encrypt("6F6B", "A73B");
        System.out.println("0000".substring(0, 4 - Integer.toString(c, 16).length()) +Integer.toString(c, 16));
        int p = saes.decrypt("0738", "A73B");
        System.out.println("0000".substring(0, 4 - Integer.toString(p, 16).length()) +Integer.toString(p, 16));
    }

}
