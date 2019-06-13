import java.util.*;

public class HuiChangAnPai {

    private static int n;		//�������
    private static List<Point> acts = new ArrayList<>(); 	//����

	//Point��ʵ��Comparable�ӿڣ��Լ��϶����������
    private static class Point implements Comparable{	
        int time;
        boolean isStartTime;

        public int compareTo(Object o) {
            Point point = (Point) o;
            
            int result = Integer.compare(time, point.time);		//̰��ѡ�񣬰� time ��С��������
            
            return result;
        }
    }
    
    public static void main(String[] args){
    	
        Scanner input = new Scanner(System.in);

        acts.clear();			//��������������ʼ��
        n = input.nextInt();	//��������Ż������n

        for(int i=1; i<2*n; i+=2){
            Point p = new Point();		//��ʼʱ��p
            Point q = new Point();		//����ʱ��q
            p.time = input.nextInt();
            q.time = input.nextInt();
            p.isStartTime = true;
            q.isStartTime = false;
            //���뵽������
            acts.add(p);
            acts.add(q);

        }

        Collections.sort(acts);		//������acts�� time ��С��������
        int result = greedy();		//����greedy()�Ի�����а���
        
        System.out.println(result);
            
    }

    private static int greedy(){
        int curr = 0;		//curr��¼��ǰ���ŵĻ������
        int sum = 0;		//sum��¼���ŵİ��ŵĻ������
        int m = acts.size();	
        
        for(int i=0; i<m-1; i++){		//��������
        	
            if(acts.get(i).isStartTime)		//�����i���ǿ�ʼʱ��
            {
                curr++;			//��ǰ�ɰ��ŵĻ�����+1
                
                //�ӵ�1��ʱ�俪ʼ����time���򣬵�0���ض�Ϊ��ʼʱ�䣩
                //�����ǰʱ���ǰһ��ʱ���ǿ�ʼʱ�䣬֤����һ�����黹û�н���
                if(i>=1 && (acts.get(i-1).isStartTime)) 
                	curr --;		//�ѸղŰ����ϵĻ���ȥ��
            }
            
            /*����ǵ����ڶ�����������һ���ض��ǽ���ʱ�䣩
			 *���߲��ǵ����ڶ���������ǰ��ʱ��������һ����ʱ��
			 *���ҵ�ǰ����İ��Ÿ����������ŵİ��ŵĻ������
			 */
            if((i==m-2||(acts.get(i).time<acts.get(i+1).time)) && curr>sum)
                sum = curr;		//�������ŵİ��ŵĻ������
        }

        return sum;
    }
}
