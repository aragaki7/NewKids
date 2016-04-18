package senori.or.jp.sharering.thread;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

public class ImageThread {

    private static Context mContext;
    // private AlbumArt

    private AlbumArtHandler mHandler;

    private static ImageThread albumArtLoader;
    private static AlbumArtThread albumArtThread;

    private int width;
    private int height;
    private int simplesize;

    public static ImageThread get(Context context) {
        albumArtLoader = new ImageThread(context);
        return albumArtLoader;
    }

    public ImageThread(Context context) {
        mContext = context;
        // mIsSetDelayingTime = false;
        mHandler = new AlbumArtHandler();
    }

    public void requestAlbumArt(ImageView view, int uri) {

        this.height = height;
        this.width = width;
        this.simplesize = simplesize;


        view.setTag(uri);

        albumArtThread = new AlbumArtThread(mContext);
        albumArtThread.set(new AlbumArtData(view, uri)).start();

    }

    private class AlbumArtData {
        public ImageView imageView;
        public int uri;
        public Bitmap albumArt;

        public AlbumArtData(AlbumArtData a, Bitmap b) {
            imageView = a.imageView;
            uri = a.uri;
            albumArt = b;
        }

        public AlbumArtData(ImageView i, int uri) {
            imageView = i;
            this.uri = uri;
        }

    }

    private class AlbumArtThread extends Thread {

        private AlbumArtData albumArtData;

        public AlbumArtThread(Context context) {

        }

        public AlbumArtThread set(AlbumArtData a) {
            albumArtData = a;
            return this;
        }

        public void run() {


            ContentResolver res = mContext.getContentResolver();
            if (albumArtData.imageView.getTag().equals(albumArtData.uri)) {
                BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
                ParcelFileDescriptor fd = null;
                Bitmap b = null;
                Bitmap tmp = null;
                int width = albumArtData.imageView.getWidth();
                int getheight = albumArtData.imageView.getHeight();

                // sBitmapOptionsCache.inPreferredConfig = Bitmap.Config.RGB_565;
                //sBitmapOptionsCache.inPurgeable = true;

                sBitmapOptionsCache.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(mContext.getResources(), albumArtData.uri, sBitmapOptionsCache);
                int imageheight = sBitmapOptionsCache.outHeight;
                int imagewidth = sBitmapOptionsCache.outWidth;

                // int scaleFactor = Math.min(imagewidth / width, imageheight / getheight);
                //Log.d("position", "" + scaleFactor);
                sBitmapOptionsCache.inJustDecodeBounds = false;
                sBitmapOptionsCache.inSampleSize = 4;
                sBitmapOptionsCache.inPurgeable = true;
                tmp = BitmapFactory.decodeResource(mContext.getResources(), albumArtData.uri, sBitmapOptionsCache);
                //b.recycle();
                Message msg = new Message();
                msg.obj = new AlbumArtData(albumArtData, tmp);

                mHandler.sendMessage(msg);


            }

        }
    }

    private static class AlbumArtHandler extends Handler {
        public void handleMessage(Message msg) {
            AlbumArtData albumArtData = (AlbumArtData) msg.obj;
            if (albumArtData.imageView.isShown() || albumArtData.imageView.getTag().equals(albumArtData.uri)) {

                albumArtData.imageView.setImageBitmap(albumArtData.albumArt);

                // } else {
                // Bitmap bitmap =
                // BitmapFactory.decodeResource(AlbumArtLoader.mContext.getResources(),
                // R.drawable.icon,
                // sBitmapOptionsCache);
                // albumArtData.imageView.setImageBitmap(bitmap);
            }

        }
    }
}
