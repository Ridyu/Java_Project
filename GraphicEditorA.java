import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//http://bisuanytime.blogspot.com/2016/10/java_11.html

enum Mode {
    NONE, PEN, LINE, RECT, FILLRECT, CIRCLE
}
public class GraphicEditorA extends JFrame implements ActionListener  {
    final int NTOOL=10;
    final int NWIDTH=5;
    final int NCOLOR=16;
    final String[] btnLabel = { "NONE", "PEN", "LINE", "RECT", "FILLRECT", "CIRCLE"};
    String[] colorTable = { "#000000", "#ffffff", "#ff0000", "#00ff00", "#0000ff", "#ffff00", "#00ffff", "#ff00ff",
                            "#c0c0c0", "#808080", "#800000", "#808000", "#008000", "#800080", "#008080", "#000080"
    };
    MyCanvas myCanvas;
    JButton[] toolbox, colorBox;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        for( int i = 0; i < btnLabel.length; i++ ) {
            if( btnLabel[i].equals(cmd))
                myCanvas.setMode(Mode.valueOf(cmd));
        }
        if( toolbox[8] == e.getSource())
            myCanvas.loadImage();
        if( toolbox[9] == e.getSource())
            myCanvas.saveImage();

        for (int i = 0; i < colorBox.length; i++) {
            if( e.getSource() == colorBox[i]) {
                myCanvas.setColor(colorBox[i].getBackground());
                break;
            }
        }

    }

    GraphicEditorA()
    {
    	  JMenuBar menuBar;
          JMenu fileMenu;
          JMenuItem newMenuItem;
          JMenuItem loadMenuItem;
          JMenuItem saveMenuItem;
          JMenuItem exitMenuItem;

          menuBar = new JMenuBar();

              // build the File menu
          fileMenu = new JMenu("File");
          fileMenu.setMnemonic('F');
          newMenuItem = new JMenuItem("New");
          newMenuItem.setMnemonic('N');
          newMenuItem.addActionListener(this);
          fileMenu.add(newMenuItem);
          loadMenuItem = new JMenuItem("Load");
          loadMenuItem.setMnemonic('L');
          loadMenuItem.addActionListener(this);
          fileMenu.add(loadMenuItem);
          saveMenuItem = new JMenuItem("Save");
          saveMenuItem.setMnemonic('S');
          saveMenuItem.addActionListener(this);
          fileMenu.add(saveMenuItem);
          exitMenuItem = new JMenuItem("Exit");
          exitMenuItem.setMnemonic('x');
          exitMenuItem.addActionListener(this);
          fileMenu.add(exitMenuItem);

          // add menus to menubar
          menuBar.add(fileMenu);

          // put the menubar on the frame
          setJMenuBar(menuBar);
    	
    	
    	
    	
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("20150651_Â÷ÁØÇõ_MyPaintingCanvas");

        JPanel topPanel;
        topPanel = new JPanel();
        myCanvas = new MyCanvas();
        topPanel.setBackground(Color.orange);

        //topPanel.setPreferredSize(new Dimension(1000,100));
        toolbox = new JButton[NTOOL];
        JPanel toolPanel = new JPanel(new GridLayout(2,0));
        for (int i = 0; i < toolbox.length; i++) {
            if( i < btnLabel.length-1) {
                toolbox[i] = new JButton(btnLabel[i+1]);
            }
            else
                toolbox[i] = new JButton("T" + (i + 1));
            toolbox[i].addActionListener(this);
            toolPanel.add(toolbox[i]);
        }
        topPanel.add(toolPanel);

        JButton[] widthList = new JButton[NWIDTH];
        JPanel wPanel = new JPanel(new GridLayout(5,1));
        for (int i = 0; i < widthList.length; i++) {
            widthList[i] = new JButton("");
            widthList[i].setSize(20,(i+1)*5);
            wPanel.add(widthList[i]);
        }
        topPanel.add(wPanel);

        colorBox = new JButton[NCOLOR];
        JPanel cPanel = new JPanel(new GridLayout(2,0));
        for (int i = 0; i < colorBox.length; i++) {
            colorBox[i] = new JButton("");
            colorBox[i].setPreferredSize(new Dimension(40,20));
            int j = Integer.decode(colorTable[i]);
            colorBox[i].setBackground(new Color((j>>16)&0xff, (j>>8)&0xff, j&0xff));
            cPanel.add(colorBox[i]);
            colorBox[i].addActionListener(this);

        }
        topPanel.add(cPanel);

        //myCanvas.setBackground(Color.magenta);
        add(topPanel, BorderLayout.PAGE_START);
        add(myCanvas, BorderLayout.CENTER);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new GraphicEditorA();
    }


}

