package com.example.schoollife;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class QuestModeActivity extends AppCompatActivity {
    // UI components
    private TextView questionTextView;
    private TextView rankTextView;
    private TextView questTextView;
    private Button answerAButton;
    private Button answerBButton;
    private Button answerCButton;
    private Button restartButton;
    private Button homeButton;
    private CardView questionCardView;
    private CardView resultCardView;
    private TextView resultTextView;
    private ConstraintLayout headerLayout;

    // Game data
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int currentRankIndex = 0;
    private String[] ranks;

    // Quest type
    private String questType;
    private int questId;

    private static final String TAG = "QuestModeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quest);

        // Get quest details from intent
        Intent intent = getIntent();
        questId = intent.getIntExtra("QUEST_ID", 1);
        questType = intent.getStringExtra("QUEST_TYPE");
        String questTitle = intent.getStringExtra("QUEST_TITLE");

        // Եթե questType-ը null է, փորձում ենք որոշել այն questId-ի միջոցով
        if (questType == null) {
            questType = determineQuestTypeFromId(questId);
            Log.d(TAG, "questType was null, determined from ID: " + questType);
        }

        // Debug տեղեկատվություն
        Log.d(TAG, "Quest Type: " + questType);
        Log.d(TAG, "Quest ID: " + questId);
        Log.d(TAG, "Quest Title: " + questTitle);

        Snackbar.make(findViewById(android.R.id.content),
                "Quest Type: " + questType,
                Snackbar.LENGTH_LONG).show();

        // Initialize UI components
        questionTextView = findViewById(R.id.questionTextView);
        rankTextView = findViewById(R.id.rankTextView);
        questTextView = findViewById(R.id.questTextView);
        answerAButton = findViewById(R.id.answerAButton);
        answerBButton = findViewById(R.id.answerBButton);
        answerCButton = findViewById(R.id.answerCButton);
        questionCardView = findViewById(R.id.questionCardView);
        resultCardView = findViewById(R.id.resultCardView);
        resultTextView = findViewById(R.id.resultTextView);
        headerLayout = findViewById(R.id.headerLayout);

        // Set quest title
        if (questTitle != null) {
            questTextView.setText(questTitle);
        } else {
            // Եթե questTitle-ը null է, օգտագործում ենք նրա փոխարեն questType-ը
            questTextView.setText(getQuestTitleFromType(questType));
        }

        // Set up button click listeners
        answerAButton.setOnClickListener(v -> processAnswer(0));
        answerBButton.setOnClickListener(v -> processAnswer(1));
        answerCButton.setOnClickListener(v -> processAnswer(2));
        headerLayout.setOnClickListener(v -> goToHomePage());

        // Initialize game data based on quest type
        initializeQuestData();
        startQuest();
    }

    /**
     * Որոշում է quest-ի տիպը՝ ելնելով ID-ից
     * Այս մեթոդը կարող եք հարմարեցնել ձեր questId-ների և questType-երի համար
     */
    private String determineQuestTypeFromId(int questId) {
        // Հիմնվելով questId-ի վրա, որոշում ենք quest-ի տիպը
        // Այստեղ կարող եք հարմարեցնել ID-ների համապատասխանությունը տիպերին
        switch(questId) {
            case 1:
                return "MILITARY";
            case 2:
                return "FOOTBALL";
            case 3:
                return "HIKING";
            case 4:
                return "SCHOOL";
            case 5:
                return "LOVE";
            default:
                return "MILITARY"; // Նախնական տարբերակ
        }
    }

    /**
     * Ստանում է Quest-ի վերնագիրը՝ հիմնվելով նրա տիպի վրա
     */
    private String getQuestTitleFromType(String questType) {
        if ("MILITARY".equals(questType)) {
            return "Military Quest";
        } else if ("FOOTBALL".equals(questType)) {
            return "Football Quest";
        } else if ("HIKING".equals(questType)) {
            return "Hiking Quest";
        } else if ("SCHOOL".equals(questType)) {
            return "School Quest";
        } else if ("LOVE".equals(questType)) {
            return "Love Quest";
        } else {
            return "Unknown Quest";
        }
    }

    private void initializeQuestData() {
        questions = new ArrayList<>();

        Log.d(TAG, "Initializing quest data for questType: " + questType);

        // Օգտագործում ենք equals մեթոդը String-ի համեմատման համար
        if ("MILITARY".equals(questType)) {
            initializeMilitaryQuest();
        } else if ("FOOTBALL".equals(questType)) {
            initializeFootballQuest();
        } else if ("HIKING".equals(questType)) {
            initializeHikingQuest();
        } else if ("SCHOOL".equals(questType)) {
            initializeSchoolQuest();
        } else if ("LOVE".equals(questType)) {
            initializeLoveQuest();
        } else {
            // Եթե տիպը հայտնի չէ, օգտագործում ենք default տարբերակը
            Log.d(TAG, "Unknown quest type: " + questType + ", defaulting to MILITARY");
            initializeMilitaryQuest();
        }
    }
    private void initializeMilitaryQuest() {
        Log.d(TAG, "Initializing MILITARY quest");

        // Set military ranks
        ranks = new String[]{"Private", "Corporal", "Junior Sergeant", "Sergeant", "Senior Sergeant",
                "Warrant Officer", "Non-commissioned Officer", "Lieutenant", "Senior Lieutenant"};

        // Military-themed questions
        questions.add(new Question(
                "You have discovered enemy positions during a combat mission, what will you do?",
                new String[]{
                        "Report to the commander immediately and await instructions",
                        "Begin reconnaissance without reporting",
                        "Attack independently, using the element of surprise"
                },
                new int[]{1, 0, -1}
        ));
        questions.add(new Question(
                "Your comrade is injured during combat, what will you do?",
                new String[]{
                        "Stop the combat and immediately try to help him",
                        "Ensure safety, then provide first aid and report",
                        "Continue the combat with the intention of providing assistance later"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "You noticed that one of your unit members has stolen ammunition, what will you do?",
                new String[]{
                        "Report to the commander",
                        "Talk to him personally and offer to return it",
                        "Ignore it, avoiding conflict"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "During combat duty, your partner asks you to replace him because he is feeling unwell, what will you do?",
                new String[]{
                        "Replace him and then report the situation to the commander",
                        "Replace him, but not report",
                        "Refuse, citing a violation of the regulations"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "The commander gives you a task that you think is dangerous and poorly planned, what will you do?",
                new String[]{
                        "Carry out the task without asking questions",
                        "Refuse to carry out the task",
                        "Present your concerns and suggest alternative solutions"
                },
                new int[]{0, -1, 1}
        ));

        questions.add(new Question(
                "During military operations, you discover civilians who may be in danger, what will you do?",
                new String[]{
                        "Report to the command and await instructions",
                        "Continue the mission, ignoring the civilians",
                        "Try to evacuate them yourself"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "You notice that your comrade often drinks alcohol before duty, what will you do?",
                new String[]{
                        "Talk to him about the problem and offer help",
                        "Report it to higher command",
                        "Ignore it as long as it does not interfere with his service"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "During a combat mission, you receive conflicting orders from different commanders, what will you do?",
                new String[]{
                        "Carry out the first order you receive",
                        "Report the situation to higher command and wait for clarification",
                        "Choose the order that you think is more correct"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "During combat operations, you have discovered a wounded enemy soldier, what will you do?",
                new String[]{
                        "Report and provide first aid",
                        "Ignore and continue the mission",
                        "Take a prisoner, but will not provide assistance"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "Your commander makes a decision that you disagree with, but it doesn't directly endanger anyone, what will you do?",
                new String[]{
                        "Follow orders, but later express your opinion in a private conversation",
                        "Openly criticize the decision in front of the other soldiers",
                        "Refuse to carry out the order"
                },
                new int[]{1, -1, -1}
        ));

        // Added 5 more questions to reach a total of 15
        questions.add(new Question(
                "Your unit is on a reconnaissance mission and you spot an ambush ahead. What will you do?",
                new String[]{
                        "Signal quietly to your unit and retreat strategically",
                        "Try to outflank the ambush on your own",
                        "Rush forward to engage the enemy directly"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "During training, you notice a fellow soldier struggling with a particular skill. What will you do?",
                new String[]{
                        "Offer to help them practice after hours",
                        "Mention it to the instructor privately",
                        "Ignore it, their weakness isn't your problem"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You're assigned to guard duty but are feeling extremely tired due to previous duties. What will you do?",
                new String[]{
                        "Request a replacement and explain your situation",
                        "Consume energy drinks to stay awake",
                        "Take short naps during your shift when no one is watching"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "Your unit has been given limited rations that must last for several days. You notice a comrade taking more than their share. What will you do?",
                new String[]{
                        "Confront them privately about the importance of fair distribution",
                        "Report them to the commanding officer immediately",
                        "Take extra for yourself as well to compensate"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "During a military parade, you notice your uniform has a visible defect. The parade starts in 5 minutes. What will you do?",
                new String[]{
                        "Fix it quickly with available means and participate",
                        "Inform your superior and request to be excused",
                        "Participate anyway and hope no one notices"
                },
                new int[]{1, 0, -1}
        ));
    }

    private void initializeFootballQuest() {
        // Football team positions
        ranks = new String[]{"Bench Player", "Substitute", "Regular Player", "Key Player", "Star Player",
                "Team Captain", "Club Legend"};

        // Football-themed questions
        questions.add(new Question(
                "Your team is losing 0-1, and the coach puts you in for the last 15 minutes. What's your approach?",
                new String[]{
                        "Play cautiously to avoid mistakes",
                        "Take risks and try to create chances",
                        "Focus on defending to prevent conceding more goals"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "You see your teammate arguing with the referee after a decision. What do you do?",
                new String[]{
                        "Join in to support your teammate",
                        "Pull your teammate away to avoid a yellow card",
                        "Stay out of it completely"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "The coach has benched you for three games in a row. How do you respond?",
                new String[]{
                        "Request a meeting to discuss your performance",
                        "Work harder in training to prove yourself",
                        "Complain to other teammates about unfair treatment"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "Your team has a penalty kick in the final minute. The designated penalty taker isn't confident. What do you do?",
                new String[]{
                        "Step up and offer to take it yourself",
                        "Encourage them and offer advice",
                        "Let the coach decide who takes it"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You notice a key opposition player is injured but playing through pain. What do you do?",
                new String[]{
                        "Target them to exploit their weakness",
                        "Play normally without considering their injury",
                        "Avoid challenging them too hard out of sportsmanship"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "Your team has a crucial match but you feel slightly injured. What do you do?",
                new String[]{
                        "Tell the coach and accept their decision",
                        "Hide the injury to ensure you play",
                        "Withdraw yourself from the match"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "A younger teammate is struggling with confidence. How do you help?",
                new String[]{
                        "Give them constant advice during matches",
                        "Spend extra time after training to help them",
                        "Let them figure it out themselves - it builds character"
                },
                new int[]{0, 1, -1}
        ));

        // Added 8 more questions to reach a total of 15
        questions.add(new Question(
                "Your team is winning 3-0 and the coach instructs you to take it easy. What do you do?",
                new String[]{
                        "Keep playing intensely to score more goals",
                        "Maintain possession and control the game tempo",
                        "Show off with fancy skills and tricks"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "A teammate makes a mistake that leads to the opposition scoring. How do you react?",
                new String[]{
                        "Criticize them immediately to ensure they don't repeat it",
                        "Offer encouragement and focus on the next play",
                        "Blame them when talking to other teammates"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "You receive a lucrative offer from a rival team. How do you handle it?",
                new String[]{
                        "Discuss it openly with your current coach and management",
                        "Keep it private while you consider your options",
                        "Use it as leverage to demand better conditions from your current team"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "During an important match, you notice your direct opponent has a specific weakness. What do you do?",
                new String[]{
                        "Focus exclusively on exploiting that weakness",
                        "Adapt your play to take advantage while maintaining team strategy",
                        "Ignore it and stick rigidly to the pre-match tactics"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "Your team has qualified for a major tournament but you're not selected as a starter. How do you respond?",
                new String[]{
                        "Accept the decision and be ready when called upon",
                        "Demand an explanation from the coach",
                        "Show your frustration openly during training"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You witness a teammate violating team rules (drinking before a match, breaking curfew). What do you do?",
                new String[]{
                        "Talk to them privately about their behavior",
                        "Report them directly to the coach",
                        "Join them in breaking the rules"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "The team is implementing a new playing style that doesn't suit your strengths. How do you adapt?",
                new String[]{
                        "Work extra hours to improve the required skills",
                        "Suggest modifications that might work better for everyone",
                        "Stick to playing your own way regardless"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "During a close match, you have an opportunity to fake an injury to win a free kick in a dangerous position. What do you do?",
                new String[]{
                        "Play honestly and stay on your feet",
                        "Go down if there was some contact, but don't exaggerate",
                        "Dive dramatically to ensure the referee awards the free kick"
                },
                new int[]{1, 0, -1}
        ));
    }

    private void initializeHikingQuest() {
        // Hiking expertise levels
        ranks = new String[]{"Beginner", "Novice Hiker", "Intermediate", "Advanced", "Expert",
                "Trail Master", "Mountaineer"};

        // Hiking-themed questions
        questions.add(new Question(
                "You're leading a group hike when weather conditions suddenly worsen. What do you do?",
                new String[]{
                        "Continue as planned but move faster",
                        "Assess conditions and find the safest route back",
                        "Take shelter immediately and wait it out"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "A member of your hiking group is showing signs of fatigue. How do you handle this?",
                new String[]{
                        "Tell them to push through - you're almost there",
                        "Adjust the pace for the entire group",
                        "Split the group and continue at different paces"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "You come across a trail that's not on your map. Do you:",
                new String[]{
                        "Explore it to see where it leads",
                        "Stick to your planned route",
                        "Take a quick look but return if it diverts too much"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "You notice illegal trash dumping in a protected area. What's your response?",
                new String[]{
                        "Clean up what you can and report it to authorities",
                        "Take photos as evidence but leave the cleanup to officials",
                        "Ignore it - it's not your responsibility"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "Your water supply is running low with several hours of hiking left. What do you do?",
                new String[]{
                        "Ration your remaining water carefully",
                        "Look for a natural water source to refill and purify",
                        "Turn back immediately to avoid dehydration"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "You encounter wildlife on the trail that appears threatening. Your reaction is to:",
                new String[]{
                        "Slowly back away while maintaining visual contact",
                        "Make yourself look bigger and make noise",
                        "Run away as quickly as possible"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "A storm is forecast for later in the day. Do you:",
                new String[]{
                        "Change your plans completely for a shorter hike",
                        "Adjust your timeline to finish before the storm",
                        "Bring rain gear and continue with your original plan"
                },
                new int[]{0, 1, -1}
        ));

        // Added 8 more questions to reach a total of 15
        questions.add(new Question(
                "You find a rare plant species during your hike. What do you do?",
                new String[]{
                        "Take a photo and note the location for reporting to conservation authorities",
                        "Take a small sample for identification purposes",
                        "Collect it as a souvenir"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "Your hiking partner wants to take a shortcut that goes off-trail through sensitive vegetation. Your response is:",
                new String[]{
                        "Refuse and explain the environmental impact",
                        "Suggest finding an alternative route that's still efficient",
                        "Follow them to save time"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You realize you've accidentally taken a wrong turn and have been hiking in the wrong direction. What do you do?",
                new String[]{
                        "Immediately retrace your steps back to the last known correct position",
                        "Consult your map/GPS and find the most efficient route to get back on track",
                        "Continue hoping you'll eventually connect with the right trail"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "You're approached by another hiker who is clearly unprepared (inappropriate clothing, little water). They ask to join your group. What do you do?",
                new String[]{
                        "Welcome them but share your supplies and advice",
                        "Allow them to join but suggest they turn back at the next trail junction",
                        "Refuse outright - their poor planning isn't your problem"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You're planning a challenging hike and a less experienced friend wants to join. How do you respond?",
                new String[]{
                        "Suggest they train for a few months and join you on a future hike",
                        "Choose an easier trail that you can both enjoy safely",
                        "Let them come along without warning them about the difficulty"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "During a multi-day hike, you notice your food supply won't last as long as planned. What's your strategy?",
                new String[]{
                        "Ration carefully and adjust your caloric intake",
                        "Cut the trip short and return to resupply",
                        "Continue as planned and hope you find food along the way"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You notice another hiking group behaving carelessly (playing loud music, leaving trash). What do you do?",
                new String[]{
                        "Politely approach them and discuss trail etiquette",
                        "Report them to park rangers if possible",
                        "Confront them aggressively about their behavior"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You find a piece of expensive hiking gear on the trail with no owner in sight. What do you do?",
                new String[]{
                        "Leave it prominently where it can be seen by the owner backtracking",
                        "Take it to the trailhead or visitor center and report it",
                        "Keep it since the owner was careless"
                },
                new int[]{0, 1, -1}
        ));
    }

    private void initializeSchoolQuest() {
        // School achievement levels
        ranks = new String[]{"Struggling Student", "Average Student", "Good Student", "Honor Student",
                "Top Student", "Class Representative", "Valedictorian"};

        // School-themed questions
        questions.add(new Question(
                "You're assigned to work with a classmate who never contributes equally. What do you do?",
                new String[]{
                        "Do most of the work yourself to ensure good quality",
                        "Talk to them directly about fair division of tasks",
                        "Complain to the teacher about the situation"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "You notice a classmate cheating during an important exam. Your response is to:",
                new String[]{
                        "Mind your own business and focus on your exam",
                        "Report it to the teacher after the exam",
                        "Confront the student during the exam"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "You're struggling with a particular subject. How do you address this?",
                new String[]{
                        "Form a study group with classmates",
                        "Ask the teacher for extra help after school",
                        "Try to get by with minimal effort in that subject"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "There's an opportunity to join a new extracurricular activity, but your schedule is already busy. Do you:",
                new String[]{
                        "Join anyway and try to manage everything",
                        "Evaluate your current commitments and priorities",
                        "Decline immediately without consideration"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "A teacher gives you a lower grade than you think you deserve. Your reaction is to:",
                new String[]{
                        "Accept the grade without question",
                        "Politely request feedback and clarification",
                        "Argue aggressively for a better grade"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "You witness bullying in the hallway. What do you do?",
                new String[]{
                        "Intervene directly to stop it",
                        "Report it to a teacher or counselor",
                        "Keep walking - it's not your problem"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "You have multiple assignments due on the same day. Your approach is to:",
                new String[]{
                        "Start with the easiest one to finish quickly",
                        "Create a schedule and prioritize by importance",
                        "Request extensions for some assignments"
                },
                new int[]{0, 1, -1}
        ));

        // Added 8 more questions to reach a total of 15
        questions.add(new Question(
                "You find out a friend shared answers to homework with classmates. What do you do?",
                new String[]{
                        "Talk to your friend about academic integrity",
                        "Decline if they offer to share with you but otherwise stay out of it",
                        "Accept the answers if offered to you"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You have the opportunity to take either an easy class with a guaranteed good grade or a challenging class that interests you. Which do you choose?",
                new String[]{
                        "The challenging class that will help you grow intellectually",
                        "The easier class that will boost your GPA",
                        "Whichever class your friends are taking"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "During a class discussion, a student makes comments you strongly disagree with. How do you respond?",
                new String[]{
                        "Challenge their view respectfully with counter-arguments",
                        "Remain silent in class but discuss with the teacher later",
                        "Interrupt and tell them they're wrong"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You notice that a classmate is consistently absent and falling behind. What do you do?",
                new String[]{
                        "Reach out to check if they're okay and offer to share notes",
                        "Mention it to a teacher or counselor",
                        "Focus on your own studies - it's not your concern"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "Your best friend asks to copy your homework just before class. How do you respond?",
                new String[]{
                        "Refuse but offer to help them understand the material later",
                        "Let them look at it for general guidance only",
                        "Allow them to copy it completely"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You discover you accidentally plagiarized part of an essay that's already been submitted. What do you do?",
                new String[]{
                        "Immediately inform your teacher and request to resubmit",
                        "Wait to see if it's noticed and only then explain",
                        "Hope nobody notices and say nothing"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "A teacher consistently singles you out for criticism in class. How do you handle it?",
                new String[]{
                        "Request a private meeting to discuss how you can improve",
                        "Ask other students if they've experienced similar treatment",
                        "Complain about the teacher to other staff members"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You're elected to student council. What's your first priority?",
                new String[]{
                        "Address an issue that affects many students",
                        "Implement changes that will look good on your college applications",
                        "Use your position to get special privileges"
                },
                new int[]{1, 0, -1}
        ));
    }

    private void initializeLoveQuest() {
        // Relationship stages
        ranks = new String[]{"Single", "Dating", "Exclusive", "Committed", "Engaged",
                "Married", "Soul Mates"};

        // Relationship-themed questions
        questions.add(new Question(
                "You and your partner disagree on weekend plans. How do you handle this?",
                new String[]{
                        "Insist on your preference since you rarely get your way",
                        "Suggest a compromise that includes elements from both ideas",
                        "Give in completely to keep the peace"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "Your partner forgets an important date (anniversary, birthday). Your reaction is:",
                new String[]{
                        "Express your disappointment calmly and discuss its importance",
                        "Say nothing but feel resentful",
                        "Have a major argument about their thoughtlessness"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You notice your partner texting someone frequently but they haven't mentioned who. Do you:",
                new String[]{
                        "Ask directly who they're communicating with",
                        "Look at their phone when they're not around",
                        "Trust them and don't bring it up"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "Your partner receives a job offer that would require moving to another city. How do you respond?",
                new String[]{
                        "Discuss how this affects both your futures and find a solution together",
                        "Tell them to take it and you'll figure out the details later",
                        "Ask them to decline it because you don't want to move"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "You and your partner have different views on saving versus spending money. Your approach is to:",
                new String[]{
                        "Create a budget together that respects both perspectives",
                        "Keep finances completely separate",
                        "Try to convince them that your approach is better"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "Your partner has been working late frequently. You feel neglected. Do you:",
                new String[]{
                        "Schedule quality time together when they're available",
                        "Express your feelings and listen to their perspective",
                        "Make passive-aggressive comments about their absence"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "Your partner's family doesn't approve of your relationship. How do you handle this?",
                new String[]{
                        "Make extra efforts to win them over",
                        "Support your partner while respecting family boundaries",
                        "Pressure your partner to choose between you and family"
                },
                new int[]{0, 1, -1}
        ));
        questions.add(new Question(
                "Your partner forgets your anniversary. What's your reaction?",
                new String[]{
                        "Use it as an opportunity to plan something special together",
                        "Get angry and start a fight about it",
                        "Feel disappointed but understand people make mistakes"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "You notice your partner has been distant lately. What do you do?",
                new String[]{
                        "Give them space until they're ready to talk",
                        "Gently ask if everything is okay and if they'd like to talk about it",
                        "Assume they're losing interest and become defensive"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "Your partner receives a job offer in another city. How do you respond?",
                new String[]{
                        "Accept whatever decision they make without expressing your feelings",
                        "Tell them they need to choose between the job and your relationship",
                        "Discuss openly how you might make a long-distance relationship work or possibly relocate together"
                },
                new int[]{0, -1, 1}
        ));

        questions.add(new Question(
                "Your partner has a friendship that makes you uncomfortable. What's your approach?",
                new String[]{
                        "Share your concerns calmly and listen to their perspective",
                        "Keep your feelings to yourself to avoid conflict",
                        "Demand they end the friendship immediately"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(
                "How do you handle finances in your relationship?",
                new String[]{
                        "Let the higher earner handle the finances completely",
                        "Create a system that respects both individual needs and shared goals",
                        "Keep separate accounts and split everything exactly 50/50"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "Your partner wants to spend the holiday with their family instead of yours. Your response?",
                new String[]{
                        "Reluctantly agree but feel resentful about it",
                        "Get upset and tell them family should come first - your family",
                        "Suggest alternating holidays or finding a way to celebrate with both families"
                },
                new int[]{0, -1, 1}
        ));

        questions.add(new Question(
                "You and your partner have different love languages. How do you handle this?",
                new String[]{
                        "Try to occasionally remember what matters to them",
                        "Expect them to adjust to your preferred way of expressing love",
                        "Learn about their love language and make consistent efforts to speak it"
                },
                new int[]{0, -1, 1}
        ));

        questions.add(new Question(
                "When making major life decisions together, you tend to:",
                new String[]{
                        "Discuss it briefly but ultimately do what you think is best",
                        "Research options together and make decisions as a team",
                        "Make the decision yourself and inform your partner afterward"
                },
                new int[]{0, 1, -1}
        ));
    }

    private void startQuest() {
        currentQuestionIndex = 0;
        currentRankIndex = ranks.length / 3; // Start in the middle range
        updateRankDisplay();
        displayQuestion(currentQuestionIndex);

        // Initially hide result and restart/home buttons
        resultCardView.setVisibility(View.GONE);

        // Show question and answer buttons
        questionCardView.setVisibility(View.VISIBLE);
        answerAButton.setVisibility(View.VISIBLE);
        answerBButton.setVisibility(View.VISIBLE);
        answerCButton.setVisibility(View.VISIBLE);
    }

    private void displayQuestion(int questionIndex) {
        if (questionIndex < questions.size()) {
            Question currentQuestion = questions.get(questionIndex);
            questionTextView.setText(currentQuestion.getQuestionText());
            answerAButton.setText(currentQuestion.getAnswers()[0]);
            answerBButton.setText(currentQuestion.getAnswers()[1]);
            answerCButton.setText(currentQuestion.getAnswers()[2]);

            // Animation for question
            questionCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        } else {
            endQuest();
        }
    }

    private void processAnswer(int answerIndex) {
        // Check if current index is within bounds
        if (currentQuestionIndex >= questions.size()) {
            endQuest();
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        int rankChange = currentQuestion.getResults()[answerIndex];

        // Change rank based on answer
        currentRankIndex += rankChange;

        // Ensure rank stays within bounds
        if (currentRankIndex < 0) {
            currentRankIndex = 0;
        } else if (currentRankIndex >= ranks.length) {
            currentRankIndex = ranks.length - 1;
        }

        updateRankDisplay();

        // Move to next question
        currentQuestionIndex++;

        // Animation after answering
        questionCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));

        // Display next question after short delay
        questionCardView.postDelayed(() -> displayQuestion(currentQuestionIndex), 500);
    }

    private void updateRankDisplay() {
        rankTextView.setText("Present Situation: " + ranks[currentRankIndex]);
    }

    // Fixed method - now there are two versions: one with no parameters and one with parameters
    private void endQuest() {
        // Calculate score based on current rank
        int score = (int)((float)currentRankIndex / (ranks.length - 1) * 100);
        int totalTasks = questions.size();

        // Call the parameterized version of endQuest
        endQuest(score, totalTasks);
    }

    private void endQuest(int score, int totalTasks) {
        try {
            // Ստեղծում ենք Intent QuestResultActivity-ի համար
            Intent intent = new Intent(QuestModeActivity.this, QuestResultActivity.class);

            // Փոխանցում ենք անհրաժեշտ տվյալները
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL_TASKS", totalTasks);
            intent.putExtra("QUEST_TYPE", questType); // Ենթադրում ենք, որ questType փոփոխականը արդեն հասանելի է

            // Գրանցում ենք գործողությունը լոգերում
            Log.d("EduThinkPlay", "Starting QuestResultActivity with score=" + score +
                    ", totalTasks=" + totalTasks + ", questType=" + questType);

            // Բացում ենք QuestResultActivity-ն
            startActivity(intent);

            // Փակում ենք ընթացիկ Activity-ն (ըստ ցանկության)
            finish();
        } catch (Exception e) {
            Log.e("EduThinkPlay", "Error finishing quest: " + e.getMessage());
            Toast.makeText(this, "Սխալ տեղի ունեցավ արդյունքները ցուցադրելիս", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveQuestProgress() {
        SharedPreferences prefs = getSharedPreferences("QuestProgress", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Calculate progress percentage based on final rank
        int progress = (int)((float)currentRankIndex / (ranks.length - 1) * 100);

        // Save progress for this quest
        editor.putInt("quest_" + questId + "_progress", progress);
        editor.apply();
    }

    private void restartQuest() {
        startQuest();
    }

    private void goToHomePage() {
        // Navigate back to home or quest list
        Intent intent = new Intent(this, QuestListActivity.class);
        startActivity(intent);
        finish();
    }
}