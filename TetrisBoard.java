import java.util.Scanner;

public class TetrisBoard {
	 
	 int fieldwidth = 10;
	 int fieldHeight = 20;
	 TetrisBlock curBlock;
	 Tetrominoes[] field;
	 int curX = 0, curY = 0;		//보드에서 가지고 있을 변수
	 boolean isDownFinished = false;
	 
	 
	 //보드 초기화
	 public TetrisBoard() {
		curBlock = new TetrisBlock();		//테트리스 블록 메소드 호출
		field = new Tetrominoes[fieldwidth * fieldHeight];			//필트 초기화
		
		initBoard();		//코드 생성시 가장 먼저 초기화
	 }
	 
	 
	 //보드에 대한 초기화
	 public void initBoard() {
		 for(int i=0; i<fieldwidth * fieldHeight; i++) {
			 field[i] = Tetrominoes.NoBlock;
		 }
	 }
	 
	 //이동을 할려는 지점에 블록이 있는지 없는지를 판별
	 Tetrominoes curField(int x, int y) {
		 return field[(y*fieldwidth) + x];
		 			//행					열
	 }
	 
	 public void start() {
		 boolean isStarted = true;
		 
//		 1.필드를 초기화
		 initBoard();
//		 2.블록을 생성
		 createBlock();
//		 3. 화면에 출력
		 print();
//		 4. 키 입력 받기, 입력에 따른 블록의 이동 & 회전
		 while(isStarted) {
			 isStarted = moveBlock();
			 print();	 
			 //줄이 지워졌을 경우
			 if(isDownFinished) {
				 isDownFinished = false;
				 createBlock();
			 }
		 }
	 }
	 
//	 블록생성
	 void createBlock() {
		 curBlock.randomBlock();
		 
		 curX = fieldwidth / 2;
		 curY = curBlock.minY()+2;
//		 curY = 1;
	 }
	 
	 
//	 화면출력
	 void print() {				
		 Tetrominoes[] printField 
		 = new Tetrominoes[fieldwidth * fieldHeight];

//		 현재필드상태
		for(int i=0; i<field.length; i++) {
			printField[i] = field[i];
		}
		
//		블록추가	
		if(curBlock.getBlock() != Tetrominoes.NoBlock) {		//블록이 노블록인지 아닌지 확인하는것,
			for(int i=0; i<4; i++) {		//현재 필드에 있는 위치 값을 가져옴
				int x = curX + curBlock.x(i);
				int y = curY + curBlock.y(i);
				printField[(y * fieldwidth)+x] = curBlock.getBlock();		//필드값을 적용	  																													
			}
		}
		
//		필드 출력
		for(int i=0; i<fieldHeight; i++) {
			for(int j=0; j<fieldwidth; j++) {
				Tetrominoes tempField = printField[i*fieldwidth + j];
				if(tempField != Tetrominoes.NoBlock) {	//필드에 공백이 없는 상태
					System.out.print("#");
				}
				else
					System.out.print("^");
			}
			System.out.println();
		}
		
	 }
	 
	 
	 
	 boolean moveBlock() {
	  Scanner s = new Scanner(System.in);
	  System.out.println("키입력 : ");
	  String key = s.nextLine();	  
	  boolean flag = true;
	  
	  switch(key) {

	  case "j": 	//왼쪽
		  move(curBlock, curX-1, curY);
		  break;
		  
	  case "l":		//오른쪽
		  move(curBlock, curX+1, curY);
		  break;
		  
	  case "i":		//회전
		  move(curBlock.rotateBlock(), curX, curY);
		  break;		  
		  
	  case "k":		//밑으로 한 칸
		  moveDown();
		  break;	        

	  case "q":
		  System.out.println("게임종료");
		  flag = false;
		  break;
		  
	  }
	  return flag;
	 }
	 
	 
	 boolean move(TetrisBlock newBlock, int newX, int newY) {
		  for(int i=0; i<4; i++) {		//현재 필드에 있는 위치 값을 가져옴
			  int x = newX + newBlock.x(i);
			  int y = newY + newBlock.y(i);
			  
			  //벽에 나가지 않도록
			  if(x < 0 || x >= fieldwidth || y<0 || y >= fieldHeight) {
				  return false;
			  }
			  if( curField(x,y) != Tetrominoes.NoBlock) {
				  return false;
			  }
		  }
		  
		  curX = newX;		//new x,y는 이동을 했을 때, 바뀐 결과
		  curY = newY;
		  curBlock = newBlock;
		  return true;
	 }
	 
	 
	 void downFinished() {
		 for(int i=0; i<4; i++) {
			 int x = curX + curBlock.x(i);
			 int y = curY + curBlock.y(i);
			 field[(y*fieldwidth)+x] = curBlock.getBlock();
		 }
		 
		 removeLine();
		 
		 if(!isDownFinished)
			 createBlock();
	 }
	 
	 private void moveDown() {
		 if(!move(curBlock, curX, curY+1)) {
			 System.out.println("Tetris Game");
			 downFinished();
		 }
	 }
	 
	 
	 
	 //구현 = 각 '열'을 다 확인하면서 블록이 없으면 return false
	 boolean removeLine() {
		 int removedLineNum = 0;		//한 번 Down해서 removeLine이 실행되면 몇개의 줄이 삭제 됬는지
		 //줄 확인
		 for(int i=0; i<fieldHeight; i++) {
			 boolean isFull = true;
			 for(int j=0; j<fieldwidth; j++) {
				 //빈 공간이 있어서 줄을 지울 필요가 없어서 return false;
				 if(curField(j,i) == Tetrominoes.NoBlock) {
					 isFull = false;
//					 return false;  는 오류남
					 break;
				 }		
			 }
			 
			 //줄 이동
			 if(isFull) {
				 removedLineNum++;
				 for(int k=i; k>0; k--) {
					 for(int j=0; j<fieldwidth; j++) {
						 field[(k*fieldwidth+j)] = curField(j,k-1); //field[(y * fieldwidth) + x]; 처럼 j, k-1
						 //하는 역할은 현재필드(curField)에서 삭제되어야하는 위치에 이동시켜주는 field
//						 makeField(j, k-1, Tetrominoes.NoBlock);
					 }
				 }
			 }			 
		 
		 }	
		if(removedLineNum > 0) {
			isDownFinished = true;
		 	curBlock.setBlock(Tetrominoes.NoBlock);
		}
			
		 return true;
	 }
	 
	 private void makeField(int x, int y, Tetrominoes field) {
		 this.field[(y*fieldwidth)+x] = field;
	 }
	 
}
