import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

class Main {
	
	static long K;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		K = sc.nextLong();
		
		boolean isReversed = false;
		
		// 현재 레벨 사이즈
		long l = 1;	 
		while (K > l) l <<= 1;
		
		long idx = K;	// 현재 계층의 인덱스 (1~l)	
		while ( l > 1 ) {
			if (idx > (l>>1)) { // 인덱스가 레벨 사이즈의 반보다 클 경우,
				isReversed = !isReversed;
				idx -= (l>>1);
			}
//			System.out.printf("%d %d %b\n", idx, l, isReversed);
			l >>= 1;
		}
		
		System.out.println(isReversed? '1' : '0');
	}
}

/**
 *	18222. 투에-모스 문자열 
 */