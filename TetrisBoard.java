import java.util.Scanner;

public class TetrisBoard {
	 
	 int fieldwidth = 10;
	 int fieldHeight = 20;
	 TetrisBlock curBlock;
	 Tetrominoes[] field;
	 int curX = 0, curY = 0;		//���忡�� ������ ���� ����
	 boolean isDownFinished = false;
	 
	 
	 //���� �ʱ�ȭ
	 public TetrisBoard() {
		curBlock = new TetrisBlock();		//��Ʈ���� ��� �޼ҵ� ȣ��
		field = new Tetrominoes[fieldwidth * fieldHeight];			//��Ʈ �ʱ�ȭ
		
		initBoard();		//�ڵ� ������ ���� ���� �ʱ�ȭ
	 }
	 
	 
	 //���忡 ���� �ʱ�ȭ
	 public void initBoard() {
		 for(int i=0; i<fieldwidth * fieldHeight; i++) {
			 field[i] = Tetrominoes.NoBlock;
		 }
	 }
	 
	 //�̵��� �ҷ��� ������ ����� �ִ��� �������� �Ǻ�
	 Tetrominoes curField(int x, int y) {
		 return field[(y*fieldwidth) + x];
		 			//��					��
	 }
	 
	 public void start() {
		 boolean isStarted = true;
		 
//		 1.�ʵ带 �ʱ�ȭ
		 initBoard();
//		 2.����� ����
		 createBlock();
//		 3. ȭ�鿡 ���
		 print();
//		 4. Ű �Է� �ޱ�, �Է¿� ���� ����� �̵� & ȸ��
		 while(isStarted) {
			 isStarted = moveBlock();
			 print();	 
			 //���� �������� ���
			 if(isDownFinished) {
				 isDownFinished = false;
				 createBlock();
			 }
		 }
	 }
	 
//	 ��ϻ���
	 void createBlock() {
		 curBlock.randomBlock();
		 
		 curX = fieldwidth / 2;
		 curY = curBlock.minY()+2;
//		 curY = 1;
	 }
	 
	 
//	 ȭ�����
	 void print() {				
		 Tetrominoes[] printField 
		 = new Tetrominoes[fieldwidth * fieldHeight];

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
		
//		�ʵ� ���
		for(int i=0; i<fieldHeight; i++) {
			for(int j=0; j<fieldwidth; j++) {
				Tetrominoes tempField = printField[i*fieldwidth + j];
				if(tempField != Tetrominoes.NoBlock) {	//�ʵ忡 ������ ���� ����
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
	  System.out.println("Ű�Է� : ");
	  String key = s.nextLine();	  
	  boolean flag = true;
	  
	  switch(key) {

	  case "j": 	//����
		  move(curBlock, curX-1, curY);
		  break;
		  
	  case "l":		//������
		  move(curBlock, curX+1, curY);
		  break;
		  
	  case "i":		//ȸ��
		  move(curBlock.rotateBlock(), curX, curY);
		  break;		  
		  
	  case "k":		//������ �� ĭ
		  moveDown();
		  break;	        

	  case "q":
		  System.out.println("��������");
		  flag = false;
		  break;
		  
	  }
	  return flag;
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
