public class Test {
    private final short BIT_0 = 0x01;
    private final short BIT_1 = 0x02;
    private final short BIT_2 = 0x04;
    
    public static void main(String[] args) {
        short key = 5;
        System.out.printf("%02X\n", 0x00 | (key & 0x04));

    }

}