import java.util.HashMap;


class Pair {
    private int x, y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}



public class test {
    public static void main(String[] args) {
        // String s = Integer.toBinaryString(10);
        // System.out.println(s);

        // int x1 = 0b1010;
        // String s1 = Integer.toString(x1, 2);
        // System.out.println(s1);

        // String x = "A";
        // int s = Integer.parseInt(x, 16);
        // int s = Integer.valueOf("1010", 2);
        // System.out.println(s);

        int sBox[] = {0xa, 0x4, 0xa, 0xb, 
                      0xd, 0x1, 0x8, 0x5,
                      0x6, 0x2, 0x0, 0x3,
                      0xc, 0xe, 0xf, 0x7};

        // int x = sBox[0x1];
        // System.out.println(Integer.toString(x, 16));

        String plaintext = "0110111101101011";
        int s00 = Integer.valueOf(plaintext.substring(0,4),2);
        int s10 = Integer.valueOf(plaintext.substring(4,8),2);
        int s01 = Integer.valueOf(plaintext.substring(8,12),2);
        int s11 = Integer.valueOf(plaintext.substring(12,16),2);

        // System.out.println(Integer.toString(s00, 2));
        // System.out.println(Integer.toString(s10, 2));
        // System.out.println(Integer.toString(s01, 2));
        // System.out.println(Integer.toString(s11, 2));

        // String key = "1010011100111011";
        // int w0 = Integer.valueOf(key.substring(0,8),2);
        // int w1 = Integer.valueOf(key.substring(8,16),2);

        // System.out.println(Integer.toString(w0, 2));
        // System.out.println(Integer.toString(w1, 2));


        // Map<Integer, Map<Integer, Integer> > mixColmn;
        // HashMap<Pair, Integer> MixColmn = new HashMap<Pair, Integer>();
        // Pair p1 = new Pair(1, 1);
        // MixColmn.put(p1, 2);
        int[][] MixColumn = {{0x2, 0x4, 0x6, 0x8, 0xa, 0xc, 0xe, 0x3, 0x1, 0x7, 0x5, 0xb, 0x9, 0xf, 0xd},
         {0x4, 0x8, 0xc, 0x3, 0x7, 0xb, 0xf,0x6, 0x2, 0xe, 0xa, 0x5, 0x1, 0xd, 0x9},
        {0x9, 0x1, 0x8, 0x2, 0xb, 0x3, 0xa, 0x4, 0xd, 0x5, 0xc, 0x6, 0xf, 0x7, 0xe}};


        System.out.println(Integer.toString(MixColumn[0x2 / 2 - 1][0xf - 1], 16));


        String s = "AABBCCDD";

        System.out.printf("%s\n", ("00000000").substring(s.length()) + s);
        


    }   

}