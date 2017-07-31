package com.ufrpe.ppgia.quantumapp.fragments;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ufrpe.ppgia.quantumapp.R;
import com.ufrpe.ppgia.quantumapp.circuit.CircuitLine;
import com.ufrpe.ppgia.quantumapp.circuit.StateMachine;
import com.ufrpe.ppgia.quantumapp.circuit.Utils;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

/**
 * Created by leonardo on 6/7/17.
 */

public class EditorFragment extends Fragment {
    private ConstraintLayout mViewCircuitTimeLine, mViewBottom, mViewTopLayout;//, mFragment_circuit_editor;
    private Context mContext;
    private LayoutInflater mInflater;
    private Button mButtonAlterQubitValue, mButtonDeleteQubit, mButtonAddQubit;
    private static final int
    ALTER_QUBIT_VALUE = 1, DELETE_QUBIT = 2, ADD_QUBIT = 3;
    private int dialogOption = 0;
    private AlertDialog mAlertDialog;
    private AlertDialog.Builder mAlertDialogBuilder;

    // Represents the circuit with qubits and operators
    private List< List<ImageView> > mCircuit;

    // Represents the circuit's first line
    private List<ImageView> mFirstCircuitLine;

    private ImageView
            mImageViewHadamard,
            mImageViewPauliY,
            mImageViewPauliX,
            mImageViewPauliZ,
            mImageViewPhase,
            mImageViewPI8,
            mImageViewControledPhase,
            mImageViewControledZ,
            mImageViewControledNot,
            mImageViewSwap,
            mFirstQubit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_circuit_editor, container, false);

        // Instantiate members
        this.mViewCircuitTimeLine = (ConstraintLayout) v.findViewById(R.id.circuitTimeLine);
        this.mViewTopLayout = (ConstraintLayout) v.findViewById(R.id.topLayout);
        this.mViewBottom = (ConstraintLayout) v.findViewById(R.id.bottomLayout);
        this.mContext = inflater.getContext();
        this.mInflater = inflater;

        this.mFirstQubit = (ImageView) v.findViewById(R.id.imageViewFirstQubit);
        this.mFirstQubit.setTag(R.id.qubit_value, 0);
        this.mFirstQubit.setTag(R.id.xml_resource_id, R.drawable.ic_qubit_0);
        this.mFirstQubit.setOnClickListener(new MyOnClickListenerQubit());
//        this.mFirstQubit.setOnTouchListener(new MyOnTouchListenerQubit());
//        this.mFirstQubit.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                Toast.makeText(mContext, "sdfsdfsdfsdf", Toast.LENGTH_LONG).show();
//
//                return false;
//            }
//        });


        Button buttonSimulate = (Button) v.findViewById(R.id.buttonSimulate);
        buttonSimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCircuitInDialog();
            }
        });

        View dialogView = inflater.inflate(R.layout.layout_dialog, null);

//        this.mButtonAddQubit = (Button) dialogView.findViewById(R.id.buttonAddQubit);
//        this.mButtonAlterQubitValue = (Button) dialogView.findViewById(R.id.buttonAlterQubitValue);
//        this.mButtonDeleteQubit = (Button) dialogView.findViewById(R.id.buttonDeleteQubit);

        dialogView.findViewById(R.id.buttonAddQubit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("fsdfsdf", "add");
                dialogOption = ADD_QUBIT;
                mAlertDialog.dismiss();

            }
        });

        dialogView.findViewById(R.id.buttonAlterQubitValue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("fsdfsdf", "alter");
                dialogOption = ALTER_QUBIT_VALUE;
                mAlertDialog.dismiss();

            }
        });

        dialogView.findViewById(R.id.buttonDeleteQubit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("fsdfsdf", "delete");
                dialogOption = DELETE_QUBIT;
                mAlertDialog.dismiss();

            }
        });

        this.mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        this.mAlertDialogBuilder.setView(dialogView);
        this.mAlertDialogBuilder.setTitle("Opções");
