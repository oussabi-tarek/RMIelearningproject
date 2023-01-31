package elearningproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener{

    // drawing tools
    private final int PENCIL_TOOL = 0;
    private final int LINE_TOOL = 1;
    private final int RECTANGLE_TOOL = 2;
    private final int CIRCLE_TOOL = 3;
    private final int ERASER_TOOL = 4;

    // shapes
    private final int LINE = 1;
    private final int RECTANGLE = 2;
    private final int CIRCLE = 3;

    // graphics tool
    private Graphics2D graphics2D;

    // active tool
    int active_tool = 0;
  //  private int grouped;

    // define a stack for undo and redo and shapes
    Stack<Shape> shapes;
    Stack<Shape> removed;
    Stack<Shape> preview;
    String name;

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    private  ArrayList<String> users=new ArrayList<String>();
     // buffer image for drawing area and its graphics object for drawing
    private BufferedImage canvas;

    BasicStroke stroke = new BasicStroke((float) 1);

    int x1, y1, x2, y2;

    Color currentColor = Color.black;
    Color eraserColor = Color.white;
//    private Color fillColor;

    private boolean transparent = true;
    private boolean dragged = false;

    public Elearning getFrame() {
        return frame;
    }

    private Elearning frame;
    int paintPanelWidth, paintPanelHeight;
    int firstaccess = 0;
    int firstaccess1 = 0;

    PaintPanel() {
        setBackground(Color.WHITE);
        shapes = new Stack<>();
    }
    public  PaintPanel(Elearning frame, int paintPanelWidth, int paintPanelHeight,String name,String profname) {
        this.name=name;
        this.frame = frame;
        Server.pt.add(this);
        //grouped = 1;
        shapes = new Stack<>();
        removed = new Stack<>();
        preview = new Stack<>();

        setLayout(null);
        setBackground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.paintPanelWidth = paintPanelWidth;
        this.paintPanelHeight = paintPanelHeight;

        System.out.println("this is "+Server.pt.size()+" and: "+profname);
        for(PaintPanel p:Server.pt){
            System.out.println("this is "+p.name);
            if(p.name.equals("Prof "+profname)){
                System.out.println("eeeeeeeeeeeeeeee");
                this.shapes=p.shapes;
                this.removed=p.removed;
            }
        }

//  this just commented
//        if(Server.shapes.size() > 0 && firstaccess == 0){
//            System.out.println("shapes size: " + Server.shapes.size());
//            for(Shape s : Server.shapes){
//                shapes.push(s);
//            }
//            System.out.println("shapes pt  size: " + shapes.size());
////            for(Shape s: shapes){
////                Server.shapes.push(s);
////            }
//            firstaccess = 1;
//        }
//        if(Server.removed.size() > 0 && firstaccess1 == 0){
//            System.out.println("removed size: " + Server.removed.size());
//            for(Shape s : Server.removed){
//                removed.push(s);
//            }
//            firstaccess1 = 1;
//        }

        paintPanelSize();

//        if(Server.shapes.size() > 0){
//            System.out.println("shapes size: " + Server.shapes.size());
//            for(elearningproject.Shape s : Server.shapes){
//                shapes.push(s);
//            }
//        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        graphics2D = (Graphics2D) g;
        System.out.println("hello from paintComponent");


        // if canvas is null, create it with the size of the panel and fill it with white color (background)
        if (canvas == null) {
            System.out.println("canvas is null");
            canvas = new BufferedImage(paintPanelWidth, paintPanelHeight, BufferedImage.TYPE_INT_ARGB);
            graphics2D = canvas.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear();
        }
        // draw the canvas on the panel
        graphics2D.drawImage(canvas , 0 , 0 , null);


        // get
        Graphics2D g2d = (Graphics2D) g;


        for (Shape s : shapes) {
            g2d.setColor(s.getColor());
            g2d.setStroke(s.getStroke());

            if (s.getShape() == LINE) {
                System.out.println("drawing line");
                g2d.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            } else if (s.getShape() == RECTANGLE) {
                g2d.drawRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            } else if (s.getShape() == CIRCLE) {
                g2d.drawOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            } else if (s.getShape() == ERASER_TOOL) {
                g2d.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            }

        }

        if (preview.size() > 0) {
            Shape s = preview.pop();
            g2d.setColor(s.getColor());
            g2d.setStroke(s.getStroke());

            if (s.getShape() == RECTANGLE) {
                g2d.drawRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            } else if (s.getShape() == CIRCLE) {
                g2d.drawOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            } else if (s.getShape() == LINE) {
                g2d.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            }
        }


        }

    // mouse listener
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        getCoordinate(e);
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //grouped++;
        System.out.println("afin djjjjjjjjjjjjjjj");
        if (active_tool == RECTANGLE_TOOL && dragged) {
            System.out.println("afin dj");
            if (x1 < x2 && y1 < y2) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 2));
                        Server.shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 2));
                    }
                }

              shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 2));
            } else if (x2 < x1 && y1 < y2) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 2));
                        Server.shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 2));
                    }
                }
               shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 2));
            } else if (x1 < x2 && y2 < y1) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 2));
                        Server.shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 2));
                    }
                }
             shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 2));
            } else if (x2 < x1 && y2 < y1) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 2));
                        Server.shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 2));
                    }
                }
            shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 2));
            }
            repaint();
            for(PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.repaint();
                }
            }
        } else if (active_tool == CIRCLE_TOOL && dragged) {
            System.out.println("afin djj");
            if (x1 < x2 && y1 < y2) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 3));
                        Server.shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 3));
                    }
                }
            shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 3));
            } else if (x2 < x1 && y1 < y2) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 3));
                        Server.shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 3));
                    }
                }
               shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 3));
            } else if (x1 < x2 && y2 < y1) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 3));
                        Server.shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 3));
                    }
                }
             shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 3));
            } else if (x2 < x1 && y2 < y1) {
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addShape(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 3));
                        Server.shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 3));
                    }
                }
             shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 3));
            }
            repaint();
            for(PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.repaint();
                }
            }
        } else if (active_tool == LINE_TOOL && dragged) {
            System.out.println("afin djjj");
            for(PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.addShape(new Shape(x1, y1, x2, y2, currentColor, stroke, 1));
                    Server.shapes.push(new Shape(x1, y1, x2, y2, currentColor, stroke, 1));
                }
            }
          shapes.push(new Shape(x1, y1, x2, y2, currentColor, stroke, 1));
        }
        dragged = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public  void addShape(Shape shape){
        shapes.push(shape);
    }
    public void addPreview(Shape shape){
        preview.push(shape);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        getCoordinate(e);
        dragged = true;
        x2 = e.getX();
        y2 = e.getY();

        if (active_tool == PENCIL_TOOL) {
           // System.out.println("pencil ");
            shapes.push(new elearningproject.Shape(x1, y1, x2, y2, currentColor, stroke, 1));
            repaint();
            for(elearningproject.PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.addShape(new elearningproject.Shape(x1, y1, x2, y2, currentColor, stroke, 1));
                    m.repaint();
                }
            }
            Server.shapes.push(new elearningproject.Shape(x1, y1, x2, y2, currentColor, stroke, 1));
            x1 = x2;
            y1 = y2;
        } else if (active_tool == RECTANGLE_TOOL) {
            if (x1 < x2 && y1 < y2) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 2));
                    }
                }
               preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 2));
            } else if (x2 < x1 && y1 < y2) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 2));
                    }
                }
              preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 2));
            } else if (x1 < x2 && y2 < y1) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 2));
                    }
                }
                preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 2));
            } else if (x2 < x1 && y2 < y1) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 2));
                    }
                }
                preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 2));
            }
            repaint();
            for(elearningproject.PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.repaint();
                }
            }
        } else if (active_tool == CIRCLE_TOOL) {
            if (x1 < x2 && y1 < y2) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 3));
                    }
                }
                preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 3));
            } else if (x2 < x1 && y1 < y2) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 3));
                    }
                }
               preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 3));
            } else if (x1 < x2 && y2 < y1) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 3));
                    }
                }
               preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 3));
            } else if (x2 < x1 && y2 < y1) {
                for(elearningproject.PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        m.addPreview(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 3));
                    }
                }
              preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 3));
            }
            repaint();
            for(elearningproject.PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.repaint();
                }
            }
        } else if (active_tool == LINE_TOOL) {
            for(elearningproject.PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.addPreview(new Shape(x1, y1, x2, y2, currentColor, stroke, 1));
                }
            }
             preview.push(new Shape(x1, y1, x2, y2, currentColor, stroke, 1));
            repaint();
            for(elearningproject.PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    m.repaint();
                }
            }
        } else if (active_tool == ERASER_TOOL) {
            System.out.println("asat");
            shapes.push(new Shape(x1, y1, x2, y2, eraserColor, stroke, 4));
            repaint();
            for(PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                m.addShape(new Shape(x1, y1, x2, y2, eraserColor, stroke, 4));
                m.repaint();
                }
            }
            Server.shapes.push(new Shape(x1, y1, x2, y2, eraserColor, stroke, 4));
            x1 = x2;
            y1 = y2;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    private void paintPanelSize() {
//        frame.getCoordinateBar().getSizeText().setText(paintPanelWidth + " , " + paintPanelHeight + " px");
    }

    public void undo() {
        System.out.println("undo");
//        int i;
        if (shapes.size() > 0 && shapes.peek().getGroup() == 0) {
//            i=0;
            removed.push(shapes.pop());
            repaint();
            for (PaintPanel m : Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    System.out.println("shape pt before undo size : " + m.shapes.size());
                    System.out.println("shape Server size before undo:" + Server.shapes.size());
                    Shape shape = m.shapes.pop();
                    m.addRemoved(shape);
                    m.repaint();
                }
//                if(i==0) {
//                    Server.removed.push(shape);
//                    Server.shapes.pop();
//                }
//                i++;
                System.out.println("shape Server size after undo : "+Server.shapes.size());

            }

        } else if (shapes.size() > 0 && shapes.peek().getGroup() != 0) {
//            i=0;
            removed.push(shapes.pop());
            for (PaintPanel m : Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    Shape shape = m.shapes.pop();
                    m.addRemoved(shape);
                }
//                if(i==0) {
//                    Server.removed.push(shape);
//                    Server.shapes.pop();
//                }
                //i++;
            }

