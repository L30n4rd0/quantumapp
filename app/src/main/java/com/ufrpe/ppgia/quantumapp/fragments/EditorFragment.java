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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Jama.Matrix;

/**
 * Created by leonardo on 6/7/17.
 */

public class EditorFragment extends Fragment {
    private ConstraintLayout mViewCircuitTimeLine, mViewBottom, mLeftLayout, mViewTopLayout;//, mFragment_circuit_editor;
    private Context mContext;
    private LayoutInflater mInflater;
    private static final int OPERATOR_LOCATION_TOP = 1, OPERATOR_LOCATION_CIRCUIT = 2;

    // Represents the circuit with qubits and operators
    private List< List<ImageView> > mCircuit;

    // Timeliness added together with each qubit
    private List<ImageView> mLinesAdded;

//    private ImageView
//            mImageViewHadamard,
//            mImageViewPauliY,
//            mImageViewPauliX,
//            mImageViewPauliZ,
//            mImageViewPhase,
//            mImageViewPI8,
//            mImageViewControledPhase,
//            mImageViewControledZ,
//            mImageViewControledNot,
//            mImageViewSwap,
//            mFirstQubit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_circuit_editor, container, false);

        // Instantiating members
        this.mViewCircuitTimeLine = (ConstraintLayout) v.findViewById(R.id.circuitTimeLine);
        this.mViewTopLayout = (ConstraintLayout) v.findViewById(R.id.topLayout);
        this.mViewBottom = (ConstraintLayout) v.findViewById(R.id.bottomLayout);
        this.mLeftLayout = (ConstraintLayout) v.findViewById(R.id.leftLayout);
        this.mContext = inflater.getContext();
        this.mInflater = inflater;

        ImageView mFirstQubit = (ImageView) v.findViewById(R.id.imageViewFirstQubit);
        mFirstQubit.setTag(R.id.qubit_value, 0);
        mFirstQubit.setTag(R.id.xml_resource_id, R.drawable.ic_qubit_0);
        mFirstQubit.setX(0f);
        mFirstQubit.setY(0f);
        mFirstQubit.setOnClickListener(new MyOnClickListenerQubit());

        this.mLinesAdded = new ArrayList<>();
        this.mLinesAdded.add((ImageView) this.mViewCircuitTimeLine.findViewById(R.id.imageViewLine));


        List<ImageView> mFirstCircuitLine = new ArrayList<>();
        mFirstCircuitLine.add(mFirstQubit);

        this.mCircuit = new ArrayList<>();
        this.mCircuit.add(mFirstCircuitLine);

        // Instantiating and setting operators image views
        instantiateAndSettingOperators();

        // Adding DragListener to view circuitTimeLine
        v.findViewById(R.id.circuitTimeLine).setOnDragListener(new MyOnDragListener());

        Button buttonSimulate = (Button) v.findViewById(R.id.buttonSimulate);
        buttonSimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultsInDialog();
            }
        });

        return v;
    }

    // Internal and personal class for implement OnClickListener to qubits
    class MyOnClickListenerQubit implements View.OnClickListener {

        @Override
        public void onClick(final View qubitView) {

//            mAlertDialog = mAlertDialogBuilder.create();
//            mAlertDialog.show();

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Opções");
            builder.setItems(new CharSequence[] {"ALTERAR VALOR", "APAGAR QUBIT (-)", "ADICIONAR QUBIT (+)"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch (which) {
                                case 0: // Alter qubit value

                                    changeQubitValue(qubitView);

                                    break;

                                case 1: // Delete qubit

                                    removeQubitFromCircuit(qubitView);

                                    break;

                                case 2: // Add qubit

                                    addQubitToCircuit(qubitView);

                                    break;

                            }
                        }
                    });

            builder.create().show();

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

        @Override
        public boolean onDrag(View v, DragEvent event) {
            action = event.getAction();

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

                    removeOperatorFromCircuit(event);

                    Log.i("EXITED", "executed");
                    break;

                case DragEvent.ACTION_DROP:

                    // Getting clipData value to be used in anything ....
//                    String clipData = event.getClipDescription().getLabel().toString();
//                    Log.i("Clip", clipData);

                    addOperatorToCircuit(event);

                    Log.i("DROP", "executed");
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("ENDED", "executed");
                    break;

            }

            // Default return
            return true;
        }
    }

    private void instantiateAndSettingOperators() {

        // Create operators image views
        ImageView mImageViewHadamard = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorHadamard);
        ImageView mImageViewPauliX = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorPauliX);
        ImageView mImageViewPauliY = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorPauliY);
        ImageView mImageViewPauliZ = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorPauliZ);
        ImageView mImageViewPhase = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorPhase);
        ImageView mImageViewPI8 = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorPI8);
