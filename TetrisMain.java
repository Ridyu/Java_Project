import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;


//과제 +점수 = 블록별로 색갈 다르게 설정
//쌓이는 블록도 다른 색상으로 변경
//보드판 좌표 수정
//createBlock 메소드의 if문 수정 생각해보기(블록이 처음 띄어질 때 맨 위에서 시작할 좌표 생각하기)


class TetrisBoard extends JPanel implements KeyListener{
	
	Tetrominoes[] printField;
	 int fieldwidth = 10;
	 int fieldHeight = 20;
	 TetrisBlock curBlock;
	 Tetrominoes[] field;
	 int curX = 0, curY = 0;		//보드에서 가지고 있을 변수
	 boolean isDownFinished = false;
//	 boolean isStarted = false; 
	
	public TetrisBoard() {//생성자	
		curBlock = new TetrisBlock();		//테트리스 블록 메소드 호출
		field = new Tetrominoes[fieldwidth * fieldHeight];			//필트 초기화
		
//		initBoard();		//코드 생성시 가장 먼저 초기화
		start();		
		
		this.setBackground(Color.GRAY);
		this.setFocusable(true);
		this.requestFocus();
		addKeyListener(this);
	}
	
	 Tetrominoes curField(int x, int y) {
		 return field[(y*fieldwidth) + x];
		 			//행					열
	 }
	 
	 public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			for(int i = 0; i<this.getHeight()/30+1; i++) {
				//가로줄
				g.drawLine(0, i*30, 300, i*30);
				//g.drawLine(330,i*30, 600,i*30);

				//세로줄
				g.drawLine(i*30, 0, i*30, 600);
			}
			
			
//			블록색상 변경
//			Tetrominoes.value(TBlock, SquareBlock);
			
//			필드 출력
			for(int i=0; i<fieldHeight; i++) {
				for(int j=0; j<fieldwidth; j++) {
					Tetrominoes tempField = printField[i*fieldwidth + j];		//필드
					if(tempField != Tetrominoes.NoBlock) {	//필드에 공백이 없는 상태
						g.setColor(Color.ORANGE);
						g.fillRect(j*30, i*30, 30, 30);
					}
				}
			}
	 }
	 
	 
	
	 
	public void initBoard() {
		 for(int i=0; i<fieldwidth * fieldHeight; i++) {
			 field[i] = Tetrominoes.NoBlock;
		 }
	 }
	 
	public void start() {
		 boolean isStarted = true;
		 
//		 1.필드를 초기화
		 initBoard();
//		 2.블록을 생성
		 createBlock();	 
//		 isStarted = true;	 
//		 3. 화면에 출력
		 print();	 
//		 4. 키 입력 받기, 입력에 따른 블록의 이동 & 회전

	 }
		 
	void print() {		
		printField = new Tetrominoes[fieldwidth * fieldHeight];
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
		
		repaint();			//Graphics g 의 메소드, 그림이 수정되었을 떄, 그림을 다시 그려주는 메소드
	}
	
	//if문 수정 생각해보기
	void createBlock() {
		 curBlock.randomBlock();
		 curX = fieldwidth / 2;
		 curY = curBlock.minY()+2;
//		 curY = 1;
		 if(curBlock.getBlock() != Tetrominoes.SquareBlock) {
			 curY = 0;
		 }
		 if(curBlock.getBlock() != Tetrominoes.TBlock) {
			 curY = 1;
		 }
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
	
	private boolean isGameOver() {
		for(int i=0; i<4; i++) {		//현재 필드에 있는 위치 값을 가져옴
			int x = curBlock.x(i);
			int y = curBlock.y(i);
			if(field[(y * fieldwidth) + x] != Tetrominoes.NoBlock) {
				return false;
			} 																													
		} 
		return true;
		
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
						 makeField(j, k-1, Tetrominoes.NoBlock);
					 }
				 }
			 }			  
		 }	
		 if(removedLineNum > 0) {
			 isDownFinished = true;
			 curBlock.setBlock(Tetrominoes.NoBlock);
		 }
		 createBlock();
//		 isGameOver();
		 
		 return true;
	 }

	 
	 private void makeField(int x, int y, Tetrominoes field) {
		 this.field[(y*fieldwidth)+x] = field;
	 }



	 
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		  
		  switch(e.getKeyCode()) {

		  case KeyEvent.VK_LEFT: 	//왼쪽
			  move(curBlock, curX-1, curY);
			  break;
			  
		  case KeyEvent.VK_RIGHT:		//오른쪽
			  move(curBlock, curX+1, curY);
			  break;
			  
		  case KeyEvent.VK_UP:		//회전
			  move(curBlock.rotateBlock(), curX, curY);
			  break;		  
			  
		  case KeyEvent.VK_DOWN:		//밑으로 한 칸
			  moveDown();
			  break;	          
		  }
		print();
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

public class TetrisMain extends JFrame {
	
	public TetrisMain() {
		setLayout(null);
		setTitle("20150651_차준혁");
		setSize(600, 600);
		TetrisBoard tb = new TetrisBoard();
		
		add(tb);
		//앞에 두 개의 int는 시작할 좌표 ____
		tb.setBounds(0, 0, 300, 600);		// int, int, int, int 정수 형임		
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}

	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new TetrisMain();
	}

}
