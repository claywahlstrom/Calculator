package me.Dylan.JFrame;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextPane;

public class noeditMaterial{
	public void changeDimension(JFrame jf, int height, int width){
		Dimension d = new Dimension();
		d.setSize(height, width);
		jf.setMinimumSize(d);
	}
	public String backspace(String number){
		String tempNumber = "";
		if(number.length() > 1){
			for(int i = 0; i<number.length()-1;i++){
				tempNumber = tempNumber + "" + number.charAt(i); 
			}
		}else{
			tempNumber = "";
		}
	return tempNumber;
}
	
	public String negativeConverter(String number){
		boolean isDouble;
		double tempConvD;
		int tempConvI;
		try{
			if(number.contains(".")){
				isDouble= true;
			}else{
				isDouble= false;
			}
			if(isDouble){
				tempConvD = Double.parseDouble(number);
				tempConvD = tempConvD * (-1);
				number = "" + tempConvD;
			}else{
				tempConvI = Integer.parseInt(number);
				tempConvI = tempConvI * (-1);
				number = "" + tempConvI; 
			}
		
			return number;
		}catch(NullPointerException ie){
			System.out.println("Syntax");
			return "";
			
			//this is for when someone presses the +- and it is at an error or null.
		}catch(NumberFormatException ie){
			System.out.println("Syntax");
			return "";
		}
		
	}
	
	//NumberEnd is a terrible var name. What it means is the first number that is done is at its end, otherwise throw a syntax error (that means they pressed the operator button twice without a value)
	public boolean clickedOperation(boolean numberEnd, mainWindow window) throws SyntaxErrorException{
		if(numberEnd && !window.isSin() && !window.isCos() && !window.isTan() && window.isRoot()){
			throw new SyntaxErrorException();
		}
		return true;
		
	}
	
	public void windowPacker(JFrame jf){
		jf.pack();
		jf.setVisible(true);
	}
	
	
	public boolean secondaryKeyPressedOperators(int key, int vk, int vk2, String operatorSymbol, boolean operatorB, boolean shift, boolean numberEnd, boolean isCycling, JTextPane displayer, mainWindow window){
		if(key == vk || (shift == true && key == vk2)){
			try {
				window.setNumberEnd(clickedOperation(numberEnd, window));
				
				if(!isCycling){
					displayer.setText(operatorSymbol);
				}else{
					displayer.setText("Ans->"+operatorSymbol);
				}
				
				//really sloppy way of making sure ^2 is correctly displayed
				if(operatorSymbol.contains("^2") || operatorSymbol.contains("Tan") || operatorSymbol.contains("Sin") || operatorSymbol.contains("Cos") || operatorSymbol.contains("\u221A")){
					window.setEquationDisplayer(operatorSymbol);
				}else{
					window.setEquationDisplayer(window.getNumberS() + " " + operatorSymbol);
				}
			} catch (SyntaxErrorException e1) {
				window.syntaxErrorCatch(true, window, displayer);
			}
			return true;
		}
		return operatorB;
		
	}
	public String finalCalculations(String number, String number2, boolean square, boolean subtract, boolean mult, boolean div, 
			boolean add, boolean root, boolean sin, boolean cos, boolean tan, double answer,
			int answerT, String answerConvToString, JTextPane displayer, mainWindow window){
		//You don't have to have two numbers that you need to enter when you square, sin, etc. So I let it enter the else
		if(number ==null || (number2 == null && !square&& !root && !cos && !sin && !tan)){
			window.syntaxErrorCatch(true, window, displayer);
			return "";
		} else {
			double tempNum2 = 0;
			double tempNum = 0;
			try{
				tempNum = Double.parseDouble(number);
				tempNum2 = 0;
				if(!square && !root && !cos && !sin && !tan){
					tempNum2 = Double.parseDouble(number2);
				}
	
                if(subtract){
                    window.setAnswerDouble(tempNum - tempNum2);
                }else if(mult){
                    window.setAnswerDouble(tempNum*tempNum2);
                }else if(div){
                    window.setAnswerDouble(tempNum/tempNum2);
                }else if(add){
                    window.setAnswerDouble(tempNum+tempNum2);
                }else if(square){
                    window.setAnswerDouble(tempNum*tempNum);
                }else if(root){
                    window.setAnswerDouble(Math.sqrt(tempNum));
                }else if(sin){
                    if(window.isDegrees()){
                        window.setAnswerDouble(Math.sin(tempNum/57.2958));
                    }else{
                        window.setAnswerDouble(Math.sin(tempNum));
                    }
                }else if(cos){
                    if(window.isDegrees()){
                        window.setAnswerDouble(Math.cos(tempNum/57.2958));
                    }else{
                        window.setAnswerDouble(Math.cos(tempNum));
                    }
                }else if(tan){
                    if(window.isDegrees()){
                        window.setAnswerDouble(Math.cos(tempNum/57.2958));	//turning to degrees
                    }else{
                        window.setAnswerDouble(Math.tan(tempNum));					
                    }


                }

                String tempUser = ".";
                String tempUser2 = "0";
                String tempConv = "" + window.getAnswerDouble();
                String tempConv2 = "";
                //Definitely better ways to do this. I just looked if .0 exists and erased it to turn into an int. Otherwise it stays double.
                if(tempConv.charAt(tempConv.length()-2) == tempUser.charAt(tempUser.length() -1) && tempConv.charAt(tempConv.length()-1) == tempUser2.charAt(tempUser2.length() -1)){
                    for(int i=0; i<tempConv.length()-2; i++){
                        tempConv2 = tempConv2 + "" +tempConv.charAt(i);
                    }
                    window.setInteger(true);
                    window.setAnswerInt(Integer.parseInt(tempConv2));
                    answerConvToString = "" + window.getAnswerInt();
                }else{
                    window.setInteger(false);
                    window.setAnswerDouble(Double.parseDouble(tempConv));
                    answerConvToString = "" + window.getAnswerDouble();
                }
                
                if(window.isSquare()){
                    window.setEquationDisplayer(window.getNumberS() + "^2" + " " + "=");
                }else if(window.isSin() || window.isCos() || window.isTan() || window.isRoot()){
                    window.setEquationDisplayer(window.getOperator() + window.getNumberS() + " " + "=");
                }else{
                    window.setEquationDisplayer(window.getNumberS() + " " + window.getOperator() + " " + window.getNumberS2() + " " + "=");
                }

                window.syntaxErrorCatch(false, window, displayer);
                return answerConvToString;
			//this is for when some loser puts in a value that is weird (or no value at all). So I just basically reject them with my method syntax error.
			} catch (NumberFormatException ie){
				System.out.println("Noob error, try again...");
				window.syntaxErrorCatch(true, window, displayer);
			}
		}
		return "";
	}
	public void periodAdder(mainWindow window, JTextPane displayer){
		if(window.isNumberEnd()){
			if(window.getNumberS2() == null){
				window.setNumberS2("0");
			}
			if(window.getNumberS2().contains(".")){
				
			}else{
				window.setNumberS2(window.getNumberS2() + "" + ".");
				displayer.setText(window.getNumberS2());
			}
		}else{
			if(window.getNumberS() == null){
				window.setNumberS("0");
			}
			if(window.getNumberS().contains(".")){
				
			}else{
				window.setNumberS(window.getNumberS() + "" + ".");
				displayer.setText(window.getNumberS());
			}
		}
	}
	
