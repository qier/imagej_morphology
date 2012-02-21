import java.awt.*;
import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import org.ajdecon.morphology.*;

public class Morph_Open implements PlugIn {

	public void run(String arg) {

		String[] available = imageList();
		GenericDialog inputs = erosionDialog(available);
		inputs.showDialog();
		if (inputs.wasCanceled()) return;

		String objectName = inputs.getNextChoice();
		String seName = inputs.getNextChoice();
		boolean bgWhite = inputs.getNextBoolean();

		ImagePlus object = ij.WindowManager.getImage(objectName);
		ImagePlus seImage = ij.WindowManager.getImage(seName);
		StructElement se = new StructElement(seImage, bgWhite);

		ImagePlus e = Morphology.open(object,se);
		e.show();
	}

	private String[] imageList() {

		int numImages = ij.WindowManager.getImageCount();
		String[] result = new String[numImages];
		for (int n=1; n<=numImages; n++) {
			ImagePlus im = ij.WindowManager.getImage(
					ij.WindowManager.getNthImageID(n));
			result[n-1] = im.getTitle();
		}

		return result;
	}

	private GenericDialog erosionDialog(String[] imageList) {
		
        GenericDialog result = new GenericDialog("Image Opening");
		result.addMessage("Image opening\n" +
			"Result of an erosion followed by a dilation.");
		result.addChoice("Object image:",imageList,imageList[0]);
		result.addChoice("Structuring element:",imageList,imageList[0]);
		result.addCheckbox("Background is white:",true);

		return result;		
	}
}
