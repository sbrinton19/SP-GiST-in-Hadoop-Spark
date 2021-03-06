package edu.purdue.cs.HSPGiST.SupportClasses;

import org.apache.hadoop.io.LongWritable;

/**
 * A Copyable LongWritable
 * 
 * @author Stefan Brinton
 *
 */
public class CopyWritableLong extends LongWritable implements
		Copyable<CopyWritableLong>, Sized {
	public CopyWritableLong() {
		super();
	}

	public CopyWritableLong(LongWritable l) {
		super(l.get());
	}

	public CopyWritableLong(long l) {
		super(l);
	}

	@Override
	public CopyWritableLong copy() {
		return new CopyWritableLong(get());
	}

	@Override
	public long getSize() {
		return Long.SIZE>>3;
	}

}
