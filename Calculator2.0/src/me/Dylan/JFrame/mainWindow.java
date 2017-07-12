package me.Dylan.JFrame;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class mainWindow extends noeditMaterial implements MouseListener, KeyListener {
	// mainJframe
	private JFrame mainWindow;
    
	// JPanels
	private JPanel jf, textField, equationTextField;
    
	// JButtons
	private JButton[] nums = new JButton[10];
    private JButton addb, backspace, clear, clearall, cosb, divb, eb, equals, multb, period, pib, plusorminus, radian, rootb, sinb, squareb, subtractb, tanb;
	// Displayer is what displays the numbers and operators
	private JTextPane calcDisplayer, equationDisplayer;
	// number saves the first number into a string and number2 saves the second number into a string
    // AnswerConvToString is what is used to display the number after calculations
	private String number, number2, answerConvToString;
	// presNumber is the current number that was pressed and answer is the final answer before it is converted to String
	private int presNumber, answerT;
	private double answer;
	// bunch of booleans. the operator booleans are turned true when they are pressed. That way I know which operation to apply to the two numbers. 
	private boolean add, cos, degrees, div, isCycling, isInteger, mult, numberEnd, root, shift, sin, square, subtract, tan = false;

	// I added the display boolean in case you didn't want the display but still wanted to create a new mainWindow object outside the class
	public mainWindow(boolean display) {
		if (display) {
			
			mainWindow = new JFrame();
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
			jf = new JPanel();
			textField = new JPanel();
			equationTextField = new JPanel();
	    
			// make it look purty
			GridLayout fl = new GridLayout();
			fl.setRows(7);
			fl.setVgap(10);
			fl.setHgap(50);

			jf.setLayout(fl);
            jf.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jf.setBackground(java.awt.Color.black);
            
            // create number buttons
            for (int i = 0; i < 10; i++) {
                nums[i] = new JButton(Integer.toString(i));
            }
			
			// For some reason the textField only extends across the window when you apply BorderLayout to it. It doesn't work if you apply the BorderLayout to the mainWindow instead of the textField. 
			BorderLayout textFieldbl = new BorderLayout();
			textField.setLayout(textFieldbl);
			textField.add(calcDisplayer = new JTextPane());

			BorderLayout equationTextFieldbl = new BorderLayout();		
			equationTextField.setLayout(equationTextFieldbl);
			equationTextField.add(equationDisplayer = new JTextPane());

			// This is what disables the text field at the top from being typed in.
			calcDisplayer.setEditable(false);
			equationDisplayer.setEditable(false);
			
			jf.add(backspace = new JButton("<"));
            jf.add(clear = new JButton("C"));
            jf.add(clearall = new JButton("CE"));
            jf.add(rootb = new JButton("\u221A"));
            jf.add(sinb = new JButton("sin()"));
            jf.add(cosb = new JButton("cos()"));
            jf.add(tanb = new JButton("tan()"));
            jf.add(squareb = new JButton("^2"));
            jf.add(eb = new JButton("e"));
            jf.add(pib = new JButton("\u03C0"));
            jf.add(plusorminus = new JButton("+-"));
            jf.add(multb = new JButton("*"));
            jf.add(nums[1]);
            jf.add(nums[2]);
            jf.add(nums[3]);
            jf.add(subtractb = new JButton("-"));
            jf.add(nums[4]);
            jf.add(nums[5]);
            jf.add(nums[6]);
            jf.add(addb = new JButton("+"));
            jf.add(nums[7]);
            jf.add(nums[8]);
            jf.add(nums[9]);
            jf.add(divb = new JButton("/"));
            jf.add(radian = new JButton("RAD"));
            jf.add(nums[0]);
            jf.add(period = new JButton("."));
            jf.add(equals = new JButton("="));
            
			// Mouse listeners
			for (int i = 0; i < 10; i++) {
                nums[i].addMouseListener(this);
            }
            
            addb.addMouseListener(this);backspace.addMouseListener(this);clear.addMouseListener(this);clearall.addMouseListener(this);cosb.addMouseListener(this);divb.addMouseListener(this);eb.addMouseListener(this);equals.addMouseListener(this);multb.addMouseListener(this);period.addMouseListener(this);pib.addMouseListener(this);plusorminus.addMouseListener(this);radian.addMouseListener(this);rootb.addMouseListener(this);sinb.addMouseListener(this);squareb.addMouseListener(this);subtractb.addMouseListener(this);tanb.addMouseListener(this);
			
            // Key listeners (for some reason... I have to have add all of these... otherwise things get weird...)
			for (int i = 0; i < 10; i++) {
                nums[i].addKeyListener(this);
            }
            addb.addKeyListener(this);backspace.addKeyListener(this);clear.addKeyListener(this);clearall.addKeyListener(this);cosb.addKeyListener(this);divb.addKeyListener(this);eb.addKeyListener(this);equals.addKeyListener(this);multb.addKeyListener(this);period.addKeyListener(this);pib.addKeyListener(this);plusorminus.addKeyListener(this);radian.addKeyListener(this);rootb.addKeyListener(this);sinb.addKeyListener(this);squareb.addKeyListener(this);subtractb.addKeyListener(this);tanb.addKeyListener(this);
            
            calcDisplayer.addKeyListener(this);equationDisplayer.addKeyListener(this);
			
            BorderLayout bl = new BorderLayout();
			mainWindow.setLayout(bl);
			mainWindow.add(equationTextField, BorderLayout.NORTH);
			mainWindow.add(textField, BorderLayout.CENTER);
			mainWindow.add(jf, BorderLayout.SOUTH);
			
			// more purty things
			mainWindow.setTitle("Boss Calculator");
            
            String path = "calculator.png";
	        File file = new File(path);
	        BufferedImage image = null;
			try {
				image = ImageIO.read(file);
                mainWindow.setIconImage(image);
			} catch (IOException e) {
				e.printStackTrace();
                System.exit(0);
			}
            
			Font displayerFont = new Font("Serif", Font.BOLD, 33);
			calcDisplayer.setFont(displayerFont);
            
			Font buttonFont = new Font("Serif", Font.BOLD, 28);
            
			for (int i = 0; i < 10; i++) {
                nums[i].setFont(buttonFont);
            }
            addb.setFont(buttonFont);backspace.setFont(buttonFont);clear.setFont(buttonFont);clearall.setFont(buttonFont);cosb.setFont(buttonFont);divb.setFont(buttonFont);eb.setFont(buttonFont);equals.setFont(buttonFont);multb.setFont(buttonFont);period.setFont(buttonFont);pib.setFont(buttonFont);plusorminus.setFont(buttonFont);radian.setFont(buttonFont);rootb.setFont(buttonFont);sinb.setFont(buttonFont);squareb.setFont(buttonFont);subtractb.setFont(buttonFont);tanb.setFont(buttonFont);
            
    		// finally
			changeDimension(mainWindow, 300, 250);
			windowPacker(mainWindow);
		}
			
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		mainWindow mw = new mainWindow(true);
	}
    
	@Override
	public void mouseClicked(MouseEvent e) {
		
		for (int i = 0; i < 10; i++) {
            if (e.getSource() == nums[i]) {
                displayNumbers(i, calcDisplayer, isNumberEnd(), this);
            }
        }

		if(e.getSource() == pib) {
			displayNumbers(Math.PI,calcDisplayer, isNumberEnd(), this);
		} else if (e.getSource() == eb) {
			displayNumbers(Math.E, calcDisplayer, isNumberEnd(), this);
		}
		// addition, subtraction etc buttons.

		if(e.getSource() == subtractb) {
    		setSubtract(keyPressedOperators(0, 0, "-", isSubtract(),shift,calcDisplayer,this,isCycling()));
		} else if (e.getSource() == multb) {
			setMult(keyPressedOperators(0,0,"*",isMult(),shift,calcDisplayer,this,isCycling()));
			
		} else if (e.getSource() == divb) {
			setDiv(keyPressedOperators(0, 0, "/", isDiv(), shift, calcDisplayer, this, isCycling()));
			
		} else if (e.getSource() == addb) {
			setAdd(keyPressedOperators(0, 0, "+", isAdd(), shift, calcDisplayer, this, isCycling()));

		} else if (e.getSource() == squareb) {
			if(getNumberS() != null && getNumberS() != "") {
				setSquare(keyPressedOperators(0,0,getNumberS() + "^2",isSquare(),shift,calcDisplayer,this,isCycling()));
			} else {
				completeClear(calcDisplayer, this);
			}
			
		} else if (e.getSource() == rootb) {
			setRoot(keyPressedOperators(0,0,"\u221A" + getNumberS(),isRoot(),shift,calcDisplayer,this,isCycling()));
			
		} else if (e.getSource() == sinb) {
			if(getNumberS() != null && getNumberS() != "") {
				setSquare(keyPressedOperators(0,0,getNumberS() + "^2",isSquare(),shift,calcDisplayer,this,isCycling()));
			} else {
				completeClear(calcDisplayer, this);
			}
		} else if (e.getSource() == cosb) {
			if(getNumberS() != null && getNumberS() != "") {
				setCos(keyPressedOperators(0,0,"Cos(" + getNumberS() + ")",isCos(),shift,calcDisplayer,this,isCycling()));
			} else {
				setCos(keyPressedOperators(0,0,"Cos(",isCos(),shift,calcDisplayer,this,isCycling()));
			}
    	} else if (e.getSource() == tanb) {
    		if(getNumberS() != null && getNumberS() != "") {
    			setTan(keyPressedOperators(0,0,"Tan(" + getNumberS() + ")",isTan(),shift,calcDisplayer,this,isCycling()));
    		} else {
    			setTan(keyPressedOperators(0,0,"Tan(",isTan(),shift,calcDisplayer,this,isCycling()));
    		}
        }
		
		if(e.getSource() == radian) {
			setDegrees(!isDegrees());
		} else if(e.getSource() == equals) {
			calculations();
    	} else if(e.getSource() == clearall) {
			completeClear(calcDisplayer, this);
		} else if(e.getSource() == clear) {
			if(isNumberEnd()) {
				setNumberS2("");
				calcDisplayer.setText("");
			} else {
				if(isCycling()) {
					setCycling(false);
					System.out.println("No longer cycling");
				}
				setNumberS("");
				calcDisplayer.setText("");
			}
		} else if(e.getSource() == backspace) {
			moreBackspace(this, calcDisplayer);
		} else if(e.getSource() == period) {
			periodAdder(this, calcDisplayer);
		} else if(e.getSource() == plusorminus) {
			if(isNumberEnd()) {
				setNumberS2(negativeConverter(getNumberS2()));
				calcDisplayer.setText(getNumberS2());
			} else {
				setNumberS(negativeConverter(getNumberS()));
				calcDisplayer.setText(getNumberS());
			}
		}
	}
	public void calculations() {
		// If you are wondering why this has so many parameters, dont worry, everything will be ok.
		calcDisplayer.setText(finalCalculations(getNumberS(), getNumberS2(), isSquare(), isSubtract(), isMult(), 
				isDiv(), isAdd(), isRoot(), isSin(), isCos(),isTan(), getAnswerDouble(), getAnswerInt(), answerConvToString, calcDisplayer, this));
	}
    
    // Are these lines needed?
	// Next 5 lines are useless (almost)
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

        // shift
		if(key == 16) {
			shift = true;
		}
        // for some reason the vk thing is only working for enter. So I just leave this one
		if(key == KeyEvent.VK_ENTER) {
			calculations();
		}
		
        // This seems to be in it's simplest form, from what I know
		keyPressedNumbers(key, KeyEvent.VK_0,0,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_1,1,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_2,2,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_3,3,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_4,4,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_5,5,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_6,6,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_7,7,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_8,8,shift,calcDisplayer,this);
		keyPressedNumbers(key, KeyEvent.VK_9,9,shift,calcDisplayer,this);
		
		// the secondary method is for the extra shift functionality
		setAdd(secondaryKeyPressedOperators(key,107,61,"+", isAdd(),shift,isNumberEnd(),isCycling(),calcDisplayer,this));
		setMult(secondaryKeyPressedOperators(key,106,56,"*", isMult(), shift, isNumberEnd(), isCycling(), calcDisplayer,this));
		if(getNumberS() != null && getNumberS() != "") {
			setSquare(secondaryKeyPressedOperators(key,192,54,getNumberS() + "^2",isSquare(),shift,isNumberEnd(),isCycling(),calcDisplayer,this));
		}
        setRoot(secondaryKeyPressedOperators(key,40,50, "\u221A" + getNumberS(),isRoot(),shift,isNumberEnd(),isCycling,calcDisplayer,this));
		setDiv(keyPressedOperators(key,47,"/",isDiv(),shift,calcDisplayer,this,isCycling()));
		setDiv(keyPressedOperators(key,111,"/",isDiv(), shift, calcDisplayer, this, isCycling()));
		setSubtract(keyPressedOperators(key,45,"-",isSubtract(),shift,calcDisplayer,this,isCycling()));
		setSubtract(keyPressedOperators(key,109,"-",isSubtract(),shift,calcDisplayer,this,isCycling()));
		
		if(key == 8) { // backspace
			moreBackspace(this, calcDisplayer);
		} else if(key == 67) { // clear
			completeClear(calcDisplayer, this);
		} else if(key == 46 || key == 127) { // period on left or period on numpad
			periodAdder(this, calcDisplayer);
		} else if(shift && key == 45) { // converts to negative if you ctrl minus
			if(isNumberEnd()) {
				setNumberS2(negativeConverter(getNumberS2()));
				calcDisplayer.setText(getNumberS2());
			} else {
				setNumberS(negativeConverter(getNumberS()));
				calcDisplayer.setText(getNumberS());
			}
		} else if(key == 32) { // press space
			completeClear(calcDisplayer, this);
			calcDisplayer.setText("spacebar? really?");
		} else if(key == 69) { //L ... It's the e key... for the e button
			displayNumbers(Math.E,calcDisplayer,isNumberEnd(),this);
		} else if(key == 83) { // S for sign
			if(getNumberS() != null && getNumberS() != "" && !isNumberEnd()) {
				setSin(keyPressedOperators(0,0,"Sin(" + getNumberS() + ")",isSin(),shift,calcDisplayer,this,isCycling()));
			} else {
				setSin(keyPressedOperators(0,0,"Sin(",isSin(),shift,calcDisplayer,this,isCycling()));
			}
		} else if(key == 79) { // Using an o for cosign because I used c for clear
			if(getNumberS() != null && getNumberS() != "" && !isNumberEnd()) {
				setCos(keyPressedOperators(0,0,"Cos(" + getNumberS() + ")",isCos(),shift,calcDisplayer,this,isCycling()));
			} else {
				setCos(keyPressedOperators(0,0,"Cos(",isCos(),shift,calcDisplayer,this,isCycling()));
			}
		} else if(key == 84) { // t for tan
    		if(getNumberS() != null && getNumberS() != "" && !isNumberEnd()) {
    			setTan(keyPressedOperators(0,0,"Tan(" + getNumberS() + ")",isTan(),shift,calcDisplayer,this,isCycling()));
    		} else {
    			setTan(keyPressedOperators(0,0,"Tan(",isTan(),shift,calcDisplayer,this,isCycling()));
    		}
		} else if(key == 80) { // p for pi
			displayNumbers(Math.PI,calcDisplayer,isNumberEnd(),this);
		} else if(key == 82) { // r for radians cycles betweens degrees and radians when using sin cos and tan
			setDegrees(!isDegrees());
		}
	}
    
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 16) { // shift
			shift = false;
		}
	}
    
	// just a bunch of getters and setters for the no edit method.
	public String getOperator() {
		if(isMult()) {
			return "*";
		} else if (isAdd()) {
			return "+";
		} else if (isSubtract()) {
			return "-";
		} else if (isSquare()) {
			return "^2";
		} else if (isDiv()) {
			return "/";
		} else if (isRoot()) {
			return "\u221A";
		} else if (isSin()) {
			return "Sin(";
		} else if (isCos()) {
			return "Cos(";
		} else if (isTan()) {
			return "Tan(";
		}
		return "N/A";
	}
	
	public void setDisplayer(String message) {
		calcDisplayer.setText(message);
	}
	public String getEquationDisplayer() {return equationDisplayer.getText();}
	public String getNumberS() {return number;}
	public String getNumberS2() {return number2;}
	public boolean isAdd() {return add;}
	public boolean isCos() {return cos;}
	public boolean isCycling() {return isCycling;}
	public boolean isDegrees() {return degrees;}
	public boolean isDiv() {return div;}
	public boolean isInteger() {return isInteger;}
	public boolean isMult() {return mult;}
	public boolean isNumberEnd() {return numberEnd;}
	public boolean isRoot() {return root;}
	public boolean isSin() {return sin;}
	public boolean isSquare() {return square;}
	public boolean isSubtract() {return subtract;}
	public boolean isTan() {return tan;}
	public double getAnswerDouble() {return answer;}
	public int getAnswerInt() {return answerT;}
	public int getPresNumber() {return presNumber;}
	public void setAdd(boolean add) {this.add = add;}
	public void setAnswerDouble(double answer) {this.answer = answer;}
	public void setAnswerInt(int answerT) {this.answerT = answerT;}
	public void setCos(boolean cos) {this.cos = cos;}
	public void setCycling(boolean isCycling) {this.isCycling = isCycling;}
	public void setDegrees(boolean degrees) {this.degrees = degrees;}
	public void setDiv(boolean div) {this.div = div;}
	public void setEquationDisplayer(String text) {equationDisplayer.setText(text);}
	public void setInteger(boolean isInteger) {this.isInteger = isInteger;}
	public void setMult(boolean mult) {this.mult = mult;}
	public void setNumberEnd(boolean numberEnd) {this.numberEnd = numberEnd;}
	public void setNumberS(String number) {this.number = number;}
	public void setNumberS2(String number2) {this.number2 = number2;}
	public void setPresNumber(int presNumber) {this.presNumber = presNumber;}
	public void setRoot(boolean root) {this.root = root;}
	public void setSin(boolean sin) {this.sin=sin;}
	public void setSquare(boolean square) {this.square = square;}
	public void setSubtract(boolean subtract) {this.subtract = subtract;}
	public void setTan(boolean tan) {this.tan = tan;}
}
