//����� ����, ����
// �迭�� ũ��� ������ ��� 5x5, ������ ��� 5x3, 3x5�� �Է����� �޾� ����� ��, 5x5�� ����� ����Ѵ�. 
package week4;

import java.util.Random;
import java.util.Scanner;

public class week4_hanglul {
	
	//��� ���� �޼ҵ�(�Լ�)
	static void MatrixSum(){
		int[][] A =new int[5][5];	
		int[][] B =new int[5][5];
		int[][] C =new int[5][5];	//������ ���
		Random r = new Random();

		for(int i =0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				A[i][j] = r.nextInt(20);		// ���� 0~ 20
				B[i][j] = r.nextInt(20);
			}
		}
		
		for(int i = 0;) {
			//C = A+B
		}
		
		
		for() {
			//C���
			System.out.println();
		}
	}
	
	//��� ���� �޼ҵ�(�Լ�)
	static void MatrixMum(){
		int[][] A = new int[5][3];
		int[][] B = new int[3][5];
		int[][] C = new int[5][5];	//����, ��� ���� ���ƾ� �������� �����Ѵ�.
		
		Random r = new Random();
		for(int i =0; i<5; i++) {
			for(int j = 0; j<3; j++) {
				A[i][j] = r.nextInt(20);		//A�� ����� 5X3
				B[j][i] = r.nextInt(20);		//B�� ����� 3X5
			}
		}
		
		for() {	//�࿭���� ���ϴ� �� ,, ���Ҹ� ���ϴ� �� �ƴϴ�
			//C = A*B
		}
		
		for() {
			//C���
			System.out.println();
		}
	
	}

	public static void main(String[] args) {
		//1. �迭�� �̿��Ͽ� ����� ����, ������ ����
		
	
		System.out.println("==================");
		System.out.println("1. ��� ����");
		System.out.println("2. ��� ����");
		System.out.println("==================");
		Scanner s = new Scanner(System.in);
		
		
		
		switch(s.nextInt()) {
		case 1:	//����
			MatrixSum();
			break;
		case 2:	//����
			MatrixMum();
			break;
		}
		
	}

}
