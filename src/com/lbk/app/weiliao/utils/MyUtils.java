package com.lbk.app.weiliao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyUtils {
	// ����������ڰѴ���ȥ��ͼƬ�ļ�ѹ����С�� bitmap��Դ�������ҾͲ����ж��ǲ���ͼƬ�ļ���
	public static Bitmap revitionImageSize(File file, int size){
		Bitmap bitmap = null;
		try {
			// ȡ��ͼƬ
			InputStream temp = new FileInputStream(file);
			BitmapFactory.Options options = new BitmapFactory.Options();
			// �������������Ϊbitmap�����ڴ�ռ䣬ֻ��¼һЩ��ͼƬ����Ϣ������ͼƬ��С����˵���˾���Ϊ���ڴ��Ż�
			options.inJustDecodeBounds = true;
			// ͨ������ͼƬ�ķ�ʽ��ȡ��options�����ݣ��������������java�ĵ�ַ��������ֵ��
			BitmapFactory.decodeStream(temp, null, options);
			// �ر���
			temp.close();
			// ����ѹ����ͼƬ
			int i = 0;
			while (true) {
				// ��һ���Ǹ���Ҫ���õĴ�С��ʹ��͸߶�������
				if ((options.outWidth >> i <= size)
						&& (options.outHeight >> i <= size)) {
					// ����ȡ������ע�⣺����һ��Ҫ�ٴμ��أ����ܶ���ʹ��֮ǰ������
					temp = new FileInputStream(file);
					// ���������ʾ �����ɵ�ͼƬΪԭʼͼƬ�ļ���֮һ��
					options.inSampleSize = (int) Math.pow(2.0D, i);
					// ����֮ǰ����Ϊ��true������Ҫ��Ϊfalse������ʹ�������ͼƬ
					options.inJustDecodeBounds = false;
					// ���ڵõ���������Ҫ��λͼ
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
