package gov.jbb.missaonascente.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.EnergyController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.model.Alternative;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.model.Question;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Question question;
    private Context context;
    private TextView itemQuestion;
    private Explorer explorer = new Explorer();
    private LoginController loginController = new LoginController();
    private MainScreenActivity mainScreenActivity;
    private EnergyController energyController;
    private Integer energyQuestion = 10;
    
    public QuestionAdapter(Context context, Question question){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.question = question;
        this.context = context;
        explorer = loginController.getExplorer();
        energyController = new EnergyController(context);
        mainScreenActivity = new MainScreenActivity();
    }

    @Override
    public int getCount() {
        return question.getAlternativeQuantity();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View listItem;
        listItem = inflater.inflate(R.layout.question_adapter,null);
        itemQuestion = (TextView) listItem.findViewById(R.id.choice);
        final int item =0;

        final Holder holder = new Holder();
        holder.alternativeText = (TextView) listItem.findViewById(R.id.choice);
        final List<Alternative> alternativeList = question.getAlternativeList();
        holder.alternativeText.setText(alternativeList.get(position).getAlternativeDescription());

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = alternativeList.get(position).getAlternativeLetter();
                String correctAnswer = question.getCorrectAnswer();

                if(userAnswer.equals(correctAnswer)){
                    int explorerEnergy;
                    int explorerEnergyText;
                    holder.alternativeText.setBackgroundColor(Color.parseColor("#32CD32"));
                    Toast.makeText(context, "Parabéns, você acertou!", Toast.LENGTH_SHORT).show();
                    explorerEnergy = energyController.getExplorer().getEnergy() ;
                    explorerEnergyText= explorerEnergy+energyQuestion;
                    //explorer.setEnergy(explorerEnergyText);
                    energyController.setExplorerEnergyInDataBase(energyController.getExplorer().getEnergy(),energyQuestion);

                    Log.i("QUEST ENERGY explorer",""+explorerEnergy);
                    Log.i("QUEST ENERGY explorer2",""+energyController.getExplorer().getEnergy());
                    Log.i("QUEST ENERGY exp+quest",""+explorerEnergyText);
                    Log.i("QUEST ENERGY EXP",energyController.getExplorer().getEmail());

                    //mainScreenActivity.modifyEnergy();
                    //mainScreenActivity.updateEnergyProgress();
                }else{
                    holder.alternativeText.setBackgroundColor(Color.parseColor("#FF0000"));
                    Toast.makeText(context, "Parabéns, você errou!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        return listItem;
    }

    private class Holder{
        private TextView alternativeText;
    }
}