//        this.mAlertDialog = this.mAlertDialogBuilder.create();
//        this.mAlertDialog.show();

        this.mFirstCircuitLine = new ArrayList<>();
        this.mCircuit = new ArrayList<>();

        this.mFirstCircuitLine.add(this.mFirstQubit);

        this.mCircuit.add(this.mFirstCircuitLine);

        this.mImageViewHadamard = (ImageView) v.findViewById(R.id.imageViewOperatorHadamard);
        this.mImageViewPauliX = (ImageView) v.findViewById(R.id.imageViewOperatorPauliX);
        this.mImageViewPauliY = (ImageView) v.findViewById(R.id.imageViewOperatorPauliY);
        this.mImageViewPauliZ = (ImageView) v.findViewById(R.id.imageViewOperatorPauliZ);
        this.mImageViewPhase = (ImageView) v.findViewById(R.id.imageViewOperatorPhase);
        this.mImageViewPI8 = (ImageView) v.findViewById(R.id.imageViewOperatorPI8);
        this.mImageViewControledNot = (ImageView) v.findViewById(R.id.imageViewOperatorControledNot);
        this.mImageViewControledPhase = (ImageView) v.findViewById(R.id.imageViewOperatorControledPhase);
        this.mImageViewControledZ = (ImageView) v.findViewById(R.id.imageViewOperatorControledZ);
        this.mImageViewSwap = (ImageView) v.findViewById(R.id.imageViewOperatorSwap);

        // Setting tag xml_resource_id
        this.mImageViewHadamard.setTag(R.id.xml_resource_id, R.drawable.ic_operator_hadamard);
        this.mImageViewPauliX.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pauli_x);
        this.mImageViewPauliY.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pauli_y);
        this.mImageViewPauliZ.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pauli_z);
        this.mImageViewPhase.setTag(R.id.xml_resource_id, R.drawable.ic_operator_phase);
        this.mImageViewPI8.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pi_8);
        this.mImageViewControledNot.setTag(R.id.xml_resource_id, R.drawable.ic_operator_controled_not);
        this.mImageViewControledPhase.setTag(R.id.xml_resource_id, R.drawable.ic_operator_controled_phase);
        this.mImageViewControledZ.setTag(R.id.xml_resource_id, R.drawable.ic_operator_controled_z);
        this.mImageViewSwap.setTag(R.id.xml_resource_id, R.drawable.ic_operator_swap);

        // Setting tag operator_id
        this.mImageViewHadamard.setTag(R.id.operator_id, 1);
        this.mImageViewPauliX.setTag(R.id.operator_id, 2);
        this.mImageViewPauliY.setTag(R.id.operator_id, 3);
        this.mImageViewPauliZ.setTag(R.id.operator_id, 4);
        this.mImageViewPhase.setTag(R.id.operator_id, 5);
        this.mImageViewPI8.setTag(R.id.operator_id, 6);
        this.mImageViewControledNot.setTag(R.id.operator_id, 7);
        this.mImageViewControledPhase.setTag(R.id.operator_id, 8);
        this.mImageViewControledZ.setTag(R.id.operator_id, 9);
        this.mImageViewSwap.setTag(R.id.operator_id, 10);

        // Add OnTouchListener to Images View
        this.mImageViewHadamard.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPauliX.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPauliY.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPauliZ.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPhase.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewPI8.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewControledNot.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewControledPhase.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewControledZ.setOnTouchListener(new MyOnTouchListener());
        this.mImageViewSwap.setOnTouchListener(new MyOnTouchListener());

//        v.findViewById(R.id.fragment_circuit_editor).setOnDragListener(new MyOnDragListener());

        v.findViewById(R.id.circuitTimeLine).setOnDragListener(new MyOnDragListener());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    Log.i("Info no while", mViewTopLayout.getY() + "");
//
//                    try {
//                        Thread.sleep(800);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
////                Log.i("Info no while", mViewTopLayout.getHeight() + "");
//
//            }
//        }).start();



        return v;
    }

    private void showCircuitInDialog() {

        /*
         * Lista que recebe os qubits da tela do circuito.
         */
        List<Matrix> matrixResult = new ArrayList<>();

        StateMachine stateMachine = StateMachine.getInstance();

        Utils utils = Utils.getInstance();

        for (int i = 0; i<mCircuit.size(); i++) {
        /*
         * Classe que modela os qubits de cada linha do circuito
         */
            CircuitLine circuitLine = new CircuitLine();

            String circuitString = "";
            for (int j = 0; j < mFirstCircuitLine.size(); j++) {
                if (j == 0) {
                    circuitLine.setKet((Integer) mFirstCircuitLine.get(j).getTag(R.id.qubit_value));
//                Log.i("QubitValue", mFirstCircuitLine.get(i).getTag(R.id.qubit_value) + "");
                    //circuitString += mFirstCircuitLine.get(i).getTag(R.id.qubit_value) + " ";

                } else {
                    circuitLine.setListGate((Integer) mFirstCircuitLine.get(j).getTag(R.id.operator_id));
//                Log.i("OperatorValue", mFirstCircuitLine.get(i).getTag(R.id.operator_id) + "");
                    //circuitString += mFirstCircuitLine.get(i).getTag(R.id.operator_id) + " ";
                }
            }

            /*
                 * Os qubits são passados para máquina de estados e devolve uma matriz.
                 * Caso seja melhor o objeto CircuitLine pode ser passado diretamente ou criado um laço para
                 * percorrer a lista e fazer de forma iterativa.
                 */
            Matrix matrix = stateMachine.circuitCalculator(circuitLine);

            matrixResult.add(matrix);

            utils.printMatrix(matrix);

        }

//        View viewDialog = mInflater.inflate(C0453R.layout.dialog_test, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
//        alertDialogBuilder.setView(viewDialog);
        alertDialogBuilder.setTitle("Circuito");
        alertDialogBuilder.setMessage("33frgtgt");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
//        alertDialogBuilder.setNegativeButton((int) C0453R.string.button_cancel, new C04523());
        alertDialogBuilder.create().show();
    }

    private void changeQubitValue (View v) {
        if ( (int) v.getTag(R.id.qubit_value) == 0) {
            v.setTag(R.id.qubit_value, 1);
            v.setTag(R.id.xml_resource_id, R.drawable.ic_qubit_1);
            ((ImageView) v).setImageResource(R.drawable.ic_qubit_1);

        } else {
            v.setTag(R.id.qubit_value, 0);
            v.setTag(R.id.xml_resource_id, R.drawable.ic_qubit_0);
            ((ImageView) v).setImageResource(R.drawable.ic_qubit_0);

        }

    }

    // Internal and personal class for implement OnClickListener to qubits
    class MyOnClickListenerQubit implements View.OnClickListener {

        @Override
        public void onClick(final View v) {

//            mAlertDialog = mAlertDialogBuilder.create();
//            mAlertDialog.show();

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Opções");
            builder.setItems(new CharSequence[] {"ALTERAR VALOR", "APAGAR QUBIT", "ADICIONAR QUBIT"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch (which) {
                                case 0:
                                    Toast.makeText(mContext, "change value", Toast.LENGTH_LONG).show();

                                    changeQubitValue(v);

                                    break;

                                case 1:
                                    Toast.makeText(mContext, "clicked 2", Toast.LENGTH_LONG).show();
                                    break;

                                case 2:
                                    Toast.makeText(mContext, "clicked 3", Toast.LENGTH_LONG).show();
                                    break;

                            }
                        }
                    });
            builder.create().show();

            Log.i("gggggggggggg", "fffffffffffffffffffff");


        }
    }

    // Internal and personal class for implement TouchListener codes
    class MyOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

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
        private View viewOnDragging; //, viewfragment_circuit_editor;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            action = event.getAction();

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
//                    Log.i("LOCATION", "executed");
//                    Log.i("ImgX", viewOnDragging.getX() + "");
//                    Log.i("ImgY", viewOnDragging.getY() + "");
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("EXITED", "executed");
                    break;

                case DragEvent.ACTION_DROP:
                    Log.i("DROP", "executed");

                    // Getting clipData value to be used in anything ....
                    String clipData = event.getClipDescription().getLabel().toString();
                    Log.i("Clip", clipData);

