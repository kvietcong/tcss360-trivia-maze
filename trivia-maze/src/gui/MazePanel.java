package gui;

import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {

    private final ImageIcon emptyroom;
    private final ImageIcon horizontalline;
    private final ImageIcon verticalline;
    private final ImageIcon flag;
    private final ImageIcon start;

    private JLabel name;
    private JLabel room1Col1;
    private JLabel room2Col2;
    private JLabel room3Col3;
    private JLabel room4Col4;

    private JLabel room5Col1;
    private JLabel room6Col2;
    private JLabel room7Col3;
    private JLabel room8Col4;

    private JLabel room9Col1;
    private JLabel room10Col2;
    private JLabel room11Col3;
    private JLabel room12Col4;

    private JLabel room13Col1;
    private JLabel room14Col2;
    private JLabel room15Col3;
    private JLabel room16Col4;

    private JLabel horizontalpath1;
    private JLabel horizontalpath2;
    private JLabel horizontalpath3;
    private JLabel horizontalpath4;
    private JLabel horizontalpath5;
    private JLabel horizontalpath6;
    private JLabel horizontalpath7;
    private JLabel horizontalpath8;
    private JLabel horizontalpath9;
    private JLabel horizontalpath10;
    private JLabel horizontalpath11;
    private JLabel horizontalpath12;

    private JLabel verticalpath1;
    private JLabel verticalpath2;
    private JLabel verticalpath3;
    private JLabel verticalpath4;





    //JLabel[][] rooms;
    public MazePanel() {
        setLayout(null);

        emptyroom = new ImageIcon(MazePanel.class.getResource("/images/emptyroom.PNG"));
        horizontalline = new ImageIcon(MazePanel.class.getResource("/images/Lines.png"));
        verticalline = new ImageIcon(MazePanel.class.getResource("/images/Vertical.png"));
        flag = new ImageIcon(MazePanel.class.getResource("/images/Flag.png"));
        start = new ImageIcon(MazePanel.class.getResource("/images/Start.png"));
        intializeMaze();

        setBackground(Color.black);
        add();
        }
    private void intializeMaze(){

        room1Col1 = new JLabel();
        room1Col1.setIcon(start);
        room1Col1.setBounds(20,10,100,100);

        horizontalpath1 = new JLabel();
        horizontalpath1.setIcon(horizontalline);
        horizontalpath1.setBounds(20,10,70,100);

        verticalpath1 = new JLabel();
        verticalpath1.setIcon(verticalline);
        verticalpath1.setBounds(10,70,70,160);

        room2Col2 = new JLabel();
        room2Col2.setIcon(emptyroom);
        room2Col2.setBounds(80,10,100,100);

        horizontalpath2 = new JLabel();
        horizontalpath2.setIcon(horizontalline);
        horizontalpath2.setBounds(80,10,70,100);

        room3Col3 = new JLabel();
        room3Col3.setIcon(emptyroom);
        room3Col3.setBounds(140,10,100,100);

        horizontalpath3 = new JLabel();
        horizontalpath3.setIcon(horizontalline);
        horizontalpath3.setBounds(140,10,70,100);

        room4Col4 = new JLabel();
        room4Col4.setIcon(emptyroom);
        room4Col4.setBounds(200,10,100,100);


        verticalpath2 = new JLabel();
        verticalpath2.setIcon(verticalline);
        verticalpath2.setBounds(70,70,70,160);

        room5Col1 = new JLabel();
        room5Col1.setIcon(emptyroom);
        room5Col1.setBounds(200,70,100,100);

        horizontalpath4 = new JLabel();
        horizontalpath4.setIcon(horizontalline);
        horizontalpath4.setBounds(140,70,70,100);

        room6Col2 = new JLabel();
        room6Col2.setIcon(emptyroom);
        room6Col2.setBounds(140,70,100,100);

        horizontalpath5 = new JLabel();
        horizontalpath5.setIcon(horizontalline);
        horizontalpath5.setBounds(80,70,70,100);

        room7Col3 = new JLabel();
        room7Col3.setIcon(emptyroom);
        room7Col3.setBounds(80,70,100,100);

        horizontalpath6 = new JLabel();
        horizontalpath6.setIcon(horizontalline);
        horizontalpath6.setBounds(20,70,70,100);

        room8Col4 = new JLabel();
        room8Col4.setIcon(emptyroom);
        room8Col4.setBounds(20,70,100,100);

        verticalpath3 = new JLabel();
        verticalpath3.setIcon(verticalline);
        verticalpath3.setBounds(130,70,70,160);

        room9Col1 = new JLabel();
        room9Col1.setIcon(emptyroom);
        room9Col1.setBounds(20,130,100,100);

        horizontalpath7 = new JLabel();
        horizontalpath7.setIcon(horizontalline);
        horizontalpath7.setBounds(140,130,70,100);

        room10Col2 = new JLabel();
        room10Col2.setIcon(emptyroom);
        room10Col2.setBounds(80,130,100,100);

        horizontalpath8 = new JLabel();
        horizontalpath8.setIcon(horizontalline);
        horizontalpath8.setBounds(80,130,70,100);

        room11Col3 = new JLabel();
        room11Col3.setIcon(emptyroom);
        room11Col3.setBounds(140,130,100,100);

        horizontalpath9 = new JLabel();
        horizontalpath9.setIcon(horizontalline);
        horizontalpath9.setBounds(20,130,70,100);

        room12Col4 = new JLabel();
        room12Col4.setIcon(emptyroom);
        room12Col4.setBounds(200,130,100,100);

        verticalpath4 = new JLabel();
        verticalpath4.setIcon(verticalline);
        verticalpath4.setBounds(190,70,70,160);

        room13Col1 = new JLabel();
        room13Col1.setIcon(flag);
        room13Col1.setBounds(200,190,100,100);

        horizontalpath10 = new JLabel();
        horizontalpath10.setIcon(horizontalline);
        horizontalpath10.setBounds(20,190,70,100);

        room14Col2 = new JLabel();
        room14Col2.setIcon(emptyroom);
        room14Col2.setBounds(140,190,100,100);

        horizontalpath11 = new JLabel();
        horizontalpath11.setIcon(horizontalline);
        horizontalpath11.setBounds(80,190,70,100);

        room15Col3 = new JLabel();
        room15Col3.setIcon(emptyroom);
        room15Col3.setBounds(80,190,100,100);

        horizontalpath12 = new JLabel();
        horizontalpath12.setIcon(horizontalline);
        horizontalpath12.setBounds(140,190,70,100);

        room16Col4 = new JLabel();
        room16Col4.setIcon(emptyroom);
        room16Col4.setBounds(20,190,100,100);

        name = new JLabel("MAZE");
        name.setFont(new Font("Pacifico", Font.PLAIN, 20));
        name.setForeground(Color.RED);
        name.setBounds(50,20,70,20);
        name.setVisible(true);

    }
    private void add(){
        add(room1Col1);
        add(room2Col2);
        add(room3Col3);
        add(room4Col4);

        add(room5Col1);
        add(room6Col2);
        add(room7Col3);
        add(room8Col4);

        add(room9Col1);
        add(room10Col2);
        add(room11Col3);
        add(room12Col4);

        add(room13Col1);
        add(room14Col2);
        add(room15Col3);
        add(room16Col4);

        add(horizontalpath1);
        add(horizontalpath2);
        add(horizontalpath3);
        add(horizontalpath4);
        add(horizontalpath5);
        add(horizontalpath6);
        add(horizontalpath7);
        add(horizontalpath8);
        add(horizontalpath9);
        add(horizontalpath10);
        add(horizontalpath11);
        add(horizontalpath12);

        add(verticalpath1);
        add(verticalpath2);
        add(verticalpath3);
        add(verticalpath4);
        add(name);

    }
    }//end of class


