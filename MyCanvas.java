import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


class MyPen2D extends Polygon
{
    boolean bFill = false;
    Color color;
    Color getColor() { return color; }
    void setColor(Color color) { this.color = color; }
}

class MyLine2D extends Line2D.Double
{
    boolean bFill = false;
    Color color;
    Color getColor() { return color; }
    void setColor(Color color) { this.color = color; }
    MyLine2D() { super(); }
    MyLine2D(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }
}

class MyRectangle2D extends Rectangle2D.Double
{
    boolean bFill = false;
    Color color;
    Color getColor() { return color; }
    void setColor(Color color) { this.color = color; }
    MyRectangle2D() { super(); }
    MyRectangle2D(int x0, int y0, int x1, int y1)
    {
        this.x = Math.min(x0, x1);
        this.y = Math.min(y0, y1);
        this.width = Math.abs(x0-x1);
        this.height = Math.abs(y0-y1);
    }
}

public class MyCanvas extends JPanel implements MouseListener, MouseMotionListener {
    int x0=-1, y0=-1, x1, y1;
    MyPen2D pen;
    Mode drawingMode=Mode.PEN;
    ArrayList<Shape> objectList;
    BufferedImage bgImage=null;
    Color curColor;

    MyCanvas()
    {
        objectList = new ArrayList<>();

        curColor = Color.BLACK;
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    void loadImage()
    {
        File input = new File("saved.jpg");
        try {
            bgImage = ImageIO.read(input);
        } catch(IOException e) {
            bgImage = null;
        }
        // clear all objects
        objectList.clear();
        x0 = y0 = -1;
        repaint();
    }

    void saveImage()
    {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        drawimages(g);
        try {
            File output = new File("saved.jpg");
            ImageIO.write(image, "jpg", output);
        }
        catch(IOException e) {}
    }
    void setMode(Mode mode)
    {
        drawingMode = mode;
        System.out.println(mode);
    }

    void setColor(Color color)
    {
        curColor = color;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        x0 = x1 = mouseEvent.getX();
        y0 = y1 = mouseEvent.getY();
        if( drawingMode == Mode.PEN ) {
            pen = new MyPen2D();
            pen.addPoint( x0, y0);
        }
        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        x1 = mouseEvent.getX();
        y1 = mouseEvent.getY();
        switch (drawingMode)
        {
            case PEN:
                pen.setColor(curColor);
                objectList.add(pen);
                break;
            case LINE:
                MyLine2D l = new MyLine2D(x0, y0, x1, y1);
                l.setColor(curColor);
                objectList.add(l);
                break;
            case FILLRECT:
            case RECT:
                MyRectangle2D r = new MyRectangle2D(x0, y0, x1, y1);
                if( drawingMode == Mode.FILLRECT)
                    r.bFill = true;
                r.setColor(curColor);
                objectList.add(r);
                break;
            case CIRCLE:
            	MyLine2D c = new MyLine2D(x0, y0, x1, y1);
            	c.setColor(curColor);
            	objectList.add(c);
            	break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        x1 = mouseEvent.getX();
        y1 = mouseEvent.getY();
        if( drawingMode == Mode.PEN ) {
            pen.addPoint(x1, y1);
        }
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    void drawimages(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        if( bgImage != null )
            g.drawImage(bgImage, 0, 0, null);

        // draw saved object
        for (int i = 0; i < objectList.size() ;i++) {
            Shape s = objectList.get(i);
            switch(s.getClass().getName())
            {
                case "MyPen2D":
                    MyPen2D p = (MyPen2D)s;
                    g.setColor(p.getColor());
                    g.drawPolyline(p.xpoints, p.ypoints, p.npoints);
                    break;
                case "MyLine2D":
                    MyLine2D l = (MyLine2D)s;
                    g.setColor(l.getColor());
                    g2.draw(l);
                    MyLine2D c = (MyLine2D)s;
                    g.setColor(c.getColor());
                    g2.draw(c);
                    break;
                case "MyRectangle2D":
                    MyRectangle2D r = (MyRectangle2D)s;
                    g.setColor(r.getColor());
                    if( r.bFill == true )
                        g2.fill(r);
                    else
                        g2.draw(r);             
                    break;
            }
        }


        if( x0 != -1 ) {
            g.setColor(curColor);
            // draw current object
            switch(drawingMode)
            {
                case PEN:
                    g.drawPolyline(pen.xpoints, pen.ypoints, pen.npoints);
                    break;
                case LINE:
                    g.drawLine(x0, y0, x1, y1);
                    break;
                case RECT:
                    g.drawRect(Math.min(x0,x1), Math.min(y0,y1), Math.abs(x1-x0), Math.abs(y1-y0));
                    break;
                case FILLRECT:
                    g.fillRect(Math.min(x0,x1), Math.min(y0,y1), Math.abs(x1-x0), Math.abs(y1-y0));
                    break;
                case CIRCLE:
                	g.drawOval(x0, y0, x1, y1);
                	break;
            }
        }

    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawimages(g);
    }
}
