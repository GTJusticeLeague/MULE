package edu.gatech.justiceleague.mule.model;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Emeke on 12/4/2015.
 */
public class Slots extends JFrame {
    private int total = GamePlay.getCurrentPlayer().getMoney();
    private int bet=100,maxWin;

    private String[] symbols={"Seven","Shamrock","Diamond","3Bar","Star","Bell",
            "Bar","Orange","Lemon"}; //slot symbols

    //amount winning
    int[] twoMatches={30,16,15,12,11,10,9,7,5};
    int[] threeMatches={60,32,30,24,22,20,18,14,10};

    private JPanel slotPanel=new JPanel (new GridLayout(1,3)); //holding 3 slots
    private JLabel[] slot=new JLabel[3]; //create three slots

    //money panel labels
    private JLabel amount=new JLabel("Total: $"+total);
    private JLabel betLabel=new JLabel("Bet: $"+bet);

    //game control panel buttons
    private JButton add50=new JButton("+50");
    private JButton spin=new JButton("Spin");
    private JButton maxbet=new JButton("Max");
    private JButton subtract50=new JButton("-50");
    private JButton getBet=new JButton("Enter Bet");

    public Slots() { //constructor
        maxWin=total;
        JPanel moneyPanel =new JPanel(new GridLayout(2,1));
        moneyPanel.setBorder(new TitledBorder("Money"));
        moneyPanel.add(amount);
        moneyPanel.add(betLabel);

        JPanel buttonPanel=new JPanel(new GridLayout(2,1));
        JPanel buttonTopPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,5,2));
        buttonPanel.setBorder(new TitledBorder("Button Panel"));

        //set colors
        add50.setBackground(Color.YELLOW);
        spin.setBackground(Color.ORANGE);
        maxbet.setBackground(Color.GREEN);

        //add buttons to the panel
        buttonTopPanel.add(add50);
        buttonTopPanel.add(spin);
        buttonTopPanel.add(maxbet);

        JPanel buttonBottomPanel=new JPanel();
        buttonBottomPanel.add(subtract50);
        buttonBottomPanel.add(getBet);

        buttonPanel.add(buttonTopPanel,BorderLayout.NORTH);
        buttonPanel.add(buttonBottomPanel,BorderLayout.SOUTH);

        //set slots their first look
        slot[0]=new JLabel(new ImageIcon("/img/left.png"));
        slot[1]=new JLabel(new ImageIcon("/img/center.png"));
        slot[2]=new JLabel(new ImageIcon("/img/right.png"));
        for (int i = 0; i < 3; i++) {
            slotPanel.add(slot[i]);
        }
        //add listeners to bet buttons
        add50.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                bet+=50;
                if (bet>total) bet=total; //else skip
                betLabel.setText("Bet: $"+bet);
            }
        } );

        maxbet.addActionListener (e -> {
            bet=total;
            betLabel.setText("Bet: $"+bet);
        });

        subtract50.addActionListener(e -> {
            bet-=50;
            if (bet<0) bet=0; //else skip
            betLabel.setText("Bet: $"+bet);
        });

        getBet.addActionListener(e -> {
            String input;
            int betInput;
            input=JOptionPane.showInputDialog(null,
                    "Enter the amount of money you want to bet","Enter Bet",
                    JOptionPane.QUESTION_MESSAGE);
            betInput=Integer.parseInt(input);
            if (betInput>total) bet=total;
            else bet=betInput;
            betLabel.setText("Bet: $"+bet);
        });

        spin.addActionListener(e -> {
            String[] rand=new String[3];
            int addTotal;
            //generate 3 new slots
            for(int i=0;i<3;i++) {
                int choice;
                double random=Math.random();
                random*=8;
                choice=(int) random;
                rand[i]=symbols[choice];
                slot[i].setIcon(new ImageIcon("/img/"+symbols[choice]+".jpg"));
            }

            //check for matches
            //three matches
            if (rand[0]==rand[1] && rand[1]==rand[2]) {
                int index=0;
                for (int i=0;i<symbols.length;i++)
                    if (rand[0]==symbols[i]) index=i;
                addTotal= bet * threeMatches[index] / 20;
                JOptionPane.showMessageDialog(null,"Your bet: $"+bet+"\nYou win: $"+addTotal,
                        "Three Matches",JOptionPane.INFORMATION_MESSAGE);
                GamePlay.getCurrentPlayer().setMoney((total += addTotal));
                amount.setText("Total: $"+total);
                if (total>maxWin) maxWin=total;
            }

            //two matches
            else if (rand[0]==rand[1] || rand[1]==rand[2] || rand[2]==rand[0]) {
                int match,index=0;
                if (rand[0]==rand[1] || rand[0]==rand[2]) match=0;
                else match=1;

                //find the index
                for (int i=0;i<symbols.length;i++)
                    if (rand[0]==symbols[i]) index=i;
                addTotal=bet * twoMatches[index] / 10;
                JOptionPane.showMessageDialog(null,"Your bet: $"+bet+"\nYou win: $"+addTotal,
                        "Two Matches",JOptionPane.INFORMATION_MESSAGE);
                GamePlay.getCurrentPlayer().setMoney((total += addTotal));
                amount.setText("Total: $"+total);
                if (total>maxWin) maxWin=total;
            }

            else {
                GamePlay.getCurrentPlayer().setMoney((total -= bet));
                amount.setText("Total: $"+total);
                if (total>maxWin) maxWin=total;
                if (bet>total) {
                    bet=total;
                    betLabel.setText("Bet: $"+bet);
                }
                if (total==0) {
                    bet=0;
                    betLabel.setText("Bet: $"+bet);
                    JOptionPane.showMessageDialog(null,"You lost! Your maximum is $"+maxWin,
                            "End of Game",JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        });

        JPanel bottomPanel=new JPanel(new BorderLayout());
        bottomPanel.add(moneyPanel,BorderLayout.WEST);
        bottomPanel.add(buttonPanel,BorderLayout.EAST);

        JPanel all=new JPanel(new GridLayout(2,1));
        all.add(slotPanel);
        all.add(bottomPanel);

        add(all);
    }

    public static void main(String[] args) {
        JFrame frame=new Slots();
        frame.setTitle("Slots");
        frame.setLocationRelativeTo(null);
        frame.setSize(300,210);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}