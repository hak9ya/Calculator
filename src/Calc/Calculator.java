package Calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame {
	
	private JTextField textField;
	private ArrayList<String> arrList = new ArrayList<String>();
	private String value = "";
	private String prev_operation = "";
	private int pointCheck = 0;
	
	public Calculator() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(Color.WHITE);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setFont(new Font("Courier", Font.BOLD, 60));
		textField.setBounds(8, 10, 367, 160);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5,4,5,5));
		buttonPanel.setBounds(8, 180, 367, 300);
		
		String button_names[] = {"CE", "C", "←", "÷", "7", "8", "9", "×", "4", "5", "6", "-", "1", "2", "3", "+", "0", "00", ".", "="};
		JButton buttons[] = new JButton[button_names.length];
		
		for(int i = 0; i < button_names.length; i++) {
			buttons[i] = new JButton(button_names[i]);
			buttons[i].setFont(new Font("Courier", Font.BOLD, 20));
			if((i >= 4 && i <= 6) || (i >= 8 && i <= 10) ||
			   (i >= 12 && i <= 14) || (i >= 16 && i <= 18)) buttons[i].setBackground(Color.BLACK);
			else buttons[i].setBackground(Color.GRAY);
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setBorderPainted(false);
			buttons[i].addActionListener(new PadActionListener());
			buttonPanel.add(buttons[i]);
		}
		
		add(textField);
		add(buttonPanel);
		
		
		setSize(400, 530);
		setTitle("계산기");
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class PadActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String operation = e.getActionCommand();			
			
			if(operation.equals("C") || operation.equals("CE")) {
				textField.setText("");
				pointCheck = 0;
			}else if(operation.equals(".") && textField.getText().equals("")) {
				textField.getText();
			}else if(operation.equals(".") && pointCheck == 0)  {
				textField.setText(textField.getText() + e.getActionCommand());
				pointCheck = 1;
			}else if(operation.equals(".") && pointCheck == 1) {
				textField.getText();
			}
			else if(operation.equals("=")) {
				String result = Double.toString(calculate(textField.getText()));
				textField.setText("" + result);
				value = "";
				pointCheck = 0;
			}else if(operation.equals("←")) {
				setBackSpace(getBackSpace().substring(0, getBackSpace().length() - 1));
			}else if(operation.equals("+") || operation.equals("-") || operation.equals("×") || operation.equals("÷")) {				
				if(textField.getText().equals("") && operation.equals("-")) {
					textField.setText(textField.getText() + e.getActionCommand());
				}else if(!textField.getText().equals("") && !prev_operation.equals("+") && !prev_operation.equals("-") && !prev_operation.equals("×") && !prev_operation.equals("÷")) {
					textField.setText(textField.getText() + e.getActionCommand());
					pointCheck = 0;
				}
			} else {
				textField.setText(textField.getText() + e.getActionCommand());
			}
			prev_operation = e.getActionCommand();

			}

		}
	
	
	private void textSave(String inputText) {
		arrList.clear();
		
		for(int i = 0; i < inputText.length(); i++) {
			char ch = inputText.charAt(i);
			
			if(ch == '+' || ch == '-' || ch == '×' || ch == '÷') {
				arrList.add(value);
				value = "";
				arrList.add(ch + "");
			} else {
				value = value + ch;
			}
		}
		arrList.add(value);
		arrList.remove("");
	}
	
	public double calculate(String inputText) {
		textSave(inputText);
		
		double prevResult = 0;
		double nowValue = 0;
		String func = "";
		
		for(int i = 0; i < arrList.size(); i++) {
			String s = arrList.get(i);
			
			if(s.equals("+")) {
				func = "add";
			}else if(s.equals("-")) { 
				func = "sub";
			}else if(s.equals("×")) {
				func = "mul";
			}else if(s.equals("÷")) {
				func = "div";
			}else {
				if(func.equals("mul") || func.equals("div") && !s.equals("+") && !s.equals("-") && !s.equals("×") && !s.equals("÷")) {
					Double one = Double.parseDouble(arrList.get(i - 2));
					Double two = Double.parseDouble(arrList.get(i));
					Double result = 0.0;
					
					if(func.equals("mul")) {
						result = one * two;
					}else if(func.equals("div")) {
						result = one / two;
					}
					
					arrList.add(i+1, Double.toString(result));
					
					for(int j = 0; j < 3; j++) {
						arrList.remove(i - 2);
					}
					
					i -= 2;
				}
			}
		}
		
		for(String s : arrList) {
			if(s.equals("+")) {
				func = "add";
			} else if(s.equals("-")) {
				func = "sub";
			} else {
				nowValue = Double.parseDouble(s);	//double형 변환
				if(func.equals("add")) {
					prevResult += nowValue;
				} else if(func.equals("sub")) {
					prevResult -= nowValue;
				} else {
					prevResult = nowValue;
				}
			}
			prevResult = Math.round(prevResult * 100000) / 100000.0; 
		}
		return prevResult;
	}
	
	private void setBackSpace(String inputText) {
		textField.setText(inputText);
	}
	
	private String getBackSpace() {
		return textField.getText();
	}
	
	public static void main(String[] args) {
		new Calculator();
	}
}

