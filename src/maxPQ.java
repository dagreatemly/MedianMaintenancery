import java.util.NoSuchElementException;

public class maxPQ {
	private Integer[] pq;
	private int N;
	
	public maxPQ(int initCapacity) {
		pq = new Integer[initCapacity + 1];
		N = 0;
	}
	
	public maxPQ() {
		this(1);
	}

	public boolean isEmpty() {
		return N==0;
	}
	
	public int size() {
		return N;
	}
	
	public Integer max() {
		if (isEmpty()) throw new NoSuchElementException();
		return pq[1];
	}
	
	private void resize(int capacity) {
		assert capacity > N;
		Integer[] temp = new Integer[capacity];
		for (int i = 1; i <= N; i++) temp[i] = pq[i];
		pq = temp;
	}
	
	public void insert(Integer x) {
		if (N >= pq.length - 1) resize(2*pq.length);
		pq[++N] = x;
		swim(N);
		assert isMaxHeap();
	}
	
	public Integer delMax() {
		if (isEmpty()) throw new NoSuchElementException();
		Integer max = pq[1];
		exch(1, N--);
		sink(1);
		pq[N+1] = null;
		if ((N > 0) && (N == (pq.length -1)/4)) resize(pq.length /2);
		assert isMaxHeap();
		return max;
	}
	
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(j, j+1)) j++;
			if(!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
	
	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Integer swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..N] a max heap?
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    // is subtree of pq[1..N] rooted at k a max heap?
    private boolean isMaxHeap(int k) {
        if (k > N) return true;
        int left = 2*k, right = 2*k + 1;
        if (left  <= N && less(k, left))  return false;
        if (right <= N && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }
}
