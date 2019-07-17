import java.util.Scanner;

public class ZiJiShu {

    private static int n,k,c;				//n����Ʒ������k��Լ������������c:����ֵ��
    private static int[] w,sum,x,r;		   	//w[i]��ʾ��i����Ʒ��ֵ��sum[i]��ʾ��i��Լ��������x[i]��ʾ��i����Ʒ�Ƿ�װ�뱳����r[i]��ʾ��i��Լ��������ʣ��װ�ؿռ�
    private static int[][] attribute;		//attribute[j][i]��¼��i����Ʒ�ĵ�j������
    private static int[] cw,bestx;			//cw[i]��ʾ��ǰ��i�������Ե���������bestx�������ڴ洢���·����
    private static int currentsum = 0,bestsum = 0,rsum = 0;	//currentsumΪ��ǰ��ֵ��bestsumΪ���ż�ֵ��rsumΪʣ��δ���ǵ���Ʒ���ܼ�ֵ
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
      
        n = input.nextInt();		//������Ʒ����n
        k = input.nextInt();		//����Լ����������k
        c = input.nextInt();		//��������ֵc

        w = new int[n+1];		//w[i]��ʾ��i����Ʒ��ֵ��
        x = new int[n+1];		//bestx�������ڴ洢���·����
        bestx = new int[n+1];
        attribute = new int [k+1][n+1];
        sum = new int[k+1];			//ÿ��Լ������������
        r = new int[k+1];		//ÿ��Լ��������ʣ��ռ�          
        cw = new int[k+1];		//c[i]��ʾ��ǰ��i�������Ե�������
        
//      <!----------------�������벿��----------------!>
        
        //������Ʒ��ֵ
        for(int i=1; i<=n; i++)
        {
            w[i] = input.nextInt();		
            rsum  += w[i];		//����ʣ��δ������Ʒ�ܼ�ֵ
        }
        
        //����ÿ����Ʒ������
        for(int j=1; j<=k; j++)
        {
        	for(int i=1; i<=n; i++)
            {
        		attribute[j][i] = input.nextInt();		
            }
        }
        
        //����װ������
        for(int i=1; i<=k; i++)
        {
            sum[i] = input.nextInt();		//sum[i]��ʾ��i��Լ��������
        }
  
        //��ʼ��ÿ��Լ��������ʣ����������ʼֵ��Ϊ��������Ʒ��װ�뱳��ʱ��ÿ��Լ�����������������ޣ���
        for(int i=1; i<=k; i++)
        {
        	for(int j=1; j<=n; j++)
            {
        		r[i] += attribute[i][j];		//��i��Լ������ʣ������
            }
        }
        
        backtrack(1);		//���ݷ�
        
