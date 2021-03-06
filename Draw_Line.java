package week4;

public class test12 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//x,y좌표 그리기
		//y = x^2 그래프
		int[][] board=new int[40][40];
		int x,y;
		
		//가운데가 0,0으로 만들게 하기 위한 좌표이동
		for(x = -19; x<20; x++) {//가운데에 대한 좌표가 0,0이기 떄문에
			y=x;		//y=x그래프   -- x :-20~19일 떄, y : -20~19이다.
			board[40-(y+20)][x+20] = 1;		//그래프가 그려지는 값이 1 -- 원점으로 이동
		// 40-(y+20) = 20-y 는 x축대칭(일반적인 그래프 y=x를 만들기 위해
		// 이렇게 코딩을 안 하고 y+20을 해주면 y= - x축의 그래프가 나온다. 그 이유는 좌표 중심값이 다르기 떄문이다.
		}
		
		//그래프가 그려지는 코드
		for(int i=0; i<40; i++) {
			for(int j=0; j<40; j++) {
				if(i == 20) {
					System.out.print('-');
				}
				else if(j==20) {
					System.out.print('|');
				}
				else {
//					System.out.print(' ');		//아무것도 존재하지 않을 경우
					System.out.print(board[i][j]==1?'*':' ');	//그래프를 그리는 값은 1
					//1이면 *표시, 1이 아니면 공백' '
				}
			}
			System.out.println();
		}
 
	}

}
