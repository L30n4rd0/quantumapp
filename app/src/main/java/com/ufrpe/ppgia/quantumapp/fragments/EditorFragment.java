package com.ufrpe.ppgia.quantumapp.fragments;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ufrpe.ppgia.quantumapp.R;

/**
 * Created by leonardo on 6/7/17.
 */

public class EditorFragment extends Fragment {
    private ConstraintLayout mViewEditorLayout, mFragment_circuit_editor;
    private Context mContext;

    private ImageView
            mImageViewHadamard,
            mImageViewPI8,
            mImageViewPhase,
            mImageViewPauliY,
            mImageViewPauliX,
            mImageViewPauliZ,
            mImageViewControledPhase,
            mImageViewControledZ,
            mImageViewControledNot,
            mImageViewSwap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_circuit_editor, container, false);

        this.mFragment_circuit_editor = (ConstraintLayout) v.findViewById(R.id.fragment_circuit_editor);
        this.mViewEditorLayout = (ConstraintLayout) v.findViewById(R.id.editor_layout);
        this.mContext = getActivity().getApplicationContext();

        // Instantiate members
        this.mImageViewHadamard = (ImageView) v.findViewById(R.id.imageViewOperatorHadamard);
        this.mImageViewControledNot = (ImageView) v.findViewById(R.id.imageViewOperatorControledNot);
        this.mImageViewControledPhase = (ImageView) v.findViewById(R.id.imageViewOperatorControledPhase);
        this.mImageViewControledZ = (ImageView) v.findViewById(R.id.imageViewOperatorControledZ);
        this.mImageViewPauliX = (ImageView) v.findViewById(R.id.imageViewOperatorPauliX);
        this.mImageViewPauliY = (ImageView) v.findViewById(R.id.imageViewOperatorPauliY);
        this.mImageViewPauliZ = (ImageView) v.findViewById(R.id.imageViewOperatorPauliZ);
        this.mImageViewPhase = (ImageView) v.findViewById(R.id.imageViewOperatorPhase);
        this.mImageViewPI8 = (ImageView) v.findViewById(R.id.imageViewOperatorPI8);
        this.mImageViewSwap = (ImageView) v.findViewById(R.id.imageViewOperatorSwap);

        // Add OnTouchListener to Images View
        this.mImageViewHadamard.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewControledNot.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewControledPhase.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewControledZ.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPauliX.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPauliY.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPauliZ.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPhase.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPI8.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewSwap.setOnTouchListener(new MyOnTouchListener());

        v.findViewById(R.id.fragment_circuit_editor).setOnDragListener(new MyOnDragListener());

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.ic_operator_pauli_x);

        this.mFragment_circuit_editor.addView(imageView);

        return v;
    }

    // Internal and personal class for implement TouchListener codes
    class MyOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

//            ImageView imageView1 = new ImageView(mContext);
//            imageView1.setImageResource(R.drawable.ic_operator_pauli_x);
//            imageView1.setX(v.getX() + 50);
//
//            mFragment_circuit_editor.addView(imageView1);

            // optional param used on startDragAndDrop method
            ClipData data = ClipData.newPlainText("imagem", "texto");

            // Shadow used in object on dragging movement
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, shadowBuilder, v, 0);

            } else {
                v.startDrag(data, shadowBuilder, v, 0);

            }

            // Setting object view invisible during dragging movement
//            v.setVisibility(View.INVISIBLE);

            // Default return
            return true;
        }
    }

    // Internal and personal class for implement DragListener codes
    class MyOnDragListener implements View.OnDragListener {
        private  int action;
        private View viewOnDragging, viewfragment_circuit_editor;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            action = event.getAction();

            viewfragment_circuit_editor = LayoutInflater.from(getContext()).inflate(R.layout.fragment_circuit_editor, null);

            // Return the object view that started drag event
            viewOnDragging = (View) event.getLocalState();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:

                    Log.i("STARTED", "executed");
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("ENTERED", "executed");
                    break;

                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i("LOCATION", "executed");

                    Log.i("ImgX", viewOnDragging.getX() + "");
                    Log.i("ImgY", viewOnDragging.getY() + "");

                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("EXITED", "executed");
                    break;

                case DragEvent.ACTION_DROP:
                    Log.i("DROP", "executed");

                    // Getting clipData value to be used in anything ....
                    String clipData = event.getClipDescription().getLabel().toString();
                    Log.i("Clip", clipData);

                    // Setting new position to object view at time drop action
//                    viewOnDragging.setX( event.getX() - (viewOnDragging.getWidth() / 2) );
//                    viewOnDragging.setY( event.getY() - (viewOnDragging.getHeight() / 2) );

                    // Setting object view to visible again
//                    viewOnDragging.setVisibility(View.VISIBLE);

//                    ViewGroup viewGroup = (ViewGroup) viewfragment_circuit_editor.findViewById(R.id.editor_layout);

                    ImageView imageView1 = new ImageView(mContext);
                    imageView1.setImageResource(R.drawable.ic_operator_pauli_x);
                    imageView1.setX( event.getX() - (imageView1.getWidth() / 2) );
                    imageView1.setY( event.getY() - (imageView1.getHeight() / 2) );
                    imageView1.setMaxWidth(viewOnDragging.getWidth());
                    imageView1.setMaxHeight(viewOnDragging.getHeight());

                    mFragment_circuit_editor.addView(imageView1);

                    Log.i("InfoImage", viewOnDragging.getWidth() + "");

//                    textView.setVisibility(View.VISIBLE);

                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("ENDED", "executed");
                    break;

            }

            // Default return
            return true;
        }
    }

}
