package com.example.quizapp.Adapters;

import static com.example.quizapp.DbQuery.ANSWERED;
import static com.example.quizapp.DbQuery.REVIEW;
import static com.example.quizapp.DbQuery.UNANSWERED;
import static com.example.quizapp.DbQuery.g_quesList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.DbQuery;
import com.example.quizapp.Models.QuestionModel;
import com.example.quizapp.R;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    private List<QuestionModel> questionsList;

    public QuestionsAdapter(List<QuestionModel> questionsList) {
        this.questionsList = questionsList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ques;
        private Button optionA,optionB,optionC,optionD, prevSelectedB;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ques=itemView.findViewById(R.id.tv_question);
            optionA=itemView.findViewById(R.id.optionA);
            optionB=itemView.findViewById(R.id.optionB);
            optionC=itemView.findViewById(R.id.optionC);
            optionD=itemView.findViewById(R.id.optionD);
            prevSelectedB=null;
        }

        private void setData(final int pos){
            ques.setText(questionsList.get(pos).getQuestion());
            optionA.setText(questionsList.get(pos).getOptionA());
            optionB.setText(questionsList.get(pos).getOptionB());
            optionC.setText(questionsList.get(pos).getOptionC());
            optionD.setText(questionsList.get(pos).getOptionD());

            setOptions(optionA,1,pos);
            setOptions(optionB,2,pos);
            setOptions(optionC,3,pos);
            setOptions(optionD,4,pos);


            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionA, 1, pos);
                }
            });

            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionB, 2, pos);
                }
            });

            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionC, 3, pos);
                }
            });

            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionD, 4, pos);
                }
            });
        }

        private void selectOption(Button btn, int option_num, int quesID){
            if(prevSelectedB==null){
                btn.setBackgroundResource(R.drawable.selected_bt);
                DbQuery.g_quesList.get(quesID).setSelectedAns(option_num);
                changeStatus(quesID, ANSWERED);
                prevSelectedB=btn;
            }
            else{
                if(prevSelectedB.getId()==btn.getId()){
                    btn.setBackgroundResource(R.drawable.unselected_bt);
                    DbQuery.g_quesList.get(quesID).setSelectedAns(-1);
                    changeStatus(quesID,UNANSWERED);
                    prevSelectedB=null;
                }
                else {
                     prevSelectedB.setBackgroundResource(R.drawable.unselected_bt);
                     btn.setBackgroundResource(R.drawable.selected_bt);
                     DbQuery.g_quesList.get(quesID).setSelectedAns(option_num);
                     changeStatus(quesID,ANSWERED);
                     prevSelectedB=btn;
                }
            }
        }

        private void changeStatus(int id, int status){
            if(g_quesList.get(id).getStatus()!=REVIEW){
                g_quesList.get(id).setStatus(status);
            }
        }

        private void setOptions(Button btn, int option_num, int quesID){
            if(DbQuery.g_quesList.get(quesID).getSelectedAns()==option_num){
                btn.setBackgroundResource(R.drawable.selected_bt);
            }
            else {
                btn.setBackgroundResource(R.drawable.unselected_bt);
            }
        }
    }

}
