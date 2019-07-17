import java.util.Scanner;

public class ZiJiShu {

    private static int n,k,c;				//n：物品个数；k：约束条件个数；c:最大价值；
    private static int[] w,sum,x,r;		   	//w[i]表示第i个物品价值；sum[i]表示第i个约束条件；x[i]表示第i个物品是否装入背包；r[i]表示第i个约束条件的剩余装载空间
    private static int[][] attribute;		//attribute[j][i]记录第i个物品的第j种属性
    private static int[] cw,bestx;			//cw[i]表示当前第i的种属性的总重量；bestx数组用于存储最佳路径；
    private static int currentsum = 0,bestsum = 0,rsum = 0;	//currentsum为当前价值；bestsum为最优价值；rsum为剩余未考虑的物品的总价值
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
      
        n = input.nextInt();		//输入物品个数n
        k = input.nextInt();		//输入约束条件个数k
        c = input.nextInt();		//输入最大价值c

        w = new int[n+1];		//w[i]表示第i个物品价值；
        x = new int[n+1];		//bestx数组用于存储最佳路径；
        bestx = new int[n+1];
        attribute = new int [k+1][n+1];
        sum = new int[k+1];			//每个约束条件的上限
        r = new int[k+1];		//每个约束条件的剩余空间          
        cw = new int[k+1];		//c[i]表示当前第i的中属性的总重量
        
//      <!----------------数据输入部分----------------!>
        
        //输入物品价值
        for(int i=1; i<=n; i++)
        {
            w[i] = input.nextInt();		
            rsum  += w[i];		//更新剩余未考虑物品总价值
        }
        
        //输入每个物品的属性
        for(int j=1; j<=k; j++)
        {
        	for(int i=1; i<=n; i++)
            {
        		attribute[j][i] = input.nextInt();		
            }
        }
        
        //输入装载上限
        for(int i=1; i<=k; i++)
        {
            sum[i] = input.nextInt();		//sum[i]表示第i个约束条件上
        }
  
        //初始化每个约束条件的剩余重量（初始值即为当所有物品都装入背包时，每个约束条件的重量（上限））
        for(int i=1; i<=k; i++)
        {
        	for(int j=1; j<=n; j++)
            {
        		r[i] += attribute[i][j];		//第i个约束条件剩余重量
            }
        }
        
        backtrack(1);		//回溯法
        
        output();			//输出
        
    }

    //回溯法
	private static void backtrack(int t){
		
	    if(t > n)
	    	record();		
	    else
	    {
	        for(int i=0; i<=1; i++)
	        {
	            x[t] = i;		//是否装入物品t，值为0时不装入，值为1时装入
	            if(constraint(t) && bound(t))	//如果没有超过上界，并且有可能取得最优值
	            {		
	                change(t);				//change()方法改变当前各属性的值
	                backtrack(t+1);			//递归调用自己，进行深度搜索
	                restore(t);				//回溯法，向上层回溯
	            }
	        }
	    }
	    
	}

    //记录最佳路径
    private static void record(){
    	
    	//判断当前物品价值currentsum是否大于最大价值bestsum，如果大于则更新最大价值和物品装载路径bestx
    	if(currentsum > bestsum)	
    	{
    		bestsum = currentsum;	//更新最大价值bestsum
    		
	    	for(int i=1; i<n+1; i++)
        	{
        		bestx[i] = x[i];	//更新物品装载路径（物品i是否装入）
        	}
    	}
    	
    }

    /* 约束函数：constraint()
     * 若装入物品t，此时任何一项属性超过了该属性的上限值，则放弃装入物品t
     * 是否超过该属性的上限值有decide()方法判断
     */
    private static boolean constraint(int t){	
    	
    	/* 如果不装入物品t
    	 * 或者 装入物品t时，没有超过各属性的上限值
    	 * 返回true，进一步搜索
    	 */
    	
        if(x[t]==0 || (x[t]==1 && decide(t)))
        {
        	return true;
        }
        else 
        {
        	return false;
        }
        
    }
    
    //decide()方法判断各项属性是否超过上限值（即是否满足约束条件），如果超过（不满足）返回false
    private static boolean decide(int t){
    	
    	 for(int i =1;i<k+1;i++)
    	 {
    		 if(cw[i] + attribute[i][t] > sum[i])
    		 {
    			return false;
    		 } 
    	 }
    	 
         return true;
    }
    
    /* 限界函数:bound()
     * 如果得不到最优解，舍去这一分支
     */
    private static boolean bound(int t){
    	
    	/* 如果装入物品t，或者虽然不装入物品t，但是也有可能获取到最大价值
    	 * 即：目前的总价值currentsum + 剩余的未装入的物品价值rsum -物品t的价值w[t] > 当前最大价值bestsum
    	 * 返回true，进一步搜索
    	 */
    	
        if(x[t]==1 || x[t]==0 && currentsum + rsum - w[t] > bestsum)  
        {
        	return true;
        }
        else 
        {
        	return false;
        }
        
    }
    
    /* 如果各项都满足约束条件，并且有可能取得最优值，则调用change()方法
     * 更新每种属性的重量cw[i]、当前背包所装入物品的价值currentsum、剩余未考虑的各项属性的重量r[i]以及剩余物品的价值  rsum
     */
    private static void change(int t){
    	
        if(x[t] == 1) 		//如果装入了物品t，更新各项值
        {
	       	 for(int i =1;i<k+1;i++)
	       	 {
	       		 cw[i] += attribute[i][t];		//每种属性的重量cw[i]都加上物品t所对应的属性重量attribute[i][t]
	       	 }
	       	 
       		 currentsum += w[t];      		 	//当前背包所装入物品的价值currentsum加上去物品t的价值 w[t]
        }
        
        for(int i = 1;i < k+1;i++)
      	{
        	r[i] -= attribute[i][t];	     	//剩余未考虑的各项属性的重量r[i]减去物品t所对应的属性重量attribute[i][t]
      	}
        
        rsum -= w[t];		//剩余物品的价值rsum减去物品t的价值w[t]
        
    }

    //回溯方法：restore
    private static void restore(int t){
    	
        if(x[t] == 1)		//如果装入了物品t，还原各项值
        {
	       	 for(int i =1;i<k+1;i++)
	       	 {
	       		 cw[i] -= attribute[i][t];		//每种属性的重量cw[i]都减去物品t所对应的属性重量attribute[i][t]
	       	 }
	       	 
	       	 currentsum -= w[t];				//当前背包所装入物品的价值currentsum减去物品t的价值 w[t]
        }

        for(int i =1;i<k+1;i++)
      	{
        	r[i] += attribute[i][t];	     	//剩余未考虑的各项属性的重量r[i]加上物品t所对应的属性重量attribute[i][t]
      	}		
        
        rsum += w[t];			//剩余物品的价值rsum加上物品t的价值w[t]
        
    }

    //输出方法：output()
    private static void output(){
    	
        int count = 0;	//count用于计算装入背包的物品个数
        
        System.out.println("\n装入背包的最大价值为：" + bestsum);
        System.out.print("各物品的装入情况为：");
        
        //输出各物品状态（0为不装入背包，1为装入背包）
        for(int i=1; i<=n; i++)
        {
            System.out.print(bestx[i]+" ");
        }
        
        System.out.print("\n即：将第");

        for(int i=1; i<=n; i++)
        {
        	if(bestx[i]==1)
        	{
        		System.out.print(i+"、");
        		count ++;
        	}
        }
        System.out.print("共"+count+"个物品装入背包");
    }
}