	public boolean keyPressedOperators(int key, int vk,String operatorSymbol, boolean operatorB, boolean shift, JTextPane displayer, mainWindow window, boolean isCycling){
		if(key == vk && !shift){
			try{
				if(window.getNumberS() != null && window.getNumberS() != ""){
					window.setNumberEnd(clickedOperation(window.isNumberEnd(), window));
				}
				
				if(!isCycling){
					displayer.setText(operatorSymbol);

				}else{
					displayer.setText("Ans->"+operatorSymbol);
				}
				
				if(operatorSymbol.contains("^2") || operatorSymbol.contains("Tan") || operatorSymbol.contains("Sin") || operatorSymbol.contains("Cos") || operatorSymbol.contains("\u221A")){
					window.setEquationDisplayer(operatorSymbol);
				}else{
					window.setEquationDisplayer(window.getNumberS() + " " + operatorSymbol);
				}
			} catch (SyntaxErrorException e1) {
				window.syntaxErrorCatch(true, window, displayer);
			}
			return true;
		}
		return operatorB;
	}
	public void keyPressedNumbers(int key, int vk,int number,boolean shift, JTextPane displayer, mainWindow window){
		if(key == vk && !shift){

			window.displayNumbers(number,displayer,window.isNumberEnd(), window);
		}
		
	}
	public void moreBackspace(mainWindow window, JTextPane displayer){
		if(window.isNumberEnd()){
			if(window.getNumberS2() != null){
				window.setNumberS2(window.backspace(window.getNumberS2()));

				displayer.setText(window.getNumberS2());
			}
			
		}else{
			if(window.isCycling()){
				window.setNumberS("");
				window.setNumberS2("");
				window.setCycling(false);
				System.out.println("No longer cycling");
				displayer.setText("");
			}
			if(window.getNumberS() != null){
				window.setNumberS(window.backspace(window.getNumberS()));
				displayer.setText(window.getNumberS());
			}
			
		}
	
	}
	
