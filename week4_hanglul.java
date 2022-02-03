//행렬의 덧셈, 곱셈
// 배열의 크기는 덧셈의 경우 5x5, 곱셉의 경우 5x3, 3x5를 입력으로 받아 계산한 후, 5x5의 행렬을 출력한다. 
package week4;

import java.util.Random;
import java.util.Scanner;

public class week4_hanglul {
	
	//행렬 덧셈 메소드(함수)
	static void MatrixSum(){
		int[][] A =new int[5][5];	
		int[][] B =new int[5][5];
		int[][] C =new int[5][5];	//덥셈의 출력
		Random r = new Random();

		for(int i =0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				A[i][j] = r.nextInt(20);		// 범위 0~ 20
				B[i][j] = r.nextInt(20);
			}
		}
		
		for(int i = 0;) {
			//C = A+B
		}
		
		
		for() {
			//C출력
			System.out.println();
		}
	}
	
	//행렬 곱셈 메소드(함수)
	static void MatrixMum(){
		int[][] A = new int[5][3];
		int[][] B = new int[3][5];
		int[][] C = new int[5][5];	//곱셈, 행과 열이 같아야 곱셈공식 성립한다.
		
		Random r = new Random();
		for(int i =0; i<5; i++) {
			for(int j = 0; j<3; j++) {
				A[i][j] = r.nextInt(20);		//A의 행렬은 5X3
				B[j][i] = r.nextInt(20);		//B의 행렬은 3X5
			}
		}
		
		for() {	//행열끼리 곱하는 값 ,, 원소를 곱하는 게 아니다
			//C = A*B
		}
		
		for() {
			//C출력
			System.out.println();
		}
	
	}

	public static void main(String[] args) {
		//1. 배열을 이용하여 행렬의 덧셈, 곱셈을 구현
		
	
		System.out.println("==================");
		System.out.println("1. 행렬 덧셈");
		System.out.println("2. 행렬 곱셈");
		System.out.println("==================");
		Scanner s = new Scanner(System.in);
		
		
		
		switch(s.nextInt()) {
		case 1:	//덧셈
			MatrixSum();
			break;
		case 2:	//곱셈
			MatrixMum();
			break;
		}
		
	}

}
