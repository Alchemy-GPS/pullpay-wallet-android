package org.payio.wallet.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.greenrobot.greendao.annotation.NotNull;
import org.payio.wallet.Constants;
import org.payio.wallet.OTCApplication;
import org.payio.wallet.params.User;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.http.PUT;

public class CommonUtil {

    public static void setWalletAddress(String address) {
        SharedPreference.get(OTCApplication.APPLICATION).putStringValue(User.WALLET_ADDRESS, address);
    }

    public static String getWalletAddress() {
        return SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.WALLET_ADDRESS);
    }

    public static void setWalletPassword(String walletPassword) {
        SharedPreference.get(OTCApplication.APPLICATION).putStringValue(User.WALLET_PASSWORD, walletPassword);
    }

    public static String getWalletPassword() {
        return SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.WALLET_PASSWORD);
    }

    public static void setWalletName(String name) {
        SharedPreference.get(OTCApplication.APPLICATION).putStringValue(User.WALLET_NAME, name);
    }

    public static String getWalletName() {
        return SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.WALLET_NAME);
    }

    public static void setWalletPath(String path) {
        SharedPreference.get(OTCApplication.APPLICATION).putStringValue(User.WALLET_PATH, path);
    }

    public static String getWalletPath() {
        return SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.WALLET_PATH);
    }

    public static void setUserId(String userId) {
        SharedPreference.get(OTCApplication.APPLICATION).putStringValue(User.USER_ID, userId);
    }

    public static String getUserId() {
        return SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.USER_ID);
    }

    public static void setUserName(String name) {
        SharedPreference.get(OTCApplication.APPLICATION).putStringValue(User.USER_NAME, name);
    }

    public static String getUserName() {
        return SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.USER_NAME);
    }

    public static void setOrderStatus(String orderStatus) {
        SharedPreference.get(OTCApplication.APPLICATION).putStringValue(User.LAST_ORDER_STATUS, orderStatus);
    }

    public static String getOrderStatus() {
        return SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.LAST_ORDER_STATUS);
    }

    /**
     * 获取币种的唯一Id值(钱包地址+币种全名的MD5值)
     */
    public static String getCoinId(@NonNull String walletAddress, @NonNull String coinName) {
        return MD5(walletAddress + coinName);
    }

    /**
     * 对传递过来的字符串进行md5加密
     *
     * @param str 待加密的字符串
     * @return 字符串Md5加密后的结果
     */
    public static String MD5(@NonNull String str) {
        return new String(Hex.encodeHex(DigestUtils.md5(str)));
    }

    /**
     * 利用MD5进行加密
     *
     * @param file 待加密的文件
     * @return 加密后的字符串
     */
    public static String MD5File(File file) {
        FileInputStream fis = null;
        String md5 = null;
        try {
            fis = new FileInputStream(file);
            //md5 = DigestUtils.md5Hex(fis);//服务端用此方法
            md5 = new String(Hex.encodeHex(DigestUtils.md5(fis)));//Android端没有encodeHexString方法，只能使用这种方式
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(md5)) {
            return "";
        }
        return md5.toUpperCase();
    }

    /**
     * 将unix时间转换为中国标准时间
     *
     * @return 时间
     */
    public static String formatDateTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 将unix时间转换为中国标准时间
     *
     * @return 时间
     */
    public static String formatDateTimeMillis(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    public static String transOrderStatus(Context mContext, String status) {
        String translate = "";
        if (status.equals("1")) {
            translate = mContext.getString(org.payio.wallet.R.string.order_status_create);
        } else if (status.equals("3")) {
            translate = mContext.getString(org.payio.wallet.R.string.order_status_trans);
        } else if (status.equals("4")) {
            translate = mContext.getString(org.payio.wallet.R.string.order_status_success);
        } else if (status.equals("5")) {
            translate = mContext.getString(org.payio.wallet.R.string.order_status_fail);
        }
        return translate;
    }

    public static Bitmap base64ToBitmap(String base64) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(base64.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 把bitmap 转file
     *
     * @param bitmap
     * @param fileName 包含路径和文件名
     */
    public static File saveBitmapFile(Bitmap bitmap, String fileName) {
        File file = new File(fileName);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 把bitmap 转file
     *
     * @param bitmap
     * @param file   文件对象
     */
    public static void saveBitmapFile(Bitmap bitmap, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知文件更新
     *
     * @param mContext
     * @param file
     */
    public static void notifyImgChanged(Context mContext, File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
        mContext.sendBroadcast(intent);
    }

    public static String createImgName() {
        String filePath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date date = new Date(time);
        return filePath + format.format(date) + ".png";
    }

    public static DisplayMetrics getWindowDisplayMetrics(Context context) {
        WindowManager wm = (android.view.WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static void layoutView(View v, int width, int height) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    public static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    public static long getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    public static int getTodayCurrentTime() {
        return (int) (System.currentTimeMillis() - getTodayStartTime());
    }

    public static String setPaySchemaParams(@NotNull String agentCode, @NotNull String outFlowNo) {
        return Constants.PAY_SCHEMA.concat("?outFlowNo=").concat(outFlowNo).concat("&agentCode=").concat(agentCode);
    }

    /**
     * 字符串长度 汉字长度为2 子母和数字为1
     *
     * @param text
     * @return
     */
    public static int stringLength(String text) {
        int amount = 0;
        for (int i = 0; i < text.length(); i++) {
            boolean matches = Pattern.matches("^[\u4E00-\u9Fa5]*$", text.charAt(i) + "");
            if (matches) {
                amount = amount + 2;
            } else {
                amount++;
            }
        }
        return amount;
    }

    /**
     * 判断是否是手机号
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        Pattern p = null;
        Matcher m = null;
        boolean isPhone = false;
        String regex="^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";// 验证手机号

        if (phone.length() != 11) {
            return false;
        } else {
            p = Pattern.compile(regex);
            m = p.matcher(phone);
            isPhone = m.matches();
            return isPhone;
        }
    }

    /**
     * 判断是否是正确的密码
     * @param password
     * @return
     */
    public static boolean isRightPassword(@NotNull String password) {
        String passwordRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return password.matches(passwordRegex);
    }
}
