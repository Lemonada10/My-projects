// Steven DY, 40283742
// Firas AL HADDAD, 40283180

import java.util.NoSuchElementException;
import java.util.Objects;

public class PQ<K extends Comparable<K>, V> {

	private Entry<K, V>[] arrPQ;
	private int indexLastItem;
	private boolean isMinHeap;
	private boolean isLeft;
	private boolean isRoot;
	private int height;

	public PQ(int index, boolean isMinHeap) {
		this.arrPQ = new Entry[index];
		this.indexLastItem = 0;
		this.isMinHeap = isMinHeap;
		this.isRoot = true;
	}

	public void toggle() {
		isMinHeap = !isMinHeap;
		for (int i = indexLastItem / 2; i >= 0; i--) {
			downHeap(i);
		}
	}

	public Entry<K, V> removeTop() {
		if (isEmpty())
			throw new NoSuchElementException();
		Entry<K, V> entry = arrPQ[0];
		arrPQ[0] = arrPQ[indexLastItem - 1];
		arrPQ[indexLastItem - 1] = null;
		indexLastItem--;
		downHeap(0);
		return entry;
	}

	public void checkSize() {
		if (arrPQ.length == indexLastItem) {
			Entry<K, V>[] newArr = (Entry<K, V>[]) new Entry[arrPQ.length * 2];
			for (int i = 0; i < arrPQ.length; i++) {
				newArr[i] = arrPQ[i];
			}

			arrPQ = newArr;
		}
	}

	public void insert(K key, V value) {
		checkSize();
		Entry<K, V> entry = new Entry(key, value);
		arrPQ[indexLastItem] = entry;
		upHeap(indexLastItem);
		indexLastItem++;
	}

	public void upHeap(int index) {
		while (index > 0) {
			int parent = (index - 1) / 2;
			if (compare(arrPQ[index], arrPQ[parent]) < 0) {
				Entry<K, V> entry = arrPQ[parent];
				arrPQ[parent] = arrPQ[index];
				arrPQ[index] = entry;
				index = parent;
			} else {
				break;
			}
		}
	}

	public void downHeap(int index) {
		while (2 * index + 1 < indexLastItem) {
			int left = 2 * index + 1;
			int right = 2 * index + 2;
			int smallest = left;
			if (right < indexLastItem && compare(arrPQ[right], arrPQ[left]) < 0) {
				smallest = right;
			}
			if (compare(arrPQ[smallest], arrPQ[index]) < 0) {
				Entry<K, V> entry = arrPQ[index];
				arrPQ[index] = arrPQ[smallest];
				arrPQ[smallest] = entry;
				index = smallest;
			} else {
				break;
			}

		}
	}

	public Entry top() {
		if (!isEmpty()) {
			return arrPQ[0];
		} else {
			System.out.println("The array is empty!");
			return null;
		}
	}

	public Entry<K, V> remove(Entry<K, V> entry) {

		int index = -1;

		for (int i = 0; i < indexLastItem; i++) {
			if (arrPQ[i].equals(entry)) {
				index = i;
				break;
			}
		}

		if (index == -1)
			throw new NoSuchElementException();

		Entry<K, V> removedEntry = arrPQ[index];
		arrPQ[index] = arrPQ[indexLastItem - 1];

		indexLastItem--;
		downHeap(index);
		upHeap(index);
		arrPQ[indexLastItem] = null;
		return removedEntry;

	}

	public K replaceKey(Entry<K, V> entry, K newKey) {
		int index = -1;

		for (int i = 0; i < indexLastItem; i++) {
			if (arrPQ[i].equals(entry)) {
				index = i;
				break;
			}
		}

		if (index == -1) {
			System.out.println("No such element is found");
			return null;
		} else {
			K oldKey = arrPQ[index].key;
			arrPQ[index].key = newKey;
			//isMinHeap ? e1.key.compareTo(e2.key) : e2.key.compareTo(e1.key);
			if(isMinHeap) {
			if (newKey.compareTo(oldKey) < 0) {
				upHeap(index);
			} else {
				downHeap(index);
			}
			}else {
				if (oldKey.compareTo(newKey) < 0) {
					upHeap(index);
				} else {
					downHeap(index);
				}
			}
			return oldKey;
		}
	}

	public V replaceValue(Entry<K, V> entry, V newValue) {

		int index = -1;

		for (int i = 0; i < indexLastItem; i++) {
			if (arrPQ[i].equals(entry)) {
				index = i;
				break;
			}
		}

		if (index == -1) {
			System.out.println("No such element is found");
			return null;
		} else {
			V oldValue = arrPQ[index].value;
			arrPQ[index].value = newValue;
			return oldValue;
		}

	}

	public String state() {
		if (isMinHeap) {
			return "Min";
		} else {
			return "Max";
		}
	}

	public boolean isEmpty() {
		if (arrPQ[0] == null) {
			return true;
		} else {
			return false;
		}

	}

	public int size() {
		return indexLastItem;
	}

	public void print() {
		for (int i = 0; i < arrPQ.length; i++) {
			if (arrPQ[i] != null) {
				System.out.println(arrPQ[i].toString());
			}
		}
	}

	private int compare(Entry<K, V> e1, Entry<K, V> e2) {
		return isMinHeap ? e1.key.compareTo(e2.key) : e2.key.compareTo(e1.key);
	}

}

class Entry<K, V> {

	K key;
	V value;

	public Entry(K key2, V value) {

		this.key = key2;
		this.value = value;
	}

	public void print() {
		System.out.println(key.toString() + value.toString());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Entry<?, ?> entry = (Entry<?, ?>) o;
		return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
	}

	@Override
	public String toString() {
		return "Key: " + key + " Value: " + value;
	}
	

}
