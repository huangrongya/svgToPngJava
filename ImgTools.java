package com.witkey.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ImgTools {

	/**
	 * 
	 * 方法说明: svg转为png
	 * 
	 * @author huangrongya 2018-5-17 下午3:44:44
	 * @param src
	 * @param dest
	 */
	public static void svgToPng(String src, String dest) {
		try {
			// 获取长宽数据
			String[] viewBox = findViewBox(src);
			float left = Float.parseFloat(viewBox[0]);
			float top = Float.parseFloat(viewBox[1]);
			float width = (Float.parseFloat(viewBox[2]) - left)/2;
			float height = (Float.parseFloat(viewBox[3]) - top)/2;

			// Step -1: We read the input SVG document into Transcoder Input
			String svg_URI_input = new File(src).toURI().toString();
			TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);
			// Step-2: Define OutputStream to PNG Image and attach to
			// TranscoderOutput
			OutputStream png_ostream = new FileOutputStream(dest);
			TranscoderOutput output_png_image = new TranscoderOutput(
					png_ostream);
			// Step-3: Create PNGTranscoder and define hints if required
			PNGTranscoder my_converter = new PNGTranscoder();
			// Step-4: Convert and Write output
			 my_converter.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
			 my_converter.addTranscodingHint(PNGTranscoder.KEY_HEIGHT,
			 height);
			my_converter.transcode(input_svg_image, output_png_image);
			// Step 5- close / flush Output Stream
			png_ostream.flush();
			png_ostream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 方法说明: 获取viewbox参数
	 * 
	 * @author huangrongya 2018-5-17 下午4:34:39
	 * @param svgURI
	 * @return
	 * @throws Exception
	 */
	public static String[] findViewBox(String svgURI) throws Exception {

		String[] numStr = new String[4];
		try {
			SAXReader saxReader = new SAXReader();
			Document configDocument = saxReader.read(svgURI);
			Element element = configDocument.getRootElement();
			String viewBox = element.attributeValue("viewBox");
			numStr = viewBox.split(" ");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return numStr;
	}


	public static void main(String[] args) {
		svgToPng("E:\\GOCR\\2947782448.pattern.svg",
				"E:\\GOCR\\111.png");
	}
}
