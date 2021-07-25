import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
    private final String[] KEYS = {"7", "8", "9", "/", "sqrt", "4", "5", "6",
    "*", "%", "1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "="};
    private final String[] COMMAND = {"Backspace", "CE", "C"};
    private final String[] M = {" ", "MC", "MR", "MS", "M+"};
    private JButton keys[]  = new JButton[KEYS.length];
    private JButton commands[] = new JButton[COMMAND.length];
    private JButton m[] = new JButton[M.length];
    private JTextField resultText = new JTextField("0");

    private boolean firstDigit = true;
    private double resultNum = 0.0;
    private String operator = "=";
    private boolean operateValidFlag = true;

    public Calculator() {
        super();
        init();
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("计算器");
        this.setLocation(500, 300);
        this.setResizable(false);
        this.pack();
    }

    private void init() {
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        resultText.setEditable(false);
        resultText.setBackground(Color.white);

        JPanel calckeysPanel = new JPanel();
        calckeysPanel.setLayout(new GridLayout(4, 5, 3, 4));
        for(int i = 0; i < KEYS.length; i++){
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setForeground(Color.blue);
        }

        keys[3].setForeground(Color.red);
        keys[3].setForeground(Color.red);
        keys[3].setForeground(Color.red);
        keys[3].setForeground(Color.red);
        keys[19].setForeground(Color.red);

        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new GridLayout(1,3, 3, 3));
        for(int i = 0; i < COMMAND.length; i++){
            commands[i] = new JButton(COMMAND[i]);
            commandPanel.add(commands[i]);
            commands[i].setForeground(Color.blue);
        }

        JPanel calmsPanel = new JPanel();
        calmsPanel.setLayout(new GridLayout(5,1, 3, 3));
        for(int i = 0; i < M.length; i++){
            m[i] = new JButton(M[i]);
            calmsPanel.add(m[i]);
            m[i].setForeground(Color.blue);
        }

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(3,3));
        panel1.add("North", commandPanel);
        panel1.add("West", calckeysPanel);

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add("Center", resultText);

        getContentPane().setLayout(new BorderLayout(3,5));
        getContentPane().add("North", top);
        getContentPane().add("Center", panel1);
        getContentPane().add("West", calmsPanel);

        for(int i=0;i<KEYS.length;i++) {
            keys[i].addActionListener(this);
        }
        for(int i=0;i<COMMAND.length;i++) {
            commands[i].addActionListener(this);
        }
        for(int i=0;i<M.length;i++) {
            m[i].addActionListener(this);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        if(label.equals(COMMAND[0])) {
            handleBackspace();
        } else if (label.equals(COMMAND[1])) {
            resultText.setText("0");
        } else if (label.equals(COMMAND[2])) {
            handleC();
        } else if ("0123456789.".indexOf(label)>=0) {
            handleNumber(label);
        } else {
            handleOperator(label);
        }
    }

    private void handleBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if(i > 0) {
            text = text.substring(0,i - 1);
            if(text.length() == 0) {
                resultText.setText("0");
                firstDigit = true;
                operator = "=";
            } else {
                resultText.setText(text);
            }
        }
    }

    private void handleNumber(String key) {
        if(firstDigit) {
            resultText.setText(key);
        } else if ((key.equals("."))&&(resultText.getText().indexOf(".")<0)) {
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")) {
            resultText.setText(resultText.getText() + key);
        }
        firstDigit = false;
    }

    private void handleC() {
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
    }

    private void handleOperator(String key) {
        if(operator.equals("/")) {
            if(getNumberFromText() == 0.0) {
                operateValidFlag = false;
                resultText.setText("除数不能为0");
            }else {
                resultNum /= getNumberFromText();
            }
        }else if(operator.equals("1/x")){
            if(resultNum == 0) {
                operateValidFlag = false;
                resultText.setText("0没有倒数");
            }else {
                resultNum = 1/resultNum;
            }
        }else if(operator.equals("+")){
            resultNum += getNumberFromText();
        }else if(operator.equals("-")){
            resultNum -= getNumberFromText();
        }else if(operator.equals("*")){
            resultNum *= getNumberFromText();
        }else if(operator.equals("sqrt")){
            resultNum = Math.sqrt(resultNum);
        }else if(operator.equals("%")){
            resultNum = resultNum/100;
        }else if(operator.equals("+/-")){
            resultNum = resultNum*(-1);
        }else if(operator.equals("=")){
            resultNum = getNumberFromText();
        }
        if(operateValidFlag) {
            long t1;
            double t2;
            t1=(long)resultNum;
            t2 = resultNum - t1;
            if(t2 == 0) {
                resultText.setText(String.valueOf(t1));
            }else {
                resultText.setText(String.valueOf(resultNum));
            }
        }
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }

    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();
        }catch(NumberFormatException e) {
        }
        return result;
    }
    public static void main(String[] args) {
        Calculator calculatorl = new Calculator();
        calculatorl.setVisible(true);
        calculatorl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

