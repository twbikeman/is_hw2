class SDES {
	
	
	public static final int BIT1 = 1 << 7;
	public static final int BIT2 = 1 << 6;
	public static final int BIT3 = 1 << 5;
	public static final int BIT4 = 1 << 4;
	public static final int BIT5 = 1 << 3;
	public static final int BIT6 = 1 << 2;
	public static final int BIT7 = 1 << 1;
	public static final int BIT8 = 1;
	public static final int BIT1_10 = 1 << 9;
	public static final int BIT2_10 = 1 << 8;
	public static final int BIT3_10 = 1 << 7;
	public static final int BIT4_10 = 1 << 6;
	public static final int BIT5_10 = 1 << 5;
	public static final int BIT6_10 = 1 << 4;
	public static final int BIT7_10 = 1 << 3;
	public static final int BIT8_10 = 1 << 2;
	public static final int BIT9_10 = 1 << 1;
	public static final int BIT10_10 = 1;
	public static final int BIT1_4 = 1 << 3;
	public static final int BIT2_4 = 1 << 2;
	public static final int BIT3_4 = 1 << 1;
	public static final int BIT4_4 = 1;
	
	public void expBin(char input, int num) {
		for (int i = 1 << (num -1); i >= 1; i /= 2) {

			if ((input & i) != 0)
				System.out.print('1');
			else
				System.out.print('0'); 
		}
		System.out.print('\n');
	}


	public char permute(char input, int[] index) {
		char output = '\0';
		int x = 0x00;
		for(int i = 0; i < index.length; i++) {			
			if ((input & index[i]) != 0)
				x = 0x01;
			else 
				x = 0x00;
			output = (char)((output << 1) | x);
		}
		return output;
	}


	public char ls(char input, int num, int shift) {
		char output;
		output = (char)(input << (32 - num + shift) >>> (32 - num ) | (input >> (num - shift)));
		return output; 
	}
	
	public char merge(char input1,  char input2, int num) {
		char output;
		output = (char)(input1 << num | input2);
		return output;
	}

	public void keyGen(char k, char[] key) {
		int[] p10 = {BIT3_10, BIT5_10, BIT2_10, BIT7_10, BIT4_10, BIT10_10, BIT1_10, BIT9_10, BIT8_10, BIT6_10};
	 	int[] l4_8 = {BIT1, BIT2, BIT3, BIT4};
		int[] r4_8 = {BIT5, BIT6, BIT7, BIT8};
		int[] l5_10 = {BIT1_10, BIT2_10, BIT3_10, BIT4_10, BIT5_10};
		int[] r5_10 = {BIT6_10, BIT7_10, BIT8_10, BIT9_10, BIT10_10};
		int[] p8 = {BIT6_10, BIT3_10,BIT7_10,BIT4_10, BIT8_10,BIT5_10,BIT10_10,BIT9_10};

		k = permute(k, p10);
		char k1_l = ls(permute(k, l5_10), 5, 1);
		char k1_r = ls(permute(k, r5_10), 5, 1);
		char k2_l = ls(k1_l, 5, 2);
		char k2_r = ls(k1_r, 5, 2);
		
		char k1 = permute(merge(k1_l, k1_r, 5), p8);
		char k2 = permute(merge(k2_l, k2_r, 5), p8);
		key[0] = k1;
		key[1] = k2;
	}

	public char encrypt(char p, char k) {
		int[]  ip = {BIT2, BIT6, BIT3, BIT1, BIT4, BIT8, BIT5,BIT7};
		int[] inv_ip = {BIT4, BIT1, BIT3, BIT5, BIT7, BIT2, BIT8, BIT6};
		int[]  ep = {BIT4_4, BIT1_4, BIT2_4, BIT3_4, BIT2_4, BIT3_4, BIT4_4,BIT1_4};
		int[] p4 = {BIT2_4, BIT4_4, BIT3_4, BIT1_4};
		int[] row = {BIT1_4, BIT4_4};
		int[] col = {BIT2_4, BIT3_4};
	  	int[] l4_8 = {BIT1, BIT2, BIT3, BIT4};
		int[] r4_8 = {BIT5, BIT6, BIT7, BIT8};
		int[] sw = {BIT5, BIT6, BIT7, BIT8, BIT1, BIT2, BIT3, BIT4};
		int[][] s0 = {{1,0,3,2}, {3,2,1,0}, {0,2,1,3}, {3,1,3,2}};
		int[][] s1 = {{0,1,2,3}, {2,0,1,3}, {3,0,1,0}, {2,1,0,3}};
		char c = permute(p, ip);
		char[] key = new char[2];
		keyGen(k, key);
		c = fk(c, key[0]);
		c = permute(c, sw);
		c = fk(c, key[1]);
		return c;
	}

	public char fk(char p, char key) {
		int[] ep = {BIT4_4, BIT1_4, BIT2_4, BIT3_4, BIT2_4, BIT3_4, BIT4_4,BIT1_4};
		int[] p4 = {BIT2_4, BIT4_4, BIT3_4, BIT1_4};
		int[] row = {BIT1_4, BIT4_4};
		int[] col = {BIT2_4, BIT3_4};
	  	int[] l4_8 = {BIT1, BIT2, BIT3, BIT4};
		int[] r4_8 = {BIT5, BIT6, BIT7, BIT8};
		int[] sw = {BIT5, BIT6, BIT7, BIT8, BIT1, BIT2, BIT3, BIT4};	
		int[][] s0 = {{1,0,3,2}, {3,2,1,0}, {0,2,1,3}, {3,1,3,2}};
		int[][] s1 = {{0,1,2,3}, {2,0,1,3}, {3,0,1,0}, {2,1,0,3}};
		
		char c_r = permute(p, r4_8);
		char c_l = permute(p, l4_8);
		char f = permute(c_r, ep);
		f = (char)(f ^ key);
		char f0 = permute(f, l4_8);
		f0 = (char)(s0[permute(f0, row)][permute(f0, col)]);
		char f1 = permute(f, r4_8);
		f1 = (char)(s1[permute(f1, row)][permute(f1, col)]);
		char s = permute(merge(f0, f1, 2), p4);
		c_l = (char)(s ^ c_l);
		char c = merge(c_l, c_r, 4);
		return c;	
	}
	

	public char decrypt(char c, char k) {
		int[]  ip = {BIT2, BIT6, BIT3, BIT1, BIT4, BIT8, BIT5,BIT7};
		int[] inv_ip = {BIT4, BIT1, BIT3, BIT5, BIT7, BIT2, BIT8, BIT6};
		int[] sw = {BIT5, BIT6, BIT7, BIT8, BIT1, BIT2, BIT3, BIT4};
		
		char[] key = new char[2];
		keyGen(k, key);
		char p = permute(c, ip);
		p = fk(p, key[1]);
		p = permute(p, sw);
		p = fk(p, key[0]);
		p = permute(p, inv_ip);
		return p;

	}
	
/*
		SDES sdes = new SDES();
		char x = 0x0282;
		char c= 0x46;
		
		int c = sdes.encrypt(p, x);
		int p = sdes.decrypt(c, x);

*/
	


}
