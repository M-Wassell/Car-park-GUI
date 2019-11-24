package thecarparkgui;

import com.sun.istack.internal.logging.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.Writer;
import java.io.Reader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sun.util.logging.PlatformLogger;

/**
 * 
 * @author v8254888
 */
public class Display extends JPanel implements MouseListener, ActionListener
{
    
    private JButton save, clearAll;
    private JComboBox dropBox;
    private JPanel north, south, west, central, centralE;
    private JLabel northWest,tTotal, jTotal;
    private JTextField reg, hoursStayed, carLength,lorryWeight, daysStayed, passengerNumber;
    private ImageIcon parking, car, lorry, coach, campervan;
    private JLabel[] carLabels, lorryLabels, coachLabels;
    private JCheckBox isDisabled, isTourist; 
    private Vehicle[] cars, lorrys, coaches;
    
    JComponent[] carDialog, lorryDialog, coachDialog, displayTheTotal;
    String[] x = {"Add Car", "Add Lorry", "Add Coach", "Load", "Current Total", "Days Total"};
    /**
     * the display
     */
    Display()
    {
        Properties props = new Properties();
        props.setProperty("Backcolor", "White");
        props.setProperty("Backcolor", "BLACK");
        props.setProperty("Fontsize", "12");
        
        
        carDialog = new JComponent[]
        {
            new JLabel ("reg"),
            reg = new JTextField(),
            new JLabel("carLength"),
            carLength = new JTextField(),
            new JLabel ("hoursStayed"),
            hoursStayed = new JTextField(),
            
            isDisabled = new JCheckBox("Are you a blue badge holder")
            
        };
        
        lorryDialog = new JComponent[]
        {
            new JLabel ("reg"),
            reg = new JTextField(),
            new JLabel ("lorryWeight"),
            lorryWeight = new JTextField(),
            new JLabel ("daysStayed"),
            daysStayed = new JTextField(),
            
        };
        
        coachDialog = new JComponent[]
        {
            new JLabel ("reg"),
            reg = new JTextField(),
            new JLabel ("passengerNumber"),
            passengerNumber = new JTextField(),
            
            isTourist = new JCheckBox("Are you a blue badge holder")
        };
        
        displayTheTotal = new JComponent[]
        {
            jTotal = new JLabel(),
        };
            
    
        car = new ImageIcon("car.png");
        lorry = new ImageIcon("campervan.png");
        
        carLabels = new JLabel[16]; 
        
        lorryLabels = new JLabel[8];
        
        lorrys = new Vehicle[16];
        
        Dimension size = new Dimension(1000, 600);

        north = new JPanel();
        north.setLayout(new FlowLayout());
        Dimension a = new Dimension(1300, 120);
        north.setPreferredSize(a);
        north.setVisible(true);
        north.setOpaque(true);
        north.setBackground(Color.LIGHT_GRAY);

        northWest = new JLabel("          Teesside Car Parking");
        Font nwf = new Font("Ariel", Font.PLAIN, 70);
        Dimension b = new Dimension (1300, 120);
        northWest.setPreferredSize(b);
        northWest.setVisible(true);
        northWest.setOpaque(true);
        northWest.setBackground(Color.white);
        northWest.setFont(nwf);
        
        parking = new ImageIcon("image/carPark.jpg");
        northWest.setIcon(parking);
        north.add(northWest);
       
        west = new JPanel();
        
        west.setLayout(new FlowLayout());
        Dimension c = new Dimension(200, 700);
        west.setPreferredSize(c);
        west.setVisible(true);
        west.setOpaque(true);
        west.setBackground(Color.white);
        
        dropBox = new JComboBox(x);
        dropBox.addActionListener(this);
        west.add(dropBox);
        
        central = new JPanel();
        central.setLayout(new GridLayout(8, 1, 5, 5));
        Dimension d = new Dimension(530, 700);
        central.setPreferredSize(d);
        central.setVisible(true);
        central.setOpaque(true);
        central.setBackground(Color.LIGHT_GRAY);
        
                
        
        for(int i = 0; i < lorryLabels.length; i++)
        {
            lorryLabels[i] = new JLabel();
            lorryLabels[i].setBackground(Color.white);
            lorryLabels[i].setOpaque(true);
            lorryLabels[i].setVisible(true);
            lorryLabels[i].addMouseListener(this);
            lorryLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
            
            central.add(lorryLabels[i]);
        }
        
        centralE = new JPanel();
        centralE.setLayout(new GridLayout(4, 3, 5, 5));
        centralE.setPreferredSize(d);
        centralE.setVisible(true);
        centralE.setOpaque(true);
        centralE.setBackground(Color.white);

        for(int i = 0; i < carLabels.length; i++)
        {
            carLabels[i] = new JLabel();            
            carLabels[i].setBackground(Color.white);
            carLabels[i].setOpaque(true);
            carLabels[i].setVisible(true);        
            carLabels[i].addMouseListener(this);
            carLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
            centralE.add(carLabels[i]);    
        }
        
        save = new JButton("Save");
        save.addActionListener(this);
        
        clearAll = new JButton("Clear All");
        clearAll.addActionListener(this);
        
        south = new JPanel();
        south.setLayout(new BorderLayout());
        south.setPreferredSize(a);
        south.setVisible(true);
        south.setOpaque(true);
        south.setBackground(Color.white);
        south.add(clearAll, BorderLayout.WEST);
        south.add(save, BorderLayout.EAST);
           
        this.add(north);
        this.add(west);
        this.add(central);
        this.add(centralE);
        this.add(south);
        
        this.setPreferredSize(size);
        this.setBackground(Color.LIGHT_GRAY);
    }   

