package com.matrixdev.dremergency.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Milind on 7/6/2018.
 */

public class ShareHelper {
    public static void shareImage(Activity activity, Bitmap bitmap) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        String bitmapPath = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        activity.startActivity(Intent.createChooser(intent, "Share"));
    }

    public static void shareImageAsPdf(Activity activity, Bitmap bitmap) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            File file = ImageUtils.bitmapToFile(bitmap);
            Document document = new Document();
            String dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/example.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(file.getAbsolutePath());  // Change image's name and extension.

            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            //img.setAlignment(Image.LEFT| Image.TEXTWRAP);

            document.add(img);
            document.close();

            File cardDocument = new File(dirpath + "/example.pdf");

            if (cardDocument.exists()) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Uri apkURI = FileProvider.getUriForFile(
                        activity.getBaseContext(),
                        activity.getBaseContext().getApplicationContext()
                                .getPackageName() + ".provider", cardDocument);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_STREAM, apkURI);

                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "Sharing File...");
                intent.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                activity.startActivity(Intent.createChooser(intent, "Share File"));
            }

//            intent.setDataAndType(apkURI, "application/pdf");
//            intent.setType("application/pdf");
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cardDocument));
//            startActivity(Intent.createChooser(intent, "Share"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shareText(Activity activity, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Card");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share"));
    }
}