//            removed.push(lastRemoved);
            while (!shapes.isEmpty() && shapes.peek().getGroup() == shapes.pop().getGroup()) {
//                i=0;
                removed.push(shapes.pop());
                repaint();
                for (PaintPanel m : Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        Shape shape = m.shapes.pop();
                        m.addRemoved(shape);
//                    if(i==0) {
//                        Server.shapes.pop();
//                        Server.removed.push(shape);
//                    }
                        m.repaint();
                    }
                }
//                removed.push(shapes.pop());
//                repaint();
            }
        }
    }

    private void addRemoved(Shape shape) {
        removed.push(shape);
    }

    public void redo() {
        System.out.println("redo");
//        int i=0;
        if (removed.size() > 0 && removed.peek().getGroup() == 0) {
            shapes.push(removed.pop());
            repaint();
            for (PaintPanel m : Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    System.out.println("removed pt before redo size : " + m.removed.size());
                    Shape lastRemoved = m.removed.pop();
                    m.addShape(lastRemoved);
//                    if(i==0) {
//                        Server.shapes.push(lastRemoved);
//                        Server.removed.pop();
//                    }
//                    i=1;
                    System.out.println("removed pt after redo size : " + m.removed.size());
                    m.repaint();
                }
            }
//            shapes.push(removed.pop());
//            repaint();
        } else if (removed.size() > 0 && removed.peek().getGroup() != 0) {
//            i=0;
            shapes.push(removed.pop());
            for(PaintPanel m:Server.pt) {
                if(frame.dlm.contains(m.name)) {
                    Shape lastRemoved = m.removed.pop();
                    m.addShape(lastRemoved);
//                if(i==0) {
//                    Server.shapes.push(lastRemoved);
//                    Server.removed.pop();
//                }
//                i=1;
                }
            }
//            shapes.push(lastRemoved);
            while (!removed.isEmpty() && removed.peek().getGroup() == removed.pop().getGroup()) {
//                i=0;
                shapes.push(removed.pop());
                repaint();
                for(PaintPanel m:Server.pt) {
                    if(frame.dlm.contains(m.name)) {
                        Shape lastRemoved = m.removed.pop();
                        m.addShape(lastRemoved);
//                    if(i==0) {
//                        Server.shapes.push(lastRemoved);
//                        Server.removed.pop();
//                    }
//                    i=1;
                        m.repaint();
                    }
                }
//                shapes.push(removed.pop());
//                repaint();
            }
        }
    }

    public void setImage(BufferedImage image) {
        graphics2D.dispose();
        setPaintPanel(image.getWidth(), image.getHeight());
        canvas = new BufferedImage(paintPanelWidth, paintPanelHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.drawImage(image, 0, 0, this);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void setPaintPanel(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintPanelSize();
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        //clear();
    }


    public void clear(){
        for(PaintPanel m:Server.pt) {
            m.shapes.removeAllElements();
            m.preview.removeAllElements();
            m.removed.removeAllElements();
            m.repaint();
        }
    }



    private void getCoordinate(MouseEvent e) {
        String x = String.valueOf(e.getPoint().x);
        String y = String.valueOf(e.getPoint().y);

        //frame.getCoordinateBar().getCoordinateText().setText(x + " , " + y + " px");
    }
}
