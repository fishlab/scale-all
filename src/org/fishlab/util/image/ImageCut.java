package org.fishlab.util.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageCut {
	public static String getFileNameWithoutSuffix(String fileName) {
		String fn=null;
		int p0 = fileName.lastIndexOf("/");
		int p1;
		if (p0 == -1) {
			p0 = fileName.lastIndexOf("\\");
		}
		
		if (p0 != -1) {
			p1=fileName.lastIndexOf('.');
			if(p1!=-1&&p1>p0)
				fn=fileName.substring(p0+1,p1);
			else{
				fn=fileName.substring(p0+1);
			}
		}else{
			p1=fileName.lastIndexOf('.');
			if(p1!=-1)
				fn=fileName.substring(0,p1);
			else{
				fn=fileName;
			}
		}
		return fn;
	}
	public final static ImageReader getImageReader(ImageInputStream iis) {
		try {
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return null;
			}
			ImageReader reader = iter.next();
			return reader;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 读取图像文件 用 ImageReader
	 * 
	 * @param imgPath
	 * @throws IOException
	 */
	public static void cut(String imgFile, String destDir,
			int width, int height) throws IOException {

		// 取得图片读入流
		InputStream source = new FileInputStream(imgFile);
		ImageInputStream iis = ImageIO.createImageInputStream(source);

		// 取得图片读入器
		// Iterator readers = ImageIO.getImageReadersByFormatName("png");
		// System.out.println(readers);
		// ImageReader reader = (ImageReader) readers.next();
		ImageReader reader = getImageReader(iis);
		// System.out.println(reader);
		reader.setInput(iis, true);
		// 图片参数
		int srcwidth=reader.getWidth(0)-width;
		int srcheight=reader.getHeight(0)-height;
		ImageReadParam param = reader.getDefaultReadParam();
	
		int index = 0;
	
		String tmpfn=destDir+getFileNameWithoutSuffix(imgFile)+"_cut";
		Rectangle rect = new Rectangle(width, height);
		for (int y=0,i=0;y<srcheight;y+=height,i++){
			for(int x=0,j=0;x<srcwidth;x+=width,j++){
				index++;
				rect.setLocation(x, y);
				param.setSourceRegion(rect);
				BufferedImage bi = reader.read(0, param);
				ImageIO.write(bi, reader.getFormatName(), 
						new File( tmpfn+ "["+index+"]("+i+","+j+")."+reader.getFormatName().toLowerCase()
								));
			}
		}
		
	}
}