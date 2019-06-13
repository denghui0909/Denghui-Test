/*
5 10
7 2 6 5 4
*/

import java.util.Scanner;

public class ZiJiShu {

    private static int n,c;		
    private static int[] x,bestx,w;
    private static int r,cw,bestw;

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        while (true){
            bestw = 0;
            cw = 0;		//当前集装箱的总重量
            r = 0;		//剩余集装箱重量

            n = input.nextInt();		//集装箱数n
            c = input.nextInt();		//载重量c

            w = new int[n+1];		
            x = new int[n+1];
            bestx = new int[n+1];

            for(int i=1; i<=n; i++)
                w[i] = input.nextInt();

            for(int i=1; i<=n; i++)
                r += w[i];		

            backtrack(1);		//回溯法

            output();
        }
    }

    private static void backtrack(int t){
        if(t > n) record();		
        else
            for(int i=0; i<=1; i++){
                x[t] = i;		//是否装入集装箱t，值为0时不装入，值为1时装入
                if(constraint(t) && bound(t)){		//如果没有超过上界，并且有可能取得最优值
                    change(t);
                    backtrack(t+1);
                    restore(t);
                }
            }
    }

    private static void record(){
        if(cw > bestw){
            bestw = cw;		//最大装载量
            for(int i=1; i<=n; i++)
                bestx[i] = x[i];	//路径（集装箱i是否装入）
        }
    }

    /* 限界函数
     * 如果装入集装箱t，超过了载重量c，则舍去这一分支
     */
    private static boolean constraint(int t){	
    	/* 如果不装入集装箱t
    	 * 或者 装入集装箱t时，没有超过载重量c
    	 * 返回true，进一步搜索
    	 */
        if(x[t]==0 || x[t]==1 && cw+w[t]<=c) 
        	return true;
        else 
        	return false;
    }

    /* 减枝函数
     * 如果得不到最优解，舍去这一分支
     */
    private static boolean bound(int t){
    	/* 如果装入集装箱t
    	 * 或者 不装入集装箱t时，目前的装载量cw + 剩余的未装入的集装箱重量r > 当前最优装载量
    	 * 则返回true，进一步搜索
    	 */
        if(x[t]==1 || x[t]==0 && cw+r-w[t]>bestw)  
        	return true;
        else 
        	return false;
    }

    private static void change(int t){
        if(x[t] == 1) 		//如果装入集装箱t，更新当前的载重量cw
        	cw+=w[t];
        r -= w[t];			//剩余未考虑的集装箱的重量减去集装箱t的重量
    }

    //回溯
    private static void restore(int t){
        if(x[t] == 1)		//如果装入集装箱t，当前的载重量cw减去集装箱t的重量
        	cw-=w[t];
        r += w[t];			//将集装箱t的重量加入到剩余未考虑的集装箱的重量
        
    }

    private static void output(){
        System.out.println("装入集装箱的最大重量为：" + bestw);
        System.out.print("所装入的集装箱为：");
        for(int i=1; i<=n; i++)
            System.out.print(bestx[i]+" ");
    }
}
