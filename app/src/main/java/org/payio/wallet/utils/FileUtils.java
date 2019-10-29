package org.payio.wallet.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * File name: FileUtils.java
 *
 * @author ChenDatong @version: 1.0 @Date: 2017/1
 * @Description: 文件工具类
 * @Hostory 2017/1/22 TangShasha 修改注释
 */
public class FileUtils {
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static Uri file2Content(Context context, Uri uri) {
        if (uri.getScheme().equals("file")) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(")
                        .append(MediaStore.Images.ImageColumns.DATA)
                        .append("=")
                        .append("'" + path + "'")
                        .append(")");
                Cursor cur = cr.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                while (cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    //do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        Log.i("uri = ", uri + "");
        return uri;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     * <p>
     * 从文件的URI地址中得到文件的路径
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (uri != null) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // MediaStore (and general)
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // File
                return uri.getPath();
            }
        }
        return null;
    }

    /**
     * 获取临时文件
     *
     * @return
     */
    public static File getTempFile() {
        if (isSDCARDMounted()) {
            File file = new File(Environment.getExternalStorageDirectory(), getTime() + ".jpg");
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
            return file;
        }
        return null;
    }

    /**
     * 获取图片在系统数据库中的URI地址
     *
     * @param context   上下文
     * @param imageFile 图片文件
     * @return URI地址
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * SD卡是否加载
     *
     * @return
     */
    public static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间
     *
     * @return 时间字符串, 格式为:yyyyMMddHHmmss
     */
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 将bitmap(图片)对象转换为图片文件--此方法将图片文件直接保存到SD卡根目录中
     *
     * @param bmp      bitmap对象
     * @param fileName 文件名
     * @return 保存后的文件
     */
    public static File bitMap2File(Bitmap bmp, @NonNull String fileName) {

        File newFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return newFile;
    }

    /**
     * 将字节流转换为文件--此方法会将文件直接保存在SD卡根目录中
     *
     * @param buffer   字节流
     * @param fileName 文件名
     * @return 转换后的文件
     */
    public static File byte2File(byte[] buffer, @NonNull String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件的全路径(storage/0/1.txt)
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 将一个文件复制到另一个文件中
     *
     * @param source 源文件
     * @param target 目标文件
     */
    public static void copyFile(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                in.close();
                outStream.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在系统储存的/data/data/ <application package> /files下创建文件
     *
     * @param context 上下文
     * @param name    文件名
     * @return 文件对象
     */
    public static File createFileInFiles(Context context, @NonNull String name) {
        return new File(context.getFilesDir(), name);
    }

    /**
     * 在系统储存的/data/data/ <application package> /cache下创建文件--缓存文件
     *
     * @param context 上下文
     * @param name    文件名
     * @return 文件对象
     */
    public static File createFileInCache(Context context, @NonNull String name) {
        return new File(context.getCacheDir(), name);
    }

    /**
     * 在SDCard下的/Android/data/ <application package> /cache下创建文件--缓存文件
     * 当应用在被用户卸载后，SDCard/Android/data/ <application package> / 目录下的所有文件都会被删除，不会留下垃圾信息
     *
     * @param context 上下文
     * @param name    文件名
     * @return 文件对象
     */
    public static File createFileInExternalCache(Context context, @NonNull String name) {
        return new File(context.getExternalCacheDir(), name);
    }

    /**
     * 在SDCard下的/Android/data/ <application package> /files下创建文件--缓存文件
     * 当应用在被用户卸载后，SDCard/Android/data/ <application package> / 目录下的所有文件都会被删除，不会留下垃圾信息
     *
     * @param context 上下文
     * @param name    文件名
     * @param type    文件类型-一般为文件相应的类型(如音乐,图片等)--可以为空
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES}, or
     *                {@link Environment#DIRECTORY_MOVIES}.
     * @return 文件对象
     */
    public static File createFileInExternalFiles(Context context, @NonNull String name, String type) {
        return new File(context.getExternalFilesDir(type), name);
    }

    /**
     * 在SDCard下的/Android/data/ <application package> /files下创建文件--缓存文件
     * 当应用在被用户卸载后，SDCard/Android/data/ <application package> / 目录下的所有文件都会被删除，不会留下垃圾信息
     *
     * @param context 上下文
     * @param name    文件名
     * @return 文件对象
     */
    public static File createFileInExternalFiles(Context context, @NonNull String name) {
        return new File(context.getExternalFilesDir(null), name);
    }

    /**
     * 利用MD5进行加密
     *
     * @param file 待加密的文件
     * @return 加密后的字符串
     */
    public static String getMd5ByFile(File file) {
        FileInputStream fis = null;
        String md5 = null;
        try {
            fis = new FileInputStream(file);
            //md5 = DigestUtils.md5Hex(fis);//服务端用此方法
            md5 = new String(Hex.encodeHex(DigestUtils.md5(fis)));//Android端没有encodeHexString方法，只能使用这种方式
            IOUtils.closeQuietly(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(md5)) {
            return "";
        }

        Log.i("MD5---", md5.toUpperCase());

        return md5.toUpperCase();
    }

    public static Bitmap file2Bitmap(File image) {
        return BitmapFactory.decodeFile(image.getAbsolutePath());
    }

    public static void copyFilesFromAssets(Context context, String assetsPath, String savePath) {
        try {
            String fileNames[] = context.getAssets().list(assetsPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(savePath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, assetsPath + "/" + fileName,
                            savePath + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(assetsPath);
                FileOutputStream fos = new FileOutputStream(new File(savePath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String getFileContent(File file) {
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
