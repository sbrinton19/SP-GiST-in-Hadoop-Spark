package edu.purdue.cs.HSPGiST.SupportClasses;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

import edu.purdue.cs.HSPGiST.SupportClasses.Copyable;

/**
 * Simple writablestring implementation. Only represents the 26 lowercase
 * letters.
 * 
 * @author Dan Fortney and Stefan Brinton
 *
 */
public class WritableString implements WritableComparable<WritableString>,
		Copyable<WritableString>, Sized {
	private String str;

	public WritableString() {
	}

	public WritableString(String input) {
		str = input.toLowerCase() + "-";
	}

	public void setStr(String input) {
		this.str = input;
	}

	public String getString() {
		return str;
	}

	public void readFields(DataInput in) throws IOException {
		str = in.readUTF();
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(str);
	}

	public int hashCode() {
		return str.hashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof WritableString))
			return false;
		WritableString other = (WritableString) o;
		return this.str.equals(other.str);
	}

	public int compareTo(WritableString o) {
		for (int i = 0; i < o.str.length() && i < str.length(); i++) {
			char ch1 = str.charAt(i);
			char ch2 = o.str.charAt(i);
			if (ch1 < ch2)
				return -1;
			if (ch1 > ch2)
				return 1;
		}
		if (o.str.length() == str.length())
			return 0;
		if (str.length() < o.str.length())
			return -1;
		return 1;
	}

	public String toString() {
		return "Str: " + str;
	}

	public WritableString clone() {
		return new WritableString(str);
	}

	public WritableString copy() {
		return clone();
	}

	@Override
	public long getSize() {
		return 2 + str.length();
	}
}
