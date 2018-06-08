package com.example.jayhind.workingwithservices;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class imgDowanloadingService extends IntentService {
    File file;
    static Bitmap bitmap[];

    private static final String ACTION_FOO = "com.example.jayhind.workingwithservices.action.FOO";
    private static final String ACTION_BAZ = "com.example.jayhind.workingwithservices.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.jayhind.workingwithservices.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.jayhind.workingwithservices.extra.PARAM2";
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager;

    public imgDowanloadingService() {
        super("imgDowanloadingService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, imgDowanloadingService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String url[]) {
        Intent intent = new Intent(context, imgDowanloadingService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, url);
//       intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1[] = intent.getStringArrayExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1);
            }
        }
    }
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void handleActionBaz(String url[]) {
        // TODO: Handle action Baz
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_launcher_background);
        mBuilder.setProgress(100, 0, false);
        mNotifyManager.notify(1, mBuilder.build());


        this.bitmap=dowanloadimage(url);
        if(bitmap!=null) {
            file = Environment.getExternalStorageDirectory();
            file = new File(file, "AndroidDatabasee");
            if (!file.exists()) {
                if (file.mkdir()) {
                    Toast.makeText(this, "Directory Created", Toast.LENGTH_SHORT).show();
                    for (int i=0;i<this.bitmap.length;i++) {
                        saveimage(this.bitmap[i]);
                        int p=(i*100)/this.bitmap.length;
                        mBuilder.setProgress(100, p, false);
                        mNotifyManager.notify(1, mBuilder.build());
                    }
                    mBuilder.setContentText("Download complete");
                    // Removes the progress bar
                    mBuilder.setProgress(0, 0, false);
                    mNotifyManager.notify(1, mBuilder.build());
                } else {
                    Toast.makeText(this, "Directory Create Failed", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                for (int i=0;i<this.bitmap.length;i++) {
                    saveimage(this.bitmap[i]);
                    int p=(i*100)/this.bitmap.length;
                    mBuilder.setProgress(100, p, false);
                    mNotifyManager.notify(1, mBuilder.build());
                }
                mBuilder.setContentText("Download complete");
                // Removes the progress bar
                mBuilder.setProgress(0, 0, false);
                mNotifyManager.notify(1, mBuilder.build());
            }
        }
    }

    private Bitmap[] dowanloadimage(String imageurl[]) {
        Bitmap bitmap[] = new Bitmap[imageurl.length];
        InputStream input =null;
        for (int i=0;i<imageurl.length;i++) {
            String url = imageurl[i];
            try {
                input = new java.net.URL(url).openStream();
                bitmap[i] = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void saveimage(Bitmap b) {
        Random r=new Random();
        String fnm=r.nextInt()+".jpg";
        File f1 = new File(file, String.valueOf(fnm));
        try {
            FileOutputStream fos = new FileOutputStream(f1, true);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(this, "okk", Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