    @Override
    public void mouseClicked(MouseEvent e) //Button 1 = Left Click. Button 2 = Middle Click. Button 3 = Right Click.
    {          
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                System.out.println("Left Button");
                break;
            case MouseEvent.BUTTON2:
                System.out.println("Middle Button");
                break;
            case MouseEvent.BUTTON3:
                System.out.println("Right Button");
                break;             
            default:
                break;
        }
    }
    @Override
    public void mousePressed(MouseEvent e) 
    {
      
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
        if(e.getSource()==clearAll)
        {
            for(int i = 0; i < carLabels.length; i++)
            {
                carLabels[i].setBackground(Color.red);
                carLabels[i].setIcon(null);
                cars[i] = null;
            }
       
            for(int i = 0; i < coachLabels.length; i++)
            {
                coachLabels[i].setBackground(Color.red);
                coachLabels[i].setIcon(null);
                coaches[i] = null;
                lorrys[i] = null;
            }

        }
        String item = String.valueOf(dropBox.getSelectedItem());
        System.out.println("ActionPerformed");
        if(item.matches("Add Car"))//addCar
        {
            System.out.println("Car");
            carV();
        }
        else if(item.matches("Add Coach"))
        {
            System.out.println("Coach");
            coachV();
        }
        else if(item.matches("Load"))
        {
            System.out.println("Load");
            loadV();
        }
        else if(item.matches("Current Total"))
        {
            System.out.println("C Total");
            currentTotalV();
        }
        else if(item.matches("Days Total"))
        {
            System.out.println("D Total");
            daysTotalV();
        }
        else if(item.matches("Add Lorry"))
        {
            System.out.println("Lorry");
            LorryV();
        } 
    }
    
    public void carV()
    {
        int inputs = JOptionPane.showConfirmDialog(null, carDialog, "Add Car", JOptionPane.PLAIN_MESSAGE);
        if(inputs == JOptionPane.OK_OPTION)   
        {
            int i = 0;
            while(carLabels[i].getBackground()== Color.GREEN)
            {
                i++;
            }
            boolean valid = false;
            
            while(!valid)
            {
                if(reg.getText().matches("[A-Za-z]{2}[0-9]{2}[A-Za-z]{3}"))
                {
                    valid = true;
                }
            }
            
            boolean length = false;
            
            while(!length)
            {
                if(carLength.getText().matches("[0-13]"))
                {
                    valid = true;
                }
            }
            
            boolean stay = false;
            
            while(!stay)
            {
                if(hoursStayed.getText().matches("[1-24]"))
                {
                    valid = true;
                }
            }
            
            if(Integer.parseInt(carLength.getText())>6)
            {
                carLabels[i].setIcon(campervan);
                Car addedCar;
             //   addedCar = new Car(Integer.parseInt(carLength.getText()),reg.getText(),campervan, Integer.parseInt(hoursStayed.getText()),isDisabled.isSelected());
             //   cars [i] = addedCar;
                carLabels[i].setBackground(Color.BLACK);
            }
            else
            {
                carLabels[i].setIcon(car);
                Car addedCar;
             //   addedCar = new Car(Integer.parseInt(carLength.getText()),reg.getText(),car, Integer.parseInt(hoursStayed.getText()),isDisabled.isSelected());
            //    cars [i] = addedCar;
                carLabels[i].setBackground(Color.BLACK);
            }
        }  
    }    
    
    public void LorryV()
    {
        int inputs = JOptionPane.showConfirmDialog(null, lorryDialog, "add lorry", JOptionPane.PLAIN_MESSAGE);
        if(inputs == JOptionPane.OK_OPTION)   
        {
            int i = 0;
            while(lorryLabels[i].getBackground()== Color.GRAY)
            {
                i++;
            }
           boolean valid = false;
            
            while(!valid)
            {
                if(reg.getText().matches("[A-Za-z]{2}[0-9]{2}[A-Za-z]{3}"))
                {
                    valid = true;
                }    
            }
            
            boolean weight = false;
            while(!weight)
            {
                if(lorryWeight.getText().matches("[1-35]"))
                {
                    weight = true;
                }
            }
            
            boolean days = false;
            while(!days)
            {
                if(daysStayed.getText().matches("[1-365]"))
                {
                    days = true;
                }
            }
            
            if(Integer.parseInt(lorryWeight.getText())>0)
            {
                lorryLabels[i].setIcon(lorry);
                Lorry addedLorry;
              //  addedLorry = new Lorry(Integer.parseInt(lorryWeight.getText()),reg.getText(),lorry, Integer.parseInt(daysStayed.getText()));
              //  lorrys [i] = addedLorry;
                lorryLabels[i].setBackground(Color.GRAY);
            }
            else
            {
                lorryLabels[i].setIcon(lorry);
                Lorry addedLorry;
               // addedLorry = new Lorry(Integer.parseInt(lorryWeight.getText()),reg.getText(),lorry, Integer.parseInt(daysStayed.getText()));
               // lorrys [i] = addedLorry;
                lorryLabels[i].setBackground(Color.GRAY);
            }
        }
    }
        
    public void coachV()
    {
        int inputs = JOptionPane.showConfirmDialog(null, coachDialog, "add coach", JOptionPane.PLAIN_MESSAGE);
        if(inputs == JOptionPane.OK_OPTION)   
        {
            int i = 0;
            while(coachLabels[i].getBackground()== Color.GREEN)
            {
                i++;
            }
            boolean valid = false;
            
            while(!valid)
            {
                if(reg.getText().matches("[A-Za-z]{2}[0-9]{2}[A-Za-z]{3}"))
                {
                    valid = true;
                }
            
            boolean pNumber = false;
            while(!pNumber)
            {
                if(passengerNumber.getText().matches("[1-80]"))
                {
                    pNumber = true;
                }
            }
            
            }
            if(Integer.parseInt(passengerNumber.getText())>0)
            {
                coachLabels[i].setIcon(coach);
                Coach addedCoach;
            //    addedCoach = new Coach(Integer.parseInt(passengerNumber.getText()),(isTourist.isSelected()),reg.getText(),coach);
              //  coaches [i] = addedCoach;
                coachLabels[i].setBackground(Color.GREEN);//Coach(int passAmount,boolean isOperator, String reg, ImageIcon img)
            }
            else
            {
                carLabels[i].setIcon(coach);
                Coach addedCoach;
              //  addedCoach = new Coach(Integer.parseInt(passengerNumber.getText()),(isTourist.isSelected()),reg.getText(),coach);
               // coaches [i] = addedCoach;
                coachLabels[i].setBackground(Color.GREEN);
            }
        }
    }  
    
    public void currentTotalV()
    {
        double amount = 0;
        for(int i = 0; i < carLabels.length; i++)
        {
            if(cars[i] != null)
            {
                amount = amount + cars[i].getAmount();
            }
        }
        
        for(int i = 0; i < coachLabels.length; i++)
        {
            if(lorrys[i] != null)
            {
                amount = amount + lorrys[i].getAmount();
            }
            if(coaches[i] != null);
            {
                amount = amount + coaches[i].getAmount();
            }
        }
        tTotal.setText("The Total:" + Double.toString(amount));
        JOptionPane.showConfirmDialog(null, displayTheTotal, "current total", JOptionPane.PLAIN_MESSAGE);  
        
    } 

    public void loadV()
    {
        if(e.getSource == load)
        {
            try
            {
                FileInputStream savedFiles = new FileInputStream("savefile.txt");
                
                ObjectInputStream object = new ObjectInputStream(savedFiles);
                
                cars = (Car[])object.readObject();
                lorrys = (Lorry[])object.readObject();
                coaches = (Coach[])object.readObject();
                
                
                object.close();
                savedFiles.close();
            }
            catch(IOException ex)
                {
                    System.out.println("There is an Error when loading this file"); 
                }
            catch(ClassNotFoundException ex)
            {
                System.out.println("Unresoved class");
            }
            
            for(int i = 0; 1 < carLabels.length; i++)
            {
                if(cars[i] != null)
                {
                    if(cars[i].getlength >= 6)
                    {
                        carLabels[i].setIcon(campervan);
                    }
                    else
                    {
                        carLabels[i].setIcon(car);
                    }
                    carLabels[i].setBackground(Color.red);
                }
            }
            
            for(int i = 0; i < coachLabels.length; i++)
            {
                if(lorrys[i] != null)
                {
                    coachLabels[i].setIcon(lorry);
                    coachLabels[i].setBackground(Color.BLACK);
                }
                if(coaches[i] != null)
                {
                    lorryLabels[i].setIcon(coach);
                    lorryLabels[i].setBackground(Color.ORANGE);
                }
            }
            try
            {
                PrintWriter theWriter = new PrintWriter("saveFile.txt");
                theWriter.print("");
                theWriter.close();
            }
            catch(FileNotFoundException ex)
            {
                Logger.getLogger(TheCarParkGUI.class.getSuperclass()).log(Level.SEVERE,null, ex);
            }
        }
    }
    
    public void daysTotalV()
    {
        double amount = 0;
        for(int i = 0; i< carLabels.length; i++)
        {
            if(cars[i] != null)
            {
                amount = amount + cars[i].getDailyCharge();
                
            }
        }
        
        for(int i = 0; i <coachLabels.length; i++)
        {
            if(lorrys[i] != null)
            {
                amount = amount + lorrys[i].getDailyCharge();
            }
            if(coaches[i] != null);
            {
                amount = amount + coaches[i].getDailyCharge();
            }
        }
            
  
    }

}
