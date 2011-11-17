package ca.ariselab.utils;

import java.util.Arrays;

public class BuffAvg {
	
	private int size;
	private int ptr;
	private boolean blank;
	private float[] vals;
	private boolean looped;
	private int printCnt = 0;
	
	public BuffAvg(int size) {
		this.size = size;
		vals = new float[size];
		ptr = 0;
		blank = true;
	}
	
	public void add(float newVal) {
		if (blank) {
			blank = false;
			Arrays.fill(vals, newVal);
			return;
		}
		vals[ptr] = newVal;
		if (!looped) {
//			System.out.println(toString());
		}
		ptr = (ptr + 1);
		if (ptr >= size) {
			looped = true;
			ptr = ptr % size;
		}
	}
	
	public float get() {
		float sum = 0;
		for (int i = 0; i < size; i++) {
			sum += vals[i];
		}
		return sum / size;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("BufAvg(");
		sb.append(printCnt++).append("/").append(size).append("/").append(ptr).append("): ");
		for (int i = 0; i < size; i++) {
			sb.append(vals[i]).append(" ");
		}
		sb.append(" >> ").append(get());
		return sb.toString();
	}
}
