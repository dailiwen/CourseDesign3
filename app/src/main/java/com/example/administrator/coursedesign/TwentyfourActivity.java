package com.example.administrator.coursedesign;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwentyfourActivity extends AppCompatActivity {

    @BindView(R.id.changeCard)
    Button changeCard;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.leftbrackets)
    Button leftBrackets;
    @BindView(R.id.rightbrackets)
    Button rightBrackets;
    @BindView(R.id.addition)
    Button addition;
    @BindView(R.id.subtraction)
    Button subtraction;
    @BindView(R.id.multiply)
    Button multiply;
    @BindView(R.id.division)
    Button division;
    @BindView(R.id.cardOne)
    ImageButton cardOne;
    @BindView(R.id.cardTow)
    ImageButton cardTow;
    @BindView(R.id.cardThree)
    ImageButton cardThree;
    @BindView(R.id.cardFour)
    ImageButton cardFour;
    @BindView(R.id.result)
    EditText result;
    @BindView(R.id.verification)
    Button verification;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.autoresult)
    EditText autoresult;
    @BindView(R.id.generateAnswer)
    Button generateAnswer;
    @BindView(R.id.version3)
    TextView version3;

    char[]  operator = {'+','-','*','/'};
    String expression;
    int[] number = new int[4];
    //所有计算情况
    int count1 = 0;
    //所有答案为24情况
    int count2 = 0;

    //生成图片索引
    private static int[] imgs = {
            R.drawable.ic_launcher_background,R.drawable.card1,R.drawable.card2,R.drawable.card3,R.drawable.card4,R.drawable.card5,R.drawable.card6,R.drawable.card7,
            R.drawable.card8,R.drawable.card9,R.drawable.card10,R.drawable.card11,R.drawable.card12,R.drawable.card13,R.drawable.card14,
            R.drawable.card15,R.drawable.card16,R.drawable.card17,R.drawable.card18,R.drawable.card19,R.drawable.card20,R.drawable.card21,
            R.drawable.card22,R.drawable.card23,R.drawable.card24,R.drawable.card25,R.drawable.card26,R.drawable.card27,R.drawable.card28,
            R.drawable.card29,R.drawable.card30,R.drawable.card31,R.drawable.card32,R.drawable.card33,R.drawable.card34,R.drawable.card35,
            R.drawable.card36,R.drawable.card37,R.drawable.card38,R.drawable.card39,R.drawable.card40,R.drawable.card41,R.drawable.card42,
            R.drawable.card43,R.drawable.card44,R.drawable.card45,R.drawable.card46,R.drawable.card47,R.drawable.card48,R.drawable.card49,
            R.drawable.card50,R.drawable.card51,R.drawable.card52
    };

    //牌是否被选择判断
    boolean[] cardChoose = {false,false,false,false};

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.twentyfour_games);
        ButterKnife.bind(this);
        initComponent();//初始化相关组件
        initListener();//初始化响应事件
    }

    /**
     * 初始化相关组件
     */
    private void initComponent() {
        number = arrayCreate();

        cardOne.setImageDrawable(getResources().getDrawable(imgs[number[0]]));
        cardTow.setImageDrawable(getResources().getDrawable(imgs[number[1]]));
        cardThree.setImageDrawable(getResources().getDrawable(imgs[number[2]]));
        cardFour.setImageDrawable(getResources().getDrawable(imgs[number[3]]));

        long startTime = System.currentTimeMillis();
        int[] operand = new int[4];

//        for (int a=0; a<13; a++) {
//            operand[0] = a + 1;
//            for (int a1=0; a1<13; a1++) {
//                operand[1] = a1 + 1;
//                for (int a2=0; a2<13; a2++) {
//                    operand[2] = a2 + 1;
//                    for (int a3=0; a3<13; a3++) {
//                        operand[3] = a3 + 1;
//                        if (findA(operand)) {
//                            continue;
//                        }
//                    }
//                }
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        Log.d("test", "" + (endTime - startTime));
//        version3.setText(operand[0] + "");
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //更换牌组按钮
        changeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                autoresult.setText("");
                for (int i =0; i<cardChoose.length; i++) {
                    cardChoose[i] = false;
                }
                number = arrayCreate();
                cardOne.setImageDrawable(getResources().getDrawable(imgs[number[0]]));
                cardTow.setImageDrawable(getResources().getDrawable(imgs[number[1]]));
                cardThree.setImageDrawable(getResources().getDrawable(imgs[number[2]]));
                cardFour.setImageDrawable(getResources().getDrawable(imgs[number[3]]));
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

        //左括号
        leftBrackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(result.getText() + "(");
            }
        });

        //右括号
        rightBrackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(result.getText() + ")");
            }
        });

        //加号
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(result.getText() + "+");
            }
        });

        //减号
        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(result.getText() + "-");
            }
        });

        //乘号
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(result.getText() + "*");
            }
        });

        //除号
        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(result.getText() + "/");
            }
        });

        //第一张牌
        cardOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardChoose[0] == false) {
                    int temp = number[0] % 13;
                    if (temp == 0) {
                        temp = 13;
                    }
                    result.setText(result.getText() + "" + temp);
                    cardChoose[0] = true;
                } else {
                    showToast("您已经选择过这张牌");
                }
            }
        });

        //第二张牌
        cardTow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardChoose[1] == false) {
                    int temp = number[1] % 13;
                    if (temp == 0) {
                        temp = 13;
                    }
                    result.setText(result.getText() + "" + temp);
                    cardChoose[1] = true;
                } else {
                    showToast("您已经选择过这张牌");
                }
            }
        });

        //第三张牌
        cardThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardChoose[2] == false) {
                    int temp = number[2] % 13;
                    if (temp == 0) {
                        temp = 13;
                    }
                    result.setText(result.getText() + "" + temp);
                    cardChoose[2] = true;
                } else {
                    showToast("您已经选择过这张牌");
                }
            }
        });

        //第四张牌
        cardFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardChoose[3] == false) {
                    int temp = number[3] % 13;
                    if (temp == 0) {
                        temp = 13;
                    }
                    result.setText(result.getText() + "" + temp);
                    cardChoose[3] = true;
                } else {
                    showToast("您已经选择过这张牌");
                }
            }
        });

        //验证按钮
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardChoose[0] || !cardChoose[1] || !cardChoose[2] || !cardChoose[3]) {
                    showToast("请选择上面的所有卡牌");
                }
                else if (!conformJudge(result.getText().toString())) {
                    showToast("请选择3个运算符");
                }
                else {
                    if (evaluateJudge(String.valueOf(result.getText()))) {
                        showToast("恭喜您，计算正确");
                    } else {
                        showToast("很遗憾，计算错误");
                    }
                }
            }
        });

        //清空按钮
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i =0; i<cardChoose.length; i++) {
                    cardChoose[i] = false;
                }
                result.setText("");
            }
        });

        //生成答案按钮
        generateAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoresult.setText(createExpression(number));
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
     * 随机挑选4个数，通过数来选择牌，自动生成牌组
     */
    public int[] arrayCreate() {
        Set set = new HashSet();
        while(true) {
            if (set.size() == 4) {
                break;
            }
            int count = (int) (1 + Math.random() * 52);
            set.add(count);
        }
        Iterator iter = set.iterator();
        int[] temp = new int[4];
        for (int i=0;iter.hasNext();i++) {
            temp[i] = (int) iter.next();
        }
        return temp;
    }

    /**
     * 判断运算结果是否为24
     */
    public boolean evaluateJudge(String input) {
        double result = evaluateExpression(input);
        if (result == 24.0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否输入这么多的运算符号
     */
    public boolean conformJudge(String writeDown) {
        int count = 0;
        for (int i=0; i<writeDown.length(); i++) {
            if (writeDown.charAt(i) == '+' || writeDown.charAt(i) == '-' || writeDown.charAt(i) == '*' || writeDown.charAt(i) == '/') {
                count++;
            }
        }
        if (count == 3) {
            return true;
        } else {
            return false;
        }
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

//    /**
//     * 通过以上的数值的排列方式加上当前的运算符构造出简单的运算式
//     */
//    public boolean findA(int[] operand){
//        String[] simpleExpression = new String[7];
//
//        //将运算符插入
//        simpleExpression[0] = operand[0] + "";
//        simpleExpression[2] = operand[1] + "";
//        simpleExpression[4] = operand[2] + "";
//        simpleExpression[6] = operand[3] + "";
//
//
//        for (int j = 0; j < operator.length; j++) {
//            simpleExpression[1] = operator[j] + "";
//            for(int j1 = 0; j1 < operator.length; j1++){
//                simpleExpression[3] = operator[j1] + "";
//                for(int j2 = 0; j2 < operator.length; j2++){
//                    simpleExpression[5] = operator[j2] + "";
//                    if (findAll(simpleExpression)) {
//                        continue;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 版本3中的所有计算情况找出
//     */
//    public boolean findAll(String[] sE){
//        String[] standardexpression = new String[7];
//        standardexpression[0] = sE[0] + sE[1] + sE[2] + sE[3] + sE[4] + sE[5] + sE[6];
//        standardexpression[1] = "(" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + sE[4] + sE[5] + sE[6];
//        standardexpression[2] = "(" + sE[0] + sE[1] + sE[2] + sE[3] + sE[4] + ")" + sE[5] + sE[6];
//        standardexpression[3] =  sE[0] + sE[1] +"("+sE[2] + sE[3] + sE[4] + ")" + sE[5] + sE[6];
//        standardexpression[4] = "(" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + "(" + sE[4] + sE[5] + sE[6] + ")";
//        standardexpression[5] = "((" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + sE[4]  + ")" + sE[5] + sE[6];
//        standardexpression[6] = "(" + sE[0] + sE[1] + "(" +sE[2] + sE[3] + sE[4]  + "))" + sE[5] + sE[6];
//        for (int i = 0; i < standardexpression.length; i++) {
//            count1++;
//            if(evaluateExpression(standardexpression[i]) == 24.0){
//                count2++;
//            }
//            Log.d("test", count1 + "");
//        }
//        return true;
//    }

}
