package com.oliva2.activities_fragments.activity_invoice;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.exoplayer2.util.Log;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.oliva2.R;
import com.oliva2.adapters.ProductBillAdapter;
import com.oliva2.databinding.ActivityInvoiceBinding;
import com.oliva2.language.Language;
import com.oliva2.models.InvoiceDataModel;
import com.oliva2.models.PdfDocumentAdpter;
import com.oliva2.models.UserModel;
import com.oliva2.preferences.Preferences;
import com.oliva2.remote.Api;
import com.oliva2.share.Common;
import com.oliva2.tags.Tags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION_CODES.KITKAT;

public class InvoiceActivity extends AppCompatActivity {
    private ActivityInvoiceBinding binding;
    private Preferences preferences;
    private String lang;
    private String salid;
    private UserModel userModel;
    private List<InvoiceDataModel.LimsProductSaleData> limsProductSaleDataList;
    private ProductBillAdapter productBillAdapter;
    private final String write_perm = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final int write_req = 100;
    //    private final String bluthoos_perm = Manifest.permission.BLUETOOTH;
//    private final String bluthoosadmin_perm = Manifest.permission.BLUETOOTH_ADMIN;
//
//    private final int bluthoos_req = 200;
//
    private boolean isPermissionGranted = false;
    private Image image;
    private Context context;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        this.context = newBase;
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        salid = intent.getStringExtra("data");
    }

    private void checkWritePermission() {

        if (ContextCompat.checkSelfPermission(this, write_perm) != PackageManager.PERMISSION_GRANTED) {


            isPermissionGranted = false;

            ActivityCompat.requestPermissions(this, new String[]{write_perm}, write_req);


        } else {
            takeScreenshot(2);
            isPermissionGranted = true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == write_req && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takeScreenshot(2);
        }
    }


    private void initView() {
        limsProductSaleDataList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        productBillAdapter = new ProductBillAdapter(limsProductSaleDataList, this);
        binding.recView.setNestedScrollingEnabled(false);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(productBillAdapter);
        getlastInvoice();
        binding.btnConfirm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkWritePermission();
            }
        });
    }

    public void getlastInvoice() {
        Log.e("kdkkd", salid);
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();


        Api.getService(Tags.base_url)
                .getInv(salid, userModel.getUser().getId() + "")
                .enqueue(new Callback<InvoiceDataModel>() {
                    @Override
                    public void onResponse(Call<InvoiceDataModel> call, Response<InvoiceDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                if (response.body() != null) {
                                    updateData(response.body());

//                                    Intent intent = new Intent(HomeActivity.this, InvoiceActivity.class);
//                                    intent.putExtra("data", response.body().getData());
//                                    startActivity(intent);
                                } else if (response.body().getStatus() == 400) {
                                    Toast.makeText(InvoiceActivity.this, getResources().getString(R.string.no_invoice), Toast.LENGTH_SHORT).show();

                                }

                            }

                        } else {
                            if (response.code() == 500) {
                                Toast.makeText(InvoiceActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("ERROR", response.message() + "");

                                //     Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<InvoiceDataModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    // Toast.makeText(SubscriptionActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(SubscriptionActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    private void updateData(InvoiceDataModel body) {
        binding.setModel(body);
        if (body.getLims_product_sale_data() != null && body.getLims_product_sale_data().size() > 0) {
            limsProductSaleDataList.addAll(body.getLims_product_sale_data());
            productBillAdapter.notifyDataSetChanged();
            Log.e("dkdkdk", limsProductSaleDataList.size() + "");
//      if(limsProductSaleDataList.size()>3){
//          LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 100);
//          lp.weight = 1;
//          binding.fl.setLayoutParams(lp);
//
//      }
        }

    }

    private void takeScreenshot(int mode) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mPath = getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/" + now + ".jpeg";
            } else {
                mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";
            }
            Log.e("kdkkdkd", mPath);
            // create bitmap screen capture
            NestedScrollView v1 = getWindow().getDecorView().findViewById(R.id.scrollView);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = getBitmapFromView(v1, v1.getChildAt(0).getHeight(), v1.getChildAt(0).getWidth());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();


            //setting screenshot in imageview
            String filePath = imageFile.getPath();
            //android.util.Log.e("ddlldld", filePath);

            if (Build.VERSION.SDK_INT >= KITKAT)
                convertPDF(filePath);
            //sendData(filePath);
            //printPhoto(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",new File(filePath)));

//   Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        } catch (Exception e) {
            // Several error may come out with file handling or DOM
            android.util.Log.e("ddlldld", e.toString());
        }
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    //    public void openBT(ScanResult scanResult) throws IOException {
//        try {
//            dialog2.dismiss();
//
//
//            // Standard SerialPortService ID
//            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
//            mmSocket.connect();
//            mmOutputStream = mmSocket.getOutputStream();
//            inputStream = mmSocket.getInputStream();
//
//            beginListenForData();
//
//             myLabel.setText("Bluetooth Opened");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//     * after opening a connection to bluetooth printer device,
//     * we have to listen and check if a data were sent to be printed.
//     */
//    void sendData(String strPath) throws IOException {
//
//
//        Bitmap imageBit = BitmapFactory.decodeFile(strPath);
//
//        ByteArrayOutputStream blob = new ByteArrayOutputStream();
//        imageBit.compress(Bitmap.CompressFormat.PNG, 0, blob);
//        byte[] bitmapdata = blob.toByteArray();
//
//     //   binding.image.setImageBitmap(imageBit);
//
//        findBT();
//
//     //   mmOutputStream.write(bitmapdata);
//        // tell the user data were sent
//        //  myLabel.setText("Data Sent");
//
//    }
//
//    void beginListenForData() {
////        try {
////            final Handler handler = new Handler();
////
////            // this is the ASCII code for a newline character
////            final byte delimiter = 10;
////
////            stopWorker = false;
////            readBufferPosition = 0;
////            readBuffer = new byte[1024];
////
////            workerThread = new Thread(new Runnable() {
////                public void run() {
////
////                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
////
////                        try {
////
////                            int bytesAvailable = inputStream.available();
////
////                            if (bytesAvailable > 0) {
////
////                                byte[] packetBytes = new byte[bytesAvailable];
////                                inputStream.read(packetBytes);
////
////                                for (int i = 0; i < bytesAvailable; i++) {
////
////                                    byte b = packetBytes[i];
////                                    if (b == delimiter) {
////
////                                        byte[] encodedBytes = new byte[readBufferPosition];
////                                        System.arraycopy(
////                                                readBuffer, 0,
////                                                encodedBytes, 0,
////                                                encodedBytes.length
////                                        );
////
////                                        // specify US-ASCII encoding
////                                        final String data = new String(encodedBytes, "US-ASCII");
////                                        readBufferPosition = 0;
////
////                                        // tell the user data were sent to bluetooth printer device
////                                        handler.post(new Runnable() {
////                                            public void run() {
////                                                // myLabel.setText(data);
////                                            }
////                                        });
////
////                                    } else {
////                                        readBuffer[readBufferPosition++] = b;
////                                    }
////                                }
////                            }
////
////                        } catch (IOException ex) {
////                            stopWorker = true;
////                        }
////
////                    }
////                }
////            });
////
////            workerThread.start();
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
////    void closeBT() throws IOException {
////        try {
////            stopWorker = true;
////            mmOutputStream.close();
////            mmInputStream.close();
////            mmSocket.close();
////            myLabel.setText("Bluetooth Closed");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//private void checkWifi(){
//    IntentFilter filter = new IntentFilter();
//    filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//    final WifiManager wifiManager =
//            (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);;
//    registerReceiver(new BroadcastReceiver(){
//        @Override
//        public void onReceive(Context arg0, Intent arg1) {
//            // TODO Auto-generated method stub
//            Log.d("wifi","Open Wifimanager");
//
//            String scanList = wifiManager.getScanResults().toString();
//            Log.d("wifi","Scan:"+scanList);
//        }
//    },filter);
//    wifiManager.startScan();
//}
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void convertPDF(String path) {

        String FILE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            FILE = getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/FirstPdf.pdf";
        } else {
            FILE = Environment.getExternalStorageDirectory().toString() + "/FirstPdf.pdf";
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            try {
                image = Image.getInstance(path);
                // image.getHeight();
                //    document.setPageSize(new Rectangle(image.getAbsoluteX(),image.getAbsoluteY()));
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - 0) / image.getWidth()) * 100;
                float scaler1 = ((document.getPageSize().getHeight() - document.bottom()
                        - document.topMargin() - 0) / image.getHeight());// 0 means you have no indentation. If you have any, change it.
                image.scalePercent(scaler);
//                image.setAbsolutePosition(
//                        (document.getPageSize().getWidth() - image.getScaledWidth()) / 2,
//                        (document.getPageSize().getHeight() - image.getScaledHeight()) / 2);
                image.scaleAbsoluteHeight(scaler1);
                image.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight() - 80);
                image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
                //  document.setPageSize(new com.itextpdf.text.Rectangle(image.getWidth(), image.getScaledHeight() * 200));

                document.add(image);
                document.close();
                //  document.add(new Paragraph("My Heading"));
                printpdf();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            android.util.Log.e("message1", e.toString());
        } catch (FileNotFoundException e) {
            android.util.Log.e("message2", e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printpdf() {

        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
        try {
            String FILE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                FILE = getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/FirstPdf.pdf";
            } else {
                FILE = Environment.getExternalStorageDirectory().toString() + "/FirstPdf.pdf";
            }
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdpter(context, FILE);
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception e) {
            android.util.Log.e("sssssss", e.getMessage());
        }
    }
}
