package com.nayeem.biswas.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nayeem.biswas.core.base.dtos.fileservice.FileDto;
import com.nayeem.biswas.core.base.exceptions.ServiceExceptionHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Slf4j
@Component
public class AppUtil {

    public static DateFormat DD_MMM_YYYY = new SimpleDateFormat("dd-MMM-yyyy");
    public static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat DD_MMM_YYYY_HH_MM_SS = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");


    public static String SHA1(String plainText) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hash = messageDigest.digest(plainText.getBytes());

            return new String(hash);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public synchronized long getMaxVersion(){
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String SHA256(String plainText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainText.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }


    public synchronized static UUID toUUID(Object obj) {
        try {
            return UUID.fromString(obj.toString());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public synchronized static UUID toUUID(String obj) {
        try {
            return UUID.fromString(obj);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public static UUID getUUID(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return null;
        }
        try {
            return UUID.fromString(obj.get(key).toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }

    public static JsonObject getJson(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return null;
        }
        try {
            return JsonParser.parseString(obj.get(key).toString()).getAsJsonObject();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }

    public static String getString(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return "";
        }
        try {
            return obj.get(key).toString();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return "";
    }

    public static long getLong(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return 0;
        }
        try {
            return toLong(obj.get(key));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static int getInt(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return 0;
        }
        try {
            return toInt(obj.get(key));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static boolean getBoolean(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return false;
        }
        try {
            return toBoolean(obj.get(key));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return false;
    }

    public static double getDouble(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return 0.0;
        }
        try {
            return toDouble(obj.get(key));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0.0;
    }

    public static float getFloat(Map<?,?> obj, String key) {
        if (obj == null || key==null) {
            return 0.0f;
        }
        try {
            return toFloat(obj.get(key));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0.0f;
    }

    public static String toString(Object str) {
        if (str == null) {
            return "";
        }
        try {
            return String.valueOf(str);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return "";
    }

    public static int toInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static int toInt(Object number) {
        try {
            return Integer.parseInt(number.toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static long toLong(Object number) {
        try {
            return Long.parseLong(number + "");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static long toLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static boolean toBoolean(Object val) {
        try {

            if( val.equals(1) || val.equals(true) ){
                return true;
            } else{
                return (boolean) val;
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return false;
    }

    public static double toDouble(Object number) {
        try {
            return Double.parseDouble(number.toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0.0f;
    }

    public static float toFloat(Object number) {
        try {
            return Float.parseFloat(number.toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0.0f;
    }

    public static Date toStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date toEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date toDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            return sdf.parse(date);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }

    public static Date toDate(Object date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            return sdf.parse(date.toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }

    public static Date getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            return sdf.parse(sdf.format(new Date()).toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }

    public static Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return sdf.parse(sdf.format(new Date()).toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }

    public static String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }

    public static String getDateString(String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }

    public static String getDateTimeString(String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(date);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return "";
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return "";
    }

    public static String get24HourTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }

    public static String get24HourTime(String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }

    public static String getInvoicePrefix() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return "I"+sdf.format(new Date());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "I";
    }

    public static String getInvoicePrefix(String date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date dt = sdf1.parse(date);
            return "I"+sdf.format(dt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "I";
    }


    public static synchronized int getRandomNumber(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String generatePassword(int length) {
        String charStr = "!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890_<>{}[].,+";

        String passStr = "";
        for (int i = 0; i < length; i++) {
            int _rn = AppUtil.getRandomNumber(0, 82);
            passStr += charStr.charAt(_rn);
        }

        return passStr;
    }

    public static String generateRandomText(int length) {
        String charStr = "!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890_<>{}[].,+";

        String passStr = "";
        for (int i = 0; i < length; i++) {
            int _rn = AppUtil.getRandomNumber(0, 82);
            passStr += charStr.charAt(_rn);
        }

        return passStr;
    }

    public static String generateRandomCapitalTextAndNumber(int length) {
        String charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        String passStr = "";
        for (int i = 0; i < length; i++) {
            int _rn = AppUtil.getRandomNumber(0, 35);
            passStr += charStr.charAt(_rn);
        }

        return passStr;
    }

    public static String generateRandomNumber(int length) {
        String passStr = "";
        for (int i = 0; i < length; i++) {
            int _rn = AppUtil.getRandomNumber(0, 9);
            passStr += _rn;
        }

        return passStr;
    }


    public String[] uploadFile(String dirPath, MultipartFile[] files) {
        String [] arr = new String[files.length];

        int idx=0;
        for( MultipartFile file : files ){
            arr[idx++] = uploadFile(dirPath, file);
        }
        return arr;
    }

    public String uploadFile(String dirPath, MultipartFile file) {

        String[] realNames = file.getOriginalFilename().split("\\.");
        int len = realNames.length;
        String fileName = UUID.randomUUID().toString() + "." + realNames[len-1];

        try {
            try {
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    if (dir.mkdirs()) {
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }
            } catch (Exception e) {
            }
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            if (!file.getOriginalFilename().equals("foo.empty_file")) {

                Path path = Paths.get(dirPath + fileName );
                Files.write(path, bytes);

                return fileName;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static List<String> generateDateRange(String startDate, String endDate)
    {
        Date begin = toDate(startDate);
        Date end = toDate(endDate);
        List<String> list = new ArrayList();

        Date tmpDate = new Date(begin.getTime());
        list.add( dateToString(tmpDate) );

        while(begin.compareTo(end)<0)
        {
            begin = new Date(begin.getTime() + 86400000);
            tmpDate = new Date(begin.getTime());

            list.add( dateToString(tmpDate) );
        }

        return list;
    }

    public static String getHeaderInfo(HttpServletRequest request, String keyName) {
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if( key.equals(keyName) ){
                return request.getHeader(key);
            }
        }
        return "";
    }

    public static String toDateFunction(String date) {
        if (date != null && !date.trim().isEmpty()) {
            return " TO_DATE('" + date.trim() + "','DD/MM/RRRR') ";
        } else {
            return " TO_DATE(null,'DD/MM/RRRR')";
        }
    }




    public static String getCurrentDate(){
        return DD_MMM_YYYY_HH_MM_SS.format(Calendar.getInstance().getTime()) ;
    }




    public static boolean isDateTime(String str){
        if(str!=null && str.length()==10 && str.matches("^\\d{4}-\\d{2}-\\d{2}$"))
            return true;
        if(str!=null && str.length()==28
                && str.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}$")){
            return true;
        }
        return  false;
    }

    public static Date toSqlDate(String str){
        if(str!=null && str.length()==10)
            return toSqlDateFromYYYYMMDD(str);
        if(str!=null && str.length()==28){
            return toSqlDateFromYYYYMMDDTHHMMSSSSSZZZZ(str);
        }
        return null;
    }
    public static java.sql.Date toSqlDateFromYYYYMMDD(String str){
        try {
            return java.sql.Date.valueOf(str);
        } catch (Exception ex) {
            return null;
        }
    }

    public static synchronized java.sql.Date toSqlDateFromYYYYMMDDTHHMMSSSSSZZZZ(String str){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ");
        try {
            return new java.sql.Date(simpleDateFormat.parse(str).getTime());
        } catch (Exception ex) {
            return null;
        }
    }

    public static String prepareBusinessName(String businessName) {
        return null != businessName && !businessName.isEmpty() ? businessName
                .replaceAll("[^A-Za-z0-9]", "-")
                .replaceAll("--", "-").replaceAll("--", "-").replaceAll("--", "-")
                .toLowerCase() : "";
    }


    public static void unZipFile(String srcPath, String destPath) throws IOException {

        File destDir = new File(destPath);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(srcPath));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newZipEntryFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static File newZipEntryFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }


    public static void replaceFileText(String filePath, String oldText, String newText) {
        File textFile = new File(filePath);
        try {
            String data = FileUtils.readFileToString(textFile, "ISO-8859-1");
            data = data.replace(oldText, newText);
            FileUtils.writeStringToFile(textFile, data, "ISO-8859-1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getTokenValue(HttpServletRequest request) {
        try {
            return request.getHeader("authorization").split(" ")[1];
        } catch (Exception ex){
            log.error("!!! Token not available on header.");
        }

        try {
            return request.getHeader("Authorization").split(" ")[1];
        } catch (Exception ex){
            log.error("!!! Token not available on header.");
        }
        return "";
    }

    public static String getHeader(HttpServletRequest request, String key) {
        try {
            return request.getHeader(key.toLowerCase());
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return "";
    }

    public static String getPreviewUrl(String bucketName, String fileName, String fileExtension) {
        return ((null != bucketName && !bucketName.trim().isEmpty()) ? "/"+bucketName : "")
                .concat("/")
                .concat((null != fileName && !fileName.trim().isEmpty()) ? fileName : "");
    }

    public static String getPreviewUrl(FileDto dto) {
        return null != dto ? getPreviewUrl(dto.getBucketName(), dto.getFileName(), dto.getFileType()) : null;
    }

    public static FileDto putPreviewUrl(FileDto dto) {
        if (null == dto) return null;
        dto.setPreviewUrl(getPreviewUrl(dto.getBucketName(), dto.getFileName(), dto.getFileType()));
        return dto;
    }

    public static String getIpProxyIp(HttpServletRequest servletRequest){
        String remoteAddr = servletRequest.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) remoteAddr = servletRequest.getRemoteAddr();

        return remoteAddr;
    }

    public static String getClientInfo(HttpServletRequest servletRequest){
        return servletRequest.getHeader("User-Agent") == null ? "": servletRequest.getHeader("User-Agent");
    }

    public static List<FileDto> putPreviewUrl(List<FileDto> list) {
        return list.stream().map(file -> {
            if (null == file) return null;
            file.setPreviewUrl(getPreviewUrl(file));
            return file;
        }).collect(Collectors.toList());
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidMobile(String plainText){
        if( plainText.length() == 11 && ( plainText.startsWith("01") || plainText.startsWith("+8801") || plainText.startsWith("8801") ) && StringUtils.isNumeric(plainText)){
            return true;
        }
        return false;
    }
    
    public static String addCountryCode(String phoneNumber) {
		if (null == phoneNumber || phoneNumber.trim().isEmpty() || phoneNumber.equals("null")
				|| phoneNumber.equalsIgnoreCase("null") || phoneNumber == "")
			return null;

		if (phoneNumber.startsWith("+880")) {
			phoneNumberValidation(phoneNumber);
			return phoneNumber;
		} else if (phoneNumber.startsWith("880")) {
			phoneNumber = "+" + phoneNumber;
			phoneNumberValidation(phoneNumber);
			return phoneNumber;
		} else if (phoneNumber.startsWith("1")) {
			phoneNumber = "+880" + phoneNumber;
			phoneNumberValidation(phoneNumber);
			return phoneNumber;
		} else {
			phoneNumber = "+88" + phoneNumber;
			phoneNumberValidation(phoneNumber);
			return phoneNumber;
		}

	}

	private static void phoneNumberValidation(String phoneNumber) {
		if (phoneNumber.length() != 14 && phoneNumber.startsWith("+8801"))
			throw new ServiceExceptionHolder.BadRequestException("Please enter a valid phone number");
	}

    private static byte[] getKeyBytes() {
        return "614E645267556B58".getBytes();
    }

    private static Cipher getCipherInstance(int mode) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(mode, new SecretKeySpec(getKeyBytes(), "AES"));
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String plainText) {
        try {
            //This is the Key that we are using for encryption. We will use the same key for decryption
            Cipher cipher = getCipherInstance(Cipher.ENCRYPT_MODE);
            //Encryption
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            //Encode Characters
            return Base64.getUrlEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encodedCipherText) {
        try {
            Cipher cipher = getCipherInstance(Cipher.DECRYPT_MODE);
            // Decode the encoded cipher text
            String decodedCiphertext = URLDecoder.decode(encodedCipherText, StandardCharsets.UTF_8.toString());
            //Decode - to base 64 Safe
            byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(decodedCiphertext.getBytes(StandardCharsets.UTF_8)));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