//        ImageView mImageViewControlledNot = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorControlledNot);
//        ImageView mImageViewControlledPhase = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorControlledPhase);
//        ImageView mImageViewControlledZ = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorControlledZ);
//        ImageView mImageViewSwap = (ImageView) mViewTopLayout.findViewById(R.id.imageViewOperatorSwap);

        // Setting tag xml_resource_id
        mImageViewHadamard.setTag(R.id.xml_resource_id, R.drawable.ic_operator_hadamard);
        mImageViewPauliX.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pauli_x);
        mImageViewPauliY.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pauli_y);
        mImageViewPauliZ.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pauli_z);
        mImageViewPhase.setTag(R.id.xml_resource_id, R.drawable.ic_operator_phase);
        mImageViewPI8.setTag(R.id.xml_resource_id, R.drawable.ic_operator_pi_8);
//        mImageViewControlledNot.setTag(R.id.xml_resource_id, R.drawable.ic_operator_controlled_not);
//        mImageViewControlledPhase.setTag(R.id.xml_resource_id, R.drawable.ic_operator_controlled_phase);
//        mImageViewControlledZ.setTag(R.id.xml_resource_id, R.drawable.ic_operator_controlled_z);
//        mImageViewSwap.setTag(R.id.xml_resource_id, R.drawable.ic_operator_swap);

        // Setting tag qubit_location
        mImageViewHadamard.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
        mImageViewPauliX.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
        mImageViewPauliY.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
        mImageViewPauliZ.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
        mImageViewPhase.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
        mImageViewPI8.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
//        mImageViewControlledNot.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
//        mImageViewControlledPhase.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
//        mImageViewControlledZ.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);
//        mImageViewSwap.setTag(R.id.operator_location, OPERATOR_LOCATION_TOP);

        // Setting tag operator_id
        mImageViewHadamard.setTag(R.id.operator_id, 1);
        mImageViewPauliX.setTag(R.id.operator_id, 2);
        mImageViewPauliY.setTag(R.id.operator_id, 3);
        mImageViewPauliZ.setTag(R.id.operator_id, 4);
        mImageViewPhase.setTag(R.id.operator_id, 5);
        mImageViewPI8.setTag(R.id.operator_id, 6);
//        mImageViewControlledNot.setTag(R.id.operator_id, 7);
//        mImageViewControlledPhase.setTag(R.id.operator_id, 8);
//        mImageViewControlledZ.setTag(R.id.operator_id, 9);
//        mImageViewSwap.setTag(R.id.operator_id, 10);

        // Add OnTouchListener to Images View
        mImageViewHadamard.setOnTouchListener(new MyOnTouchListener());
        mImageViewPauliX.setOnTouchListener(new MyOnTouchListener());
        mImageViewPauliY.setOnTouchListener(new MyOnTouchListener());
        mImageViewPauliZ.setOnTouchListener(new MyOnTouchListener());
        mImageViewPhase.setOnTouchListener(new MyOnTouchListener());
        mImageViewPI8.setOnTouchListener(new MyOnTouchListener());
