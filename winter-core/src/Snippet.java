import com.winterframework.combinatorics.CombinatoricsVector;
import com.winterframework.modules.ip.IPSeeker;



public class Snippet {
	public static void main(String[] args){
		IPSeeker ip=IPSeeker.getInstance();
		System.out.println(ip.getAddress("103.225.198.98"));
		}
}