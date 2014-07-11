package org.fishlab.test.util.image;

import java.io.File;
import java.io.IOException;

import org.fishlab.util.image.ImageCut;
import org.fishlab.util.image.ImageUtils;
import org.fishlab.util.io.FileType;
import org.junit.Test;

public class TestImageUtils {

//	@Test
	public void main() throws Exception {
		File f = new File("in/bg0.jpg");
		if (f.exists()) {
			String filetype1 = FileType.getImageFileType(f);
			System.out.println(filetype1);
			String filetype2 = FileType.getType(f);
			System.out.println(filetype2);
		}
	}

	@Test
	public void test() {
		// File ff=new File(f);
		// System.out.println(f);
		// System.out.println(File.separatorChar);
		// System.out.println(ff.getAbsolutePath());

//		ImageUtils.cut("in/bg.jpg", "T:/out/ls.jpg", 0, 0, 150, 75);
		// ImageUtils.cut3("in/bg.jpg", "T:/out/",150, 75);
		 try {
		 ImageCut.cut("in/bg0.jpg", "T:/out/",150,65);
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
	}
//	@Test
	public void testFileName() {
		String ff[]=new String[]{
				 "asd.ext.jpg",
				 "asd.jpg",
				 "cc/ss.jpg",
				 "cc/ss",
				 "c.c/ss",
				 "c.c/ss.jpg"
		 };
		 for(String f:ff)
		 System.out.println(ImageCut.getFileNameWithoutSuffix(f));
	}
}
