import java.util.Scanner;

//Commodity���ǹ�����Ʒ��,ӵ����Ʒ�Ĺ��������ͼ۸�����
class Commodity{		
    int piece;		//��������
    int price;		//����۸�
}

public class MinCost { 
	private static int MAXCODE = 999;		//��Ʒ��������ֵ 
	private static int SALECOMB = 99;		//�Ż���Ʒ�����
	private static int KIND = 5; 			//��Ʒ����
	private static int QUANTITY = 5; 		//����ĳ����Ʒ���������ֵ 
	private static int b;					//������Ʒ������ 
	private static int s;					//��ǰ�Ż������ 
	
	private static int[] num = new int[MAXCODE+1];				//��¼��Ʒ��������Ʒ����Ķ�Ӧ��ϵ
	private static int[] product = new int[KIND+1];				//��¼��ǰ��ͬ������Ʒ�Ĺ������� 
	private static int[][] offer = new int[SALECOMB+1][KIND+1];	//offer[i][j]: ��Ʒ��ϵ��Żݼ�(j=0)��ĳ���Ż������ĳ����Ʒ��Ҫ���������(j>0)
	private static Commodity[] purch = new Commodity[KIND+1];	//��¼��ͬ��Ʒ�Ĺ��������͹���۸� 
	private static int[][][][][] cost = new int[QUANTITY+1][QUANTITY+1][QUANTITY+1][QUANTITY+1][QUANTITY+1];		//��¼���ι�����ܻ���


	 public static void main(String[] args){ 
		 init();    //��ʼ��
		 comp(1); 	
		 out();     //���
	}
	 
	 //��ʼ������
	 private static void init(){ 
		 Scanner input = new Scanner(System.in);
		 int i,j,n,p,t,code; 
		 
		 //��ʼ��
		 for(i=0; i<100; i++)   
			 for(j=0; j<6; j++) 
				 offer[i][j] = 0; 
		 
		 //purch[]��¼��ͬ��Ʒ�Ĺ��������͹���۸�
		 for(i=0; i<6; i++){ 
			 purch[i] = new Commodity();
			 purch[i].piece = 0; 	//��ʼ����Ʒ����
			 purch[i].price = 0;    //��ʼ����Ʒ�۸�
			 product[i] = 0; 		//��ʼ����ǰ��������Ʒ�Ĺ������� 
		 } 
		 
		 b = input.nextInt();		//��������b����ʾ������Ʒ������
		 
		 //��������������������ζ�ȡ��i����Ʒ����Ʒ���롢�������۸�
		 for(i=1; i<=b; i++){ 
			 code = input.nextInt(); 
			 purch[i].piece = input.nextInt();
			 purch[i].price = input.nextInt(); 
			 
			 num[code] = i; 		//����num��¼��Ʒ��������Ʒ����Ķ�Ӧ��ϵ
		 } 
		 
		 s = input.nextInt();		//����ӵ�е��Ż����������s
		  
		 for(i=1; i<=s; i++){ 
			 t = input.nextInt(); 		 //�����i���Ż���Ʒ���������Ʒ��������
			 
			 //��������������������ζ�ȡ��i����Ʒ����Ʒ���롢����
			 for(j=1; j<=t; j++){
				 n = input.nextInt(); 	
				 p = input.nextInt(); 
				 
				 offer[i][num[n]] = p; 	 //offer[i][j]: ��i���Ż�����е�j����Ʒ��Ҫ���������(j>0)
			 } 
			 
			 offer[i][0] = input.nextInt(); 		//offer[i][0]������¼��i����Ʒ��ϵ��Żݺ�۸�
		 } 
	 }
	 
	 //������������ֽ�����ɸ��໥��ϵ�������⣬��˳�����������⣬���һ����������ǳ�ʼ����Ľ�
	 private static void comp(int i){ 		//�����i=1��Ĭ�ϴӵ�һ����Ʒ��ʼ����
		 
		 if(i > b){ 
			 minicost();
		 	 return; 
		 } 
		 
		 for(int j=0; j<=purch[i].piece; j++){   //purch[i].piece��ʾ��i����Ʒ�Ĺ�������
			 product[i] = j; 			//���õ�i����Ʒ�Ĺ������� Ϊj
			 comp(i+1);    
		 } 
	 } 
	 
	 //��̬�滮�㷨�������ٷ���
	 private static void minicost(){
		 
		 int i,j,k,m,n,p,minm;
		 minm = 0; 
		 
		 /* product[]��¼��ͬ������Ʒ�Ĺ�������
		  * purch[]��¼��ͬ��Ʒ�Ĺ��������͹���۸�
		  * offer[i][j]: ��i���Ż�����е�j����Ʒ��Ҫ���������(j>0)
		  * cost�����¼���ι�����ܻ���
		  */
		 
		 //��¼������Ʒi����Ҫ����󻨷ѣ���û���κ��Ż���Ϸ���ʱ�Ļ���
		 for(i=1; i<=b; i++) {
			 minm += product[i]*purch[i].price;	
		 }
		 
		 for(p=1; p<=s; p++){ 		//p������¼��ǰ�ĵ�p���Żݷ���
			 i = product[1] - offer[p][1]; 		//��ǰ����ĵ�1����Ʒ������ - ��p������е�һ����Ʒ������ = ʣ����Ҫԭ�۹������Ʒ����			 
			 j = product[2] - offer[p][2];		
			 k = product[3] - offer[p][3]; 		
			 m = product[4] - offer[p][4]; 		
			 n = product[5] - offer[p][5];		
			 
			 //�����蹺���������㣨���ڵ��ڣ��Ż������ʱ������µ�ǰ��ϵ���ͻ���
			 if(i>=0 && j>=0 && k>=0 && m>=0 && n>=0 && cost[i][j][k][m][n]+offer[p][0] < minm) 
				 minm = cost[i][j][k][m][n] + offer[p][0];
		 } 
		
		 //���¹����Ʒ�����Ļ���
		 cost[product[1]][product[2]][product[3]][product[4]][product[5]] = minm; 
	 } 
	 
	 private static void out(){ 
		 System.out.println("���ι�����Ʒ�����ٷ���Ϊ��"+cost[product[1]][product[2]][product[3]][product[4]][product[5]]+"Ԫ"); 
	} 
	 
}
