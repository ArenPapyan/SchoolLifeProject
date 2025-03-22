package com.example.schoollife;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestModeActivity extends AppCompatActivity {

    private TextView storyTextView;
    private TextView questionTextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private ImageView storyImageView;

    // Քարտեզ, որը պահում է բոլոր քվեստային հանգույցները
    private Map<String, QuestNode> questNodes;

    // Ընթացիկ հանգույցի ID
    private String currentNodeId;

    // Խաղացողի բնութագրերը/վիճակագրությունը
    private int playerHealth = 100;
    private int playerGold = 0;
    private int playerXP = 0;
    private List<String> playerInventory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quest);

        // Ինիցիալիզացնում ենք UI տարրերը
        storyTextView = findViewById(R.id.story_text);
        questionTextView = findViewById(R.id.question_text);
        option1Button = findViewById(R.id.option1_button);
        option2Button = findViewById(R.id.option2_button);
        option3Button = findViewById(R.id.option3_button);
        option4Button = findViewById(R.id.option4_button);
        storyImageView = findViewById(R.id.story_image);

        // Սահմանում ենք կոճակների գործողությունները
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processChoice(0);
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processChoice(1);
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processChoice(2);
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processChoice(3);
            }
        });

        // Ստեղծում ենք քվեստային հանգույցները
        initializeQuestNodes();

        // Սկսում ենք առաջին հանգույցից
        currentNodeId = "start";
        displayCurrentNode();
    }

    private void initializeQuestNodes() {
        questNodes = new HashMap<>();

        // Ստեղծում ենք մի քանի հանգույցներ պատմության համար

        // Սկզբնական հանգույց
        QuestNode startNode = new QuestNode();
        startNode.storyText = "Դուք արթնանում եք մի մութ անտառում: Ձեր շուրջը լսվում են տարօրինակ ձայներ: Դուք չեք հիշում, թե ինչպես եք հայտնվել այստեղ:";
        startNode.question = "Ի՞նչ եք անում:";
        startNode.options = new String[]{
                "Փորձում եք հիշել, թե ինչ է պատահել",
                "Նայում եք շուրջը՝ փորձելով գտնել որևէ արահետ",
                "Ձայն եք տալիս՝ հուսալով, որ ինչ-որ մեկը կլսի",
                "Որոնում եք որևէ զենք կամ գործիք"
        };
        startNode.nextNodeIds = new String[]{"memory", "path", "call", "search"};
        startNode.imageResourceId = R.drawable.forest_dark; // Պետք է ունենալ այս նկարը ռեսուրսներում
        questNodes.put("start", startNode);

        // Մի քանի հաջորդ հանգույցներ

        // Հիշողության հանգույց
        QuestNode memoryNode = new QuestNode();
        memoryNode.storyText = "Դուք փորձում եք կենտրոնանալ: Ինչ-որ պատկերներ են գալիս ձեր մտքին: Հիշում եք, որ եկել էիք այս անտառը հերբեր հավաքելու համար, բայց ինչ-որ բան կարծես սխալ գնաց: Ձեր գլուխը ցավում է:";
        memoryNode.question = "Ի՞նչ եք անում հիմա:";
        memoryNode.options = new String[]{
                "Շարունակում եք փորձել հիշել՝ հուսալով, որ կարևոր մանրամասներ կվերադառնան",
                "Փորձում եք գտնել ձեր հերբերի պայուսակը",
                "Հետևում եք ձեր ենթադրյալ ճանապարհին դեպի անտառ",
                "Նստում եք հանգստանալու, քանի որ ձեր գլուխը ցավում է"
        };
        memoryNode.nextNodeIds = new String[]{"deep_memory", "herb_bag", "forest_path", "rest"};
        memoryNode.imageResourceId = R.drawable.memory_flash; // Պետք է ունենալ այս նկարը ռեսուրսներում
        questNodes.put("memory", memoryNode);

        // Արահետի հանգույց
        QuestNode pathNode = new QuestNode();
        pathNode.storyText = "Շրջելով, դուք նկատում եք մի նեղ արահետ ծառերի միջով: Այն կարծես օգտագործված է, բայց ոչ վերջերս: Հեռվից լսվում է ջրի ձայն:";
        pathNode.question = "Ո՞ր ուղղությամբ եք գնում:";
        pathNode.options = new String[]{
                "Հետևում եք արահետին",
                "Շարժվում եք դեպի ջրի ձայնը",
                "Մնում եք տեղում և սպասում օգնության",
                "Փորձում եք մագլցել մոտակա ծառը՝ ավելին տեսնելու համար"
        };
        pathNode.nextNodeIds = new String[]{"follow_path", "water_source", "wait", "climb_tree"};
        pathNode.imageResourceId = R.drawable.forest_path; // Պետք է ունենալ այս նկարը ռեսուրսներում
        questNodes.put("path", pathNode);

        // Այլ հանգույցներ... Իրական ծրագրում այստեղ կավելացնեք բոլոր հանգույցները

        // Օրինակ՝ հերբերի պայուսակի հանգույց
        QuestNode herbBagNode = new QuestNode();
        herbBagNode.storyText = "Որոնելով շուրջը, դուք գտնում եք ձեր պայուսակը: Այն մասամբ բաց է, և մի քանի հերբեր ցրված են: Ինչ-որ բան կարծես թափել է պարունակությունը:";
        herbBagNode.question = "Ի՞նչ եք անում:";
        herbBagNode.options = new String[]{
                "Հավաքում եք հերբերը և ուսումնասիրում պայուսակը",
                "Փնտրում եք հետքեր շրջակայքում",
                "Ստուգում եք՝ արդյոք ինչ-որ անձնական իրեր պակասում են",
                "Օգտագործում եք հերբերը ձեր գլխացավը մեղմելու համար"
        };
        herbBagNode.nextNodeIds = new String[]{"examine_bag", "search_tracks", "check_items", "use_herbs"};
        herbBagNode.imageResourceId = R.drawable.herb_bag; // Պետք է ունենալ այս նկարը ռեսուրսներում
        questNodes.put("herb_bag", herbBagNode);
    }

    private void displayCurrentNode() {
        QuestNode currentNode = questNodes.get(currentNodeId);

        if (currentNode != null) {
            // Ցուցադրում ենք պատմությունը և հարցը
            storyTextView.setText(currentNode.storyText);
            questionTextView.setText(currentNode.question);

            // Ցուցադրում ենք ընտրության տարբերակները
            option1Button.setText(currentNode.options[0]);
            option2Button.setText(currentNode.options[1]);

            // Եթե կան 3-րդ և 4-րդ տարբերակներ, ցուցադրում ենք դրանք, հակառակ դեպքում թաքցնում ենք կոճակները
            if (currentNode.options.length > 2) {
                option3Button.setVisibility(View.VISIBLE);
                option3Button.setText(currentNode.options[2]);
            } else {
                option3Button.setVisibility(View.GONE);
            }

            if (currentNode.options.length > 3) {
                option4Button.setVisibility(View.VISIBLE);
                option4Button.setText(currentNode.options[3]);
            } else {
                option4Button.setVisibility(View.GONE);
            }

            // Ցուցադրում ենք նկարը, եթե այն սահմանված է
            if (currentNode.imageResourceId != 0) {
                storyImageView.setVisibility(View.VISIBLE);
                storyImageView.setImageResource(currentNode.imageResourceId);
            } else {
                storyImageView.setVisibility(View.GONE);
            }
        }
    }

    private void processChoice(int choiceIndex) {
        QuestNode currentNode = questNodes.get(currentNodeId);

        if (currentNode != null && choiceIndex < currentNode.nextNodeIds.length) {
            // Ստանում ենք հաջորդ հանգույցի ID-ն ընտրված տարբերակի համար
            String nextNodeId = currentNode.nextNodeIds[choiceIndex];

            // Թարմացնում ենք ընթացիկ հանգույցը
            currentNodeId = nextNodeId;

            // Ցուցադրում ենք նոր հանգույցը
            displayCurrentNode();

            // Այստեղ կարող եք նաև կատարել խաղացողի բնութագրերի փոփոխություններ,
            // կախված նրանց ընտրությունից
        }
    }

    // Քվեստային հանգույցի դաս
    private class QuestNode {
        public String storyText;        // Պատմության տեքստը
        public String question;         // Հարցը
        public String[] options;        // Ընտրության տարբերակները
        public String[] nextNodeIds;    // Հաջորդ հանգույցների ID-ները, համապատասխանելով տարբերակներին
        public int imageResourceId = 0; // Պատկերի ռեսուրսի ID

        // Կարող եք ավելացնել լրացուցիչ դաշտեր՝ էֆեկտներ, պահանջներ և այլն
        public Map<String, Integer> statChanges; // Բնութագրերի փոփոխություններ (օր․՝ "health", -10)
        public Map<String, Boolean> requirements; // Պահանջներ այս հանգույցը ցուցադրելու համար
    }
}