package com.example.administrator.coursedesign.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.coursedesign.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dailiwen
 */
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
    Button version3;
    @BindView(R.id.allcount)
    TextView allcountText;
    @BindView(R.id.count)
    TextView countText;
    @BindView(R.id.rate)
    TextView rateText;


    String expression;
    int[] number = new int[4];
    //所有计算情况
    int count1 = 0;
    //所有答案为24情况
    int count2 = 0;


    // 将4个操作数简化为3个操作数时，将这三个操作数存放在temp1中
    private static double[] temp1 = new double[3];
    // 将3个操作数简化为2个操作数时，将这两个操作数存放在temp2中
    private static double[] temp2 = new double[2];

    private static double sum;
    private static int[] cardArray = new int[4];
    private static char[] operator = { '+', '-', '*', '/' };
    private static double[] scard = new double[4];
    private static boolean isCorrect = false;

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

        //计算版本3按钮
        version3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allResult();
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


    /**
     * 列出所有不重复的组合
     */
    public void allResult() {
        int allcount = 0;
        int count = 0;
        int times = 0;
        // 这里只考虑对数据
        // 为了减少运算量，避免重复的排列组合，比如1,2,3,4与1,2,4,3，这样就是重复的，采用了j = i, k = j这种来控制循环
        for (int i = 1; i < 14; i++) {
            for (int j = i; j < 14; j++) {
                for (int k = j; k < 14; k++) {
                    for (int l = k; l < 14; l++) {
                        //判断花色重复的数量
                        times = times(i, j, k, l);
                        allcount += times;
                        // 判断该表达式是否正确
                        if (getExpression(i, j, k, l) != null) {
                            count += times;
                        }
                    }
                }
            }
        }

        double rate = (double) count / allcount;
        allcountText.setText("所有可能的组合共有：" + allcount);
        countText.setText("结果为24的组合共有： " + count);
        rateText.setText("成功的几率是:" + rate);
    }

    /**
     * 方法简述：输入4个数判断其能产生多少个结果等于24的算式，就是选出来的四个数一个会有多少种排列组合，这样是为了得到更多
     */
    public static ArrayList<String> getExpression(int num1, int num2, int num3, int num4) {
        //声明一个ArrayList，用于存放所有可能的表达式
        ArrayList<String> expressionList = new ArrayList<String>();
        for (int a = 0; a < 4; a++)
            for (int b = 0; b < 4; b++) {
                // 这些判断是为了防止产生错误的组合，比如传进来的数是1,4,6,8，如果没有判断，就可能导致产生1,1,6,8这种组合
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
                        // 让四个数产生了不同的组合，比如1,4,6,8--8,1,6,4等
                        cardArray[a] = num1;
                        cardArray[b] = num2;
                        cardArray[c] = num3;
                        cardArray[d] = num4;

                        for (int m = 0; m < 4; m++) {
                            // 这里转换为double类型是为了方便后面的除法运算
                            scard[m] = (double) cardArray[m] % 13;
                            if (cardArray[m] % 13 == 0) {
                                scard[m] = 13;
                            }
                        }

                        // 进行一次搜索
                        expressionList = search();

                        // 如果搜索出来的是正确的，那么将isCorrect置为false，便于下次使用
                        if (isCorrect) {
                            isCorrect = false;
                            return expressionList;
                        }
                    }
                }
            }

        return null;
    }

    /**
     * 基本计算
     */
    private static double calcute(double number1, double number2, char operator) {
        if (operator == '+') {
            return number1 + number2;
        } else if (operator == '-') {
            return number1 - number2;
        } else if (operator == '*') {
            return number1 * number2;
        } else if (operator == '/' && number2 != 0) {
            return number1 / number2;
        } else {
            return -1;
        }
    }

    private static ArrayList<String> search() {

        //声明一个ArrayList，用于存放所有可能的表达式
        ArrayList<String> expressionList = new ArrayList<String>();
        // 第一次放置的符号（算术优先级最高）
        for (int i = 0; i < 4; i++) {

            // 第二次放置的符号（算术优先级次高）
            for (int j = 0; j < 4; j++) {

                // 第三次放置的符号（最后一个计算）
                for (int k = 0; k < 4; k++) {

                    // 首先计算的两个相邻数字，共有3种情况，相当于括号的作用，也就是各种优先级顺序组合
                    for (int m = 0; m < 3; m++) {
                        // 如果出现除数为零则表达式出错，结束此次循环
                        if (scard[m + 1] == 0 && operator[i] == '/') {
                            break;
                        }
                        // 从4个操作数中提取出两个数，然后将可能进行优先计算的两个数计算，然后得到值，注意：这里是优先级最高的运算符，也就是第一次放置的符号
                        temp1[m] = calcute(scard[m], scard[m + 1], operator[i]);
                        // 将其余两个没有进行计算的值赋值给temp1数组
                        temp1[(m + 1) % 3] = scard[(m + 2) % 4];
                        temp1[(m + 2) % 3] = scard[(m + 3) % 4];

                        // 先确定首先计算的两个数字，计算完成相当于剩下三个数，按顺序储存在temp1数组中
                        // 三个数字选出先计算的两个相邻数字，两种情况，相当于第二个括号
                        for (int n = 0; n < 2; n++) {
                            if (temp1[n + 1] == 0 && operator[j] == '/') {
                                break;
                            }
                            // 简化运算，将三个操作数简化为两个操作数，注意：这里是优先级最高的运算符，也就是第二次放置的符号
                            temp2[n] = calcute(temp1[n], temp1[n + 1], operator[j]);
                            temp2[(n + 1) % 2] = temp1[(n + 2) % 3];

                            if (temp2[1] == 0 && operator[k] == '/') {
                                break;
                            }

                            // 将两个操作数简化为一个操作数，注意：这里是优先级最高的运算符，也就是第三次放置的符号
                            sum = calcute(temp2[0], temp2[1], operator[k]);

                            // 如果能够24，那么将该算式输出来
                            if (sum == 24) {
                                isCorrect = true;
                                String expression = "";

                                // 根据组合列出算式
                                if (m == 0 && n == 0) {
                                    expression = "((" + (int) scard[0] + operator[i] + (int) scard[1] + ")"
                                            + operator[j] + (int) scard[2] + ")" + operator[k] + (int) scard[3] + "="
                                            + (int) sum;
                                } else if (m == 0 && n == 1) {
                                    expression = "(" + (int) scard[0] + operator[i] + (int) scard[1] + ")" + operator[k]
                                            + "(" + (int) scard[2] + operator[j] + (int) scard[3] + ")=" + (int) sum;
                                } else if (m == 1 && n == 0) {
                                    expression = "(" + (int) scard[0] + operator[j] + "(" + (int) scard[1] + operator[i]
                                            + (int) scard[2] + "))" + operator[k] + (int) scard[3] + "=" + (int) sum;
                                } else if (m == 2 && n == 0) {
                                    expression = "(" + (int) scard[0] + operator[j] + (int) scard[1] + ")" + operator[k]
                                            + "(" + (int) scard[2] + operator[i] + (int) scard[3] + ")=" + (int) sum;
                                } else if (m == 2 && n == 0) {
                                    expression = (int) scard[0] + operator[k] + "(" + (int) scard[1] + operator[j] + "("
                                            + (int) scard[2] + operator[i] + (int) scard[3] + "))=" + (int) sum;
                                }

                                System.out.println(expression);
                                expressionList.add(expression);
                            }
                        }
                    }
                }
            }
        }
        return expressionList;
    }

    /**
     * 利用对花色的排列组合，避免花色重复，大量地减少运算量，提高效率
     */
    private static int times(int i,int j,int k,int l){
        //利用set判断有多少种重复
        Set<Integer> set =new HashSet<Integer>();
        set.add(i);
        set.add(j);
        set.add(k);
        set.add(l);
        //当4个数的数字全部一样时，只可能有一种花色的组合，比如红桃6，梅花6，方块6，黑桃6
        if(set.size()==1){
            return 1;
        }
        //当4个数中，有两个数相同，其余的数都不相同时，就有C4取2乘以C4取1乘以C4取1种可能的花色组合，比如红桃6，梅花6，方块7，黑桃8
        else if(set.size()==3){
            return 96;
        }
        //当4个数全部不同时，就有C4取1乘以C4取1乘以C4取1乘以C4取1种（256）情况
        else if(set.size()==4){
            return 256;
        }
        else{
            //当4个数中，两两相同时，同样的，利用排列组合，一共有C4取2乘以C4取2，共36种情况
            if((i==j&&k==l)||(i==k&&j==l)){
                return 36;
            }
            //当4个数中有三个数相同，另外一个数不同时
            else {
                return 16;
            }
        }
    }
}
