import java.util.Random;

//열거법 = 순서대로 나열하는 방법    _    테트리스 블록에 대한 이름 지정으로 열거
enum Tetrominoes { NoBlock, ZBlock, SBlock, LineBlock, TBlock, 
		SquareBlock, LBlock, ReverseLBlock };
		
//블록을 만드는 영역
public class TetrisBlock {
	
	
	
	public Tetrominoes block;				//열거법 중 하나 불러오는 구문
	public int coords[][];					//2차원 배열을 이용한 좌표 넣기
	public int[][][] coordsField;			//블록 모형 좌표
		
		
	public TetrisBlock() {	
		coords = new int[4][2];			//초기화  
	}
	
	public void setBlock(Tetrominoes block) {
//		블록 모형 좌표, 3차원 배열로 초기화
		coordsField = new int[][][] {  		
		   { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, //no 기준점
		    { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, //z
		    { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } }, //s
		    { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, //line
		    { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, //t
		    { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, //square
		    { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }, //L
		    { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } // L반대
		  };
		  
		 for(int i=0; i<4; i++) {	//i는 (4,2)라서
			 for(int j=0; j<2; j++) {
				 coords[i][j]								//ordina는 setBlock에서 이름을 가지고 있는 index값(번호)를 가지고 옴
						  = coordsField[block.ordinal()][i][j];
			 }
		 }
		 
		 this.block = block;
	}
	
	public Tetrominoes getBlock() {
		return block;
	}
		 
		 
	
	void randomBlock() { 
		Random r = new Random();
		int x = r.nextInt(7)+1;								//1~7번 까지
		Tetrominoes[] t = Tetrominoes.values();				//Tetrominoes의 객체 호출 = > values / new Tetrominoes와 동일
		setBlock(t[x]);										//생성된 블록중 하나(블록의 이름을 )를 setBlock에 넘겨줌
	}
	
	//좌표중에 최소값을 찾아서 기준으로 놓는 메소드
	public int minY() {
		int m = coords[0][1];
		for(int i =0; i<4; i++) {
			m = Math.min(m,coords[i][1]);
		}
		
		return m;
	}
	
	
	//좌표를 전달
	//현재 블록이 가지고 있는 X,Y값을 넘겨주는 것, 단 하나씩만 보내주는 것
	public int x(int index) {
		return coords[index][0];				//x좌표를 보내주고 y좌표는 0으로
	}
	public int y(int index) {
		return coords[index][1];				//x좌표를 보내주고 y좌표는 1으로
	}
	
	
	//좌표를 저장
	//보드에서 움직이면 움직인 값을 저장해야하기 때문에 x,y의 값을 덮어씌우는 메소드
	public void setX(int index, int x) {			//회전이나 키를 옴기고 난 다음 바뀐 x값 저장
		coords[index][0] = x;
	}
	public void setY(int index, int y) {			//회전이나 키를 옴기고 난 다음 바뀐 y값 저장
		coords[index][1] = y;
	}
	
	
	//시계방향 회전   X = -Y, Y=X
	public TetrisBlock rotateBlock() {			//시계 방향 회전 메소드
		
		if(this.block == Tetrominoes.SquareBlock) {
			return this;
		}
		
		TetrisBlock tb = new TetrisBlock();
		tb.block = this.block;
		
		for(int i=0; i<4; i++) {
			tb.setX(i, -y(i));			//기존값 i와, -y값을 만들기 위해
			tb.setY(i, x(i));
		}
		
		return tb;
	}


}