	public void completeClear(JTextPane displayer, mainWindow window){
		displayer.setText("");
		window.setEquationDisplayer("");
		window.setNumberEnd(false);
		window.setNumberS("");
		window.setNumberS2("");
		booleanClear(window);
		System.out.println("No longer cycling");

	}
	public void displayNumbers(double bnumber, JTextPane displayer, boolean numberEnd, mainWindow window){
		String tempUser = ".";
		String tempUser2 = "0";
		String tempString = "" + bnumber;
		double tempNum = 0;
		boolean isDouble = false;
		boolean yes = false;
		if(!window.isTan() && !window.isSin() && !window.isCos() && !window.isRoot()){
			//If you get confused at this point. Don't worry. It's not your fault.
			if(tempString.charAt(tempString.length()-2) == tempUser.charAt(tempUser.length() -1) && tempString.charAt(tempString.length()-1) == tempUser2.charAt(tempUser2.length() -1)){
				isDouble = false;
			}else{
				isDouble = true; 
			}
			if(isDouble){
			
			}else{
				window.setPresNumber((int) bnumber);
			}
			if(!numberEnd){
				if(window.isCycling() && !numberEnd){
					window.setCycling(false);
					completeClear(displayer, window);
					displayer.setText("");
				}
			
				if(window.getNumberS() == null){
					if(isDouble){
						window.setNumberS("" + bnumber);
					}else{
						window.setNumberS("" + window.getPresNumber());
					}

				}else{
					if(isDouble){
						window.setNumberS(window.getNumberS() + "" + bnumber);

					}else{
						window.setNumberS(window.getNumberS() + "" + window.getPresNumber());
					}
				}

				window.setEquationDisplayer(window.getNumberS());
				window.setDisplayer(window.getNumberS()); 

			}else{

				if(window.getNumberS2() == null){
					if(isDouble){
						window.setNumberS2("" + bnumber);
					}else{
						window.setNumberS2("" + window.getPresNumber());
					}
				}else{
					if(isDouble){
						window.setNumberS2(window.getNumberS2() + "" + bnumber);

					}else{
						window.setNumberS2(window.getNumberS2() + "" + window.getPresNumber());
					}
				}
				window.setEquationDisplayer(window.getNumberS() + " " + window.getOperator() + " " + window.getNumberS2());
			}
			
		}else{
			isDouble = true;
			window.setNumberS2("" + bnumber);
		    tempNum = Double.parseDouble(window.getNumberS2());
		    yes = true;
		}

			if(window.isRoot()){
				window.setNumberS2("" + Math.sqrt(tempNum));
				window.setRoot(false);
			}else if(window.isSin()){
				if(window.isDegrees()){
					window.setNumberS2("" +Math.sin(tempNum/57.2958));
				}else{
					window.setNumberS2("" +Math.sin(tempNum));
				}
				window.setSin(false);
			}else if(window.isCos()){
				if(window.isDegrees()){
					window.setNumberS2("" +Math.cos(tempNum/57.2958));
				}else{
					window.setNumberS2("" +Math.cos(tempNum));
				}
				window.setCos(false);
			}else if(window.isTan()){
				if(window.isDegrees()){
					window.setNumberS2("" +Math.cos(tempNum/57.2958));	//turning to degrees
				}else{

					window.setNumberS2("" +Math.tan(tempNum));					
				}

				window.setTan(false);
			}
			
			displayer.setText(window.getNumberS2());
			if(yes){
				window.setEquationDisplayer(window.getNumberS() + " " + window.getOperator() + " " + window.getNumberS2());
				yes = false;
			}
			

		
	}
	
	public void syntaxErrorCatch(boolean errorTrue,mainWindow window,JTextPane displayer){
		if(errorTrue){
			displayer.setText("Error");
			completeClear(displayer, window);

		}else{
			if(window.isInteger()){
				
				window.setNumberS("" + window.getAnswerInt());
			}else{
				window.setNumberS("" + window.getAnswerDouble());
			}
			window.setNumberS2("");
			window.setCycling(true);
			System.out.println("Cycling");
		}
		
		window.setNumberEnd(false);

		booleanClear(window);
	}
	
	public void booleanClear(mainWindow window){
		//Just resets all the booleans. It's used in the syntax error and when isCycling is off.
		window.setDiv(false);window.setAdd(false);window.setMult(false);window.setSubtract(false);window.setSquare(false);window.setRoot(false);window.setCos(false);window.setSin(false);window.setTan(false);window.setNumberEnd(false);window.setCycling(false);
	}

}
