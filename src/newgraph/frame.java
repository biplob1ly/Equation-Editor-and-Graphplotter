
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newgraph;

/**
 *
 * @author Biplob Biwas
 */
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
//import javax.swing.JOptionPane;
public class frame extends JFrame{
    private JPanel buttonpanel;
    private JPanel top;
    private JButton[] buttons;
    private static JTextField eqntext;
    private static JTextField xrange;
    private static JTextField yrange;
    private static int pointcount;
    private static JComboBox box;
    private static int nx=8,ny=15,number=0;//nx=4,ny=25
    public String first[]=new String[4];
    public String next[]=new String[4];
    public int centrex,centrey,radius;
    public static int [] x = new int[4000];             //abssica variable
    public static int [] y = new int[4000];             //ordinate variable
    private static final String[] names={"gones","circle","ellipse","hyperbola","parabola"};
    
    drawgraph graphpaper;
    public frame()
    {
        super("graph plotter");
        top=new JPanel();
        top.setLayout(new FlowLayout());
        JLabel equation=new JLabel("EQUATION");
        equation.setToolTipText("Write equation here");
        top.add(equation);
        eqntext=new JTextField(20);
        top.add(eqntext);
        JLabel unit_x=new JLabel("UNIT OF X");
        unit_x.setToolTipText("write the unit for a side of the smallest square");
        top.add(unit_x);
        xrange=new JTextField("1",5);
        top.add(xrange);
        JLabel unit_y=new JLabel("UNIT OF Y");
        unit_y.setToolTipText("write the unit for a side of the smallest square");
        top.add(unit_y);
        yrange=new JTextField("1",5);
        top.add(yrange);
        box=new JComboBox(names);
        box.setMaximumRowCount(3);
        top.add(box);
        
        
        buttons=new JButton[7];//creating 6 buttons
        Icon exit=new ImageIcon(getClass().getResource("quit2.PNG"));
        
        Icon exit1=new ImageIcon(getClass().getResource("quit0.png"));
        Icon inform=new ImageIcon(getClass().getResource("inform.PNG"));
        Icon draw=new ImageIcon(getClass().getResource("draw.PNG"));
        Icon draw1=new ImageIcon(getClass().getResource("draw1.PNG"));
        Icon help=new ImageIcon(getClass().getResource("help.PNG"));
        Icon reset=new ImageIcon(getClass().getResource("reset.PNG"));
        buttonpanel=new JPanel();
        buttonpanel.setLayout(new GridLayout(1,buttons.length,5,0));//3 buttons in 1 row
        graphpaper=new drawgraph();
        for(int count=0;count<buttons.length;count++)
        {
            if(count==0)
            {
                buttons[count]=new JButton("Instruction",help);
                buttons[count].setToolTipText("How to run");
                buttons[count].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        String instruct="You can plot a graph of both an eqn or a function\n"
                                + "case1:function->It can plot a single variable function in the form of f(x)\n"
                                + "i.e. to draw y=sinx only write sin(x)(of course with first brace) in the eqn field\n"
                                + "case2:it can plot at most 2 variable eqn incluing straight line,circle,parabola,hyperbola,ellipse\n"
                                + "i.e. to draw st. line ax+by=c write just the eqn\n"
                                + "to draw circle: x^2+y^2=square of radius\n"
                                + "one thing you should keep in mind:if possible use first case for better result\n";
                        JOptionPane.showMessageDialog(frame.this,instruct,"INSTRUCTION",1);
                    }
                }
               );
                
            }
            else if(count==1)
            {
                buttons[count]=new JButton("About",inform);
                buttons[count].setToolTipText("Person behind this application");
                buttons[count].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        String about="Hello this is Graph Plotter \nThis is Made by Biplob....I am grateful"
                                + " to my advisor SHAMPA SHAHRIAR madam\nAlso i'm grate"
                                + "ful to our class teacher to help me.";
                        JOptionPane.showMessageDialog(frame.this,about,"ABOUT THIS SOFTWARE",1);
                    }
                }
               );
            }
            else if(count==2) 
            {
                buttons[count]=new JButton("QUIT",exit);
                buttons[count].setRolloverIcon(exit1);
                buttons[count].setToolTipText("Exit");
                buttons[count].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        System.exit(0);
                    }
                }
               );
            }
            else if(count==4)
            {
                buttons[count]=new JButton("GRAPH",draw);
                buttons[count].setRolloverIcon(draw1);
                buttons[count].setToolTipText("Click here to draw graph");
                buttons[count].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        //p=0;
                        String eqnstr = frame.eqntext.getText();
                        double xunit = Double.parseDouble(frame.xrange.getText());
                        double yunit = Double.parseDouble(frame.yrange.getText());
                        
                        int xrange=getWidth()/2;//get range of x-axis from origin
                        int yrange=getHeight()/2;//get range of y axis from origin
                        
                        frame.pointcount = 0;
                        int eqn_upto_eql=0;
                        boolean issngl_var=true;
                        boolean varx=true;
                        int power=0;
                        for(int i=0;i<eqnstr.length();i++)
                        {
                            char ch=eqnstr.charAt(i);
                            if(ch=='y')varx=false;
                            
                            if(ch=='=')
                            {
                                
                                issngl_var=false;
                                eqn_upto_eql=i-1;
                                break;
                            }
                        }
                        for(int i=0;i<eqnstr.length();i++)
                        {
                            char ch=eqnstr.charAt(i);
                            if(ch=='^')
                            {
                                //if(eqnstr.charAt(i-1)=='x'||eqnstr.charAt(i-1)=='y')
                                    power++;
                            }
                        }
                        if(issngl_var)
                        {
                            
                            if(varx)
                            {
                                for(int i=0;i<xrange*2 && frame.pointcount<2000;i++)
                                {
                                    double value=calculate(eqnstr,0,eqnstr.length()-1,(i-xrange)*0.1*xunit,0);
                                    frame.x[frame.pointcount]=i-nx;//nx=4
                                    frame.y[frame.pointcount]=yrange-48-(int)((value*10)/yunit)-ny;//ny=25
                                    frame.pointcount++;
                                    

                                }
                            }
                            else
                            {
                                for(int i=0;i<yrange*2 && frame.pointcount<2000;i++)
                                {
                                    double value=calculate(eqnstr,0,eqnstr.length()-1,0,(yrange-i)*0.1*yunit);
                                    frame.x[frame.pointcount]=xrange-nx+(int)((value*10)/xunit);
                                    frame.y[frame.pointcount]=i-48-ny;
                                    frame.pointcount++;
                                    

                                }
                            }
                        }
                        else
                        {
                            int k=0;
                            int []upperx=new int[2000];
                            int []uppery=new int[2000];
                            int point=0;
                           for(int i=0;i<xrange*2 && frame.pointcount<2000;i++)
                            {
                                k=0;
                                for(int j=0;j<xrange*2 && frame.pointcount<2000;j++)
                                {
                                    
                                    double LHS=calculate(eqnstr,0,eqn_upto_eql,(i-xrange)*0.1*xunit,(j-xrange)*0.1*yunit);
                                    double RHS=calculate(eqnstr,eqn_upto_eql+2,eqnstr.length()-1,(i-xrange)*0.1*xunit,(j-xrange)*0.1*yunit);
                                     //System.out.println("power: "+power);
                                    if(Equal(LHS,RHS,power))
                                    {
                                        if(k==0)
                                        {
                                            frame.x[frame.pointcount]=i-nx;
                                            frame.y[frame.pointcount]=yrange*2+102-j-ny;
                                            frame.pointcount++;
                                            k++;
                                        }
                                        else if(point<2000)
                                        {
                                            upperx[point]=i-nx;
                                            uppery[point]=yrange*2+102-j-ny;
                                            point++;
                                        }
                                    }
                                }
                            } 
                           
                           for(int i=point-1;i>=0  && frame.pointcount<3998;i--)
                           {
                               frame.x[frame.pointcount]=upperx[i];
                               frame.y[frame.pointcount]=uppery[i];
                               frame.pointcount++;
                           }
                           frame.x[frame.pointcount]=frame.x[0];
                           frame.y[frame.pointcount]=frame.y[0];
                           frame.pointcount++;
                           
                        }
                        graphpaper.repaint();
                    }
                    private boolean Equal(double left, double right,int power)
                    {
                        //int power;
                        if(left==right) return true;
                        else if(power>=1)
                        {
                            
                            if(left<right && right-left<0.1) return true;
                            if(left>right && left-right<0.1) return true;
                        }
                        
                        return false;
                    }
                    
                }
               );
            }
            else if(count==3)
            {
                buttons[count]=new JButton("RESET",reset);
                buttons[count].setToolTipText("Reset the textfield to empty and unit to 1");
                buttons[count].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        eqntext.setText("");
                        xrange.setText("1");
                        yrange.setText("1");
                        pointcount=0;
                        radius=0;
                        graphpaper.repaint();
                    }
                }
               );
            }
            else if(count==5)
            {
                buttons[count]=new JButton("piecewise");
                buttons[count].setToolTipText("Draw piecewise graph");
                buttons[count].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        String eqn = frame.eqntext.getText();
                        //p=1;
                        char[][] eq=new char[3][];
                        char[][] con=new char[3][];
                        eq[0]=new char[30];
                        eq[1]=new char[30];
                        eq[2]=new char[30];
                        con[0]=new char[30];
                        con[1]=new char[30];
                        con[2]=new char[30];
                        int i,j,k,l=0,t;
                        char ch;
                        for(i=0;i<eqn.length();i++)
                        {
                            k=j=0;
                            t=1;
                            for(;i<eqn.length() && eqn.charAt(i)!=',';i++)
                            {
                               ch=eqn.charAt(i);
                               if(t==0)
                               {
                                   con[l][k++]=ch;
                               }
                               else if(ch!=' '&&t==1)
                               {
                                  eq[l][j++]=ch; 
                               }
                               else t=0;
                            }
                            first[l]=new String(eq[l],0,j);
                            next[l]=new String(con[l],0,k);
                            l++;

                        }
                        //System.out.println(next[0].length());
                       int num1=0,num2=0,m,n=1,n1=1,p;
                       double f;
                       double xunit = Double.parseDouble(frame.xrange.getText());
                        double yunit = Double.parseDouble(frame.yrange.getText());
                        
                        int xrange=getWidth()/2;//get range of x-axis from origin
                        int yrange=getHeight()/2;//get range of y axis from origin
                        
                        frame.pointcount = 0;
                        for(m=0;m<xrange*2 && frame.pointcount<2000;m++)
                        {
                            //p=0;
                          for(i=0;i<l;i++)
                          {
                              num1=num2=0;
                              n=n1=1;
                              if(next[i].indexOf("<x<")!=-1)
                              {
                                 for(j=0;j<next[i].indexOf('<');j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num1=num1*10;
                                         num1=num1+ch-'0';
                                     }
                                     else if(ch=='-')n=-1;
                                 } 
                                 for(j=next[i].lastIndexOf('<')+1;j<next[i].length();j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num2=num2*10;
                                         num2=num2+ch-'0';
                                     }
                                     else if(ch=='-')n1=-1;
                                 }
                                 f=(m-xrange)*0.1*xunit;
                                 for(;m<xrange*2 && num1*n<f && f<num2*n1;)
                                 {
                                     double value=calculate(first[i],0,first[i].length()-1,f,0);
                                    frame.x[frame.pointcount]=m-nx;
                                    frame.y[frame.pointcount]=yrange-48-(int)((value*10)/yunit)-ny;
                                    frame.pointcount++;
                                    m++;
                                    f=(m-xrange)*0.1*xunit;
                                 }
                                 frame.x[frame.pointcount]=4000;
                                 frame.y[frame.pointcount]=4000;
                                 frame.pointcount++;
                                 m++;
                              }
                              else if(next[i].indexOf("<=x<=")!=-1)
                              {
                                 for(j=0;j<next[i].indexOf("<=");j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num1=num1*10;
                                         num1=num1+ch-'0';
                                     }
                                     else if(ch=='-')n=-1;
                                 } 
                                 for(j=next[i].lastIndexOf("<=")+1;j<next[i].length();j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num2=num2*10;
                                         num2=num2+ch-'0';
                                     }
                                     else if(ch=='-')n1=-1;
                                 }
                                 f=(m-xrange)*0.1*xunit;
                                 for(;m<xrange*2 && num1*n<=f && f<=num2*n1;)
                                 {
                                     double value=calculate(first[i],0,first[i].length()-1,f,0);
                                    frame.x[frame.pointcount]=m-nx;
                                    frame.y[frame.pointcount]=yrange-48-(int)((value*10)/yunit)-ny;
                                    frame.pointcount++;
                                    m++;
                                    f=(m-xrange)*0.1*xunit;
                                 }
                                 frame.x[frame.pointcount]=4000;
                                 frame.y[frame.pointcount]=4000;
                                 frame.pointcount++;
                                 m++;
                              }
                              else if(next[i].indexOf('<')!=-1)
                             {
                                 for(j=next[i].lastIndexOf('<')+1;j<next[i].length();j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num1=num1*10;
                                         num1=num1+ch-'0';
                                     }
                                     else if(ch=='-')n=-1;
                                 }
                                 f=(m-xrange)*0.1*xunit;
                                 for(;m<xrange*2 && f<num1*n;)
                                 {
                                      double value=calculate(first[i],0,first[i].length()-1,f,0);
                                    frame.x[frame.pointcount]=m-nx;
                                    frame.y[frame.pointcount]=yrange-48-(int)((value*10)/yunit)-ny;
                                    frame.pointcount++;
                                    m++;
                                    f=(m-xrange)*0.1*xunit;
                                 }
                                 frame.x[frame.pointcount]=4000;
                                 frame.y[frame.pointcount]=4000;
                                 frame.pointcount++;
                                 m++;
                             }
                             else if(next[i].indexOf('>')!=-1)
                             {
                                 for(j=next[i].lastIndexOf('>')+1;j<next[i].length();j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num1=num1*10;
                                         num1=num1+ch-'0';
                                     }
                                     else if(ch=='-')n=-1;
                                 }
                                 f=(m-xrange)*0.1*xunit;
                                 for(;m<xrange*2 && f>num1*n;)
                                 {
                                      double value=calculate(first[i],0,first[i].length()-1,f,0);
                                    frame.x[frame.pointcount]=m-nx;
                                    frame.y[frame.pointcount]=yrange-48-(int)((value*10)/yunit)-ny;
                                    frame.pointcount++;
                                    m++;
                                    f=(m-xrange)*0.1*xunit;
                                 }
                                 frame.x[frame.pointcount]=4000;
                                 frame.y[frame.pointcount]=4000;
                                 frame.pointcount++;
                                 m++;
                             }
                             else if(next[i].indexOf(">=")!=-1)
                             {
                                 for(j=next[i].lastIndexOf(">=")+1;j<next[i].length();j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num1=num1*10;
                                         num1=num1+ch-'0';
                                     }
                                     else if(ch=='-')n=-1;
                                 }
                                 f=(m-xrange)*0.1*xunit;
                                 for(;m<xrange*2 && f>=num1*n;)
                                 {
                                      double value=calculate(first[i],0,first[i].length()-1,f,0);
                                    frame.x[frame.pointcount]=m-nx;
                                    frame.y[frame.pointcount]=yrange-48-(int)((value*10)/yunit)-ny;
                                    frame.pointcount++;
                                    m++;
                                    f=(m-xrange)*0.1*xunit;
                                 }
                                 frame.x[frame.pointcount]=4000;
                                 frame.y[frame.pointcount]=4000;
                                 frame.pointcount++;
                                 m++;
                             }
                             else if(next[i].indexOf("<=")!=-1)
                             {
                                 for(j=next[i].lastIndexOf("<=")+1;j<next[i].length();j++)
                                 {
                                     ch=next[i].charAt(j);
                                     if(ch>='0'&&ch<='9')
                                     {
                                         num1=num1*10;
                                         num1=num1+ch-'0';
                                     }
                                     else if(ch=='-')n=-1;
                                 }
                                 f=(m-xrange)*0.1*xunit;
                                 for(;m<xrange*2 && f<=num1*n;)
                                 {
                                      double value=calculate(first[i],0,first[i].length()-1,f,0);
                                    frame.x[frame.pointcount]=m-nx;
                                    frame.y[frame.pointcount]=yrange-48-(int)((value*10)/yunit)-ny;
                                    frame.pointcount++;
                                    m++;
                                    f=(m-xrange)*0.1*xunit;
                                 }
                                 frame.x[frame.pointcount]=4000;
                                 frame.y[frame.pointcount]=4000;
                                 frame.pointcount++;
                                 m++;
                             }
                            /*else
                            {
                               frame.x[frame.pointcount]=4000;
                               frame.y[frame.pointcount]=4000; 
                            }*/
                          }
                      }
                        graphpaper.repaint();
                    }
                }
                );
                    
            }
            else 
            {
                //System.out.println("good"+count);
                buttons[count]=new JButton("SHAPE");
                buttons[count].setToolTipText("Draw different shapes");
                buttons[count].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        //String message=String.format("Enter %d coordinates for %s clockwise or anti-clockwise",number+3,names[number]);
                        //String first=JOptionPane.showInputDialog(message);
                        String first = frame.eqntext.getText();
                        int i,j,k=1,coeff=0;
                        if(number==0)
                            {
                                for(i=0;i<first.length();i++)
                                {
                                        if(first.charAt(i)=='(')
                                        {
                                            coeff=0;k=1;
                                            for(j=++i;first.charAt(j)!=')';j++)
                                            {
                                                char ch=first.charAt(j);
                                                if(ch>='0'&&ch<='9')
                                                {
                                                    coeff=coeff*10;
                                                    coeff=coeff+ch-'0';
                                                }
                                                else if(ch=='-')k=-1;
                                                else
                                                {

                                                       frame.x[frame.pointcount]=k*coeff*10+getWidth()/2-nx;
                                                       coeff=0;
                                                       k=1;
                                                }
                                            }
                                            frame.y[frame.pointcount++]=getHeight()/2-coeff*10*k-48-ny;
                                            i=j;
                                        }
                                    }
                            
                            frame.x[frame.pointcount]=frame.x[0];
                            frame.y[frame.pointcount++]=frame.y[0];
                            //graphpaper.repaint();
                            }
                                //break;
                        else if(number==1)
                            {
                                for(i=0;i<first.length();i++)
                                {
                                    if(first.charAt(i)=='(')
                                        {
                                            //coeff=0;k=1;
                                            for(j=++i;first.charAt(j)!=')';j++)
                                            {
                                                char ch=first.charAt(j);
                                                if(ch>='0'&&ch<='9')
                                                {
                                                    coeff=coeff*10;
                                                    coeff=coeff+ch-'0';
                                                }
                                                else if(ch=='-')k=-1;
                                                else
                                                {

                                                       centrex=k*coeff*10+getWidth()/2-nx;
                                                       coeff=0;
                                                       k=1;
                                                }
                                            }
                                            centrey=getHeight()/2-coeff*10*k-48-ny;
                                            i=j;
                                            coeff=0;k=1;
                                        }
                                    char ch=first.charAt(i);
                                    if(ch>='0'&&ch<='9')
                                    {
                                         coeff=coeff*10;
                                         coeff=coeff+ch-'0';
                                    }
                                    else if(ch=='-')k=-1;
                                    
                                }
                                radius=coeff*k*10;
                                
                            }
                                //break;
                        
                        graphpaper.repaint();
                    
                   }
                }
               );
            }
        
            if(count<5)buttonpanel.add(buttons[count]);
            else top.add(buttons[count]);
            
        }
        box.addItemListener(
          new ItemListener()
          {
            @Override
              public void itemStateChanged(ItemEvent event)
              {
                  if(event.getStateChange()==ItemEvent.SELECTED)
                  {
                      number=box.getSelectedIndex();
                      System.out.println("number="+number);
                  }
              }
          }
        );
        add(buttonpanel,BorderLayout.SOUTH);
        add(top,BorderLayout.NORTH);
        add(graphpaper);
    }
    
    double calculate(String s,int a,int b,double x,double y)
    {
        int i,j;
        double value=0,coeff=0,save=0;
        char ch,sign='+',prevsign='+';
        String eqn=s.toLowerCase();
        for(i=a;i<=b;i++)
        {
            ch=eqn.charAt(i);
            if(ch>='0'&&ch<='9')
            {
                coeff=coeff*10;
                coeff=coeff+ch-'0';
            }
            else if(ch=='x')
            {
               if(coeff==0)coeff=1;
               coeff=coeff*x;
            }
            else if(ch=='y')
            {
               if(coeff==0)coeff=1;
               coeff=coeff*y;
            }
          
            else if(ch=='+')
            {
		if(sign=='*'){ coeff = coeff*save; sign=prevsign;}
		else if(sign=='/') { coeff = save/coeff; sign=prevsign;}
		else if(sign=='^') {coeff = Math.pow(save, coeff); sign=prevsign;}

		if(sign=='+') value += coeff;
		else if(sign=='-') value -= coeff;

		sign='+';
		coeff=0;
		save=0;
            }
            else if(ch=='-')
            {
		if(sign=='*'){ coeff = coeff*save; sign=prevsign;}
		else if(sign=='/') { coeff = save/coeff; sign=prevsign;}
		else if(sign=='^') {coeff = Math.pow(save, coeff); sign=prevsign;}

		if(sign=='+') value += coeff;
		else if(sign=='-') value -= coeff;

		sign='-';
		coeff=0;
		save=0;
            }
            else if(ch=='*')
            {
		if(sign=='+' || sign=='-') 
		{
			save=coeff;
			coeff=0;
			prevsign=sign;
		}
		else
		{
                    if(sign=='*')
			{
                        	save=save*coeff;
				coeff=0;
			}
			else if(sign=='/')
			{
				save=save/coeff;
				coeff=0;
			}
			else if(sign=='^')
			{
				save = Math.pow(save,coeff);
				coeff=0;
			}
		}
		sign='*';
            }
	    else if(ch=='/')
	    {
		if(sign=='+' || sign=='-')
		{
                    prevsign=sign;
                    save=coeff;
                    coeff=0;
		}
		else
		{
                    if(sign=='*')
                    {
			save=save*coeff;
			coeff=0;
                    }
                    else if(sign=='/')
                    {
			save=save/coeff;
			coeff=0;
                    }
		    else if(sign=='^')
                    {
			save = Math.pow(save,coeff);
			coeff=0;
                    }
		}
		sign='/';
            }
            else if(ch=='^')
            {
		if(sign=='+' || sign=='-')
		{
                    prevsign=sign;
                    save=coeff;
                    coeff=0;
		}
		else
		{
                    if(sign=='*')
                    {
			save=save*coeff;
			coeff=0;
                    }
                    else if(sign=='/')
                    {
			save=save/coeff;
			coeff=0;
                    }
                    else if(sign=='^')
                    {
			save = Math.pow(save,coeff);
			coeff=0;
                    }
		}
		sign='^';
            }
        
            else if(ch=='(')
            {
                int cnt=1;
                    for(j=i+1; cnt>0; j++)
                    {
			if(eqn.charAt(j)=='(') cnt++;
			if(eqn.charAt(j)==')') cnt--;
                    }
                    j--;
                    coeff=calculate(eqn, i+1, j-1, x,y);
                    i=j;    
            }                        
            else if(ch=='|')
            {
		for(j=i+1; eqn.charAt(j)!='|'; ) j++;
		coeff=calculate(eqn, i+1, j-1, x,y);
                if(coeff<0) coeff=-coeff;
		i=j;
            }
            else if(ch=='s')
            {
		int cnt=1;
                        
		for(j=i+4; cnt>0; j++)
		{
                    if(eqn.charAt(j) =='(') cnt++;
                    if(eqn.charAt(j) ==')') cnt--;
                                
		}
                        
		j--;
		double ang = calculate(eqn, i+4, j-1, x,y);
		ang = ang*Math.PI/180;
		if(coeff==0) coeff=1;
		coeff*=Math.sin(ang);
		i=j;
            }
            else if(ch=='c')
            {
		int cnt=1;
		for(j=i+4; cnt>0; j++)
		{
                    if(eqn.charAt(j) =='(') cnt++;
                    if(eqn.charAt(j) ==')') cnt--;
		}
		j--;
		double ang = calculate(eqn, i+4, j-1, x,y);
		ang = ang*Math.PI/180;
		if(coeff==0) coeff=1;
		coeff*=Math.cos(ang);
		i=j;
            }
            else if(ch=='t')
            {
		int cnt=1;
		for(j=i+4; cnt>0; j++)
		{
                    if(eqn.charAt(j) =='(') cnt++;
                    if(eqn.charAt(j) ==')') cnt--;
		}
		j--;
		double ang = calculate(eqn, i+4, j-1, x,y);
		ang = ang*Math.PI/180;
		if(coeff==0) coeff=1;
		coeff*=Math.tan(ang);
		i=j;
            }
            else if(ch=='l'&& eqn.charAt(i+1)=='n')
            {
                int cnt=1;
                for(j=i+3;cnt>0;j++)
                {
                    if(eqn.charAt(j)=='(') cnt++;
                    if(eqn.charAt(j)==')') cnt--;
                }
                j--;
                double argument=calculate(eqn,i+3,j-1,x,y);
                double temp_value=Math.log(argument);
                if(coeff==0) coeff=1;
                coeff*=temp_value;
                i=j;
            }
            else if(ch=='e')
            {
                if(coeff==0) coeff=1;
                coeff=coeff*Math.E;
            }
            else if(ch=='l'&& eqn.charAt(i+1)=='o')
            {
                    
                int cnt=1;
                for(j=i+4;cnt>0;j++)
                {
                    if(eqn.charAt(j)=='(') cnt++;
                    if(eqn.charAt(j)==')') cnt--;
                }
                j--;
                double argument=calculate(eqn,i+4,j-1,x,y);
                double temp_value=Math.log10(argument);
                if(coeff==0) coeff=1;
                coeff*=temp_value;
                i=j;
            }
        }
        
        if(sign=='*'){ coeff = coeff*save; sign=prevsign;}
	else if(sign=='/') { coeff = save/coeff; sign=prevsign;}
	else if(sign=='^') {coeff = Math.pow(save, coeff); sign=prevsign;}

	if(sign=='+') value += coeff;
	else if(sign=='-') value -= coeff;
        return value;
    }
    private class drawgraph extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            int size=getWidth();
            int size1=getHeight();
            //g.setColor(Color.BLACK);
            
            
            g.setColor(new Color(175,175,175));
            for(int i=size/2; i<size; i+= 10)
            {
                g.drawLine(i,0,i,size1);
                
                g.drawLine(size-i, 0, size-i,size1);
                
            }
            for(int i=size1/2; i<size1; i+= 10)
            {
                
                g.drawLine(0,i,size, i);
                
                g.drawLine(0, size1-i, size, size1-i);
            }
            g.setColor(Color.BLACK);
            for(int i=size/2; i<size; i+= 50)
            {
                g.drawLine(i, 0, i,size1);
                
                g.drawLine(size-i, 0, size-i,size1);
                
            }
            for(int i=size1/2; i<size1; i+= 50)
            {
                
                g.drawLine(0,i,size, i);
                
                g.drawLine(0, size1-i, size, size1-i);
            }
            
            g.setColor(Color.RED);
            g.drawLine(size/2, 0,size/2,size1);
            g.drawLine(0,size1/2,size,size1/2);
            g.setColor(Color.BLUE);
            //System.out.println(p);
            if(radius>0)
                    {
                        //System.out.println("x="+centrex+" y="+centrey+" radius="+radius);
                        
                        g.drawOval(centrex-radius,centrey-radius,2*radius,2*radius);
                    }
            else
                for(int m=1; m<frame.pointcount; m++)
                { 
                    if(frame.x[m]!=4000 && frame.x[m-1]!=4000)
                        g.drawLine(frame.x[m-1], frame.y[m-1], frame.x[m],frame.y[m]);
                }
        }
    }
    
}

