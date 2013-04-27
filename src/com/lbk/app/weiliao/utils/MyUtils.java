package com.lbk.app.weiliao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyUtils {
	// 这个方法用于把传进去的图片文件压缩到小的 bitmap资源。这里我就不在判断是不是图片文件了
	public static Bitmap revitionImageSize(File file, int size){
		Bitmap bitmap = null;
		try {
			// 取得图片
			InputStream temp = new FileInputStream(file);
			BitmapFactory.Options options = new BitmapFactory.Options();
			// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
			options.inJustDecodeBounds = true;
			// 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
			BitmapFactory.decodeStream(temp, null, options);
			// 关闭流
			temp.close();
			// 生成压缩的图片
			int i = 0;
			while (true) {
				// 这一步是根据要设置的大小，使宽和高都能满足
				if ((options.outWidth >> i <= size)
						&& (options.outHeight >> i <= size)) {
					// 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
					temp = new FileInputStream(file);
					// 这个参数表示 新生成的图片为原始图片的几分之一。
					options.inSampleSize = (int) Math.pow(2.0D, i);
					// 这里之前设置为了true，所以要改为false，否则就创建不出图片
					options.inJustDecodeBounds = false;
					// 终于得到了我们想要的位图
					bitmap = BitmapFactory.decodeStream(temp, null, options);
					break;
				}
				i += 1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
