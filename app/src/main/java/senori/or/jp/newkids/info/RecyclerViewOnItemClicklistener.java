package senori.or.jp.newkids.info;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by JupiteR on 2016-03-12.
 */
public class RecyclerViewOnItemClicklistener extends RecyclerView.SimpleOnItemTouchListener {
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public RecyclerViewOnItemClicklistener(Context context, final RecyclerView recyclerView, OnItemClickListener linesr) {
        this.mListener = linesr;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (view != null && mListener != null) {
                    mListener.onItemLongClick(view, recyclerView.getChildAdapterPosition(view));
                }

                super.onLongPress(e);
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if (view != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(view, rv.getChildAdapterPosition(view));
        }

        return false;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }
}
