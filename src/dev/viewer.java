package dev;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.*;

public class viewer {
    JFrame v;

    int locationX=0,locationY=0;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int windowX = (int) (screenSize.getWidth()*0.6),windowY = (int) (screenSize.getWidth()*0.6*0.5625);


    BufferedImage blackground;

    static Explainer explainer;

    class Shower extends JPanel{
        BufferedImage BVG,AVG_L,AVG_M,AVG_R;
        String music,sound;
        BufferedImage cover;
        JLabel WORD;
        JLabel NAME;
        JLabel SND;
        public Shower(){
            WORD = new JLabel();

            NAME = new JLabel();
            SND = new JLabel();

        }

        public void paint(Graphics g){
            super.paint(g);
            remove(NAME);
            remove(WORD);
            g.drawImage(BVG,0,0,null);
            g.drawImage(AVG_L,(int)(windowX*(-0.068229)),(int)(windowY*(0.12)),null);
            g.drawImage(AVG_M,(int)(windowX*(0.127083)),(int)(windowY*(0.12)),null);
            g.drawImage(AVG_R,(int)(windowX*(0.321354)),(int)(windowY*(0.12)),null);
            g.drawImage(cover,0,0,null);

            WORD.setBounds((int)(windowX*0.2984),(int)(windowY*0.863),(int)(windowX*0.55),(int)(windowY*0.105));
            WORD.setOpaque(false);
            WORD.setFont(new Font("黑体",Font.PLAIN,(int)(windowY*0.031)));
            WORD.setForeground(Color.white);
            WORD.setVerticalAlignment(SwingConstants.TOP);
            WORD.setHorizontalAlignment(SwingConstants.LEFT);

            NAME.setBounds((int)(windowX*0.0205),(int)(windowY*0.852),(int)(windowX*0.2387125),(int)(windowY*0.07));
            NAME.setOpaque(false);
            NAME.setFont(new Font("黑体",Font.PLAIN,(int)(windowY*0.036)));
            NAME.setForeground(new Color(160,160,160));
            NAME.setHorizontalAlignment(SwingConstants.RIGHT);

            SND.setBounds(10,60,500,150);
            SND.setFont(new Font("宋体",Font.BOLD,25));
            SND.setForeground(Color.white);
            SND.setVerticalAlignment(SwingConstants.TOP);
            SND.setHorizontalAlignment(SwingConstants.LEFT);



            add(NAME);
            add(WORD);
            add(SND);
            super.paintChildren(g);
        }
    }


    public viewer(){

        v = new JFrame();
        v.setSize(windowX,windowY);
        v.setResizable(false);
        v.setUndecorated(true);
        v.setVisible(true);

        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                locationX = e.getX();
                locationY = e.getY();
            }
        });
        v.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = v.getLocation();
                v.setLocation(p.x + e.getX() - locationX, p.y + e.getY() - locationY);
            }
        });

        JPanel p = new JPanel(null);
        p.setSize(520,50);
        p.setOpaque(false);

        Shower shower = new Shower();
        shower.setBackground(Color.BLACK);
        shower.setBounds(0,0,windowX,windowY);

/*        Words words = new Words();
        words.setBounds(0,0,windowX,windowY);
        words.setOpaque(false);*/



        JSlider Sizectrl = new JSlider(30,130,60);
        Sizectrl.setBounds(10,0,500,50);
        Sizectrl.setOpaque(false);
        Sizectrl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int size = Sizectrl.getValue();
                windowX= (int) (screenSize.getWidth()*size*0.01);
                windowY= (int) (screenSize.getWidth()*size*0.01*0.5625);
                v.setSize(windowX,windowY);
            }
        });
        p.add(Sizectrl);

        v.add(p);
        v.add(shower);
 //       v.add(words);

        blackground = new BufferedImage(windowX,windowY,BufferedImage.TYPE_INT_ARGB);
        Graphics g = blackground.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, windowX,windowY);


        show(textpad.TextList,shower);

        explainer = new Explainer();
        explainer.addExplainEventListener(new ExplainEventListener() {
            @Override
            public void showMessage(ExplainEvent e) {
                String[][] message = e.getMessages();
                show(message,shower);
            }
        });

        /*while(true){
            if (textpad.showflag){
                show(textpad.TextList,shower);
                textpad.showflag=false;
            }
        }*/

    }
    static BufferedImage resizeImage(File origin,int x,int y){
        try {
            BufferedImage handler = ImageIO.read(origin);
            Image scaledInstance = handler.getScaledInstance(x, y, Image.SCALE_DEFAULT);
            BufferedImage Imagefinal = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
            Graphics g = Imagefinal.getGraphics();
            boolean b = g.drawImage(scaledInstance, 0, 0, null);
            g.dispose();
            return Imagefinal;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    void show(String[][] s,Shower shower){
        shower.removeAll();
        shower.setBackground(Color.BLACK);
        String musicplay,soundplay;
        if(s[0][0]!=null){
            File BVGorigin = new File("Users_Data\\source\\BGP\\"+s[0][0]+".png");
            if(BVGorigin.exists()){
                shower.BVG=resizeImage(BVGorigin,windowX,windowY);
            }else{
                shower.BVG = blackground;
            }
        }else{
            shower.BVG = blackground;
        }
        if(s[1][0]!=null){
            musicplay="正在播放的音乐为："+s[1][0];
        }else {
            musicplay="当前没有背景音乐。";
        }
        if(s[2][0]!=null){
            soundplay="音效："+s[2][0];
        }else {
            soundplay="";
        }
        if(s[3][0]!=null){
            File AVGLorigin = new File("Users_Data\\source\\Chara\\"+s[3][0]+".png");
            if(AVGLorigin.exists()){
                shower.AVG_L=resizeImage(AVGLorigin,(int)(windowX*0.74635),(int)(windowX*0.74635));
            }else{
                shower.AVG_L=null;
            }
        }else{
            shower.AVG_L=null;
        }
        if(s[4][0]!=null){
            File AVGMorigin = new File("Users_Data\\source\\Chara\\"+s[4][0]+".png");
            if(AVGMorigin.exists()){
                shower.AVG_M=resizeImage(AVGMorigin,(int)(windowX*0.74635),(int)(windowX*0.74635));
            }else{
                shower.AVG_M=null;
            }
        }else{
            shower.AVG_M=null;
        }
        if(s[5][0]!=null){
            File AVGRorigin = new File("Users_Data\\source\\Chara\\"+s[5][0]+".png");
            if(AVGRorigin.exists()){
                shower.AVG_R=resizeImage(AVGRorigin,(int)(windowX*0.74635),(int)(windowX*0.74635));
            }else{
                shower.AVG_R=null;
            }
        }else{
            shower.AVG_R=null;
        }
        if(s[6][0]!=null){
            if(s[6][0].equals("on")){
                File CVRorigin = new File("Users_Data\\source\\BaseUI\\Frame\\arknightsUIBottom.png");
                shower.cover=resizeImage(CVRorigin,windowX,windowY);
            }else{
                shower.cover=null;
            }
        }else{
            shower.cover=null;
        }
        if(s[8][0]==null){
            s[8][0] = "";
        }



        shower.NAME.setText(s[7][0]);
        shower.WORD.setText("<html>"+s[8][0]+"</html>");
        shower.SND.setText("<html><body>"+musicplay+"<br>"+soundplay+"</body></html>");

        shower.repaint();
 //       words.repaint();


 //       System.out.println(shower.NAME.getText());

    }
    public static void main() {
        viewer V = new viewer();

    }
}