//        mImageViewControlledNot.setOnTouchListener(new MyOnTouchListener());
//        mImageViewControlledPhase.setOnTouchListener(new MyOnTouchListener());
//        mImageViewControlledZ.setOnTouchListener(new MyOnTouchListener());
//        mImageViewSwap.setOnTouchListener(new MyOnTouchListener());

    }

    private void showResultsInDialog() {

        /*
         * Lista que recebe os qubits da tela do circuito.
         */
        List<CircuitLine> listCircuitLine = new ArrayList<>();

        List<Matrix> listLinesResults;

        Matrix matrixCircuitResultTensor, matrixCircuitResultPercent;

        StateMachine stateMachine = StateMachine.getInstance();

        Utils utils = Utils.getInstance();

        // Add lines to listCircuitLine
        for (List<ImageView> circuitLineImages: mCircuit) {

            /*
             * Classe que modela os qubits de cada linha do circuito
             */
            CircuitLine circuitLine = new CircuitLine();

            for (int j = 0; j < circuitLineImages.size(); j++) {
                if (j != 0) {
                    circuitLine.setGate((Integer) circuitLineImages.get(j).getTag(R.id.operator_id));

                } else {
                    // The position 0 of each circuit line is the qubit value
                    circuitLine.setKet((Integer) circuitLineImages.get(j).getTag(R.id.qubit_value));

                }
            }

            listCircuitLine.add(circuitLine);

        }

        /*
         * Os qubits são passados para máquina de estados e devolve uma matriz.
         * Caso seja melhor o objeto CircuitLine pode ser passado diretamente ou criado um laço para
         * percorrer a lista e fazer de forma iterativa.
         */
        listLinesResults = stateMachine.circuitCalculator(listCircuitLine);

        /*
		 * Produto tensorial
		 */
        matrixCircuitResultTensor = utils.tensor(listLinesResults);
        utils.printMatrix(matrixCircuitResultTensor);

		/*
		 * Calculo das porcentagens
		 */
        matrixCircuitResultPercent = utils.percentCalculator(matrixCircuitResultTensor);
        utils.printMatrix(matrixCircuitResultPercent);

        String stringCircuitStatus = "Circuito debugging\n";

        for (int i = 0; i < mCircuit.size(); i++) {
            for (int j = 0; j < mCircuit.get(i).size(); j++) {
                if (j != 0) {
                    stringCircuitStatus += (int) mCircuit.get(i).get(j).getTag(R.id.operator_id) + " ";

                } else {
                    stringCircuitStatus += (int) mCircuit.get(i).get(j).getTag(R.id.qubit_value) + " ";

                }
            }
            stringCircuitStatus += "\n";

        }

        String stringResult = "Matriz\tPercent\n";

        for (int i = 0; i < matrixCircuitResultTensor.getRowDimension(); i++) {

            for (int j = 0; j < matrixCircuitResultTensor.getColumnDimension(); j++) {

                stringResult += matrixCircuitResultTensor.get(i, j) + "\t\t\t" + matrixCircuitResultPercent.get(i, j) + "%\n";

            }
        }

        stringResult += "\n";

//        View viewDialog = mInflater.inflate(C0453R.layout.dialog_test, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
//        alertDialogBuilder.setView(viewDialog);
        alertDialogBuilder.setTitle("Circuito Resultado");
        alertDialogBuilder.setMessage(stringResult + "...................................\n" + stringCircuitStatus);
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

    private void addQubitToCircuit(View qubitView) {

        // Create new ImageView to new Qubit
        ImageView qubitTempImageView = new ImageView(mContext);

        // *** Setting attributes to new ImageView operator
        qubitTempImageView.setImageResource((int) qubitView.getTag(R.id.xml_resource_id));
        qubitTempImageView.setTag(R.id.xml_resource_id, qubitView.getTag(R.id.xml_resource_id));
        qubitTempImageView.setTag(R.id.qubit_value, qubitView.getTag(R.id.qubit_value));
        qubitTempImageView.setLayoutParams(
                new ConstraintLayout.LayoutParams(
                        qubitView.getWidth(),
                        qubitView.getHeight()
                )
        );

        // Setting location attribute

        float
                yLastQubitAdded = mCircuit.get(mCircuit.size() - 1).get(0).getY(),
                yImage = (float) ( yLastQubitAdded + (qubitView.getHeight() * 1.5) );

        qubitTempImageView.setX( 0 );
        qubitTempImageView.setY( yImage );

        // Set OnClickListener
        qubitTempImageView.setOnClickListener(new MyOnClickListenerQubit());

        // Create new circuit line
        List<ImageView> newCircuitLine = new ArrayList<>();

        // Add new qubitImageView to new circuit line
        newCircuitLine.add(qubitTempImageView);

        // Add newCircuitLine to circuit
        mCircuit.add(newCircuitLine);

        // Set new size to left layout according to circuit size
        mLeftLayout.setLayoutParams(
                new ConstraintLayout.LayoutParams(
                        // Set same width
                        mLeftLayout.getLayoutParams().width,
                        // Set height
                        (int) ( qubitView.getLayoutParams().height * 1.5 * (mCircuit.size() + 1) )
                )
        );

        // Set new size to CircuitTimeLine layout according to circuit size
        mViewCircuitTimeLine.setLayoutParams(
                new FrameLayout.LayoutParams(
                        // Set same width
                        qubitView.getWidth() * 21,
                        // Set height
                        (int) ( qubitView.getHeight() * 1.5 * (mCircuit.size() + 1) )
                )
        );

        ImageView imageViewLine = new ImageView(mContext);
        imageViewLine.setImageResource(R.drawable.ic_line);
        imageViewLine.setLayoutParams(
                new ConstraintLayout.LayoutParams(
                        21 * qubitView.getWidth(),
                        qubitView.getHeight()
                )
        );
        imageViewLine.setX(0f);
        imageViewLine.setY(qubitTempImageView.getY());

        mViewCircuitTimeLine.addView(imageViewLine);

        mLinesAdded.add(imageViewLine);

        // Add new qubitImageView to layout
        mLeftLayout.addView(qubitTempImageView);

    }

    private void removeQubitFromCircuit(View qubitView) {

        if (qubitView.equals(mCircuit.get(0).get(0))) {
            Toast.makeText(mContext, "Não é possível apagar o primeiro Qubit!", Toast.LENGTH_LONG).show();

        } else {

            float oldY = -1f, currentY;

            // Verify line that will deleted
            for (int i = 1; i < mCircuit.size(); i++) {

                // Used view in the condition for not conflict with organizing circuit loop
                if ( qubitView.equals(mCircuit.get(i).get(0)) ) {

                    // 'y' position of line that will be removed
                    oldY = mCircuit.get(i).get(0).getY();

                    for (int j = 0; j < mCircuit.get(i).size(); j++) {
                        if (j != 0) {
                            // Removing operators views
                            mViewCircuitTimeLine.removeView(mCircuit.get(i).get(j));

                        } else {
                            // Removing qubit view
                            mLeftLayout.removeView(mCircuit.get(i).get(0));

                        }
                    }

                    int indiceLastLineAdded = mLinesAdded.size() - 1;
                    mViewCircuitTimeLine.removeView(mLinesAdded.get(indiceLastLineAdded));
                    mLinesAdded.remove(indiceLastLineAdded);
                    mCircuit.remove(i);

                }

                // Organizing circuit positions
                // 'i' is now the next line in the array
                if (oldY > 0 && i < mCircuit.size()) {

                    // 'y' of qubit in the next line
                    currentY = mCircuit.get(i).get(0).getY();

                    for (int j = 0; j < mCircuit.get(i).size(); j++) {

                        // Setting position of qubit and operators images views to oldY
                        mCircuit.get(i).get(j).setY(oldY);

                    }

                    oldY = currentY;
                }
            }

            // Set new size to left layout according to circuit size
            mLeftLayout.setLayoutParams(
                    new ConstraintLayout.LayoutParams(
                            // Set same width
                            mLeftLayout.getLayoutParams().width,
                            // Set height
                            (int) ( qubitView.getLayoutParams().height * 1.5 * (mCircuit.size() + 1) )
                    )
            );

            // Set new size to CircuitTimeLine layout according to circuit size
            mViewCircuitTimeLine.setLayoutParams(
                    new FrameLayout.LayoutParams(
                            // Set same width
                            qubitView.getWidth() * 21,
                            // Set height
                            (int) ( qubitView.getHeight() * 1.5 * (mCircuit.size() + 1) )
                    )
            );

        }

    }

    private void removeOperatorFromCircuit(DragEvent event) {
        // operatorViewOnDragging
        View viewOnDragging;

        // Return the object view that started drag event
        viewOnDragging = (View) event.getLocalState();

        if ( (int) viewOnDragging.getTag(R.id.operator_location) == OPERATOR_LOCATION_CIRCUIT) {
            mViewCircuitTimeLine.removeView(viewOnDragging);
//                        mFirstCircuitLine.remove(viewOnDragging);

            for (int i = 0; i < mCircuit.size(); i++) {
                if (viewOnDragging.getY() == mCircuit.get(i).get(0).getY()) {

                    mCircuit.get(i).remove(viewOnDragging);

                    break;

                }
            }
        }
    }

    private void addOperatorToCircuit(DragEvent event) {
        // operatorViewOnDragging
        View viewOnDragging;

        // Return the object view that started drag event
        viewOnDragging = (View) event.getLocalState();

        // Create new ImageView to new operator
        ImageView operatorTempImageView = new ImageView(mContext);

        if ( (int) viewOnDragging.getTag(R.id.operator_location) == OPERATOR_LOCATION_TOP) {

            // ***** Setting location attribute
            // Setting X location
            float
                    // X para setar na imagem com uma borda de 10 pixels ao redor da imagem
                    xTemp = event.getX() - ( (viewOnDragging.getWidth() + 20) / 2 ),

                    // Pixels restantes ao obter o menor múltiplo da imagem e mais próximo do valor de xTemp
                    xOverPixels = xTemp % (viewOnDragging.getWidth() + 20),

                    // X que será setado na imagem considerando a margem de 10 pixels
                    xImage = ((int) (xTemp - xOverPixels)) + 10;

            // Setting Y location

            // Indice's nearest line where the operator was dropped
            int indiceNearestLine = 0;

            float
                    // Smaller distance between current Y event and each circuit line
                    smallerDistance = 1000f,
                    currentQubitY,
                    yImage;

            for (int i = 0; i < mCircuit.size(); i++) {

                currentQubitY = mCircuit.get(i).get(0).getY();

                if ( Math.abs(currentQubitY - event.getY()) < smallerDistance) {

                    indiceNearestLine = i;
                    smallerDistance = Math.abs(currentQubitY - event.getY());

                }

            }

            yImage = mCircuit.get(indiceNearestLine).get(0).getY();

            // Verify if exits operator in the position (xImage, yImage)
            boolean freePosition = true;
            for (int i = 0; i < mCircuit.get(indiceNearestLine).size(); i++) {
                if (mCircuit.get(indiceNearestLine).get(i).getX() == xImage) {

                    // Position is not free
                    freePosition = false;

                    break;
                }
            }

//            if (viewOnDragging.getId() == R.id.imageViewOperatorControlledNot) {
//                yImage = yImage + ( (mCircuit.get(indiceNearestLine).get(0).getHeight() - viewOnDragging.getHeight()) / 2 );
//            }

            if (freePosition) {
                // *** Setting attributes to new ImageView operator
                operatorTempImageView.setImageResource((int) viewOnDragging.getTag(R.id.xml_resource_id));
                operatorTempImageView.setTag(R.id.operator_id, viewOnDragging.getTag(R.id.operator_id));
                operatorTempImageView.setTag(R.id.operator_location, OPERATOR_LOCATION_CIRCUIT);
                operatorTempImageView.setLayoutParams(
                        new ConstraintLayout.LayoutParams(
                                viewOnDragging.getWidth(),
                                viewOnDragging.getHeight()
                        )
                );
                operatorTempImageView.setBackgroundColor(getResources().getColor(R.color.colorBackgroundCircuit));
                operatorTempImageView.setX( xImage );
                operatorTempImageView.setY( yImage );

                // Setting touchListener to new ImageView
                operatorTempImageView.setOnTouchListener(new MyOnTouchListener());

//                if (viewOnDragging.getId() == R.id.imageViewOperatorControlledNot) {
//                    ImageView imageViewOperatorStem = new ImageView(mContext);
//                    imageViewOperatorStem.setImageResource(R.drawable.ic_operator_stem);
////                    imageViewOperatorStem.setLayoutParams(
////                            new ConstraintLayout.LayoutParams(
////                                    viewOnDragging.getWidth(),
////                                    viewOnDragging.getHeight()
////                            )
////                    );
//                    imageViewOperatorStem.setX(operatorTempImageView.getX() + ((operatorTempImageView.getWidth() - imageViewOperatorStem.getWidth()) / 2) );
//                    imageViewOperatorStem.setY(mCircuit.get(indiceNearestLine - 1).get(0).getY() +
//                            (mCircuit.get(indiceNearestLine - 1).get(0).getHeight() / 2) );
//
//                    operatorTempImageView.setTag(R.id.operator_stem, imageViewOperatorStem);
//
//                    mViewCircuitTimeLine.addView((ImageView) operatorTempImageView.getTag(R.id.operator_stem));
//                }

                // Add new operatorImageView to ViewCircuitTimeLine
                mViewCircuitTimeLine.addView(operatorTempImageView);

                // Add new operatorImageView to circuit list
                mCircuit.get(indiceNearestLine).add(operatorTempImageView);

                // Sort circuit line
                Collections.sort(mCircuit.get(indiceNearestLine), new Comparator<ImageView>() {
                    @Override
                    public int compare(ImageView imageView1, ImageView imageView2) {

                        return (int) ( imageView1.getX() - imageView2.getX() );

                    }
                });

                // Setting new size to ViewCircuitTimeLine if it height < new imageView height
                if (mViewCircuitTimeLine.getLayoutParams().height < operatorTempImageView.getLayoutParams().height) {

                    mViewCircuitTimeLine.setLayoutParams(
                            new FrameLayout.LayoutParams(
                                    operatorTempImageView.getLayoutParams().width * 20,
                                    (int) (operatorTempImageView.getLayoutParams().height * 1.5)
                            )
                    );

                }
            }
        }
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
