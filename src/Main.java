import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		maxPQ left = new maxPQ();
		minPQ right = new minPQ();
		int[] medians = new int[10001];
		try (Scanner s = new Scanner(new File("Median.txt"))) {
			int first = 0;
			int second = 0;
			if(s.hasNextInt()) first = s.nextInt();
			if(s.hasNextInt()) second = s.nextInt();
			if(first > second) { 
				right.insert(first);
				left.insert(second);
			} else {
				left.insert(first);
				right.insert(second);
			}
			medians[1] = first;
			medians[2] = left.max();
			
			int index = 3;
			while(s.hasNextInt()) {
				int i = s.nextInt();
				if(left.size()==right.size()) {
					if(i >= right.min()) right.insert(i); 
					if(i <= left.max()) left.insert(i);
					if(i < right.min() && i > left.max()) left.insert(i);
				} else if(left.size() > right.size()) {
					if(i < left.max()) {
						left.insert(i);
						right.insert(left.delMax());
					} else right.insert(i);
				} else {
					if(i > right.min()) {
						right.insert(i);
						left.insert(right.delMin());
					} else left.insert(i);
				}

				
				if(left.size() < right.size()) { 
					medians[index] = right.min(); 
				} else if (left.size() >= right.size()) {
					medians[index] = left.max(); 
				}
				index++;
			}

			
		} catch(IOException e) {
			e.getMessage();
		}
		long sum = 0;
		for(int i = 1; i < medians.length; i++) {
			sum = sum + medians[i];
		}
		System.out.println(sum);
	}
}
