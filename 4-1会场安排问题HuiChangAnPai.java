import java.util.*;

public class HuiChangAnPai {

    private static int n;		//会议个数
    private static List<Point> acts = new ArrayList<>(); 	//链表

	//Point类实现Comparable接口，对集合对象进行排序
    private static class Point implements Comparable{	
        int time;
        boolean isStartTime;

        public int compareTo(Object o) {
            Point point = (Point) o;
            
            int result = Integer.compare(time, point.time);		//贪心选择，按 time 由小到大排序
            
            return result;
        }
    }
    
    public static void main(String[] args){
    	
        Scanner input = new Scanner(System.in);

        acts.clear();			//清空链表，对链表初始化
        n = input.nextInt();	//输入待安排会议个数n

        for(int i=1; i<2*n; i+=2){
            Point p = new Point();		//开始时间p
            Point q = new Point();		//结束时间q
            p.time = input.nextInt();
            q.time = input.nextInt();
            p.isStartTime = true;
            q.isStartTime = false;
            //加入到链表中
            acts.add(p);
            acts.add(q);

        }

        Collections.sort(acts);		//对链表acts按 time 由小到大排序
        int result = greedy();		//调用greedy()对会议进行安排
        
        System.out.println(result);
            
    }

    private static int greedy(){
        int curr = 0;		//curr记录当前安排的会议个数
        int sum = 0;		//sum记录最优的安排的会议个数
        int m = acts.size();	
        
        for(int i=0; i<m-1; i++){		//遍历链表
        	
            if(acts.get(i).isStartTime)		//如果第i个是开始时间
            {
                curr++;			//当前可安排的会议数+1
                
                //从第1个时间开始（按time排序，第0个必定为开始时间）
                //如果当前时间的前一个时间是开始时间，证明上一个会议还没有结束
                if(i>=1 && (acts.get(i-1).isStartTime)) 
                	curr --;		//把刚才安排上的会议去除
            }
            
            /*如果是倒数第二个（倒数第一个必定是结束时间）
			 *或者不是倒数第二个，但当前的时间早于下一个的时间
			 *并且当前会议的安排个数大于最优的安排的会议个数
			 */
            if((i==m-2||(acts.get(i).time<acts.get(i+1).time)) && curr>sum)
                sum = curr;		//更新最优的安排的会议个数
        }

        return sum;
    }
}
