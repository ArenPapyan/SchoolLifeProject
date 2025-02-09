package com.example.schoollife.QuestMode;
import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    private List<Quest> questList = new ArrayList<>();

    public void addQuest(Quest quest) {
        questList.add(quest);
    }

    public List<Quest> getQuests() {
        return questList;
    }
}
