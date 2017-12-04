package com.example.administrator.coursedesign.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.coursedesign.R;

import java.util.LinkedList;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManualTwentyfoutActivity extends AppCompatActivity {

    @BindView(R.id.numberOne)
    EditText numberOne;
    @BindView(R.id.numberTwo)
    EditText numberTwo;
    @BindView(R.id.numberThree)
    EditText numberThree;
    @BindView(R.id.numberFour)
    EditText numberFour;
    @BindView(R.id.result)
    EditText result;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.back)
    Button back;

    char[]  operator = {'+','-','*','/'};
    String expression;
    int[] number = new int[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_twentyfout);
        ButterKnife.bind(this);
        initListener();//初始化响应事件
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //得出答案按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(numberOne.getText().toString()) || "".equals(numberTwo.getText().toString()) || "".equals(numberThree.getText().toString()) || "".equals(numberFour.getText().toString())) {
                    showToast("请在4个框中全部填入数字");
                } else if (!comforjug(numberOne.getText().toString(), numberTwo.getText().toString(), numberThree.getText().toString(), numberFour.getText().toString())) {
                    showToast("请输入1到13间的数值");
                } else {
                    number[0] = Integer.parseInt(numberOne.getText().toString());
                    number[1] = Integer.parseInt(numberTwo.getText().toString());
                    number[2] = Integer.parseInt(numberThree.getText().toString());
                    number[3] = Integer.parseInt(numberFour.getText().toString());
                    result.setText(createExpression(number));
                    result.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        //清空重填按钮
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOne.setText("");
                numberTwo.setText("");
                numberThree.setText("");
                numberFour.setText("");
                result.setText("");
            }
        });

        //回上界面按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
                finish();
            }
        });

    }

    /**
     * 显示Toast信息
     */
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 页面跳转
     */
    public void openActivity(Class<?> clz) {
        openActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     */
    public void openActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 判断所输入的值是否为1到13
     */
    public boolean comforjug(String one, String two, String three, String four) {
        String[] numberJuge = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};

        for (int i=0; i<numberJuge.length; i++) {
            if (one.equals(numberJuge[i])) {
                break;
            }
            if (i == numberJuge.length-1){
                return false;
            }
        }

        for (int i=0; i<numberJuge.length; i++) {
            if (two.equals(numberJuge[i])) {
                break;
            }
            if (i == numberJuge.length-1){
                return false;
            }
        }

        for (int i=0; i<numberJuge.length; i++) {
            if (three.equals(numberJuge[i])) {
                break;
            }
            if (i == numberJuge.length-1){
                return false;
            }
        }

        for (int i=0; i<numberJuge.length; i++) {
            if (four.equals(numberJuge[i])) {
                break;
            }
            if (i == numberJuge.length-1){
                return false;
            }
        }

        return true;
    }

    /**
     * 通过卡片上展示的数值构造一个表达式
     */
    public String createExpression(int[] noCar) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < noCar.length; i++) {
            noCar[i] = noCar[i] % 13;
            if(noCar[i] == 0) {
                noCar[i] = 13;
            }
            list.add(noCar[i]);
        }

        int[] newArray = new int[noCar.length];

        //交换各个数值的位置
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (b == a) {
                    continue;
                }

                for (int c = 0; c < 4; c++) {
                    if (c == a || c == b) {
                        continue;
                    }

                    for (int d = 0; d < 4; d++) {
                        if (d == a || d == b || d == c) {
                            continue;
                        }

                        newArray[a] = noCar[0];
                        newArray[b] = noCar[1];
                        newArray[c] = noCar[2];
                        newArray[d] = noCar[3];
                    }
                }
                if (SimpleExpression(newArray)) {
                    return expression;
                }
            }
        }
        return "no solution";
    }

    /**
     * 通过以上的数值的排列方式加上当前的运算符构造出简单的运算式
     */
    public boolean SimpleExpression(int[] operand){
        String[] simpleExpression = new String[7];

        //将运算符插入
        simpleExpression[0] = operand[0] + "";
        simpleExpression[2] = operand[1] + "";
        simpleExpression[4] = operand[2] + "";
        simpleExpression[6] = operand[3] + "";


        for (int j = 0; j < operator.length; j++) {
            simpleExpression[1] = operator[j] + "";
            for(int j1 = 0; j1 < operator.length; j1++){
                simpleExpression[3] = operator[j1] + "";
                for(int j2 = 0; j2 < operator.length; j2++){
                    simpleExpression[5] = operator[j2] + "";
                    if(standardExpression(simpleExpression)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 通过以上的简单运算式，添加不同形式的括号构造出复杂的运算式
     */
    public boolean standardExpression(String[] sE){
        String[] standardexpression = new String[7];
        standardexpression[0] = sE[0] + sE[1] + sE[2] + sE[3] + sE[4] + sE[5] + sE[6];
        standardexpression[1] = "(" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + sE[4] + sE[5] + sE[6];
        standardexpression[2] = "(" + sE[0] + sE[1] + sE[2] + sE[3] + sE[4] + ")" + sE[5] + sE[6];
        standardexpression[3] =  sE[0] + sE[1] +"("+sE[2] + sE[3] + sE[4] + ")" + sE[5] + sE[6];
        standardexpression[4] = "(" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + "(" + sE[4] + sE[5] + sE[6] + ")";
        standardexpression[5] = "((" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + sE[4]  + ")" + sE[5] + sE[6];
        standardexpression[6] = "(" + sE[0] + sE[1] + "(" +sE[2] + sE[3] + sE[4]  + "))" + sE[5] + sE[6];
        for (int i = 0; i < standardexpression.length; i++) {
            System.out.println(standardexpression[i]);
            if(evaluateExpression(standardexpression[i]) == 24.0){
                expression = standardexpression[i];
                return true;
            }
        }
        return false;
    }

    /**
     * 书上源码，将这个计算式的所有内容依次加入栈之中
     */
    public double evaluateExpression(String expression) {
        // Create operandStack to store operands
        Stack<Double> operandStack
                = new Stack<Double>();

        // Create operatorStack to store operators
        Stack<Character> operatorStack
                = new Stack<Character>();

        // Extract operands and operators
        java.util.StringTokenizer tokens =
                new java.util.StringTokenizer(expression, "()+-/*", true);

        // Phase 1: Scan tokens
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim(); // Extract a token
            if (token.length() == 0) // Blank space
                continue; // Back to the while loop to extract the next token
            else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
                // Process all +, -, *, / in the top of the operator stack
                while (!operatorStack.isEmpty() &&
                        (operatorStack.peek() == '+' ||
                                operatorStack.peek() == '-' ||
                                operatorStack.peek() == '*' ||
                                operatorStack.peek() == '/')) {
                    processAnOperator(operandStack, operatorStack);
                }

                // Push the + or - operator into the operator stack
                operatorStack.push(token.charAt(0));
            }
            else if (token.charAt(0) == '*' || token.charAt(0) == '/') {
                // Process all *, / in the top of the operator stack
                while (!operatorStack.isEmpty() &&
                        (operatorStack.peek() == '*' ||
                                operatorStack.peek() == '/')) {
                    processAnOperator(operandStack, operatorStack);
                }

                // Push the * or / operator into the operator stack
                operatorStack.push(token.charAt(0));
            }
            else if (token.trim().charAt(0) == '(') {
                operatorStack.push('('); // Push '(' to stack
            }
            else if (token.trim().charAt(0) == ')') {
                // Process all the operators in the stack until seeing '('
                while (operatorStack.peek() != '(') {
                    processAnOperator(operandStack, operatorStack);
                }

                operatorStack.pop(); // Pop the '(' symbol from the stack
            }
            else { // An operand scanned
                // Push an operand to the stack
                operandStack.push(new Double(token));
            }
        }

        // Phase 2: process all the remaining operators in the stack
        while (!operatorStack.isEmpty()) {
            processAnOperator(operandStack, operatorStack);
        }

        // Return the result
        return operandStack.pop();
    }

    /**
     * 书上源码，更加之前的栈内容，判断计算算式
     */
    public void processAnOperator(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char op = operatorStack.pop();
        try {
            double op1 = operandStack.pop();
            double op2 = operandStack.pop();

            if (op == '+') {
                operandStack.push(op2 + op1);
            } else if (op == '-') {
                operandStack.push(op2 - op1);
            } else if (op == '*') {
                operandStack.push(op2 * op1);
            } else if (op == '/' && op1 != 0) {
                operandStack.push(op2 / op1);
            }
            //若除数为0则返回结果为0
            else if (op == '/' && op1 == 0) {
                operandStack.push(0.0);
            }
        } catch (Exception e) {
            showToast("请按规则选择运算符号和卡牌");
        }
    }


}