        output();			//���
        
    }

    //���ݷ�
	private static void backtrack(int t){
		
	    if(t > n)
	    	record();		
	    else
	    {
	        for(int i=0; i<=1; i++)
	        {
	            x[t] = i;		//�Ƿ�װ����Ʒt��ֵΪ0ʱ��װ�룬ֵΪ1ʱװ��
	            if(constraint(t) && bound(t))	//���û�г����Ͻ磬�����п���ȡ������ֵ
	            {		
	                change(t);				//change()�����ı䵱ǰ�����Ե�ֵ
	                backtrack(t+1);			//�ݹ�����Լ��������������
	                restore(t);				//���ݷ������ϲ����
	            }
	        }
	    }
	    
	}

    //��¼���·��
    private static void record(){
    	
    	//�жϵ�ǰ��Ʒ��ֵcurrentsum�Ƿ��������ֵbestsum������������������ֵ����Ʒװ��·��bestx
    	if(currentsum > bestsum)	
    	{
    		bestsum = currentsum;	//��������ֵbestsum
    		
	    	for(int i=1; i<n+1; i++)
        	{
        		bestx[i] = x[i];	//������Ʒװ��·������Ʒi�Ƿ�װ�룩
        	}
    	}
    	
    }

    /* Լ��������constraint()
     * ��װ����Ʒt����ʱ�κ�һ�����Գ����˸����Ե�����ֵ�������װ����Ʒt
     * �Ƿ񳬹������Ե�����ֵ��decide()�����ж�
     */
    private static boolean constraint(int t){	
    	
    	/* �����װ����Ʒt
    	 * ���� װ����Ʒtʱ��û�г��������Ե�����ֵ
    	 * ����true����һ������
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
    
    //decide()�����жϸ��������Ƿ񳬹�����ֵ�����Ƿ�����Լ������������������������㣩����false
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
    
    /* �޽纯��:bound()
     * ����ò������Ž⣬��ȥ��һ��֧
     */
    private static boolean bound(int t){
    	
    	/* ���װ����Ʒt��������Ȼ��װ����Ʒt������Ҳ�п��ܻ�ȡ������ֵ
    	 * ����Ŀǰ���ܼ�ֵcurrentsum + ʣ���δװ�����Ʒ��ֵrsum -��Ʒt�ļ�ֵw[t] > ��ǰ����ֵbestsum
    	 * ����true����һ������
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
    
    /* ����������Լ�������������п���ȡ������ֵ�������change()����
     * ����ÿ�����Ե�����cw[i]����ǰ������װ����Ʒ�ļ�ֵcurrentsum��ʣ��δ���ǵĸ������Ե�����r[i]�Լ�ʣ����Ʒ�ļ�ֵ  rsum
     */
    private static void change(int t){
    	
        if(x[t] == 1) 		//���װ������Ʒt�����¸���ֵ
        {
	       	 for(int i =1;i<k+1;i++)
	       	 {
	       		 cw[i] += attribute[i][t];		//ÿ�����Ե�����cw[i]��������Ʒt����Ӧ����������attribute[i][t]
	       	 }
	       	 
       		 currentsum += w[t];      		 	//��ǰ������װ����Ʒ�ļ�ֵcurrentsum����ȥ��Ʒt�ļ�ֵ w[t]
        }
        
        for(int i = 1;i < k+1;i++)
      	{
        	r[i] -= attribute[i][t];	     	//ʣ��δ���ǵĸ������Ե�����r[i]��ȥ��Ʒt����Ӧ����������attribute[i][t]
      	}
        
        rsum -= w[t];		//ʣ����Ʒ�ļ�ֵrsum��ȥ��Ʒt�ļ�ֵw[t]
        
    }

    //���ݷ�����restore
    private static void restore(int t){
    	
        if(x[t] == 1)		//���װ������Ʒt����ԭ����ֵ
        {
	       	 for(int i =1;i<k+1;i++)
	       	 {
	       		 cw[i] -= attribute[i][t];		//ÿ�����Ե�����cw[i]����ȥ��Ʒt����Ӧ����������attribute[i][t]
	       	 }
	       	 
	       	 currentsum -= w[t];				//��ǰ������װ����Ʒ�ļ�ֵcurrentsum��ȥ��Ʒt�ļ�ֵ w[t]
        }

        for(int i =1;i<k+1;i++)
      	{
        	r[i] += attribute[i][t];	     	//ʣ��δ���ǵĸ������Ե�����r[i]������Ʒt����Ӧ����������attribute[i][t]
      	}		
        
        rsum += w[t];			//ʣ����Ʒ�ļ�ֵrsum������Ʒt�ļ�ֵw[t]
        
    }

    //���������output()
    private static void output(){
    	
        int count = 0;	//count���ڼ���װ�뱳������Ʒ����
        
        System.out.println("\nװ�뱳��������ֵΪ��" + bestsum);
        System.out.print("����Ʒ��װ�����Ϊ��");
        
        //�������Ʒ״̬��0Ϊ��װ�뱳����1Ϊװ�뱳����
        for(int i=1; i<=n; i++)
        {
            System.out.print(bestx[i]+" ");
        }
        
        System.out.print("\n��������");

        for(int i=1; i<=n; i++)
        {
        	if(bestx[i]==1)
        	{
        		System.out.print(i+"��");
        		count ++;
        	}
        }
        System.out.print("��"+count+"����Ʒװ�뱳��");
    }
}
