import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;


//���� +���� = ��Ϻ��� ���� �ٸ��� ����
//���̴� ��ϵ� �ٸ� �������� ����
//������ ��ǥ ����
//createBlock �޼ҵ��� if�� ���� �����غ���(����� ó�� ����� �� �� ������ ������ ��ǥ �����ϱ�)


class TetrisBoard extends JPanel implements KeyListener{
	
	Tetrominoes[] printField;
	 int fieldwidth = 10;
	 int fieldHeight = 20;
	 TetrisBlock curBlock;
	 Tetrominoes[] field;
	 int curX = 0, curY = 0;		//���忡�� ������ ���� ����
	 boolean isDownFinished = false;
//	 boolean isStarted = false; 
	
	public TetrisBoard() {//������	
		curBlock = new TetrisBlock();		//��Ʈ���� ��� �޼ҵ� ȣ��
		field = new Tetrominoes[fieldwidth * fieldHeight];			//��Ʈ �ʱ�ȭ
		
//		initBoard();		//�ڵ� ������ ���� ���� �ʱ�ȭ
		start();		
		
		this.setBackground(Color.GRAY);
		this.setFocusable(true);
		this.requestFocus();
		addKeyListener(this);
	}
	
	 Tetrominoes curField(int x, int y) {
		 return field[(y*fieldwidth) + x];
		 			//��					��
	 }
	 
	 public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			for(int i = 0; i<this.getHeight()/30+1; i++) {
				//������
				g.drawLine(0, i*30, 300, i*30);
				//g.drawLine(330,i*30, 600,i*30);

				//������
				g.drawLine(i*30, 0, i*30, 600);
			}
			
			
//			��ϻ��� ����
//			Tetrominoes.value(TBlock, SquareBlock);
			
//			�ʵ� ���
			for(int i=0; i<fieldHeight; i++) {
				for(int j=0; j<fieldwidth; j++) {
					Tetrominoes tempField = printField[i*fieldwidth + j];		//�ʵ�
					if(tempField != Tetrominoes.NoBlock) {	//�ʵ忡 ������ ���� ����
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
		 
//		 1.�ʵ带 �ʱ�ȭ
		 initBoard();
//		 2.����� ����
		 createBlock();	 
//		 isStarted = true;	 
//		 3. ȭ�鿡 ���
		 print();	 
//		 4. Ű �Է� �ޱ�, �Է¿� ���� ����� �̵� & ȸ��

	 }
		 
	void print() {		
		printField = new Tetrominoes[fieldwidth * fieldHeight];
//		 �����ʵ����
		for(int i=0; i<field.length; i++) {
			printField[i] = field[i];
		}
			
		
//		����߰�	
		if(curBlock.getBlock() != Tetrominoes.NoBlock) {		//����� �������� �ƴ��� Ȯ���ϴ°�,
			for(int i=0; i<4; i++) {		//���� �ʵ忡 �ִ� ��ġ ���� ������
				int x = curX + curBlock.x(i);
				int y = curY + curBlock.y(i);
				printField[(y * fieldwidth)+x] = curBlock.getBlock();		//�ʵ尪�� ����	  																													
			}
		}
		
		repaint();			//Graphics g �� �޼ҵ�, �׸��� �����Ǿ��� ��, �׸��� �ٽ� �׷��ִ� �޼ҵ�
	}
	
	//if�� ���� �����غ���
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
		  for(int i=0; i<4; i++) {		//���� �ʵ忡 �ִ� ��ġ ���� ������
			  int x = newX + newBlock.x(i);
			  int y = newY + newBlock.y(i);
			  
			  //���� ������ �ʵ���
			  if(x < 0 || x >= fieldwidth || y<0 || y >= fieldHeight) {
				  return false;
			  }
			  if( curField(x,y) != Tetrominoes.NoBlock) {
				  return false;
			  }
		  }
		  
		  curX = newX;		//new x,y�� �̵��� ���� ��, �ٲ� ���
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
		for(int i=0; i<4; i++) {		//���� �ʵ忡 �ִ� ��ġ ���� ������
			int x = curBlock.x(i);
			int y = curBlock.y(i);
			if(field[(y * fieldwidth) + x] != Tetrominoes.NoBlock) {
				return false;
			} 																													
		} 
		return true;
		
	}
	
//���� = �� '��'�� �� Ȯ���ϸ鼭 ����� ������ return false
	 boolean removeLine() {
		 int removedLineNum = 0;		//�� �� Down�ؼ� removeLine�� ����Ǹ� ��� ���� ���� �����
		 //�� Ȯ��
		 for(int i=0; i<fieldHeight; i++) {
			 boolean isFull = true;
			 for(int j=0; j<fieldwidth; j++) {
				 //�� ������ �־ ���� ���� �ʿ䰡 ��� return false;
				 if(curField(j,i) == Tetrominoes.NoBlock) {
					 isFull = false;
//					 return false;  �� ������
					 break;
				 }		
			 }
		 
			 //�� �̵�
			 if(isFull) {
				 removedLineNum++;
				 for(int k=i; k>0; k--) {
					 for(int j=0; j<fieldwidth; j++) {
						 field[(k*fieldwidth+j)] = curField(j,k-1); //field[(y * fieldwidth) + x]; ó�� j, k-1
						 //�ϴ� ������ �����ʵ�(curField)���� �����Ǿ���ϴ� ��ġ�� �̵������ִ� field
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

		  case KeyEvent.VK_LEFT: 	//����
			  move(curBlock, curX-1, curY);
			  break;
			  
		  case KeyEvent.VK_RIGHT:		//������
			  move(curBlock, curX+1, curY);
			  break;
			  
		  case KeyEvent.VK_UP:		//ȸ��
			  move(curBlock.rotateBlock(), curX, curY);
			  break;		  
			  
		  case KeyEvent.VK_DOWN:		//������ �� ĭ
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
		setTitle("20150651_������");
		setSize(600, 600);
		TetrisBoard tb = new TetrisBoard();
		
		add(tb);
		//�տ� �� ���� int�� ������ ��ǥ ____
		tb.setBounds(0, 0, 300, 600);		// int, int, int, int ���� ����		
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}

	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new TetrisMain();
	}

}
