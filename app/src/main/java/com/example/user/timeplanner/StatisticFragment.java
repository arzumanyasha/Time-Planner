package com.example.user.timeplanner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Realm mRealm;

    //@BindView(R.id.textView1)
    TextView workTextView;

    //@BindView(R.id.textView2)
    TextView physTextView;

    //@BindView(R.id.textView3)
    TextView mealTextView;

    //@BindView(R.id.textView4)
    TextView houseworkTextView;

    //@BindView(R.id.textView5)
    TextView learningTextView;

    //@BindView(R.id.textView6)
    TextView restTextView;

    //@BindView(R.id.textView7)
    TextView transportTextView;

    private ArrayList<Task> worktasks = new ArrayList<>();
    private ArrayList<Task> phystasks = new ArrayList<>();
    private ArrayList<Task> learningtasks = new ArrayList<>();
    private ArrayList<Task> resttasks = new ArrayList<>();
    private ArrayList<Task> mealtasks = new ArrayList<>();
    private ArrayList<Task> houseworktasks = new ArrayList<>();
    private ArrayList<Task> transporttasks = new ArrayList<>();

    private long workCounter = 0;
    private long physCounter = 0;
    private long restCounter = 0;
    private long houseworkCounter = 0;
    private long transportCounter = 0;
    private long learningCounter = 0;
    private long mealCounter = 0;
    private OnFragmentInteractionListener mListener;

    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        workTextView = (TextView) view.findViewById(R.id.textView);
        physTextView = (TextView) view.findViewById(R.id.textView2);
        mealTextView = (TextView) view.findViewById(R.id.textView3);
        houseworkTextView = (TextView) view.findViewById(R.id.textView4);
        learningTextView = (TextView) view.findViewById(R.id.textView5);
        restTextView = (TextView) view.findViewById(R.id.textView6);
        transportTextView = (TextView) view.findViewById(R.id.textView7);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Realm.init(getActivity());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        mRealm = Realm.getDefaultInstance();
        RealmResults<Task> workTasks = mRealm.where(Task.class).equalTo("taskCategoryId", 0).findAll();
        worktasks.addAll(mRealm.copyFromRealm(workTasks));
        if(worktasks.size()>0){
            for(int i = 0; i<worktasks.size(); i++){
                workCounter += (worktasks.get(i).getTaskEndTime() - worktasks.get(i).getTaskStartTime())/3600000;
            }
        }
        workTextView.setText("Hours: " + workCounter);

        RealmResults<Task> physTasks = mRealm.where(Task.class).equalTo("taskCategoryId", 1).findAll();
        phystasks.addAll(mRealm.copyFromRealm(physTasks));
        if(phystasks.size()>0){
            for(int i = 0; i<phystasks.size(); i++){
                physCounter += (phystasks.get(i).getTaskEndTime() - phystasks.get(i).getTaskStartTime())/3600000;
            }
        }
        physTextView.setText("Hours: " + physCounter);

        RealmResults<Task> restTasks = mRealm.where(Task.class).equalTo("taskCategoryId", 2).findAll();
        resttasks.addAll(mRealm.copyFromRealm(restTasks));
        if(resttasks.size()>0){
            for(int i = 0; i<resttasks.size(); i++){
                restCounter += (resttasks.get(i).getTaskEndTime() - resttasks.get(i).getTaskStartTime())/3600000;
            }
        }
        restTextView.setText("Hours: " + restCounter);

        RealmResults<Task> houseworkTasks = mRealm.where(Task.class).equalTo("taskCategoryId", 3).findAll();
        houseworktasks.addAll(mRealm.copyFromRealm(houseworkTasks));
        if(houseworktasks.size()>0){
            for(int i = 0; i<houseworktasks.size(); i++){
                houseworkCounter += (houseworktasks.get(i).getTaskEndTime() - houseworktasks.get(i).getTaskStartTime())/3600000;
            }
        }
        houseworkTextView.setText("Hours: " + houseworkCounter);

        RealmResults<Task> mealTasks = mRealm.where(Task.class).equalTo("taskCategoryId", 4).findAll();
        mealtasks.addAll(mRealm.copyFromRealm(mealTasks));
        if(mealtasks.size()>0){
            for(int i = 0; i<mealtasks.size(); i++){
                mealCounter += (mealtasks.get(i).getTaskEndTime() - mealtasks.get(i).getTaskStartTime())/3600000;
            }
        }
        mealTextView.setText("Hours: " + mealCounter);

        RealmResults<Task> transportTasks = mRealm.where(Task.class).equalTo("taskCategoryId", 5).findAll();
        transporttasks.addAll(mRealm.copyFromRealm(transportTasks));
        if(transporttasks.size()>0){
            for(int i = 0; i<transporttasks.size(); i++){
                transportCounter += (transporttasks.get(i).getTaskEndTime() - transporttasks.get(i).getTaskStartTime())/3600000;
            }
        }
        transportTextView.setText("Hours: " + transportCounter);

        RealmResults<Task> learningTasks = mRealm.where(Task.class).equalTo("taskCategoryId", 6).findAll();
        learningtasks.addAll(mRealm.copyFromRealm(learningTasks));
        if(learningtasks.size()>0){
            for(int i = 0; i<learningtasks.size(); i++){
                learningCounter += (learningtasks.get(i).getTaskEndTime() - learningtasks.get(i).getTaskStartTime())/3600000;
            }
        }
        learningTextView.setText("Hours: " + learningCounter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "Statistic fragment attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
