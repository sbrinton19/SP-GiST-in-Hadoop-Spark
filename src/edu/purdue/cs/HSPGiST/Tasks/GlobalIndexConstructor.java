package edu.purdue.cs.HSPGiST.Tasks;

import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;

import edu.purdue.cs.HSPGiST.AbstractClasses.HSPIndex;
import edu.purdue.cs.HSPGiST.AbstractClasses.HSPNode;
import edu.purdue.cs.HSPGiST.AbstractClasses.Parser;
import edu.purdue.cs.HSPGiST.SupportClasses.HSPIndexNode;
import edu.purdue.cs.HSPGiST.SupportClasses.HSPReferenceNode;
import edu.purdue.cs.HSPGiST.UserDefinedSection.CommandInterpreter;

/**
 * This tool just outputs the global tree
 * constructed during Local Index Construction to file
 * 
 * @author Stefan Brinton
 *
 */
public class GlobalIndexConstructor extends Configured implements Tool {
	@SuppressWarnings("rawtypes")
	private HSPIndex index;

	@SuppressWarnings("rawtypes")
	public GlobalIndexConstructor(HSPIndex index, Parser parse) {
		this.index = index;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int run(String[] args) throws Exception {
		// Get HDFS
		FileSystem hdfs = FileSystem.get(new Configuration());
		// Construct Path name and initialize path
		StringBuilder sb = new StringBuilder(
				CommandInterpreter.CONSTRUCTSECONDOUT);
		Path globalOutput = new Path(sb.append(CommandInterpreter.postScript)
				.toString());
		// Clear out pre-existing files if applicable
		if (hdfs.exists(globalOutput)) {
			hdfs.delete(globalOutput, true);
		}
		hdfs.mkdirs(globalOutput);
		// Create global output file
		Path globalIndexFile = new Path(sb.append("/")
				.append(CommandInterpreter.GLOBALFILE).toString());
		FSDataOutputStream output = hdfs.create(globalIndexFile);
		// Print nodes in preorder to file
		HSPNode nodule = index.globalRoot;
		index.globalRoot.getSize();
		ArrayList<HSPNode> stack = new ArrayList<HSPNode>();
		while (!(stack.size() == 0 && nodule == null)) {
			if (nodule != null) {
				if (nodule instanceof HSPIndexNode<?, ?, ?>) {
					HSPIndexNode temp = (HSPIndexNode) nodule;
					temp.write(output);
					for (int i = 0; i < temp.getChildren().size(); i++) {
						stack.add((HSPNode) temp.getChildren().get(i));
					}
					nodule = stack.remove(stack.size()-1);
				} else {
					((HSPReferenceNode) nodule).write(output);
					nodule = null;
				}
			} else
				nodule = stack.remove(stack.size()-1);
		}
		//Close and return success
		output.close();
		hdfs.close();
		return 0;
	}

}
