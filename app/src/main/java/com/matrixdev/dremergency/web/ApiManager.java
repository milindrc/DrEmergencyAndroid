package com.matrixdev.dremergency.web;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.matrixdev.dremergency.Utils.ToastHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by milin on 11-Nov-15.
 */
public class ApiManager {

    SQLiteDatabase sqldb;
    Context context;
    ServerResponseListener serverResponseListener;
    private Boolean jsonParsing;
    private Type classTypeForJson;
    private HashMap<String, String> headerParams;
    ProgressHandler progressHandler;


    public ApiManager(Context context, ServerResponseListener serverResponseListener) {
        this.context = context;
        this.serverResponseListener = serverResponseListener;
    }

    public ApiManager(Context context, ServerResponseListener serverResponseListener, SQLiteDatabase sqldb) {
        this.sqldb = sqldb;
        this.context = context;
        this.serverResponseListener = serverResponseListener;
    }

    public void setHeadersParams(HashMap<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    private void performJsonParsing(String TAG, String response, Type type) {
        jsonParsing = false;
        classTypeForJson = null;

        if (response != null) {
            Gson gson = new GsonBuilder().create();
            //JsonReader reader = new JsonReader(new StringReader(response));
            //reader.setLenient(true);
            try {
                Object responseObject = gson.fromJson(response, type);
                serverResponseListener.positiveResponse(TAG, responseObject);
            } catch (JsonSyntaxException jse) {
                jse.printStackTrace();
                FirebaseCrash.report(new Exception(jse.getMessage()));

                String errorString;
                errorString = "error For Developer: Some Issue in Json Syntax. Unable to parse it";
                Log.d("----err jsonString:", response);
                Log.d("----err json:", jse.getMessage());

                serverResponseListener.negativeResponse(TAG, errorString);
                return;
            } catch (Exception e) {
                e.printStackTrace();

                FirebaseCrash.report(new Exception(e.getMessage()));


                String errorString;
                errorString = "error For Developer: Some general exception in creating objects from returned json";
                Log.d("----err jsonString:", response);
                Log.d("----err json:", e.getMessage());

                serverResponseListener.negativeResponse(TAG, errorString);
            }
            // means server response is null
        } else {
            String errorString;
            errorString = "error Response = null";

            serverResponseListener.negativeResponse(TAG, errorString);
        }
    }

    public void getStringPostResponse(String TAG, String Url) {
        final Post post = new Post(TAG, Url);

        if (!isNetworkConnected()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_LONG).show();
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                post.execute();
            }
        });
        thread.start();

    }

    public void getStringPostResponse(String TAG, String Url, HashMap<String, String> arr) {
        final Post post = new Post(TAG, Url, arr);

        if (!isNetworkConnected()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_LONG).show();
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                post.execute();
            }
        });
        thread.start();
    }


    private class Post extends AsyncTask<Void, Void, String> {

        private String TAG;
        String Url;
        int errorFlag = 0;

        HashMap<String, String> params;

        Post(String TAG, String Url) {
            this.TAG = TAG;
            this.Url = Url;
        }

        Post(String TAG, String Url, HashMap<String, String> arr) {
            this.Url = Url;
            this.params = arr;
            this.TAG = TAG;
        }

        @Override
        public void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.d("----post result", result);

            if (errorFlag == 0) {
                Log.d("----Call : ", "positive");
                if (jsonParsing != null && jsonParsing)
                    performJsonParsing(TAG, result, classTypeForJson);
                else
                    serverResponseListener.positiveResponse(TAG, result);
            } else {
                Log.d("----Call : ", "negative");
                serverResponseListener.negativeResponse(TAG, result);
                ToastHelper.toast("Something went wrong.");
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub


            String responseStr = "";
            HashMap<String, String> nameValuePair = new HashMap<>();
            nameValuePair.put("", "");

            URL url = null;
            try {
                url = new URL(Url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (headerParams != null) {
                    applyHeaders(urlConnection);
                }
                urlConnection.setRequestMethod("POST");
                // urlConnection.setConnectTimeout(5000);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                OutputStream sendingStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(sendingStream, "UTF-8"));

                if (this.params == null)
                    writer.write(getQuery(nameValuePair));
                else
                    writer.write(getQuery(this.params));

                writer.flush();
                writer.close();
                sendingStream.close();

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        responseStr += line;
                    }
                } else {
                    responseStr = "Reponse Code : " + responseCode + "(" + urlConnection.getResponseMessage() + ")";
                    errorFlag = 1;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                FirebaseCrash.report(new Exception(e.getMessage()));
                errorFlag = 1;
                responseStr = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                FirebaseCrash.report(new Exception(e.getMessage()));
                errorFlag = 1;
                responseStr = e.getMessage();
            }

            Log.d("----post response", responseStr);
            headerParams = null;
            return filter(responseStr);
        }
    }


    void intiate(String tableName) {
        String Query = "PRAGMA table_info(" + tableName + ")";
        Cursor my_cursor = sqldb.rawQuery(Query, null);

        int pos = 0;

        String Q = "insert into " + tableName + " values (";

        for (int i = 0; i < my_cursor.getCount(); i++) {
            my_cursor.moveToPosition(i);

            if (i != 0) {
                Q += ",";

            }

            String type = my_cursor.getString(my_cursor.getColumnIndex("type"));

            if (type.contains("char")) {
                Q += "''";

            } else if (type.contains("date")) {
                Q += "'0-0-0'";

            } else
                Q += "0";

        }

        Q += ");";

        sqldb.execSQL(Q);

    }

    public int createDbXmlFromWeb(String stringResponse, String tableName) {

        try {

            if (sqldb == null || tableName == null) {
                Toast.makeText(context, "Must include database in class constructor.", Toast.LENGTH_LONG).show();
                return 1;
            }

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setInput(new ByteArrayInputStream(stringResponse.getBytes()), null);

            Cursor cs = sqldb.rawQuery("Select * from " + tableName, null);

            if (cs.getCount() != 0)
                sqldb.execSQL("delete from " + tableName);

            intiate(tableName);

            //sqldb.execSQL("insert into empl values(0,'a','a',0,'0-0-0',0,0,0);");

            cs = sqldb.rawQuery("Select * from " + tableName, null);
            cs.moveToFirst();
            System.out.println("-----#" + cs.getCount());
            int colno = 0;
            String row = "Insert into " + tableName + " values (";

            int event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:

                        name = myParser.getText();

                    case XmlPullParser.END_TAG:
                        if (name.equalsIgnoreCase("record")) {
                            colno = 0;
                            row += ");";
                            sqldb.execSQL(row);
                            row = "Insert into " + tableName + " values (";
                        }
                        if (!name.equalsIgnoreCase("record") && !name.equalsIgnoreCase(tableName)) {
                            if (colno != 0)
                                row += " , ";
                            if (cs.getType(colno) == Cursor.FIELD_TYPE_INTEGER || cs.getType(colno) == Cursor.FIELD_TYPE_FLOAT || cs.getType(colno) == Cursor.FIELD_TYPE_NULL)
                                row += name;
                            else
                                row += " ' " + name + " ' ";

                            colno++;
                            myParser.next();
                        }
                        break;
                }

                System.out.println("----" + name);
                event = myParser.next();
            }

            String col = cs.getColumnName(0);
            cs.moveToFirst();
            String val = cs.getString(0);
            sqldb.execSQL("delete from " + tableName + " where " + col + " = " + val);

            return 0;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error in parsing" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("----xmlParsing", "Error in parsing" + e.getMessage());
            FirebaseCrash.report(new Exception(e.getMessage()));

            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error in parsing", Toast.LENGTH_LONG).show();
            Log.d("----xmlParsing", "Error in parsing" + e.getMessage());

            FirebaseCrash.report(new Exception(e.getMessage()));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error in parsing", Toast.LENGTH_LONG).show();
            Log.d("----xmlParsing", "Error in parsing" + e.getMessage());
            FirebaseCrash.report(new Exception(e.getMessage()));

            return 1;
        }


    }


    public void sendFile(String TAG, String Url, HashMap<String, File> fileParams, HashMap<String, String> params) {
        PostFile post = new PostFile(TAG, Url, fileParams, params);

        if (!isNetworkConnected()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_LONG).show();
            return;
        }

        post.execute();
    }

    private class PostFile extends AsyncTask<Void, Integer, String> {

        private HashMap<String, File> fileParams;
        String Url;
        String TAG;
        String filePath;
        String fileName;
        File file;
        private int errorFlag = 0;
        HashMap<String, String> params;


        public PostFile(String TAG, String url, HashMap<String, File> fileParams, HashMap<String, String> params) {
            this.Url = url;
            this.TAG = TAG;
            this.params = params;
            this.fileParams = fileParams;

            if (fileParams == null) {
                Toast.makeText(context, "NO files to send", Toast.LENGTH_LONG).show();
                this.cancel(true);
            }
        }

        @Override
        public void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.d("----post result", result);

            if (errorFlag == 0) {
                Log.d("----Call : ", "positive");
                if (jsonParsing != null && jsonParsing)
                    performJsonParsing(TAG, result, classTypeForJson);
                else
                    serverResponseListener.positiveResponse(TAG, result);
            } else {
                Log.d("----Call : ", "negative");
                serverResponseListener.negativeResponse(TAG, result);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(progressHandler !=null)
            {
                progressHandler.handleProgress(values[0]);
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            String boundary = "===" + System.currentTimeMillis() + "===";
            String lineEnd = "\r\n";
            String twoHyphens = "--";

            HashMap<String, String> nameValuePair = new HashMap<String, String>(2);
            nameValuePair.put("", "");

            String contentDisposition = "Content-Disposition: form-data;";
            String contentTypeFile = "Content-Type: application/octet-stream";
            String responseStr = "";

            URL url = null;
            try {
                url = new URL(Url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (headerParams != null) {
                    applyHeaders(urlConnection);
                }
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setChunkedStreamingMode(1024);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setRequestProperty("Cache-Control", "no-cache");
                urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                if (fileParams.size() == 0) {
                    responseStr = "No File Provided";
                    errorFlag = 1;
                    return "";
                }

                BufferedOutputStream writer = new BufferedOutputStream(urlConnection.getOutputStream());
                writer.flush();
                StringBuffer requestBody = new StringBuffer();
                for (Map.Entry<String, File> fileParam : fileParams.entrySet()) {
                    writer.write(lineEnd.getBytes());
                    writer.write((twoHyphens + boundary + lineEnd).getBytes());
                    writer.write(contentDisposition.getBytes());
                    writer.write(String.format("name=%s;", fileParam.getKey()).getBytes());
                    writer.write(String.format("filename='%s';", fileParam.getValue().getName()).getBytes());
                    //writer.writeBytes(";" + lineEnd);
                    writer.write(lineEnd.getBytes());
                    writer.write(contentTypeFile.getBytes());
                    writer.write(lineEnd.getBytes());
                    writer.write(lineEnd.getBytes());

                    FileInputStream stream = new FileInputStream(fileParam.getValue());
                    long cur = 0;
                    long length = fileParam.getValue().length();
                    byte[] buff = new byte[1024];
                    int buffSize = 0;


                    while ((buffSize = stream.read(buff)) != -1) {
                        writer.write(buff, 0, buffSize);
                        writer.flush();
                        int perc = (int) (((double) cur / (double) (length / 1024)) * 100);
                        publishProgress(perc);
                        cur++;
                    }

//                    writer.write(fullyReadFileToBytes(fileParam.getValue()));
                    writer.write(lineEnd.getBytes());
                    writer.write(lineEnd.getBytes());
                    writer.write((twoHyphens + boundary).getBytes());
                }

                for (Map.Entry<String, String> param : this.params.entrySet()) {
                    writer.write(lineEnd.getBytes());
                    writer.write(contentDisposition.getBytes());
                    writer.write(String.format("name=%s", param.getKey()).getBytes());
                    //writer.writeBytes(";" + lineEnd);
                    writer.write(lineEnd.getBytes());
                    writer.write(lineEnd.getBytes());
                    writer.write(param.getValue().getBytes());
                    writer.write(lineEnd.getBytes());
                    writer.write((twoHyphens + boundary).getBytes());
                }
                writer.write((twoHyphens + lineEnd).getBytes());
/*

                if (this.params == null)
                    writer.writeBytes(getQuery2(nameValuePair));
                else
                    writer.writeBytes(getQuery2(this.params));
  */
                Log.d("----request", requestBody.toString());
                Log.d("----request", "");
//                writer.writeBytes(requestBody.toString());
                writer.flush();

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        responseStr += line;
                    }
                } else {
                    responseStr = "Reponse Code : " + responseCode + "(" + urlConnection.getResponseMessage() + ")";
                    errorFlag = 1;
                }
//                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
                FirebaseCrash.report(new Exception(e.getMessage()));
                errorFlag = 1;
                responseStr = "error1:" + e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                FirebaseCrash.report(new Exception(e.getMessage()));
                errorFlag = 1;
                responseStr = "error2:" + e.getMessage();
            }

            headerParams = null;
            return filter(responseStr);

        }
    }

    public void getFile(String TAG, String Url, HashMap<String, String> params, String saveFilePath, String saveFileName) {
        final GetFile post = new GetFile(TAG, Url, params, saveFilePath, saveFileName);

        if (!isNetworkConnected()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_LONG).show();
            return;
        }


        post.execute();

    }


    private class GetFile extends AsyncTask<Void, Void, String> {

        String Url;
        String TAG;
        File saveFile;
        String saveFilePath;
        String saveFileName;
        HashMap<String, String> params;
        private int errorFlag = 0;

        public GetFile(String TAG, String Url, HashMap<String, String> params, String saveFilePath, String saveFileName) {
            this.Url = Url;
            this.TAG = TAG;
            this.params = params;
            this.saveFilePath = saveFilePath;
            this.saveFileName = saveFileName;

            if (this.params == null)
                Log.d("----", "empty params");


            if (saveFilePath == null || saveFilePath.length() == 0 || saveFileName == null || saveFileName.length() == 0)
                Toast.makeText(context, "Field can't left be null", Toast.LENGTH_LONG).show();
            else
                this.saveFile = new File(this.saveFilePath, saveFileName);

        }

        @Override
        public void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (errorFlag == 0) {
                Log.d("----Call : ", "positive");
                if (jsonParsing != null && jsonParsing)
                    performJsonParsing(TAG, result, classTypeForJson);
                else
                    serverResponseListener.positiveResponse(TAG, result);
            } else {
                Log.d("----Call : ", "negative");
                serverResponseListener.negativeResponse(TAG, result);
            }

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            String responseStr = "";

            HashMap<String, String> nameValuePair = new HashMap<String, String>();
            nameValuePair.put("", "");

            URL url = null;
            try {
                url = new URL(Url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (headerParams != null) {
                    applyHeaders(urlConnection);
                }
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);


                OutputStream sendingStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(sendingStream, "UTF-8"));

                if (this.params == null)
                    writer.write(getQuery(nameValuePair));
                else
                    writer.write(getQuery(this.params));

                writer.flush();
                writer.close();
                sendingStream.close();

                if (urlConnection.getHeaderField("Content-Type").toString().trim().equals("text/html")) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        responseStr += line;
                    }
                    return responseStr;
                }
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    InputStream in = urlConnection.getInputStream();
                    Log.d("----1", saveFilePath);
                    Log.d("----2", String.valueOf(new File(saveFilePath).mkdir()));
                    Log.d("----3", String.valueOf(saveFile.createNewFile()));
                    OutputStream fileOutput = new FileOutputStream(saveFile);
                    BufferedOutputStream bufferedFileOutput = new BufferedOutputStream(fileOutput);
                    byte[] buf = new byte[1024];
                    int len;
                    int progressLen = 0;
                    long totalLen = urlConnection.getContentLength();
                    while ((len = in.read(buf)) > 0) {
                        progressLen += len;
                        Log.d("----download progress", String.valueOf(progressLen * 100 / totalLen) + "%");

                        bufferedFileOutput.write(buf, 0, len);
                    }
                    bufferedFileOutput.flush();
                    bufferedFileOutput.close();
                    in.close();
                    responseStr = "Download Completed";
                } else {
                    responseStr = "Response Code : " + responseCode + "(" + urlConnection.getResponseMessage() + ")";
                    errorFlag = 1;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                errorFlag = 1;
                FirebaseCrash.report(new Exception(e.getMessage()));
                responseStr = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                errorFlag = 1;
                FirebaseCrash.report(new Exception(e.getMessage()));
                responseStr = e.getMessage();
            }
            headerParams = null;
            return filter(responseStr);
        }
    }

    public void getStringGetResponse(String TAG, String Url) {
        final Get get = new Get(TAG, Url);

        if (!isNetworkConnected()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_LONG).show();
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                get.execute();
            }
        });
        thread.start();
    }

    public void getStringGetResponse(String TAG, String Url, HashMap<String, String> params) {
        final Get get = new Get(TAG, Url, params);

        if (!isNetworkConnected()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_LONG).show();
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                get.execute();
            }
        });
        thread.start();
    }

    private class Get extends AsyncTask<Void, Void, String> {

        private String TAG;
        String Url;
        int errorFlag = 0;

        HashMap<String, String> params;

        Get(String TAG, String Url) {
            this.TAG = TAG;
            this.Url = Url;
            params = null;
        }

        Get(String TAG, String Url, HashMap<String, String> params) {
            this.Url = Url;
            this.params = params;
            this.TAG = TAG;
        }

        @Override
        public void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.d("----get result", result);

            if (errorFlag == 0) {
                Log.d("----Call : ", "positive");
                if (jsonParsing != null && jsonParsing)
                    performJsonParsing(TAG, result, classTypeForJson);
                else
                    serverResponseListener.positiveResponse(TAG, result);
            } else {
                Log.d("----Call : ", "negative");
                serverResponseListener.negativeResponse(TAG, result);
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            String responseStr = "";

            URL url = null;
            try {
                if (this.params != null)
                    Url += urlEncodeUTF8(this.params);
                url = new URL(Url);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (headerParams != null) {
                    applyHeaders(urlConnection);
                }
                urlConnection.setRequestMethod("GET");
                //     urlConnection.setDoInput(true);
                //     urlConnection.setDoOutput(true);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        responseStr += line;
                    }
                } else {
                    responseStr = "Reponse Code : " + responseCode + "(" + urlConnection.getResponseMessage() + ")";
                    errorFlag = 1;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                FirebaseCrash.report(new Exception(e.getMessage()));
                errorFlag = 1;
                responseStr = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                FirebaseCrash.report(new Exception(e.getMessage()));
                errorFlag = 1;
                responseStr = e.getMessage();
            }

            Log.d("----get response", responseStr);
            headerParams = null;
            return filter(responseStr);
        }
    }


    public ProgressHandler getProgressHandler() {
        return progressHandler;
    }

    public void setProgressHandler(ProgressHandler progressHandler) {
        this.progressHandler = progressHandler;
    }

    private String filter(String response) {
        response = response.split("<!-- Hosting24 Analytics Code -->")[0];
        response = response.replace("<html>", "");
        response = response.replace("</html>", "");
        response = response.trim();

        return response;
    }

    private String getQuery(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> pair : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private String getQuery2(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> pair : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("; ");

            result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            result.append("=\"");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            result.append("\"");
        }

        return result.toString();
    }

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    byte[] fullyReadFileToBytes(File f) throws IOException {
        int size = (int) f.length();
        byte bytes[] = new byte[size];
        byte tmpBuff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        ;
        try {

            int read = fis.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            fis.close();
        }

        return bytes;
    }

    private void applyHeaders(HttpURLConnection urlConnection) {
        for (Map.Entry<String, String> param : headerParams.entrySet()) {
            urlConnection.setRequestProperty(param.getKey(), param.getValue());
        }
    }

    public Boolean isJsonParsing() {

        return jsonParsing;
    }

    public void doJsonParsing(Boolean jsonParsing) {
        this.jsonParsing = jsonParsing;
    }

    public Type getClassTypeForJson() {
        return classTypeForJson;
    }

    public void setClassTypeForJson(Type classTypeForJson) {
        this.classTypeForJson = classTypeForJson;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
