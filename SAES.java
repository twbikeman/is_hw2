// import java.util.HashMap;


// class Pair {
//     private int x, y;
//     public Pair(int x, int y) {
//         this.x = x;
//         this.y = y;
//     }
// }



public class SAES {

    public void print(int x) {
                String output = Integer.toString(x,2);
                System.out.printf("%s\n", ("00000000").substring(output.length()) + output);
    }

    public int[] sBox = {0xa, 0x4, 0xa, 0xb, 
                      0xd, 0x1, 0x8, 0x5,
                      0x6, 0x2, 0x0, 0x3,
                      0xc, 0xe, 0xf, 0x7};


    public void nibSub(int[] x) {
        x[0] = sBox[x[0]];
        x[1] = sBox[x[1]];
        x[2] = sBox[x[2]];
        x[3] = sBox[x[3]];
    }

    public void shiftRow(int[] x) {
        int temp = x[2];
        x[2] = x[3];
        x[3] = temp;
    }

    int[][] MixColumn = {
        {0x0, 0x2, 0x4, 0x6, 0x8, 0xa, 0xc, 0xe, 0x3, 0x1, 0x7, 0x5, 0xb, 0x9, 0xf, 0xd},
        {0x0, 0x4, 0x8, 0xc, 0x3, 0x7, 0xb, 0xf,0x6, 0x2, 0xe, 0xa, 0x5, 0x1, 0xd, 0x9},
        {0x0, 0x9, 0x1, 0x8, 0x2, 0xb, 0x3, 0xa, 0x4, 0xd, 0x5, 0xc, 0x6, 0xf, 0x7, 0xe}};

    public void mixColumn(int[] x) {
        int news00 = x[0] ^ MixColumn[1][x[2]] ; // 4 * s10
        int news10 = MixColumn[1][x[0]] ^ x[2];
        int news01 = x[1] ^ MixColumn[1][x[3]];
        int news11 = MixColumn[1][x[1]] ^ x[3]; 
        x[0] = news00;
        x[1] = news01;
        x[2] = news10;
        x[3] = news11;
    }



    public int rotnib(int x) {
        int temp = 0;
        // x >> 4
        temp =  (x >> 4) | (x << 28 >>> 24);
        return temp;

    }

    public int[] rcon = {0b10000000, 0b00110000};


    public int[] keyExpansion(int[] x) {
        int w0 = x[0];
        int w1 = x[1];
        int w2 = rotnib(w1); // rotnib
        w2= (sBox[w2 >> 4] << 4) | (sBox[w2 << 28 >>> 28]); // subnib
        w2 = w0 ^ rcon[0] ^ w2;
        
        int w3 = w2 ^ w1;
        int w4 = rotnib(w3);
        w4 = (sBox[w4 >> 4] << 4) | (sBox[w4 << 28 >>> 28]); // subnib
        w4 = w2 ^ rcon[1] ^ w4;
        int w5 = w4 ^ w3;

    
        int[] y = {w0, w1, w2, w3, w4, w5};
        return y;
    }

    public void addKey(int[] x, int key) {
        x[0] =  x[0] ^ (key >>> 12);
        x[1] = x[1] ^ (key >>> 4 << 28 >>> 28);
        x[2] = x[2] ^ (key >>> 8 << 28 >>> 28);
        x[3] = x[3] ^ (key << 28 >>> 28);
    }

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

        // int sBox[] = {0xa, 0x4, 0xa, 0xb, 
        //               0xd, 0x1, 0x8, 0x5,
        //               0x6, 0x2, 0x0, 0x3,
        //               0xc, 0xe, 0xf, 0x7};

        // int x = sBox[0x1];
        // System.out.println(Integer.toString(x, 16));

        // String plaintext = "0110111101101011";
        // int s00 = Integer.valueOf(plaintext.substring(0,4),2);
        // int s10 = Integer.valueOf(plaintext.substring(4,8),2);
        // int s01 = Integer.valueOf(plaintext.substring(8,12),2);
        // int s11 = Integer.valueOf(plaintext.substring(12,16),2);

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
        // int[][] MixColumn = {{0x2, 0x4, 0x6, 0x8, 0xa, 0xc, 0xe, 0x3, 0x1, 0x7, 0x5, 0xb, 0x9, 0xf, 0xd},
        //  {0x4, 0x8, 0xc, 0x3, 0x7, 0xb, 0xf,0x6, 0x2, 0xe, 0xa, 0x5, 0x1, 0xd, 0x9},
        // {0x9, 0x1, 0x8, 0x2, 0xb, 0x3, 0xa, 0x4, 0xd, 0x5, 0xc, 0x6, 0xf, 0x7, 0xe}};


        // System.out.println(Integer.toString(MixColumn[0x2 / 2 - 1][0xf - 1], 16));


        // String s = "AABBCCDD";

        // System.out.printf("%s\n", ("00000000").substring(s.length()) + s);
        

        SAES saes = new SAES();



        String input = "6F6B";
        int s00 = Integer.valueOf(input.substring(0, 1), 16);
        int s01 = Integer.valueOf(input.substring(2, 3), 16);
        int s10 = Integer.valueOf(input.substring(1, 2), 16);
        int s11 = Integer.valueOf(input.substring(3, 4), 16);
        

        int[] s = {s00, s01, s10, s11};
        // saes.mixColumn(s);
        for (int i: s)
            saes.print(i);

        System.out.println("----");

        String key = "A73B";
        int w0 = Integer.valueOf(key.substring(0, 2), 16);
        int w1 = Integer.valueOf(key.substring(2, 4), 16);
        int[] w = {w0, w1};
        int[] wExpand = saes.keyExpansion(w);
        for (int i: wExpand)
            saes.print(i);


        System.out.println("----");




        // //round 0
        saes.addKey(s, (w0 << 8 | w1));
       
        // //round 1
        saes.nibSub(s);
        saes.shiftRow(s);
        saes.mixColumn(s);
        saes.addKey(s, (wExpand[2] << 8 | wExpand[3]));
        // //round 2
        saes.nibSub(s);
        saes.shiftRow(s);
        saes.addKey(s, (wExpand[4] << 8 | wExpand[5])); 


        for (int i: s)
        saes.print(i);
        
        
            // int[] rcon = {0b10000000, 0b00110000};

     
        // String input = "2D55";
        // int w0 = Integer.valueOf(input.substring(0, 2), 16);
        // int w1 = Integer.valueOf(input.substring(2, 4), 16);
        // int[] x = {w0, w1};
        // int[] w = saes.keyExpansion(x);
            
        // for (int i : w)
        // saes.print(i);

        // int w2 = saes.rotnib(w1); // rotnib
        // w2= (saes.sBox[w2 >> 4] << 4) | (saes.sBox[w2 << 28 >>> 28]); // subnib
        // w2 = w0 ^ rcon[0] ^ w2;
        // String w2String = Integer.toString(w2,2);


        //rotnib


        // System.out.printf("%s\n", ("00000000").substring(w0String.length()) + w0String);
        
        // System.out.printf("%s\n", ("00000000").substring(w2String.length()) + w2String);




    }   

}