//                    Log.i("viewOnDragging getY", viewOnDragging.getY() + "");
//                    Log.i("W, H", mViewCircuitTimeLine.getLayoutParams().width + ", " + mViewCircuitTimeLine.getLayoutParams().height);

//                    ImageView test = (ImageView) mViewCircuitTimeLine.findViewById(R.id.imageViewLine);
//                    Log.i("imageViewLine getY", test.getY() + "");
//                    Log.i("imageViewLine hegth", test.getHeight() + "");

                    ImageView imageViewTemp = new ImageView(mContext);
                    imageViewTemp.setImageResource((int) viewOnDragging.getTag(R.id.xml_resource_id));
                    imageViewTemp.setTag(R.id.operator_id, viewOnDragging.getTag(R.id.operator_id));
                    imageViewTemp.setLayoutParams(
                            new ConstraintLayout.LayoutParams(
                                    viewOnDragging.getWidth(),
                                    viewOnDragging.getHeight()
                            )
                    );

                    if (mFirstCircuitLine.size() == 1) {
                        imageViewTemp.setX( 2f );
                        imageViewTemp.setY( 0f );

                        mViewCircuitTimeLine.setLayoutParams(
                                new FrameLayout.LayoutParams(
                                        mViewCircuitTimeLine.getLayoutParams().width + (9 * imageViewTemp.getLayoutParams().width),
                                        mViewCircuitTimeLine.getLayoutParams().height + imageViewTemp.getLayoutParams().height
                                )
                        );

                    } else {
                        imageViewTemp.setX( mFirstCircuitLine.get(mFirstCircuitLine.size() - 1).getX() + mFirstCircuitLine.get(mFirstCircuitLine.size() - 1).getWidth() + 2  );
                        imageViewTemp.setY( 0f );
                    }

                    Log.i("W, H", mViewCircuitTimeLine.getLayoutParams().width + ", " + mViewCircuitTimeLine.getLayoutParams().height);
                    mViewCircuitTimeLine.addView(imageViewTemp);

                    mFirstCircuitLine.add(imageViewTemp);

                    Log.i("First", mFirstQubit.getLayoutParams().height + "");

                    for (int i = 0; i < mFirstCircuitLine.size(); i++) {
                        if (i == 0) {
                            Log.i("QubitValue", mFirstCircuitLine.get(i).getTag(R.id.qubit_value) + "");

                        } else {
                            Log.i("OperatorValue", mFirstCircuitLine.get(i).getTag(R.id.operator_id) + "");
                        }
                    }

//                    Log.i("W H", mViewCircuitTimeLine.getLayoutParams().width + " " + mViewCircuitTimeLine.getLayoutParams().height);
//
//                    Log.i("viewOnDragging getY", viewOnDragging.getY() + "");
//                    Log.i("viewOnDragging.getTag", viewOnDragging.getTag(R.id.xml_resource_id).toString());

                    // Setting object view to visible again
//                    viewOnDragging.setVisibility(View.VISIBLE);

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
