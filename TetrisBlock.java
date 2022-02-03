import java.util.Random;

//���Ź� = ������� �����ϴ� ���    _    ��Ʈ���� ��Ͽ� ���� �̸� �������� ����
enum Tetrominoes { NoBlock, ZBlock, SBlock, LineBlock, TBlock, 
		SquareBlock, LBlock, ReverseLBlock };
		
//����� ����� ����
public class TetrisBlock {
	
	
	
	public Tetrominoes block;				//���Ź� �� �ϳ� �ҷ����� ����
	public int coords[][];					//2���� �迭�� �̿��� ��ǥ �ֱ�
	public int[][][] coordsField;			//��� ���� ��ǥ
		
		
	public TetrisBlock() {	
		coords = new int[4][2];			//�ʱ�ȭ  
	}
	
	public void setBlock(Tetrominoes block) {
//		��� ���� ��ǥ, 3���� �迭�� �ʱ�ȭ
		coordsField = new int[][][] {  		
		   { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, //no ������
		    { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, //z
		    { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } }, //s
		    { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, //line
		    { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, //t
		    { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, //square
		    { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }, //L
		    { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } // L�ݴ�
		  };
		  
		 for(int i=0; i<4; i++) {	//i�� (4,2)��
			 for(int j=0; j<2; j++) {
				 coords[i][j]								//ordina�� setBlock���� �̸��� ������ �ִ� index��(��ȣ)�� ������ ��
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
		int x = r.nextInt(7)+1;								//1~7�� ����
		Tetrominoes[] t = Tetrominoes.values();				//Tetrominoes�� ��ü ȣ�� = > values / new Tetrominoes�� ����
		setBlock(t[x]);										//������ ����� �ϳ�(����� �̸��� )�� setBlock�� �Ѱ���
	}
	
	//��ǥ�߿� �ּҰ��� ã�Ƽ� �������� ���� �޼ҵ�
	public int minY() {
		int m = coords[0][1];
		for(int i =0; i<4; i++) {
			m = Math.min(m,coords[i][1]);
		}
		
		return m;
	}
	
	
	//��ǥ�� ����
	//���� ����� ������ �ִ� X,Y���� �Ѱ��ִ� ��, �� �ϳ����� �����ִ� ��
	public int x(int index) {
		return coords[index][0];				//x��ǥ�� �����ְ� y��ǥ�� 0����
	}
	public int y(int index) {
		return coords[index][1];				//x��ǥ�� �����ְ� y��ǥ�� 1����
	}
	
	
	//��ǥ�� ����
	//���忡�� �����̸� ������ ���� �����ؾ��ϱ� ������ x,y�� ���� ������ �޼ҵ�
	public void setX(int index, int x) {			//ȸ���̳� Ű�� �ȱ�� �� ���� �ٲ� x�� ����
		coords[index][0] = x;
	}
	public void setY(int index, int y) {			//ȸ���̳� Ű�� �ȱ�� �� ���� �ٲ� y�� ����
		coords[index][1] = y;
	}
	
	
	//�ð���� ȸ��   X = -Y, Y=X
	public TetrisBlock rotateBlock() {			//�ð� ���� ȸ�� �޼ҵ�
		
		if(this.block == Tetrominoes.SquareBlock) {
			return this;
		}
		
		TetrisBlock tb = new TetrisBlock();
		tb.block = this.block;
		
		for(int i=0; i<4; i++) {
			tb.setX(i, -y(i));			//������ i��, -y���� ����� ����
			tb.setY(i, x(i));
		}
		
		return tb;
	}


}