import java.util.Random;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LotteryProgram {
	static int recommendNumber() {
		System.out.println("ȸ���� �Է� : ");
		Scanner s = new Scanner(System.in);
		int number = s.nextInt();
		
		System.out.println("�� "+number+" ȸ");
		return number;
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		recommendNumber();
		
		Random rand = new Random();
		Date date = new Date();
		
		System.out.println("���� �ð�");
		SimpleDateFormat sdf1 = new SimpleDateFormat("YYY.MM.dd(E)");
		System.out.println(sdf1.format(date));
		SimpleDateFormat sdf2 = new SimpleDateFormat("a h:m:m");
		System.out.println(sdf2.format(date));
		
		int[] r = new int[7];
		System.out.println();
		System.out.println("��õ ��ȣ");
		for(int i=0; i<r.length; i++) {
			r[i] = rand.nextInt(46);
			for(int j=0; j<i; j++) {
				if(r[i]==r[j]) {
					i--;
					break;
				}
			}
		}
		for(int i=0; i<r.length-1; i++) {
			System.out.println(r[i]);
		}	
	}
	
}

