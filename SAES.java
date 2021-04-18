

public class SAES {

    public void print(int x) {
                String output = Integer.toString(x,2);
                System.out.printf("%s\n", ("00000000").substring(output.length()) + output);
    }

    public int[] sBox = {0x9, 0x4, 0xa, 0xb, 
                      0xd, 0x1, 0x8, 0x5,
                      0x6, 0x2, 0x0, 0x3,
                      0xc, 0xe, 0xf, 0x7};

    public int[] sBoxinv = { 0xa, 0x5, 0x9, 0xb,
                        0x1, 0x7, 0x8, 0xf,
                        0x6, 0x0, 0x2, 0x3,
                        0xc, 0x4, 0xd, 0xe};


    public void nibSub(int[] x) {
        x[0] = sBox[x[0]];
        x[1] = sBox[x[1]];
        x[2] = sBox[x[2]];
        x[3] = sBox[x[3]];
    }

    public void nibSubinv(int[] x) {
        x[0] = sBoxinv[x[0]];
        x[1] = sBoxinv[x[1]];
        x[2] = sBoxinv[x[2]];
        x[3] = sBoxinv[x[3]];
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

    public void mixColumninv(int[] x) {
        int news00 = MixColumn[2][x[0]] ^ MixColumn[0][x[2]] ; // 4 * s10
        int news10 = MixColumn[0][x[0]] ^ MixColumn[2][x[2]];
        int news01 = MixColumn[2][x[1]] ^ MixColumn[0][x[3]];
        int news11 = MixColumn[0][x[1]] ^ MixColumn[2][x[3]]; 
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
        w2 = (sBox[w2 >> 4] << 4) | (sBox[w2 & 0x0F]); // subnib
        w2 = w0 ^ rcon[0] ^ w2;
        
        int w3 = w2 ^ w1;
        int w4 = rotnib(w3);
        w4 = (sBox[w4 >> 4] << 4) | (sBox[w4 & 0x0F]); // subnib
        w4 = w2 ^ rcon[1] ^ w4;
        int w5 = w4 ^ w3;

    
        int[] y = {w0, w1, w2, w3, w4, w5};
        return y;
    }

    public void addKey(int[] x, int[] key) {
        x[0] =  x[0] ^ (key[0] >> 4);
        x[1] =  x[1] ^ (key[1] >> 4);
        x[2] =  x[2] ^ (key[0] & 0x0F);
        x[3] =  x[3] ^ (key[1] & 0x0F);

       
    }

    public int encrypt(String input, String key) {
        int s00 = Integer.valueOf(input.substring(0, 1), 16);
        int s01 = Integer.valueOf(input.substring(2, 3), 16);
        int s10 = Integer.valueOf(input.substring(1, 2), 16);
        int s11 = Integer.valueOf(input.substring(3, 4), 16);
        

        int[] s = {s00, s01, s10, s11};
        // saes.mixColumn(s);

        int w0 = Integer.valueOf(key.substring(0, 2), 16);
        int w1 = Integer.valueOf(key.substring(2, 4), 16);
        int[] w = {w0, w1};
        int[] wExpand = keyExpansion(w);
   
        int w2 = wExpand[2];
        int w3 = wExpand[3];
        int w4 = wExpand[4];
        int w5 = wExpand[5];
        int[] key0 = {w0, w1};
        int[] key1 = {w2, w3};
        int[] key2 = {w4, w5};



        // // //round 0
        addKey(s, key0);
       
        // // // //round 1
        nibSub(s);
        shiftRow(s);
        mixColumn(s);
        addKey(s, key1);
        // // // // //round 2
        nibSub(s);
        shiftRow(s);
        addKey(s, key2); 


        return (s[0] << 12) | (s[2] << 8) | (s[1] << 4) | s[3];

    }


    public int decrypt(String input, String key) {
        int s00 = Integer.valueOf(input.substring(0, 1), 16);
        int s01 = Integer.valueOf(input.substring(2, 3), 16);
        int s10 = Integer.valueOf(input.substring(1, 2), 16);
        int s11 = Integer.valueOf(input.substring(3, 4), 16);
        

        int[] s = {s00, s01, s10, s11};
        // saes.mixColumn(s);

        int w0 = Integer.valueOf(key.substring(0, 2), 16);
        int w1 = Integer.valueOf(key.substring(2, 4), 16);
        int[] w = {w0, w1};
        int[] wExpand = keyExpansion(w);
   
        int w2 = wExpand[2];
        int w3 = wExpand[3];
        int w4 = wExpand[4];
        int w5 = wExpand[5];
        int[] key0 = {w0, w1};
        int[] key1 = {w2, w3};
        int[] key2 = {w4, w5};



        // // //round 0
        addKey(s, key2);
       
        // // // //round 1
        shiftRow(s);
        nibSubinv(s);
        addKey(s, key1);
        mixColumninv(s);
        
        // // // // //round 2
        shiftRow(s);
        nibSubinv(s);
        addKey(s, key0); 


        return (s[0] << 12) | (s[2] << 8) | (s[1] << 4) | s[3];

    }

    public static void main(String[] args) {
  
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
        int w2 = wExpand[2];
        int w3 = wExpand[3];
        int w4 = wExpand[4];
        int w5 = wExpand[5];
        int[] key0 = {w0, w1};
        int[] key1 = {w2, w3};
        int[] key2 = {w4, w5};


        System.out.println("----");




        // // //round 0
        saes.addKey(s, key0);
       
        // // // //round 1
        saes.nibSub(s);
        saes.shiftRow(s);
        saes.mixColumn(s);
        saes.addKey(s, key1);
        // // // // //round 2
        saes.nibSub(s);
        saes.shiftRow(s);
        saes.addKey(s, key2); 


        for (int i: s)
        saes.print(i);
        
       

    }   

}